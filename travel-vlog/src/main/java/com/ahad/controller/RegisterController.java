package com.ahad.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/signup")
public class RegisterController {

	@RequestMapping(method = RequestMethod.GET)
	public String getRegPage() {
		System.out.println("signup");
		return "signup";
	}
}
