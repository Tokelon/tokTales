package com.tokelon.toktales.core.engine.content;

public class ContentNotFoundException extends ContentException {


	private static final long serialVersionUID = 3416522389140268324L;

	public ContentNotFoundException() {
		super();
	}

	public ContentNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public ContentNotFoundException(String message) {
		super(message);
	}

	public ContentNotFoundException(Throwable cause) {
		super(cause);
	}	
	
}
