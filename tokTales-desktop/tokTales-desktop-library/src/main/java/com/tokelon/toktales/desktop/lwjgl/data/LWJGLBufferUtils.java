package com.tokelon.toktales.desktop.lwjgl.data;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

public final class LWJGLBufferUtils {

	private LWJGLBufferUtils() { }
	
	private static final BufferUtilsWrapper wrapper = new BufferUtilsWrapper();
	
	public static BufferUtilsWrapper getWrapper() {
		return wrapper;
	}
	
	
	// Maybe even have an interface for this (testability etc)
	
	public static class BufferUtilsWrapper {
		
		public ByteBuffer createByteBuffer(int capacity) {
			return BufferUtils.createByteBuffer(capacity);
		}
		
		public IntBuffer createIntBuffer(int capacity) {
			return BufferUtils.createIntBuffer(capacity);
		}
		
		
	}
	
}