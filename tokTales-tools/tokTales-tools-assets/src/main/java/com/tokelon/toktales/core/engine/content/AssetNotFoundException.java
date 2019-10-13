package com.tokelon.toktales.core.engine.content;

public class AssetNotFoundException extends AssetException {


	private static final long serialVersionUID = 4641185753993450985L;

	public AssetNotFoundException() {
		super();
	}

	public AssetNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public AssetNotFoundException(String message) {
		super(message);
	}

	public AssetNotFoundException(Throwable cause) {
		super(cause);
	}	
	
}
