package com.ahad.exception;

public class ResetPasswordException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ResetPasswordException(String message) {
		super(message);
	}

	@Override
	public String getMessage() {
		return "Failed to reset password : " + super.getMessage();
	}

}
