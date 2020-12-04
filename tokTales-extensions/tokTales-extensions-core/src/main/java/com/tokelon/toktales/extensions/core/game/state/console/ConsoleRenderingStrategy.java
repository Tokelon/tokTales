package com.tokelon.toktales.extensions.core.game.state.console;

import com.tokelon.toktales.core.game.model.ICamera;
import com.tokelon.toktales.core.game.state.render.IModularGameStateRenderer;
import com.tokelon.toktales.core.game.state.render.IRenderingStrategy;
import com.tokelon.toktales.core.render.renderer.ISingleRenderer;
import com.tokelon.toktales.core.screen.view.DefaultViewTransformer;
import com.tokelon.toktales.core.screen.view.IScreenViewport;
import com.tokelon.toktales.core.screen.view.IViewTransformer;
import com.tokelon.toktales.tools.core.objects.options.INamedOptions;
import com.tokelon.toktales.tools.core.objects.options.NamedOptionsImpl;

public class ConsoleRenderingStrategy implements IRenderingStrategy {
	// Implement conditional clear or limit to 30 fps since we don't need the performance?

	
	private static final String RENDER_DESCRIPTION = "Renders the console";

	private final INamedOptions renderOptions = new NamedOptionsImpl();


	@Override
	public String getDescription() {
		return RENDER_DESCRIPTION;
	}
	
	
	@Override
	public void renderContents(IModularGameStateRenderer baseRenderer) {
		ISingleRenderer consoleRenderer = baseRenderer.getRenderer(ConsoleGamestate.RENDERER_CONSOLE_NAME);
		consoleRenderer.renderContents(renderOptions);
		
		//ISingleRenderer textRenderer = baseRenderer.getRenderer(ConsoleGamestate.RENDERER_TEXT_NAME);
		//textRenderer.renderContents();
		
		ISingleRenderer dialogRenderer = baseRenderer.getRenderer(ConsoleGamestate.RENDERER_DIALOG_NAME);
		dialogRenderer.renderContents(renderOptions);
	}

	
	@Override
	public IViewTransformer createViewTransformerForRenderer(IModularGameStateRenderer baseRenderer, IScreenViewport masterViewport, ICamera camera, String rendererName) {
		DefaultViewTransformer viewTransformer = new DefaultViewTransformer(camera, masterViewport);
		return viewTransformer;
	}
	
}
