package com.ahad.dao.auth;

import org.springframework.stereotype.Repository;

import com.ahad.model.User;

@Repository
public class AuthDaoImpl implements AuthDao{

	@Override
	public int registerUser(User user) {
		System.out.println("dao regestering user "+this);
		if(user.getEmail().equals("a@gmail.com"))
			return -1;
		return 1;
	}

}
