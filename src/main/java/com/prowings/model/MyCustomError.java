package com.prowings.model;

public class MyCustomError {

	private String message;
	private int statusCode;

	public MyCustomError() {
	}

	public MyCustomError(String message, int statusCode) {
		super();
		this.message = message;
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

}
