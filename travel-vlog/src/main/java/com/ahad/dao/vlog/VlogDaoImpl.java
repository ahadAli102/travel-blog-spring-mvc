package com.ahad.dao.vlog;

import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.ahad.model.User;
import com.ahad.model.Vlog;

@Repository
public class VlogDaoImpl implements VlogDao {
	@Autowired
	JdbcTemplate jdbcTemplate;

	private static final String INSERT_VLOG = "INSERT INTO `vlog_table`(`location`, `description`, `eamil`) VALUES (?, ?, ?)";
	private static final String GET_VLOG_ID = "SELECT `id` FROM `vlog_table` WHERE vlog_table.eamil = ? AND vlog_table.location = ? AND vlog_table.description = ? ORDER BY vlog_table.id DESC LIMIT 1;";
	private static final String INSERT_VLOG_IMAGE = "INSERT INTO `vlog_videos_table`(`vlogId`, `url`) VALUES (?,?)";
	private static final String INSERT_VLOG_VIDEO = "INSERT INTO `vlog_images_table`(`vlogId`, `url`) VALUES (?,?)";
	private static final String GET_USER_VLOG = "SELECT vlog_table.id,vlog_table.location, vlog_table.description, \n"
			+ "vlog_images_table.url AS vlog_image,  		\n" + "vlog_videos_table.url AS vlog_video, \n"
			+ "user_table.name, user_table.email,  \n" + "profile_image.name as user_image_name   \n"
			+ "FROM user_table    \n"
			+ "INNER JOIN vlog_table ON vlog_table.eamil = user_table.email AND vlog_table.eamil = ? \n"
			+ "INNER JOIN vlog_images_table ON vlog_images_table.vlogId = vlog_table.id   \n"
			+ "INNER JOIN vlog_videos_table ON vlog_videos_table.vlogId = vlog_images_table.vlogId  \n"
			+ "INNER JOIN profile_image ON profile_image.email = user_table.email  AND profile_image.id = (SELECT MAX(profile_image.id) FROM profile_image GROUP BY profile_image.email) \n"
			+ "GROUP BY (vlog_table.id) \n" + "ORDER BY vlog_table.id DESC;";
	private static final String GET_SINGLE_VLOG 
	        = "SELECT \n" + 
			"    vlog_table.id, \n" + 
			"    vlog_table.location, \n" + 
			"    vlog_table.description, \n" + 
			"    vlog_images_table.url AS vlog_image, \n" + 
			"    vlog_videos_table.url AS vlog_video, \n" + 
			"    user_table.name, \n" + 
			"    user_table.email, \n" + 
			"    profile_image.name AS user_image_name \n" + 
			"FROM \n" + 
			"    user_table \n" + 
			"INNER JOIN vlog_table ON vlog_table.eamil = user_table.email \n" + 
			"INNER JOIN vlog_images_table ON vlog_images_table.vlogId = vlog_table.id \n" + 
			"INNER JOIN vlog_videos_table ON vlog_videos_table.vlogId = vlog_images_table.vlogId AND vlog_table.id = ? \n" + 
			"INNER JOIN profile_image ON profile_image.email = user_table.email AND profile_image.id = (SELECT MAX(profile_image.id) FROM profile_image GROUP BY profile_image.email) \n" + 
			"GROUP BY vlog_images_table.url, vlog_videos_table.url;";
	private static final String RATE_VLOG = "INSERT INTO `vlog_rating_table`(`vlog_id`, `rater_email`, `rating`) VALUES (?, ?, ?)";
	private static final String RATING_OF_VLOG = "SELECT AVG(vlog_rating_table.rating) AS avg_rating, COUNT(vlog_rating_table.vlog_id) AS total_votes FROM vlog_rating_table WHERE vlog_rating_table.vlog_id = ?;";
	
	@Override
	@Transactional
	public void addVlog(Vlog vlog, CommonsMultipartFile[] images, CommonsMultipartFile[] videos, String email) {
		saveVlog(vlog, email);
		System.out.println("vlog dao vlog saved");
		int vlogId = getVlogId(vlog, email);
		System.out.println("vlog dao vlog id geted");
		saveVlogImages(vlogId, images);
		System.out.println("vlog dao images added");
		saveVlogVideos(vlogId, videos);
		System.out.println("vlog dao videos added");
	}

	private void saveVlogVideos(int vlogId, CommonsMultipartFile[] videos) {
		List<String> url = new ArrayList<String>();
		for (CommonsMultipartFile video : videos) {
			File outputFile = new File("G:\\eclips-spring-mvc\\travel-vlog-content",
					"" + vlogId + "_videos_" + video.getOriginalFilename());
			try {
				FileUtils.writeByteArrayToFile(outputFile, video.getBytes());
				url.add("" + vlogId + "_videos_" + video.getOriginalFilename());
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e.getMessage());
			}
		}

		jdbcTemplate.batchUpdate(INSERT_VLOG_IMAGE, new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setInt(1, vlogId);
				ps.setString(2, url.get(i));
			}

			@Override
			public int getBatchSize() {
				return url.size();
			}
		});
	}

	private void saveVlogImages(int vlogId, CommonsMultipartFile[] images) {
		List<String> url = new ArrayList<String>();
		for (CommonsMultipartFile image : images) {
			File outputFile = new File("G:\\eclips-spring-mvc\\travel-vlog-content",
					"" + vlogId + "_image_" + image.getOriginalFilename());
			try {
				FileUtils.writeByteArrayToFile(outputFile, image.getBytes());
				url.add("" + vlogId + "_image_" + image.getOriginalFilename());
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e.getMessage());
			}
		}

		jdbcTemplate.batchUpdate(INSERT_VLOG_VIDEO, new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setInt(1, vlogId);
				ps.setString(2, url.get(i));
			}

			@Override
			public int getBatchSize() {
				return url.size();
			}
		});
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
		}, new Object[] { email, vlog.getLocation(), vlog.getDescription() });
	}

	@Override
	public Map<String, Vlog> getUserVlogs(String email) {
		return jdbcTemplate.query(GET_USER_VLOG, new ResultSetExtractor<Map<String, Vlog>>() {

			@Override
			public Map<String, Vlog> extractData(ResultSet rs) throws SQLException, DataAccessException {
				Map<String, Vlog> vlogMap = new LinkedHashMap<String, Vlog>();
				System.out.println("vlog dao " + email + " size: " + rs.getFetchSize());
				while (rs.next()) {
					int id = rs.getInt("id");

					String vlogImage = rs.getString("vlog_image");

					String vlogVideo = rs.getString("vlog_video");

					String userImage = rs.getString("user_image_name");
					System.out.println("vlog dao user image: " + userImage);

					if (vlogMap.containsKey("" + id)) {
						vlogMap.get("" + id).getImageUrl().add(vlogImage);
						vlogMap.get("" + id).getVideoUrl().add(vlogVideo);
					} else {
						Vlog vlog = new Vlog();
						vlog.setId(id);
						List<String> imageUrl = new ArrayList<String>();
						List<String> videoUrl = new ArrayList<String>();
						User user = new User();

//						id location description vlog_image_type vlog_image vlog_video_type vlog_video name email
//						user_image profile_image_type
						user.setName(rs.getString("name"));
						user.setEmail(rs.getString("email"));
						user.setImage(userImage);

						vlog.setId(rs.getInt("id"));
						vlog.setLocation(rs.getString("location"));
						vlog.setDescription(rs.getString("description"));
						vlog.setUser(user);

						imageUrl.add(vlogImage);
						videoUrl.add(vlogVideo);
						vlog.setImageUrl(imageUrl);
						vlog.setVideoUrl(videoUrl);
						vlogMap.put("" + id, vlog);
					}
				}
				return vlogMap;
			}
		}, new Object[] { email });
	}

	@Override
	public Vlog getVlog(int vlogId) {
		
		return jdbcTemplate.queryForObject(GET_SINGLE_VLOG, new RowMapper<Vlog>() {

			@Override
			public Vlog mapRow(ResultSet rs, int rowNum) throws SQLException {
				Vlog vlog = null;
				Map<String,Boolean> imageUrlMap = new HashMap<String,Boolean>();
				Map<String,Boolean> videoUrlMap = new HashMap<String,Boolean>();
				while(rs.next()) {
					if(vlog == null) {
						vlog = new Vlog();
						String userImage = rs.getString("user_image_name");
						int id = rs.getInt("id");
						vlog.setId(id);
						User user = new User();
						user.setImage(userImage);
						user.setName(rs.getString("name"));
						user.setEmail(rs.getString("email"));
						user.setImage(userImage);

						vlog.setId(rs.getInt("id"));
						vlog.setLocation(rs.getString("location"));
						vlog.setDescription(rs.getString("description"));
						vlog.setUser(user);
						System.out.println("vlog dao single vlog: "+user+"  image: "+user.getImage());
					}

					String vlogImage = rs.getString("vlog_image");
					String vlogVideo = rs.getString("vlog_video");
					
					if(!imageUrlMap.containsKey(vlogImage))
						imageUrlMap.put(vlogImage, true);
					if(!videoUrlMap.containsKey(vlogVideo))
						videoUrlMap.put(vlogVideo, true);
				}
				if(vlog!=null) {
					List<String> imageUrl = new ArrayList<String>(imageUrlMap.size());
					imageUrl.addAll(imageUrlMap.keySet());
					vlog.setImageUrl(imageUrl);
					
					List<String> videoUrl = new ArrayList<String>(videoUrlMap.size());
					videoUrl.addAll(videoUrlMap.keySet());
					vlog.setVideoUrl(videoUrl);
				}
				return vlog;
			}
		},new Object[] {vlogId});
	}

	@Override
	public int rateVlog(int vlogId, int rating, String email) {
		return jdbcTemplate.update(RATE_VLOG, vlogId,email,rating);
	}

	@Override
	public Map<String, Object> getVlogRating(int vlogId) {
		return jdbcTemplate.queryForMap(RATING_OF_VLOG, vlogId);
	}
}