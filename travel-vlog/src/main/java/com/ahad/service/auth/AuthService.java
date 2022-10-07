package com.ahad.service.auth;

import com.ahad.model.User;

public interface AuthService {
	public void registerUser(User user);

	public void verifyUser(String email, String password);
}
