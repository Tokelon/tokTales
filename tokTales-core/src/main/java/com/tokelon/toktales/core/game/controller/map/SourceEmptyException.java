package com.tokelon.toktales.core.game.controller.map;

public class SourceEmptyException extends MapActionException {

	
	private static final long serialVersionUID = -445982719477904388L;

	public SourceEmptyException() {	}

	public SourceEmptyException(String message) {
		super(message);
	}

	public SourceEmptyException(Throwable cause) {
		super(cause);
	}

	public SourceEmptyException(String message, Throwable cause) {
		super(message, cause);
	}

}
