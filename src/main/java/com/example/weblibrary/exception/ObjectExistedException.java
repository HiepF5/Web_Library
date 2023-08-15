package com.example.weblibrary.exception;

public class ObjectExistedException extends Exception {
	public ObjectExistedException(String string) {
		this.message = string;
	}
	
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
