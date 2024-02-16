package com.ishanitech.ipalikawebapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends CustomBaseException {
	private static final HttpStatus status = HttpStatus.BAD_REQUEST;
	public BadRequestException(String message) {
		super(message);
	}
	public HttpStatus getStatus() {
		return status;
	}
}
