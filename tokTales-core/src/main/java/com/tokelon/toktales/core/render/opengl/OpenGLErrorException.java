package com.tokelon.toktales.core.render.opengl;

import java.util.List;

import com.tokelon.toktales.core.render.opengl.IGLErrorUtils.IGLError;

public class OpenGLErrorException extends RuntimeException {

	private static final long serialVersionUID = -6258583222038993658L;

	
	private final List<IGLError> errorList;
	
	public OpenGLErrorException(List<IGLError> errorList) {
		super();
		this.errorList = errorList;
	}

	public OpenGLErrorException(String message, List<IGLError> errorList) {
		super(message);
		this.errorList = errorList;
	}
	
	
	public List<IGLError> getErrors() {
		return errorList;
	}

}
