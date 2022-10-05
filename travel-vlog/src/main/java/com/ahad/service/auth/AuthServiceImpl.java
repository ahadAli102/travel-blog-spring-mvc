package com.ahad.service.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ahad.dao.auth.AuthDao;
import com.ahad.exception.AuthException;
import com.ahad.model.User;

@Service
public class AuthServiceImpl implements AuthService {
	@Autowired
	private AuthDao authDao;

	@Override
	public void registerUser(User user) {
		if (!user.getPassword().equals(user.getRePassword()))
			throw new AuthException("passwords miss match");
		if (authDao.registerUser(user) <= 0)
			throw new AuthException("this email already exist");
		System.out.println("service regestering user " + this);
	}

}
