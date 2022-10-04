package com.ahad.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class StrongPasswordChecker implements ConstraintValidator<StrongPassword, String> {
	public static final String PATTERN = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\\*\\-$%^&+=])(?=\\S+$).{8,}";

	@Override
	public boolean isValid(String password, ConstraintValidatorContext context) {
		System.out.println(password+"    "+password.matches(PATTERN));
		return password.matches(PATTERN);
	}

}
