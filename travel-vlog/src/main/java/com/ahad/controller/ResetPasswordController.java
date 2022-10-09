package com.ahad.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ahad.exception.ResetPasswordException;
import com.ahad.service.auth.AuthService;

@Controller
@RequestMapping("/reset/password")
@Validated
public class ResetPasswordController {

	@Autowired
	private AuthService authService;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView getResetPasswordPage() {
		ModelAndView modelAndView = new ModelAndView("reset_password");
		modelAndView.addObject("reset_email_form", true);
//		modelAndView.addObject("reset_password_exception_message", true);
		return modelAndView;
	}

	@RequestMapping(path = "/email", method = RequestMethod.POST)
	public ModelAndView processEmail(@RequestParam("email") String email) {
		ModelAndView modelAndView = new ModelAndView("reset_password");
		authService.sendResetEmail(email);
		modelAndView.addObject("reset_email_sent", true);
		return modelAndView;
	}

	@RequestMapping(path = "/{email}/{credential}", method = RequestMethod.GET)
	public ModelAndView processEmail(@PathVariable("email") String email,
			@PathVariable("credential") String credential) {
		ModelAndView modelAndView = new ModelAndView("reset_password");
		authService.verifyResetPasswordEmailAndCredential(email, credential);
		modelAndView.addObject("reset_password_form", true);
		modelAndView.addObject("email", email);
		modelAndView.addObject("credential", credential);
		return modelAndView;
	}

	@RequestMapping(path = "/submit", method = RequestMethod.POST)
	public ModelAndView processEmail(@RequestParam Map<String, String> parameter,
			@RequestParam("password") String password,
			@RequestParam("rePassword") String rePassword) {
		ModelAndView modelAndView = new ModelAndView("reset_password");
		String email = parameter.get("email");
		String credential = parameter.get("credential");
		System.out.println("reset controller password change " + email + "    " + credential + "    " + password
				+ "    " + rePassword);
		authService.resetPassword(email, credential, password, rePassword);
		modelAndView.addObject("reset_password_succesfull", true);
		return modelAndView;
	}

	@ExceptionHandler(value = ResetPasswordException.class)
	public ModelAndView resetPasswordExceptionHandle(ResetPasswordException exception) {
		ModelAndView modelAndView = new ModelAndView("reset_password");
		String message = exception.getMessage();
		Map<String, Object> params = exception.getParams();
		System.out.println("reset password controller: " + exception.getMessage());
		if (message.equals("Failed to reset password : User dosen't exist with this email")
				|| message.equals("Failed to reset password : Invalid email address")) {
			modelAndView.addObject("reset_email_form", true);
			modelAndView.addObject("reset_password_exception_message", message);
		}
		if (message.equals("Failed to reset password : You have clicked wrong link")) {
			modelAndView.addObject("reset_password_form", true);
			modelAndView.addObject("reset_email_error", "You have clicked wrong link");
			if (params != null) {
				modelAndView.addObject("email", params.get("email"));
				modelAndView.addObject("credential", params.get("credential"));
			}
		}

		if (message.equals("Failed to reset password : Failed to change password")
				|| message.equals("Failed to reset password : Passwords don't matched")
				|| message.equals("Failed to reset password : Password not strong")) {
			modelAndView.addObject("reset_password_form", true);
			modelAndView.addObject("reset_password_exception_message", message);
			if (params != null) {
				modelAndView.addObject("email", params.get("email"));
				modelAndView.addObject("credential", params.get("credential"));
			}
		}
		System.out.println("reset password error");
		return modelAndView;
	}
}
