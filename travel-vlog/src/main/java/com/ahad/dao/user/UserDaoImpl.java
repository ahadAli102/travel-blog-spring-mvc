package com.ahad.dao.user;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	private static final String GET_USER_IMAGE = "SELECT * FROM profile_image WHERE profile_image.email=? ORDER BY profile_image.id DESC";
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

	@Override
	public String getImage(String email) {
		//myImage = "data:" + image.getType() + ";base64," + image.getTextImage();
		return jdbcTemplate.queryForObject(GET_USER_IMAGE, new RowMapper<String>() {

			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return "data:" + rs.getString("type") + ";base64," 
						+ Base64.getEncoder().encodeToString(rs.getBytes("image"));
			}
		}, new Object[] {email});
	}

}
