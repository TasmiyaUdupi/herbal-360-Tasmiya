package com.ot.Herbal360.exception;

public class InvalidCredentialException extends RuntimeException{

	private static final long serialVersionUID = -6626484210381232103L;
	
	private String message = "Invalid Credential";

	public InvalidCredentialException(String message) {
		this.message = message;
	}

	public InvalidCredentialException() {

	}

	@Override
	public String toString() {
		return message;
	}

}
