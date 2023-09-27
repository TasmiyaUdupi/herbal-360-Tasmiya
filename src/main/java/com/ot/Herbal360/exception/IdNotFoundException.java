package com.ot.Herbal360.exception;

public class IdNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -1283925257999522482L;
	
	private String message = "Id Is Not Present";

	public IdNotFoundException(String message) {
		this.message = message;
	}

	public IdNotFoundException() {

	}

	@Override
	public String toString() {
		return message;
	}
}
