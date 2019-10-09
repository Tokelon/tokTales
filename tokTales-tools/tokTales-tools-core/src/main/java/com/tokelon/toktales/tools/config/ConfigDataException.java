package com.tokelon.toktales.tools.config;

public class ConfigDataException extends Exception {

	private static final long serialVersionUID = 7171376086688053435L;

	public ConfigDataException() { }

	public ConfigDataException(String message) {
		super(message);
	}

	public ConfigDataException(Throwable cause) {
		super(cause);
	}

	public ConfigDataException(String message, Throwable cause) {
		super(message, cause);
	}

}
