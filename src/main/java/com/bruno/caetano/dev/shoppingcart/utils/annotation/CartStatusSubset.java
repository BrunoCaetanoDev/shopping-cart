package com.bruno.caetano.dev.shoppingcart.utils.annotation;


import com.bruno.caetano.dev.shoppingcart.enums.CartStatus;
import com.bruno.caetano.dev.shoppingcart.utils.validator.CartStatusValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CartStatusValidator.class)
public @interface CartStatusSubset {

	CartStatus[] anyOf();

	String message() default "must be any of {anyOf}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
