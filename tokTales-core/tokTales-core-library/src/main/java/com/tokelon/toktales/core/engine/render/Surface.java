package com.tokelon.toktales.core.engine.render;

import org.joml.Matrix4f;

import com.tokelon.toktales.core.game.screen.view.IScreenViewport;

public class Surface implements ISurface {

	
	private IScreenViewport viewport;
	private Matrix4f projectionMatrix;

	private final String name;
	
	public Surface(String name, IScreenViewport viewport, Matrix4f projectionMatrix) {
		this.name = name;
		this.viewport = viewport;
		this.projectionMatrix = projectionMatrix;
	}

	
	/** Sets a new viewport and projection matrix.
	 * 
	 * @param viewport
	 * @param projectionMatrix
	 */
	public void update(IScreenViewport viewport, Matrix4f projectionMatrix) {
		this.viewport = viewport;
		this.projectionMatrix = projectionMatrix;
	}
	
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public IScreenViewport getViewport() {
		return viewport;
	}

	@Override
	public Matrix4f getProjectionMatrix() {
		return projectionMatrix;
	}

}
