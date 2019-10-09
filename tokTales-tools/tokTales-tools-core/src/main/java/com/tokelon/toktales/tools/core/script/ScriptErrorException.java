package com.tokelon.toktales.tools.core.script;

public class ScriptErrorException extends Exception {

	private static final long serialVersionUID = 8055861730126148352L;

	
	public ScriptErrorException() {
		super();
	}

	public ScriptErrorException(String message, Throwable cause) {
		super(message, cause);
	}

	public ScriptErrorException(String message) {
		super(message);
	}

	public ScriptErrorException(Throwable cause) {
		super(cause);
	}

}
