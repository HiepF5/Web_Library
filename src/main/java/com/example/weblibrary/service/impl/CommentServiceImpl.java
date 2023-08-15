package com.example.weblibrary.service.impl;

import com.example.weblibrary.Repository.BookRepository;
import com.example.weblibrary.Repository.CommentRepository;
import com.example.weblibrary.Repository.UserRepository;
import com.example.weblibrary.entity.Comment;
import com.example.weblibrary.entity.dto.CommentDto;
import com.example.weblibrary.exception.UserNotFoundException;
import com.example.weblibrary.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
	@Autowired
	private CommentRepository commentRepository;
	@Autowired
	private BookRepository bookRepository;
	@Autowired
	private UserRepository userRepository;

	@Override
	public Comment addComment(CommentDto commentDto) throws UserNotFoundException {
		Comment comment = new Comment();
		comment.setContent(commentDto.getContent());
		comment.setUser(userRepository.findById(commentDto.getUserId())
				.orElseThrow(() -> new UserNotFoundException("No user have id " + commentDto.getUserId())));
		comment.setBook(bookRepository.findById(commentDto.getProductId())
				.orElseThrow(() -> new UserNotFoundException("No product have id " + commentDto.getProductId())));
		comment.setStar(commentDto.getStar());
		return commentRepository.save(comment);
	}

	@Override
	public List<Comment> findByProduct(int productId) {
		// TODO Auto-generated method stub
		return commentRepository.findByProduct(productId);
	}

}
