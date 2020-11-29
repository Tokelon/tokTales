package com.tokelon.toktales.extensions.core.game.state.console;

import javax.inject.Inject;

import com.tokelon.toktales.core.game.model.ICamera;
import com.tokelon.toktales.core.game.state.render.IModularGameStateRenderer;
import com.tokelon.toktales.core.game.state.render.IRenderingStrategy;
import com.tokelon.toktales.core.render.opengl.gl20.IGL11;
import com.tokelon.toktales.core.render.renderer.ISingleRenderer;
import com.tokelon.toktales.core.screen.view.DefaultViewTransformer;
import com.tokelon.toktales.core.screen.view.IScreenViewport;
import com.tokelon.toktales.core.screen.view.IViewTransformer;

public class ConsoleRenderingStrategy implements IRenderingStrategy {
	// Implement conditional clear or limit to 30 fps since we don't need the performance?

	
	public static final String RENDER_LAYER_MAIN = "console_rendering_stategy";
	private static final String RENDER_DESCRIPTION = "ConsoleRenderingStrategy";

	
	private final IGL11 gl11;
	
	@Inject
	public ConsoleRenderingStrategy(IGL11 gl11) {
		this.gl11 = gl11;
	}
	
	
	@Override
	public void renderCall(IModularGameStateRenderer baseRenderer, String layerName, double stackPosition) {
		renderLayer(baseRenderer, layerName);
	}
	
	@Override
	public String getDescription() {
		return RENDER_DESCRIPTION;
	}

	@Override
	public void prepareFrame(IModularGameStateRenderer baseRenderer) {
		// Nothing
	}
	
	@Override
	public void renderFrame(IModularGameStateRenderer baseRenderer) {
		// Clear render canvas
		gl11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		gl11.glClear(IGL11.GL_COLOR_BUFFER_BIT | IGL11.GL_DEPTH_BUFFER_BIT);
		
		
		ISingleRenderer consoleRenderer = baseRenderer.getRenderer(ConsoleGamestate.RENDERER_CONSOLE_NAME);
		consoleRenderer.renderContents();
		
		//ISingleRenderer textRenderer = baseRenderer.getRenderer(ConsoleGamestate.RENDERER_TEXT_NAME);
		//textRenderer.renderFrame();
		
		ISingleRenderer dialogRenderer = baseRenderer.getRenderer(ConsoleGamestate.RENDERER_DIALOG_NAME);
		dialogRenderer.renderContents();
	}
	
	@Override
	public void renderLayer(IModularGameStateRenderer baseRenderer, String layer) {
		if(RENDER_LAYER_MAIN.equals(layer)) {
			renderFrame(baseRenderer);
		}
	}

	
	@Override
	public IViewTransformer createViewTransformerForRenderer(IModularGameStateRenderer baseRenderer, IScreenViewport masterViewport, ICamera camera, String rendererName) {
		DefaultViewTransformer viewTransformer = new DefaultViewTransformer(camera, masterViewport);
		return viewTransformer;
	}
	
}
