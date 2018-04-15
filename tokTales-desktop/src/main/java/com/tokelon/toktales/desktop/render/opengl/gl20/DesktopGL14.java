package com.tokelon.toktales.desktop.render.opengl.gl20;

import org.lwjgl.opengl.GL14;

import com.tokelon.toktales.core.render.opengl.gl20.IGL14;

/** Desktop OGL2 v1.4 implementation of OpenGL CompatWrapper.
 */
public class DesktopGL14 implements IGL14 {

	
	@Override
	public void glBlendColor(float red, float green, float blue, float alpha) {
		GL14.glBlendColor(red, green, blue, alpha);
	}

	@Override
	public void glBlendEquation(int mode) {
		GL14.glBlendEquation(mode);
	}

	@Override
	public void glBlendFuncSeparate(int sfactorRGB, int dfactorRGB, int sfactorAlpha, int dfactorAlpha) {
		GL14.glBlendFuncSeparate(sfactorRGB, dfactorRGB, sfactorAlpha, dfactorAlpha);
	}

}
