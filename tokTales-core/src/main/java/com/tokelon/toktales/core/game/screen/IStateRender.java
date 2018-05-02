package com.tokelon.toktales.core.game.screen;

import com.tokelon.toktales.core.engine.render.ISurface;
import com.tokelon.toktales.core.engine.render.ISurfaceHandler.ISurfaceCallback;
import com.tokelon.toktales.core.game.model.ICamera;
import com.tokelon.toktales.core.game.screen.order.IRenderCallback;
import com.tokelon.toktales.core.game.screen.view.IViewTransformer;
import com.tokelon.toktales.core.render.IRenderer;
import com.tokelon.toktales.core.render.ITextureCoordinator;

/** Manages the rendering context for a state.
 * Can be used as the main renderer.
 */
public interface IStateRender extends ISurfaceCallback, IRenderCallback {

	// Maybe add prepare() and call in gamestate before calling render order
	// Move the render order into here too?

	// Maybe add current* prefix to indicate that surface camera etc can change?
	
	
	/**
	 * @return True if there is a surface, false if not.
	 */
	public boolean hasSurface();
	
	/**
	 * @return The current surface, or null if there is none.
	 */
	public ISurface getSurface();

	
	
	// TODO: This method should make sure that the camera is not updated while rendering is running
	public void updateCamera(ICamera camera);
	public ICamera getCamera();

	// Not sure how this would work since the context viewport is provided by the surface
	//public void updateViewport(IScreenViewport viewport);
	
	// Viewport is already in view transformer
	//public IScreenViewport getContextViewport(); // Not sure about this one
	
	// Can't do that because there might be multiple? -> this will be the main one
	// TODO: Check whether this should return null until the surface is assigned or not
	// And also document that the object can change
	public IViewTransformer getViewTransformer();
	
	
	public ITextureCoordinator getTextureCoordinator();
	
	
	// TODO: Implement with abstract class
	public void addManagedRenderer(String name, IRenderer renderer);
	public IRenderer getManagedRenderer(String name);
	public IRenderer removeManagedRenderer(String name);
	//public boolean removeManagedRenderer(IRenderer renderer); // better?
	public boolean hasManagedRenderer(String name);
	
	// Do like above or like below?
	//public void addManagedRenderer(IRenderer renderer);
	//public boolean removeManagedRenderer(IRenderer renderer);
	
	
	
	/*
	public interface IStateRenderFactory {
		
		public IStateRender create(IGameState gamestate);
	}
	*/
	
}
