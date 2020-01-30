package com.tokelon.toktales.core.render.renderer;

import org.joml.Matrix4f;

import com.tokelon.toktales.core.screen.view.IViewTransformer;

public interface IRenderer {


	/** Called when the render context has been created.
	 * <p>
	 * Low level dependencies should be initialized here.
	 * 
	 */
	public void contextCreated();
	
	
	/** Called when the render context has been changed.
	 * <p>
	 * THe given view properties can be used for rendering later. 
	 * 
	 * @param viewTransformer The view transformer used for rendering.
	 * @param projectionMatrix The projection matrix used for rendering.
	 */
	public void contextChanged(IViewTransformer viewTransformer, Matrix4f projectionMatrix); // TODO: What to do with the projection matrix? Include in transformer?
	
	
	/** Called when the render context has been destroyed.
	 * <p>
	 * Low level dependencies should be disposed here.
	 * 
	 */
	public void contextDestroyed();
	
}
