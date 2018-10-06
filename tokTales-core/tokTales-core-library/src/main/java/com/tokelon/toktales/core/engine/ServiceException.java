package com.tokelon.toktales.core.engine;

public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = 7201690579669178783L;

	public ServiceException() {	}

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(Throwable cause) {
		super(cause);
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

}
