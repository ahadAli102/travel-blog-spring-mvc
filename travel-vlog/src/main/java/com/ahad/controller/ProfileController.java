package com.ahad.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ahad.model.User;

@Controller
@RequestMapping("/profile")
@SessionAttributes("login_user")
public class ProfileController {
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView getRegPage(@ModelAttribute("login_user") User user) {
		System.out.println("profile");
		ModelAndView modelAndView = new ModelAndView();
		System.out.println("profile="+user);
		if(user.getEmail()==null) {
			modelAndView.setViewName("redirect:/login");
		}else {
			modelAndView.setViewName("profile");
		}
		return modelAndView;
	}
	
	@RequestMapping(path = "/uploadImage", method= RequestMethod.POST)
	public ModelAndView uploadProfileImage(@RequestParam("file") CommonsMultipartFile reqFile,@ModelAttribute("login_user") User user) {
		System.out.println("profile upload");
		ModelAndView modelAndView = new ModelAndView("redirect:/profile");
		System.out.println(user);
		System.out.println(reqFile.getOriginalFilename());
		System.out.println(reqFile.getSize());
		return modelAndView;
	}
	
	@ModelAttribute("login_user")
	public User populateUser() {
		System.out.println("profile populate user");
	    return new User();
	}
}
