package com.tokelon.toktales.core.game.state.render;

import com.tokelon.toktales.core.game.model.ICamera;
import com.tokelon.toktales.core.render.IRenderCall;
import com.tokelon.toktales.core.screen.view.IScreenViewport;
import com.tokelon.toktales.core.screen.view.IViewTransformer;

public interface IRenderingStrategy {
	//public void handleSurfaceChange(ISurface surface);


	/**
	 * @return A description for this strategy.
	 */
	public String getDescription();

	/** Returns a render call for the given content name.
	 * <p>
	 * The default implementation returns a render call from the base renderer.
	 * 
	 * @param baseRenderer
	 * @param contentName
	 * @return A render call.
	 */
	public default IRenderCall getRenderCall(IModularGameStateRenderer baseRenderer, String contentName) {
		return baseRenderer.getRenderCall(contentName);
	}


	/** Renders the contents of this strategy.
	 * 
	 * @param baseRenderer
	 */
	public void renderContents(IModularGameStateRenderer baseRenderer);
	

	/** Creates a view transformer for the renderer with the given name.
	 * 
	 * @param baseRenderer
	 * @param masterViewport
	 * @param camera
	 * @param rendererName
	 * @return A view transformer.
	 */
	public IViewTransformer createViewTransformerForRenderer(IModularGameStateRenderer baseRenderer, IScreenViewport masterViewport, ICamera camera, String rendererName);
	
}
