package com.ahad.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ahad.exception.VlogException;
import com.ahad.model.User;
import com.ahad.model.Vlog;
import com.ahad.service.user.UserService;
import com.ahad.service.vlog.VlogService;

@Controller
@RequestMapping("/profile")
@SessionAttributes({"login_user","image"})
public class ProfileController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private VlogService vlogService;
	
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
	public String uploadProfileImage(@RequestParam("file") CommonsMultipartFile reqFile,@ModelAttribute("login_user") User user) {
		System.out.println("profile upload");
		System.out.println(user);
		System.out.println(reqFile.getOriginalFilename());
		System.out.println(reqFile.getSize());
		userService.saveProfileImage(reqFile, user.getEmail());
		return "redirect:/profile";
	}
	
	@RequestMapping(path = "/uploadVlog", method= RequestMethod.POST, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public Object uploadVlog(@ModelAttribute @Valid Vlog vlog, BindingResult bindingResult,
			@RequestParam("images") CommonsMultipartFile[] images,
			@RequestParam("videos") CommonsMultipartFile[] videos,
			@ModelAttribute("login_user") User user,
			HttpServletRequest req) {
		System.out.println("profile upload vlog "+images.length+" "+videos.length);
		System.out.println("profile upload vlog "+images[0].getOriginalFilename()+" "+videos[0].getOriginalFilename());
		ModelAndView modelAndView = new ModelAndView("/profile");
		if(bindingResult.hasErrors()) {
			System.out.println("profile coltroller: errors "+bindingResult.getErrorCount());
			req.getSession().setAttribute("profile_add_vlog_value_error", true);
			return modelAndView;
		}
		else {
			vlogService.addVlog(vlog, images, videos, user.getEmail());
			System.out.println("profile upload vlog added");
			System.out.println("profile upload vlog page showed");
			return "redirect:/profile";
		}
	}
	
	@ExceptionHandler(value=VlogException.class)
	public ModelAndView vlogExceptionHandle(VlogException exception,HttpServletRequest req) {
		ModelAndView modelAndView = new ModelAndView("/profile");
		System.out.println("profile controller VlogException: "+exception.getMessage());
		req.getSession().setAttribute("profile_add_vlog_exception_message", exception.getMessage());
		return modelAndView;
	}
	
	@ModelAttribute("login_user")
	public User populateUser() {
		System.out.println("profile populate user");
	    return new User();
	}
}
