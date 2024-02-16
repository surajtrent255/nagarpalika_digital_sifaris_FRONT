package com.ishanitech.ipalikawebapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
public class UnsupportedTypeException extends CustomBaseException {
	private static final HttpStatus status = HttpStatus.UNSUPPORTED_MEDIA_TYPE;
	public UnsupportedTypeException(String message) {
		super(message);
	}
	
	public HttpStatus getStatus() {
		return status;
	}
	
}
