package com.ahad.exception;

import java.util.Map;

public class ResetPasswordException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private Map<String, Object> params;

	public ResetPasswordException(String message) {
		super(message);
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	@Override
	public String getMessage() {
		return "Failed to reset password : " + super.getMessage();
	}

}
