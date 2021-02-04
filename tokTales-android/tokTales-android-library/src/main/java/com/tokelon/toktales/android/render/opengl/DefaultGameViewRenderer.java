package com.tokelon.toktales.android.render.opengl;

import javax.inject.Inject;

import com.tokelon.toktales.core.engine.IEngineDriver;
import com.tokelon.toktales.core.engine.log.ILogging;
import com.tokelon.toktales.core.screen.view.IScreenViewport;

import android.opengl.GLES20;

public class DefaultGameViewRenderer extends GLGameViewRenderer {


	@Inject
	public DefaultGameViewRenderer(ILogging logging, IEngineDriver engineDriver) {
		super(logging, engineDriver);
	}
	
	
	@Override
	public void onDrawFrame() {
		// Disable depth testing
		//GLES20.glDisable(GLES20.GL_DEPTH_TEST);		// Use depth ?
		//GLES20.glDepthMask(false);
		//GLES20.glEnable( GLES20.GL_DEPTH_TEST );
		//GLES20.glDepthFunc( GLES20.GL_LEQUAL );
		//GLES20.glDepthMask(true);
		
		// Disable backface culling
		//GLES20.glDisable(GLES20.GL_CULL_FACE);
		
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
		
		GLES20.glEnable(GLES20.GL_BLEND);
		GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		

		IScreenViewport viewport = getCurrentSurfaceHandler().getSurface().getViewport();
		int width = (int) viewport.getWidth();
		int height = (int) viewport.getHeight();
		
		
		// Default values for the GL Viewport
		int glViewportOffsetHorizontal = 0;
		int glViewportOffsetVertical = 0;
		int glViewportWidth = width;
		int glViewportHeight = height;
		
		
		int viewportX = glViewportOffsetHorizontal;
		int viewportY = height - glViewportHeight - glViewportOffsetVertical;
		int viewportWidth = glViewportWidth;
		int viewportHeight = glViewportHeight;
		
		GLES20.glViewport(viewportX, viewportY, viewportWidth, viewportHeight);
		
		
		super.onDrawFrame();
	}

}
