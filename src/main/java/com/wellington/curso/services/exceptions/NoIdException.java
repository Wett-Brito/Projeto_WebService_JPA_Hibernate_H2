package com.wellington.curso.services.exceptions;

public class NoIdException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public NoIdException(String msg) {
		super(msg);
	}
	
}
