package com.ahad.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ahad.model.User;

@Controller
@RequestMapping("/signup")
public class RegisterController {

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
		System.out.println("processFristForm");
		System.out.println(user);
		return modelAndView;
	}
}
