package com.tokelon.toktales.extensions.core.game.states.console;

import javax.inject.Inject;

import com.tokelon.toktales.core.game.model.ICamera;
import com.tokelon.toktales.core.game.states.IGameState;
import com.tokelon.toktales.core.game.states.render.IModularStateRender;
import com.tokelon.toktales.core.game.states.render.IRenderingStrategy;
import com.tokelon.toktales.core.game.states.render.ISegmentRenderer;
import com.tokelon.toktales.core.render.opengl.gl20.IGL11;
import com.tokelon.toktales.core.screen.view.DefaultViewTransformer;
import com.tokelon.toktales.core.screen.view.IScreenViewport;
import com.tokelon.toktales.core.screen.view.IViewTransformer;
import com.tokelon.toktales.tools.core.objects.options.NamedOptionsImpl;

public class ConsoleRenderingStrategy implements IRenderingStrategy {
	// Implement conditional clear or limit to 30 fps since we don't need the performance?

	
	public static final String RENDER_LAYER_MAIN = "console_rendering_stategy";
	private static final String RENDER_DESCRIPTION = "ConsoleRenderingStrategy";

	
	private final NamedOptionsImpl mNamedOptions = new NamedOptionsImpl();
	
	private final IGL11 gl11;
	
	@Inject
	public ConsoleRenderingStrategy(IGL11 gl11) {
		this.gl11 = gl11;
	}
	
	
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
		// Clear render canvas
		gl11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		gl11.glClear(IGL11.GL_COLOR_BUFFER_BIT | IGL11.GL_DEPTH_BUFFER_BIT);
		
		
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
