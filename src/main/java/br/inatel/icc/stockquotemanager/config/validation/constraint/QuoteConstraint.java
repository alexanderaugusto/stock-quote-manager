package br.inatel.icc.stockquotemanager.config.validation.constraint;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import br.inatel.icc.stockquotemanager.config.validation.QuoteValidator;

@Documented
@Constraint(validatedBy = QuoteValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface QuoteConstraint {
	String message() default "invalid quotes";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
