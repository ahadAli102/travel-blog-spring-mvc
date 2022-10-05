package com.ahad.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ahad.exception.AuthException;
import com.ahad.exception.VerificationException;
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
		if (bindingResult.hasErrors()) {
			modelAndView.addObject("reg_error_occured", true);
			return modelAndView;
		}
		authService.registerUser(user);
		System.out.println(user);
		modelAndView.addObject("reg_succesfull", true);
		return modelAndView;
	}
	
	@ExceptionHandler(value=AuthException.class)
	public ModelAndView authenticationExceptionHandle(AuthException exception) {
		ModelAndView modelAndView = new ModelAndView("signup");
		System.out.println("reg controller: "+exception.getMessage());
		modelAndView.addObject("reg_exception_occured", true);
		modelAndView.addObject("reg_exception_message", exception.getMessage());
		return modelAndView;
	}
	
	@ExceptionHandler(value=VerificationException.class)
	public ModelAndView verificationExceptionHandle(VerificationException exception) {
		ModelAndView modelAndView = new ModelAndView("verification");
		System.out.println("reg controller verify error: "+exception.getMessage());
		modelAndView.addObject("reg_verify_exception_occured", true);
		modelAndView.addObject("reg_verify_exception_message", exception.getMessage());
		return modelAndView;
	}
	
	@RequestMapping("/verify/{email}/{password}")
	public ModelAndView verify(@PathVariable("email") String email, @PathVariable("password") String password) {
		ModelAndView modelAndView = new ModelAndView("verification");
		System.out.println("reg controller verify: "+email+" "+password);
		modelAndView.addObject("reg_verify_succesfull", true);
		return modelAndView;
	}
}
