package com.tokelon.toktales.desktop.lwjgl.render.opengl.gl20;

import java.nio.ByteBuffer;

import org.lwjgl.opengl.GL13;

import com.tokelon.toktales.core.render.opengl.gl20.IGL13;

/** Desktop OGL2 v1.3 implementation of OpenGL CompatWrapper.
 */
public class DesktopGL13 implements IGL13 {

	
	@Override
	public void glCompressedTexImage2D(int target, int level, int internalformat, int width, int height, int border, ByteBuffer data) {
		GL13.glCompressedTexImage2D(target, level, internalformat, width, height, border, data);
	}

	@Override
	public void glCompressedTexSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, ByteBuffer data) {
		GL13.glCompressedTexSubImage2D(target, level, xoffset, yoffset, width, height, format, data);
	}

	@Override
	public void glSampleCoverage(float value, boolean invert) {
		GL13.glSampleCoverage(value, invert);
	}

	@Override
	public void glActiveTexture(int texture) {
		GL13.glActiveTexture(texture);
	}

}
