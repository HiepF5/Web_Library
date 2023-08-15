package com.example.weblibrary.controler;

import com.example.weblibrary.entity.dto.SignUpRequest;
import com.example.weblibrary.exception.ObjectExistedException;
import com.example.weblibrary.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserControler {
	@Autowired
	private UserService userService;
	
	@PostMapping("/home")
	public ResponseEntity<?> create(@RequestBody @Valid SignUpRequest sign) throws ObjectExistedException {
		return new ResponseEntity<>(userService.create(sign),HttpStatus.CREATED);
	}
	
}
