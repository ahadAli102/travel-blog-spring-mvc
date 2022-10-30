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
import com.ahad.service.vlog.VlogService;

@Controller
@RequestMapping("/vlogs")
@SessionAttributes({"login_user"})
public class VlogController {
	@Autowired
	private VlogService vlogService;
	
	@RequestMapping(path = "/my",method = RequestMethod.GET)
	public ModelAndView getRegPage(@ModelAttribute("login_user") User user) {
		System.gc();
		ModelAndView modelAndView = new ModelAndView();
		System.out.println("vlogs my = "+user);
		if(user.getEmail()==null) {
			modelAndView.setViewName("redirect:/login");
		}else {
			modelAndView.setViewName("my_vlog");
			modelAndView.addObject("user_vlogs",vlogService.getUserVlogs(user.getEmail()));
		}
		return modelAndView;
	}
	
	@RequestMapping(path = "/my/uploadVlog", method= RequestMethod.POST, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public Object uploadVlog(@ModelAttribute @Valid Vlog vlog, BindingResult bindingResult,
			@RequestParam("images") CommonsMultipartFile[] images,
			@RequestParam("videos") CommonsMultipartFile[] videos,
			@ModelAttribute("login_user") User user,
			HttpServletRequest req) {
		System.out.println("my upload vlog "+images.length+" "+videos.length);
		System.out.println("my upload vlog "+images[0].getOriginalFilename()+" "+videos[0].getOriginalFilename());
		ModelAndView modelAndView = new ModelAndView("redirect:/vlogs/my");
		if(bindingResult.hasErrors()) {
			System.out.println("my coltroller: errors "+bindingResult.getErrorCount());
			req.getSession().setAttribute("my_vlog_value_error", true);
		}
		else {
			vlogService.addVlog(vlog, images, videos, user.getEmail());
			System.out.println("my upload vlog added");
			System.out.println("my upload vlog page showed");
		}
		return modelAndView;
	}
	
	@ExceptionHandler(value=VlogException.class)
	public ModelAndView vlogExceptionHandle(VlogException exception,HttpServletRequest req) {
		ModelAndView modelAndView = new ModelAndView("my_vlog");
		System.out.println("my controller VlogException: "+exception.getMessage());
		req.getSession().setAttribute("my_vlog_exception_message", exception.getMessage());
		return modelAndView;
	}
	
	@ModelAttribute("login_user")
	public User populateUser() {
		System.out.println("my populate user");
	    return new User();
	}
}
