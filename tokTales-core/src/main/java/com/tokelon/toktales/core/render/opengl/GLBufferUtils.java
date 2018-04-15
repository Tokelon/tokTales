package com.tokelon.toktales.core.render.opengl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;

public class GLBufferUtils implements IGLBufferUtils {
	

	@Override
	public ByteBuffer createByteBuffer(int capacity) {
		return ByteBuffer.allocateDirect(capacity).order(ByteOrder.nativeOrder());
	}

	@Override
	public ShortBuffer createShortBuffer(int capacity) {
		return createByteBuffer(capacity * 2).asShortBuffer();
	}

	@Override
	public CharBuffer createCharBuffer(int capacity) {
		return createByteBuffer(capacity * 2).asCharBuffer();
	}

	@Override
	public IntBuffer createIntBuffer(int capacity) {
		return createByteBuffer(capacity * 4).asIntBuffer();
	}

	@Override
	public LongBuffer createLongBuffer(int capacity) {
		return createByteBuffer(capacity * 8).asLongBuffer();
	}

	@Override
	public FloatBuffer createFloatBuffer(int capacity) {
		return createByteBuffer(capacity * 4).asFloatBuffer();
	}

	@Override
	public DoubleBuffer createDoubleBuffer(int capacity) {
		return createByteBuffer(capacity * 8).asDoubleBuffer();
	}

}
