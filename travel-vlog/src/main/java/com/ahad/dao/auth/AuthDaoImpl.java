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
	public static final String CREATE_NEW_CREDENTIAL = "INSERT INTO `reset_password_table`(`email`, `credential`, `complete`) VALUES (?, ?, '0')";
	public static final String VALIDATE_USER_CREDENTIAL = "SELECT count(email) AS number FROM `reset_password_table` WHERE `reset_password_table`.`email` = ? AND `reset_password_table`.`credential` = ? AND `reset_password_table`.`complete` = 0;"; 
	
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

	@SuppressWarnings("finally")
	@Override
	public User getUser(String email) {
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

	@Override
	public int createNewResetPasswordCredential(String email, String credential) {
		System.out.println("dao createNewResetPasswordCredential "+email+" "+credential);
		try {
			return jdbcTemplate.update(CREATE_NEW_CREDENTIAL, email, credential);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return 0;
		}
		
	}

	@SuppressWarnings("finally")
	@Override
	public boolean verifyResetPasswordEmailAndCredential(String email, String credential) {
		Object[] args = {email, credential};
		int count = 0;
		try {
			count = jdbcTemplate.queryForObject(VALIDATE_USER_CREDENTIAL, new RowMapper<Integer>() {
				@Override
				public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getInt("number");
				}
			}, args);
		}
		catch(Exception e) {
			e.fillInStackTrace();
		}finally {
			return count==1;
		}
	}
}
