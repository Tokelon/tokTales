package com.tokelon.toktales.extens.def.core.game.screen;

import com.tokelon.toktales.core.game.model.ICamera;
import com.tokelon.toktales.core.game.screen.IModularStateRender;
import com.tokelon.toktales.core.game.screen.IRenderingStrategy;
import com.tokelon.toktales.core.game.screen.ISegmentRenderer;
import com.tokelon.toktales.core.game.screen.view.DefaultViewTransformer;
import com.tokelon.toktales.core.game.screen.view.IScreenViewport;
import com.tokelon.toktales.core.game.screen.view.IViewTransformer;
import com.tokelon.toktales.core.game.states.IGameState;
import com.tokelon.toktales.core.util.NamedOptionsImpl;
import com.tokelon.toktales.extens.def.core.game.states.ConsoleGamestate;

public class ConsoleRenderingStrategy implements IRenderingStrategy {

	public static final String RENDER_LAYER_MAIN = "console_rendering_stategy";
	private static final String RENDER_DESCRIPTION = "ConsoleRenderingStrategy";
	
	private final NamedOptionsImpl mNamedOptions = new NamedOptionsImpl();
	
	
	@Override
	public void renderCall(IModularStateRender baseRenderer, String layerName, double stackPosition) {
		renderLayer(baseRenderer, layerName);
	}
	
	@Override
	public String getDescription() {
		return RENDER_DESCRIPTION;
	}

	@Override
	public void prepareFrame(IModularStateRender baseRenderer) {
		// Nothing
	}
	
	@Override
	public void renderFrame(IModularStateRender baseRenderer) {
		IGameState gamestate = baseRenderer.getGamestate();
		
		long gameTimeMillis = gamestate.getGame().getTimeManager().getGameTimeMillis();
		
		
		ISegmentRenderer consoleRenderer = baseRenderer.getSegmentRenderer(ConsoleGamestate.RENDERER_CONSOLE_NAME); 
		
		consoleRenderer.prepare(gameTimeMillis);
		consoleRenderer.drawFull(mNamedOptions);
		
		
		ISegmentRenderer textRenderer = baseRenderer.getSegmentRenderer(ConsoleGamestate.RENDERER_TEXT_NAME);
		
		textRenderer.prepare(gameTimeMillis);
		//textRenderer.drawFull(mNamedOptions);
		
		
		ISegmentRenderer dialogRenderer = baseRenderer.getSegmentRenderer(ConsoleGamestate.RENDERER_DIALOG_NAME);
		dialogRenderer.prepare(gameTimeMillis);
		dialogRenderer.drawFull(mNamedOptions);
		
	}
	
	@Override
	public void renderLayer(IModularStateRender baseRenderer, String layer) {
		if(RENDER_LAYER_MAIN.equals(layer)) {
			renderFrame(baseRenderer);
		}
	}

	
	@Override
	public IViewTransformer createViewTransformerForRenderer(IModularStateRender baseRenderer, IScreenViewport masterViewport, ICamera camera, String rendererName) {
		DefaultViewTransformer viewTransformer = new DefaultViewTransformer(camera, masterViewport);
		return viewTransformer;
	}
	
}
