package com.tokelon.toktales.core.engine.render;

import org.joml.Matrix4f;

import com.tokelon.toktales.core.game.screen.view.IScreenViewport;

/** A surface represents an area that can be used to render something.
 */
public interface ISurface {
	// Maybe rename to IRenderSurface
	// Rename methods to getCurrent*() ?
	

	/**
	 * @return The name of the surface.
	 */
	public String getName();
	
	/**
	 * @return The current viewport for the surface.
	 */
	public IScreenViewport getViewport();

	/**
	 * @return The current projection matrix for the surface.
	 */
	public Matrix4f getProjectionMatrix();
	
}
