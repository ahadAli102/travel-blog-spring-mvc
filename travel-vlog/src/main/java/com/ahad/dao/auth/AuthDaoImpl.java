package com.ahad.dao.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ahad.model.User;

@Repository
public class AuthDaoImpl implements AuthDao {
	public static final String INSERT_QUERY = "INSERT INTO `user_table`(`email`, `name`, `password`, `verified`) VALUES (?, ?, ?, ?)";
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public int registerUser(User user) {
		System.out.println("dao regestering user " + this);
		try {
			return jdbcTemplate.update(INSERT_QUERY, user.getEmail(), user.getName(), user.getPassword(), 0);
		}catch(Exception e) {
			return 0;
		}
	}
}
