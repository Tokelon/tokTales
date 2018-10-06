package com.tokelon.toktales.core.engine.storage;

public class StorageUnavailableException extends StorageException {

	private static final long serialVersionUID = -5039839458225443636L;

	public StorageUnavailableException() {
		super();
	}

	public StorageUnavailableException(String message, Throwable cause) {
		super(message, cause);
	}

	public StorageUnavailableException(String message) {
		super(message);
	}

	public StorageUnavailableException(Throwable cause) {
		super(cause);
	}

}
