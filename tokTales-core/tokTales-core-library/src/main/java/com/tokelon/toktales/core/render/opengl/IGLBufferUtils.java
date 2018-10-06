package com.tokelon.toktales.core.render.opengl;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;

public interface IGLBufferUtils {
	

	public ByteBuffer createByteBuffer(int capacity);
	
	public ShortBuffer createShortBuffer(int capacity);
	
	public CharBuffer createCharBuffer(int capacity);
	
	public IntBuffer createIntBuffer(int capacity);
	
	public LongBuffer createLongBuffer(int capacity);
	
	public FloatBuffer createFloatBuffer(int capacity);
	
	public DoubleBuffer createDoubleBuffer(int capacity);
    
}
