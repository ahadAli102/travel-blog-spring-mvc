package com.ahad.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidNameCheacker implements ConstraintValidator<ValidName, String> {
	public static final String PATTERN = "[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$"; //this regex (allowing Alphabets, Dots, Spaces)

	@Override
	public boolean isValid(String name, ConstraintValidatorContext context) {

		return name.matches(PATTERN);
	}

}