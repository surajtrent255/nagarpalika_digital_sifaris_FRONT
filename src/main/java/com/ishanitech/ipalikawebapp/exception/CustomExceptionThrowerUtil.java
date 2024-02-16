package com.ishanitech.ipalikawebapp.exception;

public class CustomExceptionThrowerUtil {
    public static CustomBaseException throwException(int statusCode, String message) {
        switch(statusCode) {
            case 400:
                return new BadRequestException(message);
            case 401:
                return new UnauthorizedException(message);
            case 403:
                return new ForbiddenException(message);
            case 404:
                return new ResourceNotFoundException(message);
            case 405:
                return new MethodNotSupportedException(message);
            case 415:
                return new UnsupportedTypeException(message);
            default:
                return new CustomInternalServerErrorException(message);
        }
    }
}
