package com.example.weblibrary.controler;

import com.example.weblibrary.entity.Book;
import com.example.weblibrary.entity.dto.BookDto;
import com.example.weblibrary.entity.dto.BookView;
import com.example.weblibrary.entity.dto.CartMessage;
import com.example.weblibrary.exception.ObjectExistedException;
import com.example.weblibrary.exception.UserNotFoundException;
import com.example.weblibrary.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@RestController
public class BookControler {
	@Autowired
	private BookService bookService;

	@PostMapping("/book")
	public ResponseEntity<?> addBook(@RequestParam(name = "bookTitle") String bookTitle,
			@RequestParam(name = "image") MultipartFile file, @RequestParam("author") String Author,
			@RequestParam("pageNum") String pageNum, @RequestParam("category") String category,
			@RequestParam("publicationDate") String publicationDate, @RequestParam("Description") String description)
			throws ObjectExistedException, IOException, ParseException {

		BookDto bookDto = new BookDto();
		bookDto.setBookName(bookTitle);
		bookDto.setCategory(category);
		bookDto.setPageNum(pageNum);
		bookDto.setAuthor(Author);
		bookDto.setImage(file);
		bookDto.setReleaseDate(publicationDate);
		bookDto.setDescription(description);
		return new ResponseEntity<>(bookService.create(bookDto), HttpStatus.CREATED);
	}

	@GetMapping("/product/search")
	public ResponseEntity<?> searchProduct(@RequestParam(name = "page", required = false, defaultValue = "1") int page,
			@RequestParam(name = "size", required = false, defaultValue = "6") int size,
			@RequestParam(name = "domain", required = false, defaultValue = "bookID") String domain,
			@RequestParam(name = "dir", required = false, defaultValue = "asc") String dir,
			@RequestParam(name = "keyword", required = false, defaultValue = "") String keyword) {
		List<Book> list = bookService.searchByBookName(size, page, domain, dir, keyword).getContent();
		int pageCount = bookService.searchByBookName(size, page, domain, dir, keyword).getTotalPages();
		BookView bookView = new BookView();
		bookView.setBookList(list);
		bookView.setPageCount(pageCount);
		return new ResponseEntity<>(bookView, HttpStatus.OK);
	}
	
	@GetMapping("/product/category/{categoryID}")
	public ResponseEntity<?> getProductByCategory(@PathVariable int categoryID,
			@RequestParam(name = "page", required = false, defaultValue = "1") int page,
			@RequestParam(name = "size", required = false, defaultValue = "6") int size){
		List<Book> books = bookService.getSameCategory(categoryID, page, size).getContent();
		int pageCount = bookService.getSameCategory(categoryID, page, size).getTotalPages();
		BookView bookView=  new BookView();
		bookView.setBookList(books);
		bookView.setPageCount(pageCount);
		return new ResponseEntity<>(bookView, HttpStatus.OK);
	}

	@GetMapping("/product/{id}")
	public ResponseEntity<?> getProductById(@PathVariable int id) throws UserNotFoundException {
		try {
			return new ResponseEntity<>(bookService.findById(id), HttpStatus.OK);
		} catch (UserNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	@PutMapping("/product/{id}")
	public ResponseEntity<?> updateProduct(@RequestParam(name = "bookTitle", required = false) String productName,
			@RequestParam(name = "image", required = false) MultipartFile file,
			@RequestParam(name = "pageNum", required = false) String pagenum,
			@RequestParam(name = "category", required = false) String category,
			@RequestParam(name = "author", required = false) String author,
			@RequestParam(name = "publicationDate", required = false) String publicDate,
			@RequestParam(name = "Description", required = false) String description, @PathVariable int id)
			throws UserNotFoundException, ObjectExistedException, IOException, ParseException {
		BookDto bookDto = new BookDto();
		if (pagenum != null)
			bookDto.setPageNum(pagenum);
		if (productName != null)
			bookDto.setBookName(productName);
		if (category != null)
			bookDto.setCategory(category);
		if (file != null)
			bookDto.setImage(file);
		if (author != null)
			bookDto.setAuthor(author);
		bookDto.setReleaseDate(publicDate);
		bookDto.setDescription(description);
		return new ResponseEntity<>(bookService.update(bookDto, id), HttpStatus.OK);
	}

	@GetMapping("/product/getSold/{id}")
	public ResponseEntity<?> getSold(@PathVariable int id) {
		return new ResponseEntity<>(bookService.getSold(id), HttpStatus.OK);
	}

	@DeleteMapping("/product/{id}")
	public ResponseEntity<?> deleteProduct(@PathVariable int id) {
		try {
			bookService.delete(id);
			return new ResponseEntity<>(new CartMessage("Da Xoa Thanh Cong "), HttpStatus.OK);
		} catch (Exception exception) {
			return new ResponseEntity<>(new CartMessage("Co loi xay ra"), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/product/detail/{id}")
	public ResponseEntity<?> getById(@PathVariable int id) throws UserNotFoundException {
		return new ResponseEntity<>(bookService.findById(id), HttpStatus.OK);
	}

	@GetMapping("/product/star/{id}")
	public ResponseEntity<?> getStar(@PathVariable int id) {
		return new ResponseEntity<>(bookService.computeStar(id), HttpStatus.OK);
	}
}
