package com.tokelon.toktales.desktop.lwjgl.render.opengl.gl20;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import org.lwjgl.opengl.GL11;

import com.tokelon.toktales.core.render.opengl.gl20.IGL11;

/** Desktop OGL2 v1.1 implementation of OpenGL CompatWrapper.
 */
public class DesktopGL11 implements IGL11 {
	
	
	@Override
	public void glEnable(int target) {
		GL11.glEnable(target);
	}

	@Override
	public void glDisable(int target) {
		GL11.glDisable(target);
	}

	@Override
	public void glBindTexture(int target, int texture) {
		GL11.glBindTexture(target, texture);
	}

	@Override
	public void glBlendFunc(int sfactor, int dfactor) {
		GL11.glBlendFunc(sfactor, dfactor);
	}

	@Override
	public void glClear(int mask) {
		GL11.glClear(mask);
	}

	@Override
	public void glClearColor(float red, float green, float blue, float alpha) {
		GL11.glClearColor(red, green, blue, alpha);
	}

	@Override
	public void glClearDepth(double depth) {
		GL11.glClearDepth(depth);
	}

	@Override
	public void glClearStencil(int s) {
		GL11.glClearStencil(s);
	}

	@Override
	public void glColorMask(boolean red, boolean green, boolean blue, boolean alpha) {
		GL11.glColorMask(red, green, blue, alpha);
	}

	@Override
	public void glCullFace(int mode) {
		GL11.glCullFace(mode);
	}

	@Override
	public void glDepthFunc(int func) {
		GL11.glDepthFunc(func);
	}

	@Override
	public void glDepthMask(boolean flag) {
		GL11.glDepthMask(flag);
	}

	@Override
	public void glDepthRange(double zNear, double zFar) {
		GL11.glDepthRange(zNear, zFar);
	}

	@Override
	public void glDrawArrays(int mode, int first, int count) {
		GL11.glDrawArrays(mode, first, count);
	}

	@Override
	public void glDrawElements(int mode, int type, ByteBuffer indices) {
		GL11.glDrawElements(mode, indices);
	}

	@Override
	public void glDrawElements(int mode, ByteBuffer indices) {
		GL11.glDrawElements(mode, indices);
	}

	@Override
	public void glDrawElements(int mode, ShortBuffer indices) {
		GL11.glDrawElements(mode, indices);
	}

	@Override
	public void glDrawElements(int mode, IntBuffer indices) {
		GL11.glDrawElements(mode, indices);
	}

	@Override
	public void glFinish() {
		GL11.glFinish();
	}

	@Override
	public void glFlush() {
		GL11.glFlush();
	}

	@Override
	public void glFrontFace(int dir) {
		GL11.glFrontFace(dir);
	}

	@Override
	public void glGenTextures(IntBuffer textures) {
		GL11.glGenTextures(textures);
	}

	@Override
	public int glGenTextures() {
		return GL11.glGenTextures();
	}

	@Override
	public void glDeleteTextures(IntBuffer textures) {
		GL11.glDeleteTextures(textures);
	}

	@Override
	public void glDeleteTextures(int texture) {
		GL11.glDeleteTextures(texture);
	}

	@Override
	public boolean glGetBoolean(int pname) {
		return GL11.glGetBoolean(pname);
	}

	@Override
	public void glGetFloatv(int pname, FloatBuffer params) {
		GL11.glGetFloatv(pname, params);
	}

	@Override
	public float glGetFloat(int pname) {
		return GL11.glGetFloat(pname);
	}

	@Override
	public void glGetIntegerv(int pname, IntBuffer params) {
		GL11.glGetIntegerv(pname, params);
	}

	@Override
	public int glGetInteger(int pname) {
		return GL11.glGetInteger(pname);
	}

	@Override
	public int glGetError() {
		return GL11.glGetError();
	}

	@Override
	public String glGetString(int name) {
		return GL11.glGetString(name);
	}

	@Override
	public void glGetTexParameteriv(int target, int pname, IntBuffer params) {
		GL11.glGetTexParameteriv(target, pname, params);
	}

	@Override
	public int glGetTexParameteri(int target, int pname) {
		return GL11.glGetTexParameteri(target, pname);
	}

	@Override
	public void glGetTexParameterfv(int target, int pname, FloatBuffer params) {
		GL11.glGetTexParameterfv(target, pname, params);
	}

	@Override
	public float glGetTexParameterf(int target, int pname) {
		return GL11.glGetTexParameterf(target, pname);
	}

	@Override
	public void glHint(int target, int hint) {
		GL11.glHint(target, hint);
	}

	@Override
	public boolean glIsEnabled(int cap) {
		return GL11.glIsEnabled(cap);
	}

	@Override
	public boolean glIsTexture(int texture) {
		return GL11.glIsTexture(texture);
	}

	@Override
	public void glLineWidth(float width) {
		GL11.glLineWidth(width);
	}

	@Override
	public void glPixelStorei(int pname, int param) {
		GL11.glPixelStorei(pname, param);
	}

	@Override
	public void glPolygonOffset(float factor, float units) {
		GL11.glPolygonOffset(factor, units);
	}

	@Override
	public void glReadPixels(int x, int y, int width, int height, int format, int type, ByteBuffer pixels) {
		GL11.glReadPixels(x, y, width, height, format, type, pixels);
	}

	@Override
	public void glReadPixels(int x, int y, int width, int height, int format, int type, ShortBuffer pixels) {
		GL11.glReadPixels(x, y, width, height, format, type, pixels);
	}

	@Override
	public void glReadPixels(int x, int y, int width, int height, int format, int type, IntBuffer pixels) {
		GL11.glReadPixels(x, y, width, height, format, type, pixels);
	}

	@Override
	public void glReadPixels(int x, int y, int width, int height, int format, int type, FloatBuffer pixels) {
		GL11.glReadPixels(x, y, width, height, format, type, pixels);
	}

	@Override
	public void glScissor(int x, int y, int width, int height) {
		GL11.glScissor(x, y, width, height);
	}

	@Override
	public void glStencilFunc(int func, int ref, int mask) {
		GL11.glStencilFunc(func, ref, mask);
	}

	@Override
	public void glStencilMask(int mask) {
		GL11.glStencilMask(mask);
	}

	@Override
	public void glStencilOp(int sfail, int dpfail, int dppass) {
		GL11.glStencilOp(sfail, dpfail, dppass);
	}

	@Override
	public void glTexImage2D(int target, int level, int internalformat, int width, int height, int border, int format, int type, ByteBuffer pixels) {
		GL11.glTexImage2D(target, level, internalformat, width, height, border, format, type, pixels);
	}

	@Override
	public void glTexImage2D(int target, int level, int internalformat, int width, int height, int border, int format, int type, ShortBuffer pixels) {
		GL11.glTexImage2D(target, level, internalformat, width, height, border, format, type, pixels);
	}

	@Override
	public void glTexImage2D(int target, int level, int internalformat, int width, int height, int border, int format, int type, IntBuffer pixels) {
		GL11.glTexImage2D(target, level, internalformat, width, height, border, format, type, pixels);
	}

	@Override
	public void glTexImage2D(int target, int level, int internalformat, int width, int height, int border, int format, int type, FloatBuffer pixels) {
		GL11.glTexImage2D(target, level, internalformat, width, height, border, format, type, pixels);
	}

	@Override
	public void glTexImage2D(int target, int level, int internalformat, int width, int height, int border, int format, int type, DoubleBuffer pixels) {
		GL11.glTexImage2D(target, level, internalformat, width, height, border, format, type, pixels);
	}

	@Override
	public void glCopyTexImage2D(int target, int level, int internalFormat, int x, int y, int width, int height, int border) {
		GL11.glCopyTexImage2D(target, level, internalFormat, x, y, width, height, border);
	}

	@Override
	public void glCopyTexSubImage2D(int target, int level, int xoffset, int yoffset, int x, int y, int width, int height) {
		GL11.glCopyTexSubImage2D(target, level, xoffset, yoffset, x, y, width, height);
	}

	@Override
	public void glTexParameteri(int target, int pname, int param) {
		GL11.glTexParameteri(target, pname, param);
	}

	@Override
	public void glTexParameteriv(int target, int pname, IntBuffer params) {
		GL11.glTexParameteriv(target, pname, params);
	}

	@Override
	public void glTexParameterf(int target, int pname, float param) {
		GL11.glTexParameterf(target, pname, param);
	}

	@Override
	public void glTexParameterfv(int target, int pname, FloatBuffer params) {
		GL11.glTexParameterfv(target, pname, params);
	}

	@Override
	public void glTexSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, int type, ByteBuffer pixels) {
		GL11.glTexSubImage2D(target, level, xoffset, yoffset, width, height, format, type, pixels);
	}

	@Override
	public void glTexSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, int type, ShortBuffer pixels) {
		GL11.glTexSubImage2D(target, level, xoffset, yoffset, width, height, format, type, pixels);
	}

	@Override
	public void glTexSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, int type, IntBuffer pixels) {
		GL11.glTexSubImage2D(target, level, xoffset, yoffset, width, height, format, type, pixels);
	}

	@Override
	public void glTexSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, int type, FloatBuffer pixels) {
		GL11.glTexSubImage2D(target, level, xoffset, yoffset, width, height, format, type, pixels);
	}

	@Override
	public void glTexSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, int type, DoubleBuffer pixels) {
		GL11.glTexSubImage2D(target, level, xoffset, yoffset, width, height, format, type, pixels);
	}

	@Override
	public void glViewport(int x, int y, int w, int h) {
		GL11.glViewport(x, y, w, h);
	}

	@Override
	public void glGenTextures(int[] textures) {
		GL11.glGenTextures(textures);
	}

	@Override
	public void glDeleteTextures(int[] textures) {
		GL11.glDeleteTextures(textures);
	}

	@Override
	public void glGetFloatv(int pname, float[] params) {
		GL11.glGetFloatv(pname, params);
	}

	@Override
	public void glGetIntegerv(int pname, int[] params) {
		GL11.glGetIntegerv(pname, params);
	}

	@Override
	public void glGetTexParameteriv(int target, int pname, int[] params) {
		GL11.glGetTexParameteriv(target, pname, params);
	}

	@Override
	public void glGetTexParameterfv(int target, int pname, float[] params) {
		GL11.glGetTexParameterfv(target, pname, params);
	}

	@Override
	public void glTexParameteriv(int target, int pname, int[] params) {
		GL11.glTexParameteriv(target, pname, params);
	}

	@Override
	public void glTexParameterfv(int target, int pname, float[] params) {
		GL11.glTexParameterfv(target, pname, params);
	}

}
