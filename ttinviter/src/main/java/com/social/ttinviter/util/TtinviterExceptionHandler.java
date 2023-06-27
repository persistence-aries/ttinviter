package com.social.ttinviter.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TtinviterExceptionHandler {
	@ExceptionHandler(TtinviterException.class)
	public Object exceptionHandler(TtinviterException e) {
		String message = e.getMessage();
		String msg = "get error: TtinviterException: " + message;
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
	}
	
	@ExceptionHandler(Exception.class)
	public Object exceptionHandler(Exception e) {
		String msg = "get error: Exception";
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(msg);
	}
	
}
