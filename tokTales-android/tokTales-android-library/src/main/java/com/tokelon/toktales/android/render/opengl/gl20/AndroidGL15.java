package com.tokelon.toktales.android.render.opengl.gl20;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;

import com.tokelon.toktales.core.render.opengl.gl20.IGL15;

import android.opengl.GLES20;

/** Android OGL2 v1.5 implementation of OpenGL CompatWrapper.
 * <p>
 * Note that for Android, values of large size types (double, long), may be cast down for specific functions.
 */
public class AndroidGL15 implements IGL15 {

	
	@Override
	public void glBindBuffer(int target, int buffer) {
		GLES20.glBindBuffer(target, buffer);
	}

	@Override
	public void glDeleteBuffers(IntBuffer buffers) {
		GLES20.glDeleteBuffers(buffers.remaining(), buffers);
	}
	
	@Override
	public void glDeleteBuffers(int buffer) {
		int[] params = new int[] { buffer };
		GLES20.glDeleteBuffers(1, params, 0);
	}

	@Override
	public void glGenBuffers(IntBuffer buffers) {
		GLES20.glGenBuffers(buffers.remaining(), buffers);
	}

	@Override
	public int glGenBuffers() {
		int[] params = new int[1];
		GLES20.glGenBuffers(1, params, 0);
		return params[0];
	}

	@Override
	public boolean glIsBuffer(int buffer) {
		return GLES20.glIsBuffer(buffer);
	}

	@Override
	public void glBufferData(int target, ByteBuffer data, int usage) {
		GLES20.glBufferData(target, data.remaining(), data, usage);
	}

	@Override
	public void glBufferData(int target, ShortBuffer data, int usage) {
		GLES20.glBufferData(target, data.remaining() * 2, data, usage);
	}

	@Override
	public void glBufferData(int target, IntBuffer data, int usage) {
		GLES20.glBufferData(target, data.remaining() * 4, data, usage);
	}

	@Override
	public void glBufferData(int target, LongBuffer data, int usage) {
		GLES20.glBufferData(target, data.remaining() * 8, data, usage);
	}

	@Override
	public void glBufferData(int target, FloatBuffer data, int usage) {
		GLES20.glBufferData(target, data.remaining() * 4, data, usage);
	}

	@Override
	public void glBufferData(int target, DoubleBuffer data, int usage) {
		GLES20.glBufferData(target, data.remaining() * 8, data, usage);
	}

	@Override
	public void glBufferSubData(int target, long offset, ByteBuffer data) {
		GLES20.glBufferSubData(target, (int)offset, data.remaining(), data);
	}

	@Override
	public void glBufferSubData(int target, long offset, ShortBuffer data) {
		GLES20.glBufferSubData(target, (int)offset, data.remaining() * 2, data);
	}

	@Override
	public void glBufferSubData(int target, long offset, IntBuffer data) {
		GLES20.glBufferSubData(target, (int)offset, data.remaining() * 4, data);
	}

	@Override
	public void glBufferSubData(int target, long offset, LongBuffer data) {
		GLES20.glBufferSubData(target, (int)offset, data.remaining() * 8, data);
	}

	@Override
	public void glBufferSubData(int target, long offset, FloatBuffer data) {
		GLES20.glBufferSubData(target, (int)offset, data.remaining() * 4, data);
	}

	@Override
	public void glBufferSubData(int target, long offset, DoubleBuffer data) {
		GLES20.glBufferSubData(target, (int)offset, data.remaining() * 8, data);
	}

	@Override
	public void glGetBufferParameteriv(int target, int pname, IntBuffer params) {
		GLES20.glGetBufferParameteriv(target, pname, params);
	}

	@Override
	public int glGetBufferParameteri(int target, int pname) {
		int[] params = new int[1];
		GLES20.glGetBufferParameteriv(target, pname, params, 0);
		return params[0];
	}

	@Override
	public void glDeleteBuffers(int[] buffers) {
		GLES20.glDeleteBuffers(buffers.length, buffers, 0);
	}

	@Override
	public void glGenBuffers(int[] buffers) {
		GLES20.glGenBuffers(buffers.length, buffers, 0);
	}

	@Override
	public void glGetBufferParameteriv(int target, int pname, int[] params) {
		GLES20.glGetBufferParameteriv(target, pname, params, 0);
	}

}
