package com.tokelon.toktales.android.render.opengl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import com.tokelon.toktales.core.engine.TokTales;

import android.opengl.GLES20;

public final class OpenGLUtils {
	
	private OpenGLUtils() {}

	
	
	public static FloatBuffer floatValuesAsBuffer(float[] values) {
		
		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(values.length * 4);
		byteBuffer.order(ByteOrder.nativeOrder());
		
		FloatBuffer floatBuffer = byteBuffer.asFloatBuffer();
		floatBuffer.put(values);
		floatBuffer.position(0);
		
		return floatBuffer;
	}
	
	
	public static void checkGLErrors(String tag) {
		
		int error, lastError = GLES20.GL_NO_ERROR;
		
		while((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
			TokTales.getLog().e("AndroidOpenGLUtils", tag + " | glError: " +error);
			lastError = error;
		}
		
		if(lastError != GLES20.GL_NO_ERROR) {
			throw new RuntimeException(tag + " | glError: " +lastError);
		}
	}
	
}
