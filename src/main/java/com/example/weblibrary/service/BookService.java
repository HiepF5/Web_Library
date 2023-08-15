package com.example.weblibrary.service;

import com.example.weblibrary.entity.Book;
import com.example.weblibrary.entity.dto.BookDto;
import com.example.weblibrary.exception.ObjectExistedException;
import com.example.weblibrary.exception.UserNotFoundException;
import org.springframework.data.domain.Page;

import java.io.IOException;

public interface BookService {
	Book create(BookDto bookDto) throws ObjectExistedException, IOException;

	Page<Book> searchByBookName(int size, int page, String domain, String dir, String keyword);

	Book findById(int id) throws UserNotFoundException;

	Book update(BookDto bookDto, int id) throws ObjectExistedException, UserNotFoundException, IOException;

	int getSold(int id);

	void delete(int id) throws UserNotFoundException;

	int computeStar(int bookID);

	Page<Book> getSameCategory(int categoryID, int page, int size);
}
