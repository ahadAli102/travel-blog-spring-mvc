package com.ahad.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ahad.exception.AuthException;
import com.ahad.model.User;
import com.ahad.service.auth.AuthService;

@Controller
@RequestMapping("/signup")
public class RegisterController {
	
	@Autowired
	private AuthService authService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String getRegPage() {
		System.out.println("signup");
		return "signup";
	}

	@RequestMapping(path = "/process", method = RequestMethod.POST)
	public ModelAndView processUserRegestration(@Valid @ModelAttribute User user,  BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView("signup");
		if (bindingResult.hasErrors())
			modelAndView.addObject("reg_error_occured", true);
		else
			modelAndView.addObject("reg_succesfull", true);
		authService.registerUser(user);
		System.out.println(user);
		return modelAndView;
	}
	
	@ExceptionHandler(value=AuthException.class)
	public ModelAndView nullPointerHandle(AuthException exception) {
		ModelAndView modelAndView = new ModelAndView("signup");
		System.out.println("reg controller: "+exception.getMessage());
		modelAndView.addObject("reg_exception_occured", true);
		modelAndView.addObject("reg_exception_message", exception.getMessage());
		return modelAndView;
	}
}
