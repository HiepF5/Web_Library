package com.example.weblibrary.exception;

public class UserNotFoundException extends Exception {
	String message;

	public UserNotFoundException(String message) {
		super();
		this.message = message;
	}
	
	
}
