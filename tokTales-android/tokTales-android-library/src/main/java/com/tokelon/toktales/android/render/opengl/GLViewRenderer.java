package com.tokelon.toktales.android.render.opengl;

import javax.inject.Inject;

import org.joml.Matrix4f;

import com.tokelon.toktales.android.render.IViewRenderer;
import com.tokelon.toktales.android.render.opengl.gl20.AndroidGL11;
import com.tokelon.toktales.core.engine.log.ILogging;
import com.tokelon.toktales.core.game.screen.view.AccurateViewport;
import com.tokelon.toktales.core.render.ISurfaceManager;
import com.tokelon.toktales.core.render.opengl.gl20.GLErrorUtils;

public class GLViewRenderer implements IViewRenderer {
	// Add checks for whether lifecycle has correctly run?


	private final boolean checkGL = true;
	
	private final Object surfaceLock = new Object(); // TODO: Remove if not needed

	
	private ISurfaceManager currentSurfaceManager;
	
	private final GLErrorUtils glErrorUtils;
	
	@Inject
	public GLViewRenderer(ILogging logging) {
		this.glErrorUtils = new GLErrorUtils(logging, new AndroidGL11(), checkGL);
	}
	
	
	/** Returns the surface manager that has been assigned in {@link #onSurfaceCreated(ISurfaceManager)}.
	 * 
	 * @return The current surface manager, or null if there is none.
	 */
	protected ISurfaceManager getCurrentSurfaceManager() {
		return currentSurfaceManager;
	}
	
	
	@Override
	public void onSurfaceCreated(ISurfaceManager surfaceManager) {
		this.currentSurfaceManager = surfaceManager;
		
		synchronized(surfaceLock) {
			glErrorUtils.assertNoGLErrors();
			
			getCurrentSurfaceManager().createSurface(new AccurateViewport(), new Matrix4f());
			getCurrentSurfaceManager().publishSurface();
			
			glErrorUtils.assertNoGLErrors();
		}
	}

	
	@Override
	public void onSurfaceChanged(int width, int height) {
		synchronized(surfaceLock) {
			glErrorUtils.assertNoGLErrors();

			getCurrentSurfaceManager().updateSurface(width, height);
			
			glErrorUtils.assertNoGLErrors();
		}
		
	}
	
	
	@Override
	public void onSurfaceDestroyed() {
		getCurrentSurfaceManager().recallSurface();
		
		this.currentSurfaceManager = null;
	}

	
	@Override
	public void onDrawFrame() {
		// Nothing yet
	}
	
}
