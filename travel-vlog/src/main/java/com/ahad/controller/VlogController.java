package com.ahad.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
@RequestMapping("/vlogs")
@SessionAttributes({"login_user"})
public class VlogController {
	@Autowired
	private VlogService vlogService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(path = "/{vlogId}",method = RequestMethod.GET)
	public ModelAndView displaySingleVlog(@ModelAttribute("login_user") User user, @PathVariable("vlogId") int vlogId) {
		ModelAndView modelAndView = new ModelAndView();
		if(user.getEmail()==null) {
			modelAndView.setViewName("redirect:/login");
		}else {
			modelAndView.setViewName("vlog");
			modelAndView.addObject("display_single_vlog",vlogService.getVlog(vlogId));
			modelAndView.addObject("vlog_rating_info", vlogService.getVlogRating(vlogId));
			modelAndView.addObject("vlog_comments",vlogService.getVlogComments(vlogId));
		}
		return modelAndView;
	}
	
	@RequestMapping(path = "/my",method = RequestMethod.GET)
	public ModelAndView getUserVlogs(@ModelAttribute("login_user") User user) {
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
	
	@RequestMapping(path = "/my", params = "q", method = RequestMethod.GET)
	public ModelAndView getUserVlogs(@ModelAttribute("login_user") User user, @RequestParam("q") String query) {
		System.gc();
		ModelAndView modelAndView = new ModelAndView();
		System.out.println("vlogs my = "+user);
		if(user.getEmail()==null) {
			modelAndView.setViewName("redirect:/login");
		}else {
			modelAndView.setViewName("my_vlog");
			modelAndView.addObject("user_vlogs",vlogService.getUserVlogs(user.getEmail(), query));
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
	
	@RequestMapping(path = "/rate-vlog", method= RequestMethod.POST)
	public ModelAndView rateVlog(
			@RequestParam("vlogId") int vlogId,
			@RequestParam("rating") int rating, 
			@ModelAttribute("login_user") User user,
			HttpServletRequest req) {
		
		ModelAndView modelAndView = new ModelAndView();
		System.out.println("vlogs my = "+user);
		if(user.getEmail()==null) {
			modelAndView.setViewName("redirect:/login");
		}else {
			modelAndView.setViewName("redirect:/vlogs/"+vlogId);
			req.getSession().setAttribute("rate_vlog_status", vlogService.rateVlog(vlogId,rating,user.getEmail()));
		}
		return modelAndView;
	}
	
	@RequestMapping(path = "/rate-author", method= RequestMethod.POST)
	public ModelAndView rateAuthor(
			@RequestParam("vlogId") int vlogId,
			@RequestParam("rating") int rating,
			@RequestParam("authorEmail") String authorEmail, 
			@ModelAttribute("login_user") User user,
			HttpServletRequest req) {
		
		ModelAndView modelAndView = new ModelAndView();
		System.out.println("vlogs my = "+user);
		if(user.getEmail()==null) {
			modelAndView.setViewName("redirect:/login");
		}else {
			modelAndView.setViewName("redirect:/vlogs/"+vlogId);
			req.getSession().setAttribute("rate_author_status", userService.rateAuthor(rating,authorEmail,user.getEmail()));
		}
		return modelAndView;
	}
	
	@RequestMapping(path = "/delete", method= RequestMethod.POST)
	public ModelAndView deleteVlog(
			@RequestParam("vlogId") int vlogId,
			@ModelAttribute("login_user") User user,
			HttpServletRequest req) {
		
		ModelAndView modelAndView = new ModelAndView();
		System.out.println("vlogs delete");
		if(user.getEmail()==null) {
			modelAndView.setViewName("redirect:/login");
		}else {
			vlogService.deleteVlog(vlogId);
			modelAndView.setViewName("redirect:/vlogs/my");
		}
		return modelAndView;
	}
	
	@RequestMapping(path = "/{vlogId}/comment",method = RequestMethod.POST)
	public ModelAndView saveComment(
			@ModelAttribute("login_user") User user, 
			@PathVariable("vlogId") int vlogId,
			@RequestParam("comment") String comment) {
		ModelAndView modelAndView = new ModelAndView();
		if(user.getEmail()==null) {
			modelAndView.setViewName("redirect:/login");
		}else {
			modelAndView.setViewName("redirect:/vlogs/"+vlogId);
			modelAndView.addObject("comment_post",vlogService.saveComment(comment,vlogId,user.getEmail()));
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
