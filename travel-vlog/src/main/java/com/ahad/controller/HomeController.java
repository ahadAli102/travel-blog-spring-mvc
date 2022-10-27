package com.ahad.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.ahad.model.User;

@Controller
@RequestMapping("/home")
@SessionAttributes("login_user")
public class HomeController {
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView getRegPage(@ModelAttribute("login_user") User user) {
		System.out.println("home");
		ModelAndView modelAndView = new ModelAndView();
		System.out.println("home="+user);
		if(user.getEmail()==null) {
			modelAndView.setViewName("redirect:/login");
		}else {
			modelAndView.setViewName("home");
		}
		return modelAndView;
	}
	
	@ModelAttribute("login_user")
	public User populateUser() {
		System.out.println("home populate user");
	    return new User();
	}
}
