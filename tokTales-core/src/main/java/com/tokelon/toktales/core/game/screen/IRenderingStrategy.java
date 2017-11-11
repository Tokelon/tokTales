package com.tokelon.toktales.core.game.screen;

import com.tokelon.toktales.core.game.model.ICamera;
import com.tokelon.toktales.core.game.screen.view.IScreenViewport;
import com.tokelon.toktales.core.game.screen.view.IViewTransformer;

public interface IRenderingStrategy {

	// TODO: Remove these two and only allow one call for one layer? -> Extend from IRenderCall?
	public String getDescription();
	public void renderCall(IModularStateRender baseRenderer, String layer, double position);

	// TODO: Refactor these?
	public void prepareFrame(IModularStateRender baseRenderer);
	public void renderFrame(IModularStateRender baseRenderer);
	public void renderLayer(IModularStateRender baseRenderer, String layer);
	
	public IViewTransformer createViewTransformerForRenderer(IModularStateRender baseRenderer, IScreenViewport masterViewport, ICamera camera, String rendererName);
	
	//public void handleSurfaceChange(ISurface surface);
	
}
