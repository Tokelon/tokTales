package com.tokelon.toktales.desktop.ui.window;

/** Occurs when there is some unchecked window error.
 */
public class WindowException extends RuntimeException {


	private static final long serialVersionUID = -4847558371291762277L;

	public WindowException() {
		super();
	}

	public WindowException(String message) {
		super(message);
	}

	public WindowException(Throwable cause) {
		super(cause);
	}

	public WindowException(String message, Throwable cause) {
		super(message, cause);
	}

}
