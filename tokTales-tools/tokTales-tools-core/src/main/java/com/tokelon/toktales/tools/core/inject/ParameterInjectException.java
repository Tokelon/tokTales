package com.tokelon.toktales.tools.core.inject;

public class ParameterInjectException extends RuntimeException {

	private static final long serialVersionUID = 6916689144228141589L;

	
	public ParameterInjectException() {
		super();
	}

	public ParameterInjectException(String message) {
		super(message);
	}

	public ParameterInjectException(String message, Throwable cause) {
		super(message, cause);
	}

	public ParameterInjectException(Throwable cause) {
		super(cause);
	}

}
