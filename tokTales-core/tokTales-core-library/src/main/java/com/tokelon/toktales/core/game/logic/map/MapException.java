package com.tokelon.toktales.core.game.logic.map;

public class MapException extends Exception {


	private static final long serialVersionUID = -1980670107251401636L;

	public MapException() {
		super();
	}

	public MapException(String message) {
		super(message);
	}

	public MapException(Throwable cause) {
		super(cause);
	}

	public MapException(String message, Throwable cause) {
		super(message, cause);
	}

}
