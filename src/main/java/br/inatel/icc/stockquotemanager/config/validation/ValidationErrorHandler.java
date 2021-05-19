package br.inatel.icc.stockquotemanager.config.validation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.inatel.icc.stockquotemanager.dto.FormErrorDto;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class ValidationErrorHandler {
private MessageSource messageSource;
	
	@Autowired
	public ValidationErrorHandler(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<FormErrorDto> fieldErrorHandle(MethodArgumentNotValidException exception){
		List<FormErrorDto> errors = new ArrayList<>();
		
		List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
		fieldErrors.forEach(err -> {
			log.error("there was an error with the field: " + err.getField(), err);
			String field = err.getField();
			String message = messageSource.getMessage(err, LocaleContextHolder.getLocale());
		
			FormErrorDto errorDto = new FormErrorDto(field, message);
			
			errors.add(errorDto);
		});
		
		return errors;
	}
	
	@ResponseStatus(code = HttpStatus.CONFLICT)
	@ExceptionHandler(DataIntegrityViolationException.class)
	public FormErrorDto uniqueFieldHandler(DataIntegrityViolationException exception){
		log.error("there was an attribute conflict in the database" + exception);
		return null;
	}
}
