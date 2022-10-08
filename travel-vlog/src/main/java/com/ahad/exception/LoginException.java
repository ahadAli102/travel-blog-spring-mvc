package com.ahad.exception;

public class LoginException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public LoginException(String message) {
		super(message);
	}

	@Override
	public String getMessage() {
		return "Failed to register user due to " + super.getMessage();
	}

}
