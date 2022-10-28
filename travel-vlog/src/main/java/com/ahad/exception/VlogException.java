package com.ahad.exception;

public class VlogException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public VlogException(String message) {
		super(message);
	}

	@Override
	public String getMessage() {
		return "Failed to insert vlog " + super.getMessage();
	}

}
