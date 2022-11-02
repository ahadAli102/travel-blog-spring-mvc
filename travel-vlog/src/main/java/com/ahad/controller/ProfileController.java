package com.ahad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ahad.model.User;
import com.ahad.service.user.UserService;
import com.ahad.service.vlog.VlogService;

@Controller
@RequestMapping("/profile")
@SessionAttributes({"login_user"})
public class ProfileController {
	@Autowired
	private UserService userService;
	@Autowired
	private VlogService vlogService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView getRegPage(@ModelAttribute("login_user") User user) {
		ModelAndView modelAndView = new ModelAndView();
		System.out.println("profile="+user);
		if(user.getEmail()==null) {
			modelAndView.setViewName("redirect:/login");
			System.out.println("profile=login");
		}else {
			modelAndView.setViewName("profile");
			modelAndView.addObject("image", userService.getImage(user));
			modelAndView.addObject("user_profile_rating_map", userService.getUserRating(user.getEmail()));
			modelAndView.addObject("user_vlog_rating_map", vlogService.getUserVlogRating(user.getEmail()));
			System.out.println("profile=profile");
		}
		return modelAndView;
	}
	
	@RequestMapping(path = "/uploadImage", method= RequestMethod.POST)
	public ModelAndView uploadProfileImage(@RequestParam("file") CommonsMultipartFile reqFile,@ModelAttribute("login_user") User user) {
		System.out.println("profile upload");
		ModelAndView modelAndView = new ModelAndView();
		System.out.println(reqFile.getOriginalFilename());
		System.out.println(reqFile.getSize());
		userService.saveProfileImage(reqFile, user.getEmail());
		modelAndView.setViewName("redirect:/profile");
		return modelAndView;
	}
	
	@ModelAttribute("login_user")
	public User populateUser() {
		System.out.println("profile populate user");
	    return new User();
	}
}
