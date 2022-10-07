package com.ahad.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = StrongPasswordChecker.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface StrongPassword {
	String message() default "Password should contain a digit ,a lower case letter, an upper case letter, a special character(@,*,-,$,^,&,+,=)";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
