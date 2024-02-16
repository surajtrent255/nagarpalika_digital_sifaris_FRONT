package com.ishanitech.ipalikawebapp.exception;

import com.ishanitech.ipalikawebapp.exception.CustomBaseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 
 * @author Yoomes <yoomesbhujel@gmail.com>
 *
 */

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class ForbiddenException extends CustomBaseException  {

	private static final long serialVersionUID = -4189015613179288610L;
	private static final HttpStatus status = HttpStatus.FORBIDDEN;
	
	public ForbiddenException(String message) {
		super(message);
	}
	public HttpStatus getStatus() {
		return status;
	}
}
