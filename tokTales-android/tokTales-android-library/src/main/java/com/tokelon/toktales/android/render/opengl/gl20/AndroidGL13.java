package com.tokelon.toktales.android.render.opengl.gl20;

import java.nio.ByteBuffer;

import com.tokelon.toktales.core.render.opengl.gl20.IGL13;

import android.opengl.GLES20;

/** Android OGL2 v1.3 implementation of OpenGL CompatWrapper.
 * <p>
 * Note that for Android, values of large size types (double, long), may be cast down for specific functions.
 */
public class AndroidGL13 implements IGL13 {

	
	@Override
	public void glCompressedTexImage2D(int target, int level, int internalformat, int width, int height, int border, ByteBuffer data) {
		GLES20.glCompressedTexImage2D(target, level, internalformat, width, height, border, data == null ? 0 : data.remaining(), data);
	}

	@Override
	public void glCompressedTexSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, ByteBuffer data) {
		GLES20.glCompressedTexSubImage2D(target, level, xoffset, yoffset, width, height, format, data == null ? 0 : data.remaining(), data);
	}

	@Override
	public void glSampleCoverage(float value, boolean invert) {
		GLES20.glSampleCoverage(value, invert);
	}

	@Override
	public void glActiveTexture(int texture) {
		GLES20.glActiveTexture(texture);
	}

}
