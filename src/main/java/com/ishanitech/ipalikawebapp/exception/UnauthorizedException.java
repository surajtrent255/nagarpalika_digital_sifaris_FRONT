package com.ishanitech.ipalikawebapp.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends CustomBaseException {
	private static final long serialVersionUID = -1192379771647675966L;
	private static final HttpStatus status = HttpStatus.UNAUTHORIZED;
	public UnauthorizedException(String message) {
		super(message);
	}
	
	public HttpStatus getStatus() {
		return status;
	}
}
