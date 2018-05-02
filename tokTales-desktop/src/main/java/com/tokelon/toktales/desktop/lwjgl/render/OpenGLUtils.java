package com.tokelon.toktales.desktop.lwjgl.render;

import org.lwjgl.opengl.GL11;

import com.tokelon.toktales.core.engine.TokTales;

public final class OpenGLUtils {
	
	private OpenGLUtils() {}

	
	
	public static void checkGLErrors(String tag) {
		
		int error, lastError = GL11.GL_NO_ERROR;
		
		while((error = GL11.glGetError()) != GL11.GL_NO_ERROR) {
			TokTales.getLog().e(tag, "glError: " +error);
			lastError = error;
		}
		
		if(lastError != GL11.GL_NO_ERROR) {
			throw new RuntimeException(tag + " | glError: " +lastError);
		}
	}
	
}
