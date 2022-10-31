package com.ahad.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.ahad.dao.user.UserDao;
import com.ahad.model.User;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao dao;

	@Override
	public void saveProfileImage(CommonsMultipartFile reqFile, String email) {
		dao.saveProfileImage(reqFile.getBytes(),reqFile.getOriginalFilename(),
				reqFile.getContentType(),email);
	}

	@Override
	public String getImage(User user) {
		
		try {
			String image = dao.getImage(user.getEmail());
			System.out.println("image: G:\\eclips-spring-mvc\\travel-vlog-content"+image);
			return image;
		}catch(Exception e) {
			System.out.println("user service get image exception : "+e.getMessage());
			return "";
		}
	}

	@Override
	public String rateAuthor(int rating, String authorEmail, String raterEmail) {
		try {
			System.out.println("user dao rate author: aurhor:"+authorEmail+" rater:"+raterEmail+" rating:"+rating);
			dao.rateAuthor(rating,authorEmail,raterEmail);
			return "Your rating have been stored succesfully";
		}catch(Exception e) {
			System.out.println("user dao rate author: "+e.getMessage());
			return "You have already rated this author";
		}
	}
}
