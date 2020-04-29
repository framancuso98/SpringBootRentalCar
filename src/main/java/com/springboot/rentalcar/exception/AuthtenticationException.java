package com.springboot.rentalcar.exception;


public class AuthtenticationException extends RuntimeException {

	private static final long serialVersionUID = -8053090608013129747L;
	
	public AuthtenticationException(String message,Throwable cause) {
		super(message,cause);
	}
	
}
