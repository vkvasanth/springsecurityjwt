package com.example.springsecutrityJwtdemo.model;

public class ErrorClazz {
	private int statusCode;
	private String message;
	public ErrorClazz()
	{
		
		
	}
	
	public ErrorClazz(int statusCode, String message) {
		super();
		this.statusCode = statusCode;
		this.message = message;
	}
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "ErrorClazz [statusCode=" + statusCode + ", message=" + message + "]";
	}
	
	

}

