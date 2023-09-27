package com.ot.Herbal360.exception;

public class EmailIdNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -703739743539325352L;
	
	private String message = "Email-Id Is Not Present";

	public EmailIdNotFoundException(String message) {
		this.message = message;
	}

	public EmailIdNotFoundException() {

	}

	@Override
	public String toString() {
		return message;
	}

}
