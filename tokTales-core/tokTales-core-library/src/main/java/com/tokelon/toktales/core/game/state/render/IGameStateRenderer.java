package com.tokelon.toktales.core.game.state.render;

import com.tokelon.toktales.core.game.model.ICamera;
import com.tokelon.toktales.core.render.IRenderCall;
import com.tokelon.toktales.core.render.IRenderContextManager;
import com.tokelon.toktales.core.render.order.IRenderOrder;
import com.tokelon.toktales.core.render.texture.ITextureCoordinator;
import com.tokelon.toktales.core.screen.surface.ISurface;
import com.tokelon.toktales.core.screen.surface.ISurfaceManager.ISurfaceCallback;
import com.tokelon.toktales.core.screen.view.IViewTransformer;
import com.tokelon.toktales.tools.core.objects.options.INamedOptions;

/** Manages the rendering context for a state.
 */
public interface IGameStateRenderer {


	/** Renders the state this renderer is assigned to.
	 */
	public void renderState();
	
	/** Returns an optional render call for the given name.
	 * 
	 * @param contentName
	 * @param renderOptions
	 * @return A render call for the given name.
	 */
	public IRenderCall getRenderCall(String contentName, INamedOptions renderOptions);
	

	/** Returns the render order for this renderer.
	 * 
	 * @return A render order.
	 */
	public IRenderOrder getRenderOrder();
	
	/**
	 * @return True if there is a surface, false if not.
	 */
	public boolean hasSurface();
	
	/**
	 * @return The current surface, or null if there is none.
	 */
	public ISurface getSurface(); // Get currentSurface?

	
	
	// TODO: This method should make sure that the camera is not updated while rendering is running
	/** Sets the camera that should be used for rendering.
	 * 
	 * @param camera
	 */
	public void updateCamera(ICamera camera);

	/**
	 * @return The current camera.
	 */
	public ICamera getCurrentCamera();

	
	// Not sure how this would work since the context viewport is provided by the surface
	//public void updateViewport(IScreenViewport viewport);
	
	// Viewport is already in view transformer
	//public IScreenViewport getContextViewport(); // Not sure about this one
	
	// Can't do that because there might be multiple? -> this will be the main one
	// TODO: Check whether this should return null until the surface is assigned or not
	// And also document that the object can change
	/** Returns the current view transformer that is used as the main view transformer.
	 * 
	 * @return A view transformer.
	 */
	public IViewTransformer getViewTransformer();
	
	
	/**
	 * @return The texture coordinator for this state renderer.
	 */
	public ITextureCoordinator getTextureCoordinator();

	/**
	 * @return The context manager for this state renderer. 
	 */
	public IRenderContextManager getContextManager();

	/**
	 * @return The surface callback for this state renderer, or null if there is none.
	 */
	public ISurfaceCallback getSurfaceCallback();

}
