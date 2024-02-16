package com.ishanitech.ipalikawebapp.exception;

import com.ishanitech.ipalikawebapp.exception.CustomBaseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
public class MethodNotSupportedException extends CustomBaseException {
	private static final long serialVersionUID = 608165085370255781L;
	private static final HttpStatus status = HttpStatus.METHOD_NOT_ALLOWED;
	public MethodNotSupportedException(String message) {
		super(message);
	}
	
	public HttpStatus getStatus() {
		return status;
	}

}
