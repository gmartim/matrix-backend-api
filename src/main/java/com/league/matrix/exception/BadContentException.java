package com.league.matrix.exception;

public class BadContentException extends Exception {

	private static final long serialVersionUID = 1L;

	public BadContentException(String message) {
		super(message);
	}

	public BadContentException(String message, Throwable cause) {
		super(message, cause);
	}

}
