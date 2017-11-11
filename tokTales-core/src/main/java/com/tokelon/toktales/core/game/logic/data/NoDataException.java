package com.tokelon.toktales.core.game.logic.data;

public class NoDataException extends RuntimeException {

	private static final long serialVersionUID = -5580388057929698003L;

	public NoDataException() {
		super();
	}

	public NoDataException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

	public NoDataException(String detailMessage) {
		super(detailMessage);
	}

	public NoDataException(Throwable throwable) {
		super(throwable);
	}
	
	

}
