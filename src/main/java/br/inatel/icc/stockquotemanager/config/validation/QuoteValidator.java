package br.inatel.icc.stockquotemanager.config.validation;

import java.util.Map;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.inatel.icc.stockquotemanager.config.validation.constraint.QuoteConstraint;

public class QuoteValidator implements ConstraintValidator<QuoteConstraint, Map<String, String>> {

	@Override
	public void initialize(QuoteConstraint quotes) {
	}

	@Override
	public boolean isValid(Map<String, String> quotes, ConstraintValidatorContext context) {
		context.disableDefaultConstraintViolation();

		boolean isValid = true;

		try {
			for (Map.Entry<String, String> quote : quotes.entrySet()) {
				if (!quote.getKey().matches("\\d{4}-\\d{2}-\\d{2}")) {
					context.buildConstraintViolationWithTemplate("invalid quote date: " + quote.getKey())
							.addConstraintViolation();
					isValid = false;
				}

				if(!quote.getValue().matches("^[0-9]*([\\.,]{1}[0-9]{0,2}){0,1}$")) {
					context.buildConstraintViolationWithTemplate(
							"invalid quote value: " + quote.getValue() + ". The value have to be a positive number")
							.addConstraintViolation();
					isValid = false;
				}
			}
		} catch (Exception e) {
			context.buildConstraintViolationWithTemplate("invalid quotes").addConstraintViolation();
			isValid = false;
		}

		return isValid;
	}
}
