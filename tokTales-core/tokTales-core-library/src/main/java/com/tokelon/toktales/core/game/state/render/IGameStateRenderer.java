package com.tokelon.toktales.core.game.state.render;

import com.tokelon.toktales.core.game.model.ICamera;
import com.tokelon.toktales.core.render.IRenderCall;
import com.tokelon.toktales.core.render.IRenderContextManager;
import com.tokelon.toktales.core.render.renderer.IRenderer;
import com.tokelon.toktales.core.render.texture.ITextureCoordinator;
import com.tokelon.toktales.core.screen.surface.ISurface;
import com.tokelon.toktales.core.screen.surface.ISurfaceManager.ISurfaceCallback;
import com.tokelon.toktales.core.screen.view.IViewTransformer;

/** Manages the rendering context for a state.
 * Can be used as the main renderer.
 */
public interface IGameStateRenderer extends ISurfaceCallback, IRenderCall, IRenderContextManager {
	/* TODO:
	 * Move render order into here?
	 * Maybe add prepare() and call in gamestate before calling render order
	 * Maybe add current* prefix to indicate that surface camera etc can change?
	*/


	/**
	 * @return True if there is a surface, false if not.
	 */
	public boolean hasSurface();
	
	/**
	 * @return The current surface, or null if there is none.
	 */
	public ISurface getSurface(); // Get currentSurface?
	//public ISurfaceHandler getSurfaceHandler(); Useful?

	
	
	// TODO: This method should make sure that the camera is not updated while rendering is running
	public void updateCamera(ICamera camera);
	public ICamera getCurrentCamera();

	
	// Not sure how this would work since the context viewport is provided by the surface
	//public void updateViewport(IScreenViewport viewport);
	
	// Viewport is already in view transformer
	//public IScreenViewport getContextViewport(); // Not sure about this one
	
	// Can't do that because there might be multiple? -> this will be the main one
	// TODO: Check whether this should return null until the surface is assigned or not
	// And also document that the object can change
	public IViewTransformer getViewTransformer();
	
	
	public ITextureCoordinator getTextureCoordinator();
	
}
