package com.ahad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ahad.exception.ResetPasswordException;
import com.ahad.service.auth.AuthService;

@Controller
@RequestMapping("/reset/password")
public class ResetPasswordController {
	
	@Autowired
	private AuthService authService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView getResetPasswordPage() {
		ModelAndView modelAndView = new ModelAndView("reset_password");
//		modelAndView.addObject("reset_password_succesfull", true);
		modelAndView.addObject("reset_email_form", true);
//		modelAndView.addObject("reset_password_exception_message", true);
		return modelAndView;
	}
	
	@RequestMapping(path = "/email", method = RequestMethod.POST)
	public ModelAndView processEmail(@RequestParam("email") String email) {
		ModelAndView modelAndView = new ModelAndView("reset_password");
		authService.sendResetEmail(email);
		modelAndView.addObject("reset_email_sent", true);
		return modelAndView;
	}
	
	@RequestMapping(path = "/{email}/{credential}", method = RequestMethod.GET)
	public ModelAndView processEmail(@PathVariable("email") String email, @PathVariable("credential") String credential) {
		ModelAndView modelAndView = new ModelAndView("reset_password");
		authService.verifyResetPasswordEmailAndCredential(email, credential);
		modelAndView.addObject("reset_password_form", true);
		return modelAndView;
	}
	
	@ExceptionHandler(value = ResetPasswordException.class)
	public ModelAndView loginExceptionHandle(ResetPasswordException exception) {
		ModelAndView modelAndView = new ModelAndView("reset_password");
		String message = exception.getMessage();
		System.out.println("reset password controller: "+exception.getMessage());
		if(message.equals("Failed to reset password : User dosen't exist with this email") 
				|| message.equals("Failed to reset password : Invalid email address")) {
			modelAndView.addObject("reset_email_form", true);
			modelAndView.addObject("reset_password_exception_message", exception.getMessage());
		}
		if(message.equals("Failed to reset password : You have clicked wrong link")) {
			modelAndView.addObject("reset_password_form", true);
			modelAndView.addObject("reset_email_error", "You have clicked wrong link");
		}
		
		System.out.println("reset password error");
		return modelAndView;
	}
}
