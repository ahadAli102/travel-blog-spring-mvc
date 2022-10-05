package com.ahad.exception;

public class AuthException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public AuthException(String message) {
		super(message);
	}

	@Override
	public String getMessage() {
		return "Failed to register user due to " + super.getMessage();
	}

}
