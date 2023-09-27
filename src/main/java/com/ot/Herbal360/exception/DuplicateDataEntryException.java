package com.ot.Herbal360.exception;

public class DuplicateDataEntryException extends RuntimeException {

	private static final long serialVersionUID = 1678463990137885673L;
	
	private String message = "This Data Is Already Exists";

	public DuplicateDataEntryException(String message) {
		this.message = message;
	}

	public DuplicateDataEntryException() {

	}

	@Override
	public String toString() {
		return message;
	}
}
