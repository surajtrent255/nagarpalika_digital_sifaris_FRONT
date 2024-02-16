package com.ishanitech.ipalikawebapp.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 
 * @author Yoomes <yoomesbhujel@gmail.com>
 *
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class CustomInternalServerErrorException extends CustomBaseException {

	private static final long serialVersionUID = 1087677544881686844L;
	private static final HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
	
	public CustomInternalServerErrorException(String message) {
		super(message);
	}
	public HttpStatus getStatus() {
		return status;
	}
}
