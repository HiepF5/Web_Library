package com.example.weblibrary.service.impl;

import com.example.weblibrary.Repository.BookRepository;
import com.example.weblibrary.Repository.CategoryRepository;
import com.example.weblibrary.cloudinary.CloudinaryService;
import com.example.weblibrary.entity.Book;
import com.example.weblibrary.entity.dto.BookDto;
import com.example.weblibrary.exception.ObjectExistedException;
import com.example.weblibrary.exception.UserNotFoundException;
import com.example.weblibrary.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.IOException;

@Service
public class BookServiceImpl implements BookService {
	@Autowired
	private BookRepository bookRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private CloudinaryService cloudinaryService;

	@Override
	public Book create(BookDto bookDto) throws ObjectExistedException, IOException {

		if (!ObjectUtils.isEmpty(bookRepository.findBytitle(bookDto.getBookName()))) {
			throw new ObjectExistedException("Product existed!");
		}
		Book book = new Book();
		book.setTitle(bookDto.getBookName());
		book.setPageNum(bookDto.getPageNum());
		book.setImage(cloudinaryService.uploadImage(bookDto.getImage()));
		book.setAuthor(bookDto.getAuthor());
		book.setReleaseDate(bookDto.getReleaseDate());
		book.setCategory(categoryRepository.findByCategoryName(bookDto.getCategory()));
		book.setDescription(bookDto.getDescription());
		return bookRepository.save(book);
	}

	@Override
	public Page<Book> searchByBookName(int size, int page, String domain, String dir, String keyword) {
		Sort sort = Sort.by(domain);
		if (dir.equals("asc")) {
			sort.ascending();
		} else {
			if (dir.equals("desc")) {
				sort.descending();
			}
		}
		Pageable pageable1 = PageRequest.of(page - 1, size, sort);
		return bookRepository.searchByBookName(keyword, pageable1);
	}

	@Override
	public Book findById(int id) throws UserNotFoundException {
		return bookRepository.findById(id).orElseThrow(() -> new UserNotFoundException("No product have id " + id));
	}

	@Override
	public Book update(BookDto bookDto, int id) throws ObjectExistedException, UserNotFoundException, IOException {
		// TODO Auto-generated method stub
		Book book = bookRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("No product have id " + id));
		if (!ObjectUtils.isEmpty(bookRepository.findBytitleExceptId(bookDto.getBookName(), id))) {
			throw new ObjectExistedException("Book existed!");
		}
		if (bookDto.getBookName() != null) {
			book.setTitle(bookDto.getBookName());
		}
		if (bookDto.getPageNum() != null) {
			book.setPageNum(bookDto.getPageNum());
		}
		if (bookDto.getAuthor() != null) {
			book.setAuthor(bookDto.getAuthor());
		}
		if (bookDto.getCategory() != null) {
			book.setCategory(categoryRepository.findByCategoryName(bookDto.getCategory()));
		}

		if (!ObjectUtils.isEmpty(bookDto.getImage())) {
			book.setImage(cloudinaryService.uploadImage(bookDto.getImage()));
		}
		if (bookDto.getReleaseDate() != null) {
			book.setReleaseDate(bookDto.getReleaseDate());
		}
		book.setDescription(bookDto.getDescription());
		return bookRepository.save(book);
	}

	@Override
	public int getSold(int id) {
		return bookRepository.statitics(id);
	}

	@Override
	public void delete(int id) throws UserNotFoundException {
		Book book = bookRepository.findById(id).orElseThrow(() -> new UserNotFoundException("No Produt have id " + id));
		bookRepository.deleteById(id);

	}

	@Override
	public int computeStar(int bookID) {
		return bookRepository.BookStart(bookID);
	}

	@Override
	public Page<Book> getSameCategory(int categoryID, int page, int size) {
		// TODO Auto-generated method stub
		Pageable pageable = PageRequest.of(page - 1, size);
		return bookRepository.getSameCategory(categoryID, pageable);
	}

}
