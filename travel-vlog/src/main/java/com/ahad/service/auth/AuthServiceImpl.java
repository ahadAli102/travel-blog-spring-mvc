package com.ahad.service.auth;

import java.util.UUID;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ahad.dao.auth.AuthDao;
import com.ahad.exception.AuthException;
import com.ahad.exception.LoginException;
import com.ahad.exception.ResetPasswordException;
import com.ahad.exception.VerificationException;
import com.ahad.model.User;
import com.ahad.utils.HTMLMail;

@Service
public class AuthServiceImpl implements AuthService {
	@Autowired
	private AuthDao authDao;

	@Autowired
	private HTMLMail mail;
	
	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = 
		    Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);


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
	
	private void sendResetPasswordMail(String email,String credential) {
		String url = "http://localhost:8080/travel-vlog/reset/password/" + email + "/" + credential;
		String html = "<h3>Hi!</h3> <br> <p>To reset password of your user account please click below</p> <a href=\""
				+ url + "\">Chick here</a>";
		mail.sendMail(email, html);
	}

	@Override
	public void verifyUser(String email, String password) {
		if (authDao.verifyUser(email, password) <= 0)
			throw new VerificationException("Please click on the mail that has been sent to your account");
	}

	@Override
	public void validateLoginInformation(String email, String password) {
		User user = authDao.validateLoginInformation(email);
		if (user != null) {
			if(user.getPassword().equals(password)) {
				if (!user.getVerified()) {
					throw new LoginException(
							"User is not verified\nPlease! verify with the link that had been sent to your email");
				}
			}else {
				throw new LoginException("Passwors is not matched");
			}
		} else {
			throw new LoginException("User dosen't exist with this email");
		}
	}

	@Override
	public void sendResetEmail(String email) {
		if(VALID_EMAIL_ADDRESS_REGEX.matcher(email).find()) {
			User user = authDao.getUser(email);
			if (user != null) {
				String credential = UUID.randomUUID().toString().replace('-','_');
				if(authDao.createNewResetPasswordCredential(email,credential)>0) {
					sendResetPasswordMail(email,credential);
				}
			} else {
				throw new ResetPasswordException("User dosen't exist with this email");
			}
		}else {
			throw new ResetPasswordException("Invalid email address");
		}
		
	}

	@Override
	public void verifyResetPasswordEmailAndCredential(String email, String credential) {
		if(!authDao.verifyResetPasswordEmailAndCredential(email, credential))
			throw new ResetPasswordException("You have clicked wrong link");
	}

}
