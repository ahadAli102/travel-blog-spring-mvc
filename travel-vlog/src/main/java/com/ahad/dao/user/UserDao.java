package com.ahad.dao.user;

public interface UserDao {
	public int saveProfileImage(byte[] bytes, String fileName, String type, String email);
	public String getImage(String email);
	public void rateAuthor(int rating, String authorEmail, String raterEmail);
}
