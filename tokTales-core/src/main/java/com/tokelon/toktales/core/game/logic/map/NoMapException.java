package com.tokelon.toktales.core.game.logic.map;

public class NoMapException extends Exception {


	private static final long serialVersionUID = 815254660496346394L;

	public NoMapException() {
		super();
	}

	public NoMapException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoMapException(String message) {
		super(message);
	}

	public NoMapException(Throwable cause) {
		super(cause);
	}

}
