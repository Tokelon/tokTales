package com.tokelon.toktales.android.render.opengl;

import javax.inject.Inject;

import org.joml.Matrix4f;

import com.tokelon.toktales.android.render.IViewRenderer;
import com.tokelon.toktales.android.render.opengl.gl20.AndroidGL11;
import com.tokelon.toktales.core.engine.log.ILogging;
import com.tokelon.toktales.core.render.opengl.gl20.GLErrorUtils;
import com.tokelon.toktales.core.screen.surface.ISurfaceHandler;
import com.tokelon.toktales.core.screen.view.AccurateViewport;

public class GLViewRenderer implements IViewRenderer {
	// Add checks for whether lifecycle has correctly run?


	private final boolean checkGL = true;
	
	private final Object surfaceLock = new Object(); // TODO: Remove if not needed

	
	private ISurfaceHandler currentSurfaceHandler;
	
	private final GLErrorUtils glErrorUtils;
	
	@Inject
	public GLViewRenderer(ILogging logging) {
		this.glErrorUtils = new GLErrorUtils(logging, new AndroidGL11(), checkGL);
	}
	
	
	/** Returns the surface manager that has been assigned in {@link #onSurfaceCreated(ISurfaceHandler)}.
	 * 
	 * @return The current surface manager, or null if there is none.
	 */
	protected ISurfaceHandler getCurrentSurfaceHandler() {
		return currentSurfaceHandler;
	}
	
	
	@Override
	public void onSurfaceCreated(ISurfaceHandler surfaceHandler) {
		this.currentSurfaceHandler = surfaceHandler;
		
		synchronized(surfaceLock) {
			glErrorUtils.assertNoGLErrors();
			
			getCurrentSurfaceHandler().createSurface(new AccurateViewport(), new Matrix4f());
			getCurrentSurfaceHandler().publishSurface();
			
			glErrorUtils.assertNoGLErrors();
		}
	}

	
	@Override
	public void onSurfaceChanged(int width, int height) {
		synchronized(surfaceLock) {
			glErrorUtils.assertNoGLErrors();

			getCurrentSurfaceHandler().updateSurface(width, height);
			
			glErrorUtils.assertNoGLErrors();
		}
		
	}
	
	
	@Override
	public void onSurfaceDestroyed() {
		getCurrentSurfaceHandler().recallSurface();
		
		this.currentSurfaceHandler = null;
	}

	
	@Override
	public void onDrawFrame() {
		// Nothing yet
	}
	
}
