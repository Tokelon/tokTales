package com.tokelon.toktales.android.render.opengl.gl20;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import com.tokelon.toktales.core.render.opengl.gl20.IGL11;

import android.opengl.GLES20;

/** Android OGL2 v1.1 implementation of OpenGL CompatWrapper.
 * <p>
 * Note that for Android, values of large size types (double, long), may be cast down for specific functions.
 */
public class AndroidGL11 implements IGL11 {
	

	@Override
	public void glEnable(int target) {
		GLES20.glEnable(target);
	}

	@Override
	public void glDisable(int target) {
		GLES20.glDisable(target);
	}

	@Override
	public void glBindTexture(int target, int texture) {
		GLES20.glBindTexture(target, texture);
	}

	@Override
	public void glBlendFunc(int sfactor, int dfactor) {
		GLES20.glBlendFunc(sfactor, dfactor);
	}

	@Override
	public void glClear(int mask) {
		GLES20.glClear(mask);
	}

	@Override
	public void glClearColor(float red, float green, float blue, float alpha) {
		GLES20.glClearColor(red, green, blue, alpha);
	}

	@Override
	public void glClearDepth(double depth) {
		GLES20.glClearDepthf((float)depth);
	}

	@Override
	public void glClearStencil(int s) {
		GLES20.glClearStencil(s);
	}

	@Override
	public void glColorMask(boolean red, boolean green, boolean blue, boolean alpha) {
		GLES20.glColorMask(red, green, blue, alpha);
	}

	@Override
	public void glCullFace(int mode) {
		GLES20.glCullFace(mode);
	}

	@Override
	public void glDepthFunc(int func) {
		GLES20.glDepthFunc(func);
	}

	@Override
	public void glDepthMask(boolean flag) {
		GLES20.glDepthMask(flag);
	}

	@Override
	public void glDepthRange(double zNear, double zFar) {
		GLES20.glDepthRangef((float)zNear, (float)zFar);
	}

	@Override
	public void glDrawArrays(int mode, int first, int count) {
		GLES20.glDrawArrays(mode, first, count);
	}

	@Override
	public void glDrawElements(int mode, int type, ByteBuffer indices) {
		GLES20.glDrawElements(mode, indices.remaining(), type, indices);
	}

	@Override
	public void glDrawElements(int mode, ByteBuffer indices) {
		GLES20.glDrawElements(mode, indices.remaining(), GLES20.GL_UNSIGNED_BYTE, indices);
	}

	@Override
	public void glDrawElements(int mode, ShortBuffer indices) {
		GLES20.glDrawElements(mode, indices.remaining(), GLES20.GL_UNSIGNED_SHORT, indices);
	}

	@Override
	public void glDrawElements(int mode, IntBuffer indices) {
		GLES20.glDrawElements(mode, indices.remaining(), GLES20.GL_UNSIGNED_INT, indices);
	}

	@Override
	public void glFinish() {
		GLES20.glFinish();
	}

	@Override
	public void glFlush() {
		GLES20.glFlush();
	}

	@Override
	public void glFrontFace(int dir) {
		GLES20.glFrontFace(dir);
	}

	@Override
	public void glGenTextures(IntBuffer textures) {
		GLES20.glGenTextures(textures.remaining(), textures);
	}
	
	@Override
	public int glGenTextures() {
		int[] textures = new int[1];
		GLES20.glGenTextures(1, textures, 0);
		return textures[0];
	}

	@Override
	public void glDeleteTextures(IntBuffer textures) {
		GLES20.glDeleteTextures(textures.remaining(), textures);
	}
	
	@Override
	public void glDeleteTextures(int texture) {
		int[] textures = new int[] { texture };
		GLES20.glDeleteTextures(1, textures, 0);
	}

	@Override
	public boolean glGetBoolean(int pname) {
		boolean[] params = new boolean[1];
		GLES20.glGetBooleanv(pname, params, 0);
		return params[0];
	}

	@Override
	public void glGetFloatv(int pname, FloatBuffer params) {
		GLES20.glGetFloatv(pname, params);
	}

	@Override
	public float glGetFloat(int pname) {
		float[] params = new float[1];
		GLES20.glGetFloatv(pname, params, 0);
		return params[0];
	}

	@Override
	public void glGetIntegerv(int pname, IntBuffer params) {
		GLES20.glGetIntegerv(pname, params);
	}

	@Override
	public int glGetInteger(int pname) {
		int[] params = new int[1];
		GLES20.glGetIntegerv(pname, params, 0);
		return params[0];
	}

	@Override
	public int glGetError() {
		return GLES20.glGetError();
	}

	@Override
	public String glGetString(int name) {
		return GLES20.glGetString(name);
	}

	@Override
	public void glGetTexParameteriv(int target, int pname, IntBuffer params) {
		GLES20.glGetTexParameteriv(target, pname, params);
	}

	@Override
	public int glGetTexParameteri(int target, int pname) {
		int[] params = new int[1];
		GLES20.glGetTexParameteriv(target, pname, params, 0);
		return params[0];
	}

	@Override
	public void glGetTexParameterfv(int target, int pname, FloatBuffer params) {
		GLES20.glGetTexParameterfv(target, pname, params);
	}

	@Override
	public float glGetTexParameterf(int target, int pname) {
		float[] params = new float[1];
		GLES20.glGetTexParameterfv(target, pname, params, 0);
		return params[0];
	}

	@Override
	public void glHint(int target, int hint) {
		GLES20.glHint(target, hint);
	}

	@Override
	public boolean glIsEnabled(int cap) {
		return GLES20.glIsEnabled(cap);
	}

	@Override
	public boolean glIsTexture(int texture) {
		return GLES20.glIsTexture(texture);
	}

	@Override
	public void glLineWidth(float width) {
		GLES20.glLineWidth(width);
	}

	@Override
	public void glPixelStorei(int pname, int param) {
		GLES20.glPixelStorei(pname, param);
	}

	@Override
	public void glPolygonOffset(float factor, float units) {
		GLES20.glPolygonOffset(factor, units);
	}

	@Override
	public void glReadPixels(int x, int y, int width, int height, int format, int type, ByteBuffer pixels) {
		GLES20.glReadPixels(x, y, width, height, format, type, pixels);
	}

	@Override
	public void glReadPixels(int x, int y, int width, int height, int format, int type, ShortBuffer pixels) {
		GLES20.glReadPixels(x, y, width, height, format, type, pixels);
	}

	@Override
	public void glReadPixels(int x, int y, int width, int height, int format, int type, IntBuffer pixels) {
		GLES20.glReadPixels(x, y, width, height, format, type, pixels);
	}

	@Override
	public void glReadPixels(int x, int y, int width, int height, int format, int type, FloatBuffer pixels) {
		GLES20.glReadPixels(x, y, width, height, format, type, pixels);
	}

	@Override
	public void glScissor(int x, int y, int width, int height) {
		GLES20.glScissor(x, y, width, height);
	}

	@Override
	public void glStencilFunc(int func, int ref, int mask) {
		GLES20.glStencilFunc(func, ref, mask);
	}

	@Override
	public void glStencilMask(int mask) {
		GLES20.glStencilMask(mask);
	}

	@Override
	public void glStencilOp(int sfail, int dpfail, int dppass) {
		GLES20.glStencilOp(sfail, dpfail, dppass);
	}

	@Override
	public void glTexImage2D(int target, int level, int internalformat, int width, int height, int border, int format, int type, ByteBuffer pixels) {
		GLES20.glTexImage2D(target, level, internalformat, width, height, border, format, type, pixels);
	}

	@Override
	public void glTexImage2D(int target, int level, int internalformat, int width, int height, int border, int format, int type, ShortBuffer pixels) {
		GLES20.glTexImage2D(target, level, internalformat, width, height, border, format, type, pixels);
	}

	@Override
	public void glTexImage2D(int target, int level, int internalformat, int width, int height, int border, int format, int type, IntBuffer pixels) {
		GLES20.glTexImage2D(target, level, internalformat, width, height, border, format, type, pixels);
	}

	@Override
	public void glTexImage2D(int target, int level, int internalformat, int width, int height, int border, int format, int type, FloatBuffer pixels) {
		GLES20.glTexImage2D(target, level, internalformat, width, height, border, format, type, pixels);
	}

	@Override
	public void glTexImage2D(int target, int level, int internalformat, int width, int height, int border, int format, int type, DoubleBuffer pixels) {
		GLES20.glTexImage2D(target, level, internalformat, width, height, border, format, type, pixels);
	}

	@Override
	public void glCopyTexImage2D(int target, int level, int internalFormat, int x, int y, int width, int height, int border) {
		GLES20.glCopyTexImage2D(target, level, internalFormat, x, y, width, height, border);
	}

	@Override
	public void glCopyTexSubImage2D(int target, int level, int xoffset, int yoffset, int x, int y, int width, int height) {
		GLES20.glCopyTexSubImage2D(target, level, xoffset, yoffset, x, y, width, height);
	}

	@Override
	public void glTexParameteri(int target, int pname, int param) {
		GLES20.glTexParameteri(target, pname, param);
	}

	@Override
	public void glTexParameteriv(int target, int pname, IntBuffer params) {
		GLES20.glTexParameteriv(target, pname, params);
	}

	@Override
	public void glTexParameterf(int target, int pname, float param) {
		GLES20.glTexParameterf(target, pname, param);
	}

	@Override
	public void glTexParameterfv(int target, int pname, FloatBuffer params) {
		GLES20.glTexParameterfv(target, pname, params);
	}

	@Override
	public void glTexSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, int type, ByteBuffer pixels) {
		GLES20.glTexSubImage2D(target, level, xoffset, yoffset, width, height, format, type, pixels);
	}

	@Override
	public void glTexSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, int type, ShortBuffer pixels) {
		GLES20.glTexSubImage2D(target, level, xoffset, yoffset, width, height, format, type, pixels);
	}

	@Override
	public void glTexSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, int type, IntBuffer pixels) {
		GLES20.glTexSubImage2D(target, level, xoffset, yoffset, width, height, format, type, pixels);
	}

	@Override
	public void glTexSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, int type, FloatBuffer pixels) {
		GLES20.glTexSubImage2D(target, level, xoffset, yoffset, width, height, format, type, pixels);
	}

	@Override
	public void glTexSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, int type, DoubleBuffer pixels) {
		GLES20.glTexSubImage2D(target, level, xoffset, yoffset, width, height, format, type, pixels);
	}

	@Override
	public void glViewport(int x, int y, int w, int h) {
		GLES20.glViewport(x, y, w, h);
	}

	@Override
	public void glGenTextures(int[] textures) {
		GLES20.glGenTextures(textures.length, textures, 0);
	}

	@Override
	public void glDeleteTextures(int[] textures) {
		GLES20.glDeleteTextures(textures.length, textures, 0);
	}

	@Override
	public void glGetFloatv(int pname, float[] params) {
		GLES20.glGetFloatv(pname, params, 0);
	}

	@Override
	public void glGetIntegerv(int pname, int[] params) {
		GLES20.glGetIntegerv(pname, params, 0);
	}

	@Override
	public void glGetTexParameteriv(int target, int pname, int[] params) {
		GLES20.glGetTexParameteriv(target, pname, params, 0);
	}

	@Override
	public void glGetTexParameterfv(int target, int pname, float[] params) {
		GLES20.glGetTexParameterfv(target, pname, params, 0);
	}

	@Override
	public void glTexParameteriv(int target, int pname, int[] params) {
		GLES20.glTexParameteriv(target, pname, params, 0);
	}

	@Override
	public void glTexParameterfv(int target, int pname, float[] params) {
		GLES20.glTexParameterfv(target, pname, params, 0);
	}

}
