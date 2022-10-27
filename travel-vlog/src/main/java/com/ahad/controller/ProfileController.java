package com.ahad.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ahad.model.User;
import com.ahad.model.Vlog;
import com.ahad.service.user.UserService;

@Controller
@RequestMapping("/profile")
@SessionAttributes("login_user")
public class ProfileController {
	@Autowired
	private UserService userService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView getRegPage(@ModelAttribute("login_user") User user) {
		System.out.println("profile");
		ModelAndView modelAndView = new ModelAndView();
		System.out.println("profile="+user);
		if(user.getEmail()==null) {
			modelAndView.setViewName("redirect:/login");
		}else {
			modelAndView.setViewName("profile");
			modelAndView.addObject("image", userService.getImage(user));
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
		userService.saveProfileImage(reqFile, user.getEmail());
		return modelAndView;
	}
	
	@RequestMapping(path = "/uploadVlog", method= RequestMethod.POST, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public ModelAndView uploadVlog(@ModelAttribute @Valid Vlog vlog, BindingResult bindingResult,
			@RequestParam("images") CommonsMultipartFile[] images,
			@RequestParam("videos") CommonsMultipartFile[] videos) {
		System.out.println("profile upload vlog "+images.length+" "+videos.length);
		System.out.println("profile upload vlog "+images[0].getOriginalFilename()+" "+videos[0].getOriginalFilename());
		if(bindingResult.hasErrors()) {
			System.out.println("Number of errors : "+bindingResult.getErrorCount());
			for(ObjectError objectError : bindingResult.getAllErrors() )
				System.out.println(objectError);
		}
		ModelAndView modelAndView = new ModelAndView("redirect:/profile");
		return modelAndView;
	}
	
	@ModelAttribute("login_user")
	public User populateUser() {
		System.out.println("profile populate user");
	    return new User();
	}
}
