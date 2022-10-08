package com.ahad.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/reset/password")
public class ResetPasswordController {
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView getResetPasswordPage() {
		ModelAndView modelAndView = new ModelAndView("reset_password");
		modelAndView.addObject("reset_password_succesfull", true);
//		modelAndView.addObject("reset_email_form", true);
//		modelAndView.addObject("reset_password_form", true);
//		modelAndView.addObject("reset_password_exception_message", true);
		return modelAndView;
	}
	
	
}
