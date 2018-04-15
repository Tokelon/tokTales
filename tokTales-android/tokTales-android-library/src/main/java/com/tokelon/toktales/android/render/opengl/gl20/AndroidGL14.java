package com.tokelon.toktales.android.render.opengl.gl20;

import com.tokelon.toktales.core.render.opengl.gl20.IGL14;

import android.opengl.GLES20;

/** Android OGL2 v1.4 implementation of OpenGL CompatWrapper.
 * <p>
 * Note that for Android, values of large size types (double, long), may be cast down for specific functions.
 */
public class AndroidGL14 implements IGL14 {

	
	@Override
	public void glBlendColor(float red, float green, float blue, float alpha) {
		GLES20.glBlendColor(red, green, blue, alpha);
	}

	@Override
	public void glBlendEquation(int mode) {
		GLES20.glBlendEquation(mode);
	}

	@Override
	public void glBlendFuncSeparate(int sfactorRGB, int dfactorRGB, int sfactorAlpha, int dfactorAlpha) {
		GLES20.glBlendFuncSeparate(sfactorRGB, dfactorRGB, sfactorAlpha, dfactorAlpha);
	}

}
