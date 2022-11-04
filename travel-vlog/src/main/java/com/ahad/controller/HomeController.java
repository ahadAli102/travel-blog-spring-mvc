package com.ahad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.ahad.model.User;
import com.ahad.service.vlog.VlogService;

@Controller
@RequestMapping("/home")
@SessionAttributes("login_user")
public class HomeController {
	@Autowired
	private VlogService vlogService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView displayAllVlog(@ModelAttribute("login_user") User user) {
		ModelAndView modelAndView = new ModelAndView();
		if(user.getEmail()==null) {
			modelAndView.setViewName("redirect:/login");
		}else {
			modelAndView.setViewName("all_vlog");
			modelAndView.addObject("all_vlogs",vlogService.getAllVlogs(1));
			modelAndView.addObject("previous_page",0);
			modelAndView.addObject("current_page",1);
			modelAndView.addObject("next_page",2);
		}
		return modelAndView;
	}
	
	@RequestMapping(params = "page",method = RequestMethod.GET)
	public ModelAndView displayAllVlog(@ModelAttribute("login_user") User user, @RequestParam("page") int pageNo) {
		ModelAndView modelAndView = new ModelAndView();
		if(user.getEmail()==null) {
			modelAndView.setViewName("redirect:/login");
		}else {
			modelAndView.setViewName("all_vlog");
			modelAndView.addObject("all_vlogs",vlogService.getAllVlogs(pageNo));
			modelAndView.addObject("previous_page",pageNo-1);
			modelAndView.addObject("current_page",pageNo);
			modelAndView.addObject("next_page",pageNo+1);
		}
		return modelAndView;
	}
	
	@ModelAttribute("login_user")
	public User populateUser() {
		System.out.println("home populate user");
	    return new User();
	}
}
