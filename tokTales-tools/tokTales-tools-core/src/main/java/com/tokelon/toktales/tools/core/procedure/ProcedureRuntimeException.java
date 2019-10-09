package com.tokelon.toktales.tools.core.procedure;

public class ProcedureRuntimeException extends RuntimeException {


	private static final long serialVersionUID = -6229819998755426617L;

	public ProcedureRuntimeException() {
		super();
	}

	public ProcedureRuntimeException(String message) {
		super(message);
	}

	public ProcedureRuntimeException(Throwable cause) {
		super(cause);
	}
	
	public ProcedureRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

}
