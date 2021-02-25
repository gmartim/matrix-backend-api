package com.league.matrix.exception;

public class OperationNotSupportedException extends BadContentException {

	private static final long serialVersionUID = 1L;

	public OperationNotSupportedException(String message) {
		super(message);
	}

}
