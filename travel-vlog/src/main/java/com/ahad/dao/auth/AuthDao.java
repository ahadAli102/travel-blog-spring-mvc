package com.ahad.dao.auth;

import com.ahad.model.User;

public interface AuthDao {
	public int registerUser(User user);

	public int verifyUser(String email, String password);

	public User validateLoginInformation(String email);
}
