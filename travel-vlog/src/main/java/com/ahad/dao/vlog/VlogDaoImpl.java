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
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.ahad.model.Comment;
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
	private static final String GET_USER_VLOGS 
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
			"INNER JOIN vlog_table ON vlog_table.eamil = user_table.email AND vlog_table.eamil = ? \n" + 
			"INNER JOIN vlog_images_table ON vlog_images_table.vlogId = vlog_table.id \n" + 
			"INNER JOIN vlog_videos_table ON vlog_videos_table.vlogId = vlog_images_table.vlogId \n" + 
			"INNER JOIN profile_image ON profile_image.email = user_table.email AND profile_image.id =( \n" + 
			"    SELECT \n" + 
			"        MAX(profile_image.id) \n" + 
			"    FROM \n" + 
			"        profile_image \n" + 
			"    WHERE \n" + 
			"        profile_image.email = ? \n" + 
			") \n" + 
			"GROUP BY \n" + 
			"    (vlog_table.id) \n" + 
			"ORDER BY \n" + 
			"    vlog_table.id DESC;";
	
	private static final String GET_USER_VLOGS_FILTERED = 
			"SELECT \n" + 
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
			"INNER JOIN vlog_table ON vlog_table.eamil = user_table.email AND( \n" + 
			"        vlog_table.eamil = ? AND( \n" + 
			"            vlog_table.description LIKE ? OR vlog_table.location LIKE ? \n" + 
			"        ) \n" + 
			"    ) \n" + 
			"INNER JOIN vlog_images_table ON vlog_images_table.vlogId = vlog_table.id \n" + 
			"INNER JOIN vlog_videos_table ON vlog_videos_table.vlogId = vlog_images_table.vlogId \n" + 
			"INNER JOIN profile_image ON profile_image.email = user_table.email AND profile_image.id =( \n" + 
			"    SELECT \n" + 
			"        MAX(profile_image.id) \n" + 
			"    FROM \n" + 
			"        profile_image \n" + 
			"    WHERE \n" + 
			"        profile_image.email = ? \n" + 
			") \n" + 
			"GROUP BY \n" + 
			"    (vlog_table.id) \n" + 
			"ORDER BY \n" + 
			"    vlog_table.id \n" + 
			"DESC \n" + 
			"    ;";
		
	private static final String GET_SINGLE_VLOG 
		   ="SELECT \n" + 
			"    vlog_table.id, \n" + 
			"    vlog_table.location, \n" + 
			"    vlog_table.description, \n" + 
			"    vlog_images_table.url AS vlog_image, \n" + 
			"    vlog_videos_table.url AS vlog_video, \n" + 
			"    user_table.name, \n" + 
			"    user_table.email \n" + 
			"FROM \n" + 
			"    user_table \n" + 
			"INNER JOIN vlog_table ON vlog_table.eamil = user_table.email \n" + 
			"INNER JOIN vlog_images_table ON vlog_images_table.vlogId = vlog_table.id \n" + 
			"INNER JOIN vlog_videos_table ON vlog_videos_table.vlogId = vlog_images_table.vlogId AND vlog_table.id = ? \n" + 
			"GROUP BY \n" + 
			"    vlog_images_table.url, \n" + 
			"    vlog_videos_table.url;";
				  
	private static final String RATE_VLOG = "INSERT INTO `vlog_rating_table`(`vlog_id`, `rater_email`, `rating`) VALUES (?, ?, ?)";
	private static final String RATING_OF_VLOG = "SELECT AVG(vlog_rating_table.rating) AS avg_rating, COUNT(vlog_rating_table.vlog_id) AS total_votes FROM vlog_rating_table WHERE vlog_rating_table.vlog_id = ?;";
	private static final String USER_VLOG_RATING = 
			"SELECT \n" + 
			"    COUNT(DISTINCT(vlog_table.id)) AS total_vlogs, \n" + 
			"    COUNT(vlog_rating_table.vlog_id) AS vlog_count_rating, \n" + 
			"    AVG(vlog_rating_table.rating) AS vlog_avg_rating \n" + 
			"FROM \n" + 
			"    vlog_table \n" + 
			"JOIN vlog_rating_table ON vlog_rating_table.vlog_id = vlog_table.id AND vlog_table.eamil = ?;";
	
	private static final String DELETE_VLOG = "DELETE FROM `vlog_table` WHERE `vlog_table`.`id` = ?";
	private static final String SAVE_VLOG_COMMENT = "INSERT INTO `vlog_comment_table`(`comment`, `vlog_id`, `user_email`, `time`) VALUES (?, ?, ?, ?);";
	private static final String GET_VLOG_COMMENTS = 
			"SELECT \n" + 
			"	vlog_comment_table.id AS id, \n" + 
			"    vlog_comment_table.comment AS comment, \n" + 
			"    vlog_comment_table.time AS time, \n" + 
			"    user_table.name AS commenterName, \n" + 
			"    profile_image.name AS commenterImage \n" + 
			"FROM \n" + 
			"    vlog_comment_table \n" + 
			"JOIN user_table ON user_table.email = vlog_comment_table.user_email AND vlog_comment_table.vlog_id = ? \n" + 
			"JOIN profile_image ON profile_image.email = user_table.email AND profile_image.id =( \n" + 
			"    SELECT \n" + 
			"        MAX(profile_image.id) \n" + 
			"    FROM \n" + 
			"        profile_image \n" + 
			"    WHERE \n" + 
			"        profile_image.email = vlog_comment_table.user_email \n" + 
			") \n" + 
			"ORDER BY \n" + 
			"    vlog_comment_table.id \n" + 
			"DESC;";
	
	private static final String GET_ALL_VLOGS = 
			"SELECT \n" + 
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
			"INNER JOIN vlog_videos_table ON vlog_videos_table.vlogId = vlog_images_table.vlogId \n" + 
			"INNER JOIN profile_image ON profile_image.email = user_table.email AND profile_image.id =( \n" + 
			"    SELECT \n" + 
			"        MAX(profile_image.id) \n" + 
			"    FROM \n" + 
			"        profile_image \n" + 
			"    WHERE \n" + 
			"        profile_image.email = vlog_table.eamil \n" + 
			") \n" + 
			"GROUP BY \n" + 
			"    (vlog_table.id) \n" + 
			"ORDER BY \n" + 
			"    vlog_table.id \n" + 
			"DESC \n" + 
			"LIMIT ?,?;";
	private static final String GET_ALL_SEARCHED_VLOG = 
			"SELECT \n" + 
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
			"INNER JOIN vlog_table ON vlog_table.eamil = user_table.email AND( \n" + 
			"        vlog_table.description LIKE ? OR vlog_table.location LIKE ? \n" + 
			"    ) \n" + 
			"INNER JOIN vlog_images_table ON vlog_images_table.vlogId = vlog_table.id \n" + 
			"INNER JOIN vlog_videos_table ON vlog_videos_table.vlogId = vlog_images_table.vlogId \n" + 
			"INNER JOIN profile_image ON profile_image.email = user_table.email AND profile_image.id =( \n" + 
			"    SELECT \n" + 
			"        MAX(profile_image.id) \n" + 
			"    FROM \n" + 
			"        profile_image \n" + 
			"    WHERE \n" + 
			"        profile_image.email = vlog_table.eamil \n" + 
			") \n" + 
			"GROUP BY \n" + 
			"    (vlog_table.id) \n" + 
			"ORDER BY \n" + 
			"    vlog_table.id \n" + 
			"DESC \n" + 
			"LIMIT ?, ?;";
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
	public Map<String, Vlog> getAllVlogs(int offset, int limit) {
		
		return jdbcTemplate.query(GET_ALL_VLOGS, new ResultSetExtractor<Map<String, Vlog>>() {

			@Override
			public Map<String, Vlog> extractData(ResultSet rs) throws SQLException, DataAccessException {
				Map<String, Vlog> vlogMap = new LinkedHashMap<String, Vlog>();
				while (rs.next()) {
					int id = rs.getInt("id");
					String vlogImage = rs.getString("vlog_image");
					String vlogVideo = rs.getString("vlog_video");

					if (vlogMap.containsKey("" + id)) {
						vlogMap.get("" + id).getImageUrl().add(vlogImage);
						vlogMap.get("" + id).getVideoUrl().add(vlogVideo);
					} else {
						String userImage = rs.getString("user_image_name");
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
		}, new Object[] { offset, limit });
	}
	
	@Override
	public Map<String, Vlog> getSearchVlogs(String query, int offset, int limit) {
		
		String like = new StringBuilder().append("%").append(query).append("%").toString();
		System.out.println("vlog dao get search vlogs like:"+like);
		return jdbcTemplate.query(GET_ALL_SEARCHED_VLOG, new ResultSetExtractor<Map<String, Vlog>>() {

			@Override
			public Map<String, Vlog> extractData(ResultSet rs) throws SQLException, DataAccessException {
				Map<String, Vlog> vlogMap = new LinkedHashMap<String, Vlog>();
				System.out.println("vlog dao get user filter vlogs response");
				while (rs.next()) {
					int id = rs.getInt("id");
					System.out.println("vlog dao get user filter vlogs respone:"+id);
					String vlogImage = rs.getString("vlog_image");
					String vlogVideo = rs.getString("vlog_video");

					if (vlogMap.containsKey("" + id)) {
						vlogMap.get("" + id).getImageUrl().add(vlogImage);
						vlogMap.get("" + id).getVideoUrl().add(vlogVideo);
					} else {
						String userImage = rs.getString("user_image_name");
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
		}, new Object[] {like, like, offset, limit });
	}
	
	@Override
	public Map<String, Vlog> getUserVlogs(String email) {
		return jdbcTemplate.query(GET_USER_VLOGS, new ResultSetExtractor<Map<String, Vlog>>() {

			@Override
			public Map<String, Vlog> extractData(ResultSet rs) throws SQLException, DataAccessException {
				Map<String, Vlog> vlogMap = new LinkedHashMap<String, Vlog>();
				System.out.println("vlog dao " + email + " size: " + rs.getFetchSize());
				while (rs.next()) {
					int id = rs.getInt("id");
					String vlogImage = rs.getString("vlog_image");
					String vlogVideo = rs.getString("vlog_video");

					if (vlogMap.containsKey("" + id)) {
						vlogMap.get("" + id).getImageUrl().add(vlogImage);
						vlogMap.get("" + id).getVideoUrl().add(vlogVideo);
					} else {
						String userImage = rs.getString("user_image_name");
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
		}, new Object[] { email, email });
	}
	
	@Override
	public Map<String, Vlog> getUserVlogs(String email, String query) {
		String like = new StringBuilder().append("%").append(query).append("%").toString();
		System.out.println("vlog dao get user filter vlogs like:"+like);
		return jdbcTemplate.query(GET_USER_VLOGS_FILTERED, new ResultSetExtractor<Map<String, Vlog>>() {

			@Override
			public Map<String, Vlog> extractData(ResultSet rs) throws SQLException, DataAccessException {
				Map<String, Vlog> vlogMap = new LinkedHashMap<String, Vlog>();
				System.out.println("vlog dao get user filter vlogs response");
				while (rs.next()) {
					int id = rs.getInt("id");
					System.out.println("vlog dao get user filter vlogs respone:"+id);
					String vlogImage = rs.getString("vlog_image");
					String vlogVideo = rs.getString("vlog_video");

					if (vlogMap.containsKey("" + id)) {
						vlogMap.get("" + id).getImageUrl().add(vlogImage);
						vlogMap.get("" + id).getVideoUrl().add(vlogVideo);
					} else {
						String userImage = rs.getString("user_image_name");
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
		}, new Object[] { email, like, like, email });
	}

	@Override
	public Vlog getVlog(int vlogId) {
		
		return jdbcTemplate.queryForObject(GET_SINGLE_VLOG, new RowMapper<Vlog>() {

			@Override
			public Vlog mapRow(ResultSet rs, int rowNum) throws SQLException {
				Vlog vlog = null;
				Map<String,Boolean> imageUrlMap = new HashMap<String,Boolean>();
				Map<String,Boolean> videoUrlMap = new HashMap<String,Boolean>();
				vlog = new Vlog();
				String userImage = null;
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
				String vlogImage = rs.getString("vlog_image");
				String vlogVideo = rs.getString("vlog_video");
				imageUrlMap.put(vlogImage, true);
				videoUrlMap.put(vlogVideo, true);
				while(rs.next()) {
					System.out.println("vlog dao get vlog fetch row");
					
					vlogImage = rs.getString("vlog_image");
					vlogVideo = rs.getString("vlog_video");
					
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

	@Override
	public Map<String, Object> getUserVlogRating(String email) {
		return jdbcTemplate.queryForMap(USER_VLOG_RATING, email);
	}

	@Override
	public void deleteVlog(int vlogId) {
		jdbcTemplate.update(DELETE_VLOG,vlogId);
	}

	@Override
	public int saveComment(String comment, int vlogId, String email) {
		return jdbcTemplate.update(SAVE_VLOG_COMMENT, comment,vlogId,email,System.currentTimeMillis());
	}

	@Override
	public List<Comment> getVlogComments(int vlogId) {
		return jdbcTemplate.query(GET_VLOG_COMMENTS, new BeanPropertyRowMapper<Comment>(Comment.class),vlogId);
	}

}