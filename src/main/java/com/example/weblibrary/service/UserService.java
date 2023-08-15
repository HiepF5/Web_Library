package com.example.weblibrary.service;

import com.example.weblibrary.entity.User;
import com.example.weblibrary.entity.dto.SignUpRequest;
import com.example.weblibrary.exception.ObjectExistedException;
import org.springframework.data.domain.Page;

public interface UserService {
	User findByUserName(String UserName);
	Page<User> findAll(int page,int size);
	User create(SignUpRequest signup) throws ObjectExistedException;

}
