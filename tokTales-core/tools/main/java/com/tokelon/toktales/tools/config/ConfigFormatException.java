package com.tokelon.toktales.tools.config;

public class ConfigFormatException extends Exception {

	private static final long serialVersionUID = 4767904873499945974L;

	public ConfigFormatException() {}

	public ConfigFormatException(String message) {
		super(message);
	}

	public ConfigFormatException(Throwable cause) {
		super(cause);
	}

	public ConfigFormatException(String message, Throwable cause) {
		super(message, cause);
	}

}
