package com.ahad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.ahad.exception.LoginException;
import com.ahad.model.User;
import com.ahad.service.auth.AuthService;

@Controller
@RequestMapping("/login")
@SessionAttributes("login_user")
public class LoginController {
	
	@Autowired
	private AuthService authService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String getRegPage() {
		System.out.println("login");
		return "login";
	}

	@RequestMapping(path = "/process",method = RequestMethod.POST)
	public ModelAndView processForm(@RequestParam("email") String email, @RequestParam("password") String password) {
		System.out.println(email + "	" + password);
		ModelAndView modelAndView = new ModelAndView("redirect:/home");
		authService.validateLoginInformation(email, password);
		System.out.println("login done");
		User user = new User();
		user.setEmail(email);
		modelAndView.addObject("login_user", user);
		return modelAndView;
	}
	
	@ExceptionHandler(value=LoginException.class)
	public ModelAndView loginExceptionHandle(LoginException exception) {
		ModelAndView modelAndView = new ModelAndView("login");
		System.out.println("login controller: "+exception.getMessage());
		modelAndView.addObject("login_exception_message", exception.getMessage());
		System.out.println("login error");
		return modelAndView;
	}
}
