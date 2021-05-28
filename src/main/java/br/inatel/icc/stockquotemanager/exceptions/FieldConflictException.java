package br.inatel.icc.stockquotemanager.exceptions;

import br.inatel.icc.stockquotemanager.dto.FormErrorDto;

public class FieldConflictException extends Exception{
	private static final long serialVersionUID = 1L;

	private FormErrorDto error;
	
	public FieldConflictException(FormErrorDto error) {
		this.error = error;
	}

	public FormErrorDto getError() {
		return error;
	}
}
