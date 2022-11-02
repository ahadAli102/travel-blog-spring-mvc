package com.ahad.dao.user;

import java.util.Map;

public interface UserDao {
	public int saveProfileImage(byte[] bytes, String fileName, String type, String email);
	public String getImage(String email);
	public void rateAuthor(int rating, String authorEmail, String raterEmail);
	public Map<String, Object> getUserRating(String email);
}
