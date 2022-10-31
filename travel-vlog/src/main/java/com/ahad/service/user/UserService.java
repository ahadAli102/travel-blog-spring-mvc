package com.ahad.service.user;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.ahad.model.User;

public interface UserService {
	public void saveProfileImage(CommonsMultipartFile reqFile, String email);
	public String getImage(User user);
	public String rateAuthor(int rating, String authorEmail, String raterEmail);
}
