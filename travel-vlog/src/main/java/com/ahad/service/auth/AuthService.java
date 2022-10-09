package com.ahad.service.auth;

import com.ahad.model.User;

public interface AuthService {
	public void registerUser(User user);

	public void verifyUser(String email, String password);

	public void validateLoginInformation(String email, String password);

	public void sendResetEmail(String email);

	public void verifyResetPasswordEmailAndCredential(String email, String password);

	public void resetPassword(String email, String credential, String password, String rePassword);
}
