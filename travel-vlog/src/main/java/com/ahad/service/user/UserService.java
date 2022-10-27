package com.ahad.service.user;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public interface UserService {
	public void saveProfileImage(CommonsMultipartFile reqFile, String email);
}
