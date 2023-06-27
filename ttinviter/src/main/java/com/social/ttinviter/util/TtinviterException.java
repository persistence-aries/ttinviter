package com.social.ttinviter.util;

public class TtinviterException extends RuntimeException{
	
	private String message;
	
	public TtinviterException(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
}
