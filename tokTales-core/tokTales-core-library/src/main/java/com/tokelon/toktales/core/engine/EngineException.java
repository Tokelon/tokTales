package com.tokelon.toktales.core.engine;

public class EngineException extends Exception {

	private static final long serialVersionUID = -8678005634168889539L;

	public EngineException() {
		super();
	}

	public EngineException(String message) {
		super(message);
	}

	public EngineException(Throwable cause) {
		super(cause);
	}

	public EngineException(String message, Throwable cause) {
		super(message, cause);
	}

}
