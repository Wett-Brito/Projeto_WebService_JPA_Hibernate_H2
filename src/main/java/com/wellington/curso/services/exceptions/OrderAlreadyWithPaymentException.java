package com.wellington.curso.services.exceptions;

public class OrderAlreadyWithPaymentException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public OrderAlreadyWithPaymentException(Long id) {
		super("Order ID: " + id + " already with a payment");
	}
	
	public OrderAlreadyWithPaymentException() {
		super("Order already with a payment");
	}

}
