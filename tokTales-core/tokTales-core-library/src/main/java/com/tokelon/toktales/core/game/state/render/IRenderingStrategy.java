package com.tokelon.toktales.core.game.state.render;

import com.tokelon.toktales.core.game.model.ICamera;
import com.tokelon.toktales.core.screen.view.IScreenViewport;
import com.tokelon.toktales.core.screen.view.IViewTransformer;

public interface IRenderingStrategy {

	// TODO: Remove these two and only allow one call for one layer? -> Extend from IRenderCall?
	public String getDescription();
	public void renderCall(IModularGameStateRenderer baseRenderer, String layer, double position);

	// TODO: Refactor these?
	public void prepareFrame(IModularGameStateRenderer baseRenderer);
	public void renderFrame(IModularGameStateRenderer baseRenderer);
	public void renderLayer(IModularGameStateRenderer baseRenderer, String layer);
	
	public IViewTransformer createViewTransformerForRenderer(IModularGameStateRenderer baseRenderer, IScreenViewport masterViewport, ICamera camera, String rendererName);
	
	//public void handleSurfaceChange(ISurface surface);
	
}
