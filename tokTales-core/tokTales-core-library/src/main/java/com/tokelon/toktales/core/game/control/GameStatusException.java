package com.tokelon.toktales.core.game.control;

public class GameStatusException extends RuntimeException {

	private static final long serialVersionUID = -7172700246405887187L;

	public GameStatusException() {
		super();
	}

	public GameStatusException(String message) {
		super(message);
	}

	public GameStatusException(Throwable cause) {
		super(cause);
	}

	public GameStatusException(String message, Throwable cause) {
		super(message, cause);
	}

}
