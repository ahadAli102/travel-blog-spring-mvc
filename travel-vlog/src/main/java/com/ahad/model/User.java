package com.ahad.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.ahad.validation.StrongPassword;
import com.ahad.validation.ValidName;

public class User {
	@ValidName
	private String name;

	@Email
	private String email;

	@StrongPassword
	@Size(min = 8, max = 50, message = "Password must be between 8 to 10")
	private String password;

	@StrongPassword
	@Size(min = 8, max = 50, message = "Password must be between 8 to 10")
	private String rePassword;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRePassword() {
		return rePassword;
	}

	public void setRePassword(String rePassword) {
		this.rePassword = rePassword;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	@NotEmpty(message = "Please accect condition to use our service")
	private String condition;

	@Override
	public String toString() {
		return "User [name=" + name + ", email=" + email + ", password=" + password + ", rePassword=" + rePassword
				+ ", condition=" + condition + "]";
	}

}
