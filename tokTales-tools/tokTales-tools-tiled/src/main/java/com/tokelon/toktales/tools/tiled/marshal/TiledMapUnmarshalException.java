package com.tokelon.toktales.tools.tiled.marshal;

public class TiledMapUnmarshalException extends RuntimeException {


	private static final long serialVersionUID = 167355364917271829L;

	public TiledMapUnmarshalException() {
		super();
	}

	public TiledMapUnmarshalException(String message) {
		super(message);
	}

	public TiledMapUnmarshalException(Throwable cause) {
		super(cause);
	}
	
	public TiledMapUnmarshalException(String message, Throwable cause) {
		super(message, cause);
	}

}
