package com.ahad.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.ahad.dao.user.UserDao;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao dao;

	@Override
	public void saveProfileImage(CommonsMultipartFile reqFile, String email) {
		dao.saveProfileImage(reqFile.getBytes(),reqFile.getOriginalFilename(),
				reqFile.getContentType(),email);
	}
}
