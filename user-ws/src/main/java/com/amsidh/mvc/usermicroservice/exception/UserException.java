package com.amsidh.mvc.usermicroservice.exception;

public class UserException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public UserException(String errorMessage) {
        super(errorMessage);
    }
}
