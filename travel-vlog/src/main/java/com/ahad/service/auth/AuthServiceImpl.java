package com.ahad.service.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ahad.dao.auth.AuthDao;
import com.ahad.exception.AuthException;
import com.ahad.exception.VerificationException;
import com.ahad.model.User;
import com.ahad.utils.HTMLMail;

@Service
public class AuthServiceImpl implements AuthService {
	@Autowired
	private AuthDao authDao;

	@Autowired
	private HTMLMail mail;

	@Override
	public void registerUser(User user) {
		if (!user.getPassword().equals(user.getRePassword()))
			throw new AuthException("passwords miss match");
		if (authDao.registerUser(user) <= 0)
			throw new AuthException("this email already exist");
		sendVerificationEmail(user);
		System.out.println("service regestering user " + this);
	}

	private void sendVerificationEmail(User user) {
		String email = user.getEmail();
		String password = user.getPassword();
		String url = "http://localhost:8080/travel-vlog/signup/verify/" + email + "/" + password;
		String html = "<h3>Hi!</h3> <br> <p>Wellcome to travel vlog to verify your user account please click below</p> <a href=\""
				+ url + "\">Chick here</a>";
		mail.sendMail(email, html);
	}

	@Override
	public void verifyUser(String email, String password) {
		if(authDao.verifyUser(email, password) <= 0)
			throw new VerificationException("Please click on the mail that has been sent to your account");
	}

}
