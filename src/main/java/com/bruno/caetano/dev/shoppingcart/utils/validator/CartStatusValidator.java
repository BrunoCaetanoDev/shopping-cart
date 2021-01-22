package com.bruno.caetano.dev.shoppingcart.utils.validator;

import com.bruno.caetano.dev.shoppingcart.enums.CartStatus;
import com.bruno.caetano.dev.shoppingcart.utils.annotation.CartStatusSubset;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class CartStatusValidator implements ConstraintValidator<CartStatusSubset, String> {

	private CartStatus[] subset;

	@Override
	public void initialize(CartStatusSubset constraint) {
		this.subset = constraint.anyOf();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
		return value == null || Arrays.asList(subset).stream().map(CartStatus::name).anyMatch(s -> s.equals(value));
	}
}
