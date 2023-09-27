package com.ot.Herbal360.exception;

public class DataNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -8317082164877646679L;
	
	private String message = "Data Not Present";

	public DataNotFoundException(String message) {
		this.message = message;
	}

	public DataNotFoundException() {

	}

	@Override
	public String toString() {
		return message;
	}
}
