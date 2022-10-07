package com.ahad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ahad.service.auth.AuthService;

@Controller
@RequestMapping("/login")
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
		ModelAndView modelAndView = new ModelAndView("login");
		return modelAndView;
	}
}
