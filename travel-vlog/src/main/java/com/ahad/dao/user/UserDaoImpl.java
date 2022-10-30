package com.ahad.dao.user;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	private static final String GET_USER_IMAGE = "SELECT * FROM profile_image WHERE profile_image.email=? ORDER BY profile_image.id DESC LIMIT 1";
	private static final String INSERT_IMAGE = "INSERT INTO `profile_image` (`id`, `name`,  `email`) VALUES (NULL, ?, ?)";

	@Override
	public int saveProfileImage(byte[] image, String fileName, String type, String email) {
		String outputFileName = addImageToFile(image, fileName, email);
		return jdbcTemplate.update(INSERT_IMAGE, new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, outputFileName);
				ps.setString(2, email);
				
			}
		});
	}
	private String addImageToFile(byte[] image, String fileName, String email) {
		long time = System.currentTimeMillis();
		File outputFile = new File("G:\\eclips-spring-mvc\\travel-vlog-content",
				"_profile_image_"+ time + fileName);
		try {
			FileUtils.writeByteArrayToFile(outputFile, image);
			return "_profile_image_"+time + fileName;
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public String getImage(String email) {
		return jdbcTemplate.queryForObject(GET_USER_IMAGE, new RowMapper<String>() {

			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return  rs.getString("name");
			}
		}, new Object[] {email});
	}

}
