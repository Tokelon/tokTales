package com.tokelon.toktales.desktop.lwjgl.render.opengl;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;

import org.lwjgl.BufferUtils;

import com.tokelon.toktales.core.render.opengl.IGLBufferUtils;

public class DesktopGLBufferUtils implements IGLBufferUtils {

	
	@Override
	public ByteBuffer createByteBuffer(int capacity) {
		return BufferUtils.createByteBuffer(capacity);
	}

	@Override
	public ShortBuffer createShortBuffer(int capacity) {
		return BufferUtils.createShortBuffer(capacity);
	}

	@Override
	public CharBuffer createCharBuffer(int capacity) {
		return BufferUtils.createCharBuffer(capacity);
	}

	@Override
	public IntBuffer createIntBuffer(int capacity) {
		return BufferUtils.createIntBuffer(capacity);
	}

	@Override
	public LongBuffer createLongBuffer(int capacity) {
		return BufferUtils.createLongBuffer(capacity);
	}

	@Override
	public FloatBuffer createFloatBuffer(int capacity) {
		return BufferUtils.createFloatBuffer(capacity);
	}

	@Override
	public DoubleBuffer createDoubleBuffer(int capacity) {
		return BufferUtils.createDoubleBuffer(capacity);
	}

}
