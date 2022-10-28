package com.ahad.dao.vlog;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.ahad.model.Vlog;

@Repository
public class VlogDaoImpl implements VlogDao {
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	private static final String INSERT_VLOG = "INSERT INTO `vlog_table`(`location`, `description`, `eamil`) VALUES (?, ?, ?)";
	private static final String GET_VLOG_ID = "SELECT `id` FROM `vlog_table` WHERE vlog_table.eamil = ? AND vlog_table.location = ? AND vlog_table.description = ? ORDER BY vlog_table.id DESC LIMIT 1;";
	private static final String INSERT_VLOG_IMAGE = "INSERT INTO `vlog_image_table`(`name`, `type`, `image`, `vlog_id`) VALUES (?, ?, ?, ?)";
	private static final String INSERT_VLOG_VIDEO = "INSERT INTO `vlog_video_table`(`name`, `type`, `video`, `vlog_id`) VALUES (?, ?, ?, ?)";
	
	@Override
	@Transactional
	public void addVlog(Vlog vlog, CommonsMultipartFile[] images, CommonsMultipartFile[] videos, String email) {
		saveVlog(vlog,email);
		System.out.println("vlog dao vlog saved");
		int vlogId = getVlogId(vlog,email);
		System.out.println("vlog dao vlog id geted");
		saveVlogImages(vlogId, images);
		System.out.println("vlog dao images added");
		saveVlogVideos(vlogId, videos);
		System.out.println("vlog dao videos added");
	}
	
	private void saveVlog(Vlog vlog, String email) {
		System.out.println("VlogDao Vlog save");
		jdbcTemplate.update(INSERT_VLOG, vlog.getLocation(), vlog.getDescription(), email);
	}
	
	private int getVlogId(Vlog vlog, String email) {
		System.out.println("VlogDao Vlog number");
		return jdbcTemplate.queryForObject(GET_VLOG_ID, new RowMapper<Integer>() {

			@Override
			public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getInt("id");
			}
		},new Object[] {email,vlog.getLocation(),vlog.getDescription()});
	}

	private void saveVlogVideos(int vlogId, CommonsMultipartFile[] videos) {
		System.out.println("VlogDao Vlog save videos");
		jdbcTemplate.batchUpdate(INSERT_VLOG_VIDEO, new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setString(1, videos[i].getOriginalFilename());
				ps.setString(2, videos[i].getContentType());
				InputStream is = new ByteArrayInputStream(videos[i].getBytes());
				ps.setBinaryStream(3, is, videos[i].getBytes().length);
				ps.setInt(4, vlogId);
			}

			@Override
			public int getBatchSize() {
				return videos.length;
			}
		});
	}

	private void saveVlogImages(int vlogId, CommonsMultipartFile[] images) {
		System.out.println("VlogDao Vlog save images");
		jdbcTemplate.batchUpdate(INSERT_VLOG_IMAGE, new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setString(1, images[i].getOriginalFilename());
				ps.setString(2, images[i].getContentType());
				InputStream is = new ByteArrayInputStream(images[i].getBytes());
				ps.setBinaryStream(3, is, images[i].getBytes().length);
				ps.setInt(4, vlogId);
			}

			@Override
			public int getBatchSize() {
				return images.length;
			}
		});
	}

	
}
