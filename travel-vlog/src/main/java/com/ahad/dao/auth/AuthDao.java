package com.ahad.dao.auth;

import com.ahad.model.User;

public interface AuthDao {
	public int registerUser(User user);

	public int verifyUser(String email, String password);

	public User validateLoginInformation(String email);

	public User getUser(String email);

	public int createNewResetPasswordCredential(String email, String credential);

	public boolean verifyResetPasswordEmailAndCredential(String email, String credential);

	public boolean resetPassword(String email, String credential, String password, String rePassword);
}
