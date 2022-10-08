package com.ahad.dao.auth;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.ahad.model.User;

@Repository
public class AuthDaoImpl implements AuthDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public static final String INSERT_QUERY = "INSERT INTO `user_table`(`email`, `name`, `password`, `verified`) VALUES (?, ?, ?, ?)";
	public static final String UPDATE_USER_VERIFY_STATUS = "UPDATE `user_table` SET `verified` = ? where `email` = ? AND `password` = ?";
	public static final String GET_USER_INFORMATION = "SELECT * FROM `user_table` WHERE `email` = ?";

	@Override
	public int registerUser(User user) {
		System.out.println("dao regestering user " + this);
		try {
			return jdbcTemplate.update(INSERT_QUERY, user.getEmail(), user.getName(), user.getPassword(), 0);
		} catch (Exception e) {
			return 0;
		}
	}

	@Override
	public int verifyUser(String email, String password) {
		try {
			return jdbcTemplate.update(UPDATE_USER_VERIFY_STATUS, 1, email, password);
		} catch (Exception e) {
			System.out.println("dao verify user failed " + this + " " + e.getMessage());
			return 0;
		}
	}
	
	@SuppressWarnings("finally")
	@Override
	public User validateLoginInformation(String email){
		Object[] args = { email };
		User user = null;
		try {
			user = jdbcTemplate.queryForObject(GET_USER_INFORMATION, new RowMapper<User>() {
				@Override
				public User mapRow(ResultSet rs, int rowNum) throws SQLException {
					User user = new User();
					user.setName(rs.getString("name"));
					user.setEmail(rs.getString("email"));
					user.setPassword(rs.getString("password"));
					user.setVerified(rs.getInt("verified") == 1);
					return user;
				}
			}, args);
		}
		catch(Exception e) {
			e.fillInStackTrace();
		}finally {
			return user;
		}
	}
}
