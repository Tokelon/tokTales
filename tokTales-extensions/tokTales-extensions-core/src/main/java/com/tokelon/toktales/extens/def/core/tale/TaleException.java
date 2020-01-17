package com.tokelon.toktales.extens.def.core.tale;

public class TaleException extends Exception {


	private static final long serialVersionUID = 2861121411771441388L;
	
	
	public TaleException() {
		super();
	}

	public TaleException(String message) {
		super(message);
	}

	public TaleException(Throwable cause) {
		super(cause);
	}

	public TaleException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
