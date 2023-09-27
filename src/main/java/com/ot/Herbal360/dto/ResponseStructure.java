package com.ot.Herbal360.dto;

import lombok.Data;

@Data
public class ResponseStructure<T> {

	private int status;
	
	private int recordCount;

	private String message;

	private T data;

	

	
}