package com.ahad.exception;

public class VerificationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public VerificationException(String message) {
		super(message);
	}

	@Override
	public String getMessage() {
		return "Failed to verify user due to " + super.getMessage();
	}

}
