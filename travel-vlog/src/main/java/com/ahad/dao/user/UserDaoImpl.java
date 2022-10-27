package com.ahad.dao.user;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private static final String INSERT_IMAGE = "INSERT INTO `profile_image` (`id`, `name`, `type`, `image`,  `email`) VALUES (NULL, ?, ?, ?, ?)";

	@Override
	public int saveProfileImage(byte[] image, String fileName, String type, String email) {

		return jdbcTemplate.update(INSERT_IMAGE, new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, fileName);
				ps.setString(2, type);
				InputStream is = new ByteArrayInputStream(image);
				ps.setBinaryStream(3, is, image.length);
				ps.setString(4, email);
				
			}
		});
	}

}
