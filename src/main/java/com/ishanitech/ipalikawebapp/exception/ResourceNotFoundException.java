package com.ishanitech.ipalikawebapp.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 
 * @author Yoomes <yoomesbhujel@gmail.com>
 *
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends CustomBaseException {

	private static final long serialVersionUID = 8818644443178493331L;
	private static final HttpStatus status = HttpStatus.NOT_FOUND;
	
	public ResourceNotFoundException(String message) {
		super(message);
	}
		
	public HttpStatus getStatus() {
		return status;
	}
}
