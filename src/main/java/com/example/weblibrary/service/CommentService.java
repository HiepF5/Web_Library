package com.example.weblibrary.service;

import com.example.weblibrary.entity.Comment;
import com.example.weblibrary.entity.dto.CommentDto;
import com.example.weblibrary.exception.UserNotFoundException;

import java.util.List;

public interface CommentService {
    Comment addComment(CommentDto commentDto) throws UserNotFoundException;
    List<Comment> findByProduct(int productId);
}
