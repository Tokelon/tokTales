package com.tokelon.toktales.core.game.controller.map;

public class MapActionException extends RuntimeException {

	private static final long serialVersionUID = -4663555126004571168L;

	public MapActionException() { }

	public MapActionException(String message) {
		super(message);
	}

	public MapActionException(Throwable cause) {
		super(cause);
	}

	public MapActionException(String message, Throwable cause) {
		super(message, cause);
	}

}
