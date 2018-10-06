package com.tokelon.toktales.core.render;

import org.joml.Matrix4f;

import com.tokelon.toktales.core.game.screen.view.IViewTransformer;

public interface IRenderer {

	
	/** Called when the render context has been created.
	 * 
	 * Here you should initialize all your low level dependencies.
	 * 
	 */
	public void contextCreated();
	
	
	/** Called when the render context has been changed.
	 * 
	 * Here you get the parameters you should work with.
	 * 
	 * @param viewport The viewport you use for rendering.
	 * @param projectionMatrix The projection matrix you use for rendering.
	 */
	public void contextChanged(IViewTransformer viewTransformer, Matrix4f projectionMatrix);
	// TODO: What to do with the projection matrix? include in transformer?
	
	
	/** Called when the render context has been destroyed.
	 * 
	 * Here you should clear all your low level dependencies.
	 * 
	 */
	public void contextDestroyed();
	
}
