package com.example.weblibrary.controler;

import com.example.weblibrary.entity.dto.CommentDto;
import com.example.weblibrary.exception.UserNotFoundException;
import com.example.weblibrary.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentControler {

	@Autowired
	private CommentService commentService;

	@GetMapping
	public ResponseEntity<?> getCommentForProduct(@RequestParam(name = "productId") int productId) {
		System.out.println(commentService.findByProduct(productId));
		return new ResponseEntity<>(commentService.findByProduct(productId), HttpStatus.OK);
	}

	@PostMapping
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<?> addComment(@RequestBody @Valid CommentDto commentDto) throws UserNotFoundException {
		return new ResponseEntity<>(commentService.addComment(commentDto), HttpStatus.CREATED);
	}
}
