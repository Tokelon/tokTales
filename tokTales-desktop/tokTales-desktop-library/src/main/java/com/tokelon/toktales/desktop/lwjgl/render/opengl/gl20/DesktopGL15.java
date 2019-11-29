package com.tokelon.toktales.desktop.lwjgl.render.opengl.gl20;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;

import org.lwjgl.opengl.GL15;

import com.tokelon.toktales.core.render.opengl.gl20.IGL15;

/** Desktop OGL2 v1.5 implementation of OpenGL CompatWrapper.
 */
public class DesktopGL15 implements IGL15 {

	
	@Override
	public void glBindBuffer(int target, int buffer) {
		GL15.glBindBuffer(target, buffer);
	}

	@Override
	public void glDeleteBuffers(IntBuffer buffers) {
		GL15.glDeleteBuffers(buffers);
	}

	@Override
	public void glDeleteBuffers(int buffer) {
		GL15.glDeleteBuffers(buffer);
	}

	@Override
	public void glGenBuffers(IntBuffer buffers) {
		GL15.glGenBuffers(buffers);
	}

	@Override
	public int glGenBuffers() {
		return GL15.glGenBuffers();
	}

	@Override
	public boolean glIsBuffer(int buffer) {
		return GL15.glIsBuffer(buffer);
	}

	@Override
	public void glBufferData(int target, ByteBuffer data, int usage) {
		GL15.glBufferData(target, data, usage);
	}

	@Override
	public void glBufferData(int target, ShortBuffer data, int usage) {
		GL15.glBufferData(target, data, usage);
	}

	@Override
	public void glBufferData(int target, IntBuffer data, int usage) {
		GL15.glBufferData(target, data, usage);
	}

	@Override
	public void glBufferData(int target, LongBuffer data, int usage) {
		GL15.glBufferData(target, data, usage);
	}

	@Override
	public void glBufferData(int target, FloatBuffer data, int usage) {
		GL15.glBufferData(target, data, usage);
	}

	@Override
	public void glBufferData(int target, DoubleBuffer data, int usage) {
		GL15.glBufferData(target, data, usage);
	}

	@Override
	public void glBufferSubData(int target, long offset, ByteBuffer data) {
		GL15.glBufferSubData(target, offset, data);
	}

	@Override
	public void glBufferSubData(int target, long offset, ShortBuffer data) {
		GL15.glBufferSubData(target, offset, data);
	}

	@Override
	public void glBufferSubData(int target, long offset, IntBuffer data) {
		GL15.glBufferSubData(target, offset, data);
	}

	@Override
	public void glBufferSubData(int target, long offset, LongBuffer data) {
		GL15.glBufferSubData(target, offset, data);
	}

	@Override
	public void glBufferSubData(int target, long offset, FloatBuffer data) {
		GL15.glBufferSubData(target, offset, data);
	}

	@Override
	public void glBufferSubData(int target, long offset, DoubleBuffer data) {
		GL15.glBufferSubData(target, offset, data);
	}

	@Override
	public void glGetBufferParameteriv(int target, int pname, IntBuffer params) {
		GL15.glGetBufferParameteriv(target, pname, params);
	}

	@Override
	public int glGetBufferParameteri(int target, int pname) {
		return GL15.glGetBufferParameteri(target, pname);
	}

	@Override
	public void glDeleteBuffers(int[] buffers) {
		GL15.glDeleteBuffers(buffers);
	}

	@Override
	public void glGenBuffers(int[] buffers) {
		GL15.glGenBuffers(buffers);
	}

	@Override
	public void glGetBufferParameteriv(int target, int pname, int[] params) {
		GL15.glGetBufferParameteriv(target, pname, params);
	}

}
