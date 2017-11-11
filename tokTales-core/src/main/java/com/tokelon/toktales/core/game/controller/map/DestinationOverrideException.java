package com.tokelon.toktales.core.game.controller.map;

public class DestinationOverrideException extends MapActionException {

	
	private static final long serialVersionUID = -8463104143212331180L;

	public DestinationOverrideException() { }

	public DestinationOverrideException(String message) {
		super(message);
	}

	public DestinationOverrideException(Throwable cause) {
		super(cause);
	}

	public DestinationOverrideException(String message, Throwable cause) {
		super(message, cause);
	}

}
