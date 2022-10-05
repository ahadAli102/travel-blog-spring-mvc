package com.ahad.utils;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class HTMLMail {
	
	@Autowired
	private JavaMailSender mailSender;

	public void sendMail(String to, String msg) {
		try {

			MimeMessage message = mailSender.createMimeMessage();
			message.setSubject("Verification Email of Travel Vlog account");
			MimeMessageHelper helper;
			helper = new MimeMessageHelper(message, true);
			helper.setFrom("thegentleman660@gmail.com");
			helper.setTo(to);
			helper.setText(msg, true);
			mailSender.send(message);
		} catch (MessagingException ex) {
			Logger.getLogger(HTMLMail.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

}
