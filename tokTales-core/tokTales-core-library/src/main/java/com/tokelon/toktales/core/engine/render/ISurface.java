package com.tokelon.toktales.core.engine.render;

import org.joml.Matrix4f;

import com.tokelon.toktales.core.game.screen.view.IScreenViewport;

public interface ISurface {

	// TODO: Maybe rename to IRenderSurface
	

	public IScreenViewport getViewport();
	
	// TODO: Replace with IMatrix4f
	public Matrix4f getProjectionMatrix();
	
}
