package com.tokelon.toktales.desktop.render;

import org.joml.Matrix4f;

import com.tokelon.toktales.core.game.screen.view.IScreenViewport;

public class DesktopSurface implements IDesktopSurface {

	
	private final IScreenViewport viewport;
	private final Matrix4f projectionMatrix;
	
	public DesktopSurface(IScreenViewport viewport, Matrix4f projectionMatrix) {
		
		this.viewport = viewport;
		this.projectionMatrix = projectionMatrix;
	}
	
	
	public IScreenViewport getViewport() {
		return viewport;
	}
	
	public Matrix4f getProjectionMatrix() {
		return projectionMatrix;
	}
	
	
}
