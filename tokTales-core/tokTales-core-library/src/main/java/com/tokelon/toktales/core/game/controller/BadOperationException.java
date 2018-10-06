package com.tokelon.toktales.core.game.controller;

public class BadOperationException extends RuntimeException {


	private static final long serialVersionUID = -4323830502329678600L;

	public BadOperationException() {
		super();
	}

	public BadOperationException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

	public BadOperationException(String detailMessage) {
		super(detailMessage);
	}

	public BadOperationException(Throwable throwable) {
		super(throwable);
	}

	
	
}
