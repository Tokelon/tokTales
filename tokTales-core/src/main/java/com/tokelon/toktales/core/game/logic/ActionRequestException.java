package com.tokelon.toktales.core.game.logic;

public class ActionRequestException extends RuntimeException {


	private static final long serialVersionUID = -3395688746230336754L;


	public ActionRequestException() { }

	public ActionRequestException(String message) {
		super(message);
	}

	public ActionRequestException(Throwable cause) {
		super(cause);
	}

	public ActionRequestException(String message, Throwable cause) {
		super(message, cause);
	}

}
