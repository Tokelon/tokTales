package com.tokelon.toktales.android.render.opengl;

import com.tokelon.toktales.core.content.graphics.IRGBAColor;
import com.tokelon.toktales.core.render.IRenderToolkit;
import com.tokelon.toktales.tools.core.objects.params.IParams;

import android.opengl.GLES20;

public class AndroidRenderToolkit implements IRenderToolkit {

	
	@Override
	public void clearSurface(IRGBAColor clearColor) {
		GLES20.glClearColor(clearColor.getRed(), clearColor.getGreen(), clearColor.getBlue(), clearColor.getAlpha());
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
	}
	
	
	public static class AndroidRenderToolkitFactory implements IRenderToolkitFactory {

		@Override
		public IRenderToolkit newRenderToolkit(IParams params) {
			return new AndroidRenderToolkit();
		}
	}

}
