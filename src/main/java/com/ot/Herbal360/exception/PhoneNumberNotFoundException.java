package com.ot.Herbal360.exception;

public class PhoneNumberNotFoundException extends RuntimeException{
	
	private static final long serialVersionUID = 6678759195952731624L;
	
	private String message = "Phone Number Is Not Present";

	public PhoneNumberNotFoundException(String message) {
		this.message = message;
	}

	public PhoneNumberNotFoundException() {

	}

	@Override
	public String toString() {
		return message;
	}

}

