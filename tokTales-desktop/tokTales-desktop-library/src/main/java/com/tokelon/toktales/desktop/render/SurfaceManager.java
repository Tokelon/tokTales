package com.tokelon.toktales.desktop.render;

import org.joml.Matrix4f;

import com.tokelon.toktales.core.engine.render.ISurfaceHandler;
import com.tokelon.toktales.core.engine.render.Surface;
import com.tokelon.toktales.core.game.screen.view.AccurateViewport;
import com.tokelon.toktales.core.game.screen.view.IScreenViewport;
import com.tokelon.toktales.desktop.lwjgl.render.GLSurfaceController;

public class SurfaceManager {


	private Surface surface;
	
	private final ISurfaceHandler surfaceHandler;

	public SurfaceManager(ISurfaceHandler surfaceHandler) {
		this.surfaceHandler = surfaceHandler;
	}
	
	
	public void publishSurface() {
		if(surface == null) {
			throw new IllegalStateException("No surface has been created yet");
		}
		
		surfaceHandler.publishSurface(surface, new GLSurfaceController());
		this.updateSurface();
	}
	
	public void updateSurface() {
		surfaceHandler.updateSurface(surface);
	}
	
	public void recallSurface() {
		surfaceHandler.recallSurface(surface);
	}
	

	public void createSurface(String name, int width, int height) {
		IScreenViewport viewport = createViewport(width, height);
		Matrix4f projectionMatrix = createProjectionMatrix(width, height);
		
		createSurface(name, viewport, projectionMatrix);
	}
	
	public void createSurface(String name, IScreenViewport viewport, Matrix4f projectionMatrix) {
		this.surface = new Surface(name, viewport, projectionMatrix);
	}
	
	public void updateSurface(int width, int height) {
		IScreenViewport viewport = createViewport(width, height);
		Matrix4f projectionMatrix = createProjectionMatrix(width, height);
		
		updateSurface(viewport, projectionMatrix);
	}
	
	public void updateSurface(IScreenViewport viewport, Matrix4f projectionMatrix) {
		surface.update(viewport, projectionMatrix);
		
		updateSurface();
	}
	
	
	protected IScreenViewport createViewport(int width, int height) {
		AccurateViewport masterViewport = new AccurateViewport();
		masterViewport.setSize(width, height);
		return masterViewport;
	}
	
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
	
}
