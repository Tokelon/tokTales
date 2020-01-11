package com.tokelon.toktales.core.render;

import javax.inject.Inject;

import org.joml.Matrix4f;

import com.tokelon.toktales.core.engine.render.ISurface;
import com.tokelon.toktales.core.engine.render.ISurfaceController;
import com.tokelon.toktales.core.engine.render.ISurfaceManager;
import com.tokelon.toktales.core.engine.render.Surface;
import com.tokelon.toktales.core.game.screen.view.AccurateViewport;
import com.tokelon.toktales.core.game.screen.view.IScreenViewport;

public class SurfaceHandler implements ISurfaceHandler {


	public static int defaultSurfaceNameCounter = 0;
	
	private String surfaceName;
	private Surface surface;
	
	private final ISurfaceManager surfaceManager;
	private final ISurfaceController surfaceController;

	@Inject
	public SurfaceHandler(ISurfaceManager surfaceManager, ISurfaceController surfaceController) {
		this.surfaceManager = surfaceManager;
		this.surfaceController = surfaceController;
		this.surfaceName = getDefaultSurfaceName(defaultSurfaceNameCounter++);
	}
	
	
	/** Returns a default surface name for a given index.
	 * 
	 * @param index
	 * @return A default surface name.
	 */
	public String getDefaultSurfaceName(int index) {
		return ISurfaceHandler.class.getSimpleName() + "Surface" + index;
	}
	
	
	@Override
	public void publishSurface() {
		if(surface == null) {
			throw new IllegalStateException("No surface has been created yet");
		}
		
		surfaceManager.publishSurface(surface, surfaceController);
		this.updateSurface();
	}
	
	@Override
	public void updateSurface() {
		surfaceManager.updateSurface(surface);
	}
	
	@Override
	public void recallSurface() {
		surfaceManager.recallSurface(surface);
	}
	

	@Override
	public void createSurface(int width, int height) {
		IScreenViewport viewport = createViewport(width, height);
		Matrix4f projectionMatrix = createProjectionMatrix(width, height);
		
		createSurface(viewport, projectionMatrix);
	}
	
	@Override
	public void createSurface(IScreenViewport viewport, Matrix4f projectionMatrix) {
		this.surface = new Surface(getSurfaceName(), viewport, projectionMatrix);
	}
	
	@Override
	public void updateSurface(int width, int height) {
		IScreenViewport viewport = createViewport(width, height);
		Matrix4f projectionMatrix = createProjectionMatrix(width, height);
		
		updateSurface(viewport, projectionMatrix);
	}
	
	@Override
	public void updateSurface(IScreenViewport viewport, Matrix4f projectionMatrix) {
		surface.update(viewport, projectionMatrix);
		
		updateSurface();
	}
	
	@Override
	public ISurface getSurface() {
		return surface;
	}

	
	@Override
	public String getSurfaceName() {
		return surfaceName;
	}
	
	@Override
	public void setSurfaceName(String name) {
		this.surfaceName = name;
	}
	
	
	/** Creates a viewport with the given properties.
	 * 
	 * @param width
	 * @param height
	 * @return A new viewport.
	 */
	protected IScreenViewport createViewport(int width, int height) {
		AccurateViewport masterViewport = new AccurateViewport();
		masterViewport.setSize(width, height);
		return masterViewport;
	}
	
	/** Creates a projection matrix with the given properties.
	 * 
	 * @param width
	 * @param height
	 * @return A new projection matrix.
	 */
	protected Matrix4f createProjectionMatrix(int width, int height) {
		float glViewportWidth = width;
		float glViewportHeight = height;
		
		Matrix4f projMatrix = new Matrix4f().ortho(
				0.0f, (float)glViewportWidth,
				//0.0f - 1.0f, (float)glViewportWidth - 1.0f,
				
				//0.0f - 1.0f, (float)glViewportHeight - 1.0f,	// Normal y axis (up)
				//(float)glViewportHeight - 1.0f, 0.0f - 1.0f,	// Flip y axis
				(float)glViewportHeight, 0.0f,	// Flip y axis
				
				0.0f, 50.0f
		);
		
		return projMatrix;
	}
	
	
	public static class SurfaceHandlerFactory implements ISurfaceHandlerFactory {
		private final ISurfaceManager surfaceManager;

		@Inject
		public SurfaceHandlerFactory(ISurfaceManager surfaceManager) {
			this.surfaceManager = surfaceManager;
		}

		@Override
		public ISurfaceHandler create(ISurfaceController surfaceController) {
			return new SurfaceHandler(surfaceManager, surfaceController);
		}
	}
	
}
