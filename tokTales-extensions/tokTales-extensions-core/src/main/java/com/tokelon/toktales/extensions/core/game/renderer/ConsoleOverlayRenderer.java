package com.tokelon.toktales.extensions.core.game.renderer;

import com.tokelon.toktales.core.content.graphics.IRGBAColor;
import com.tokelon.toktales.core.content.graphics.RGBAColor;
import com.tokelon.toktales.core.content.manage.codepoint.ICodepointAssetManager;
import com.tokelon.toktales.core.content.text.IFont;
import com.tokelon.toktales.core.engine.IEngine;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.engine.log.ILogging;
import com.tokelon.toktales.core.engine.render.IRenderService;
import com.tokelon.toktales.core.game.controller.IConsoleController;
import com.tokelon.toktales.core.game.model.Camera;
import com.tokelon.toktales.core.game.model.ICamera;
import com.tokelon.toktales.core.game.model.IConsole;
import com.tokelon.toktales.core.game.state.GameStateSuppliers;
import com.tokelon.toktales.core.game.state.IGameState;
import com.tokelon.toktales.core.render.renderer.AbstractRenderer;
import com.tokelon.toktales.core.render.renderer.CharRenderer;
import com.tokelon.toktales.core.render.texture.ITextureCoordinator;
import com.tokelon.toktales.core.screen.view.DefaultViewTransformer;
import com.tokelon.toktales.core.screen.view.IScreenViewport;
import com.tokelon.toktales.core.screen.view.IViewTransformer;
import com.tokelon.toktales.extensions.core.values.ControllerExtensionsValues;
import com.tokelon.toktales.tools.core.inject.ISupplier;
import com.tokelon.toktales.tools.core.objects.options.INamedOptions;

public class ConsoleOverlayRenderer extends AbstractRenderer implements IConsoleOverlayRenderer {


	private float startX;
	private float startY;
	
	private IRGBAColor color = RGBAColor.createFromCode("FFF");
	private float textSize = 32f;
	

	private IViewTransformer customViewTransformer;
	
	private CharRenderer charRenderer;
	
	private final ILogging logging;
	private final ILogger logger;
	private final IRenderService renderService;
	private final ICodepointAssetManager codepointManager;
	private final ISupplier<ITextureCoordinator> textureCoordinatorSupplier;
	private final ISupplier<IConsoleController> consoleControllerSupplier;
	
	public ConsoleOverlayRenderer(
			ILogging logging,
			IEngine engine,
			ICodepointAssetManager codepointManager,
			ISupplier<ITextureCoordinator> textureCoordinatorSupplier,
			ISupplier<IConsoleController> consoleControllerSupplier
	) {
		this.logging = logging;
		this.logger = logging.getLogger(getClass());
		this.renderService = engine.getRenderService();
		this.codepointManager = codepointManager;
		this.textureCoordinatorSupplier = textureCoordinatorSupplier;
		this.consoleControllerSupplier = consoleControllerSupplier;
	}

	
	public void setColor(IRGBAColor color) {
		this.color = color;
	}
	
	public IRGBAColor getColor() {
		return color;
	}
	
	public void setTextSize(float wSize) {
		this.textSize = wSize;
	}
	
	public float getTextSize() {
		return textSize;
	}
	
	
	@Override
	protected void onContextCreated() {
		charRenderer = new CharRenderer(logging, renderService.getRenderAccess(), textureCoordinatorSupplier.get(), codepointManager);
		charRenderer.contextCreated();
	}

	@Override
	protected void onContextChanged() {
		IScreenViewport contextViewport = getViewTransformer().getCurrentViewport();
		
		Camera customCamera = new Camera();
		customCamera.setSize(contextViewport.getWidth(), contextViewport.getHeight());
		
		customViewTransformer = new DefaultViewTransformer(customCamera, contextViewport);
		
		charRenderer.contextChanged(customViewTransformer, getMatrixProjection());
	}

	@Override
	protected void onContextDestroyed() {
		charRenderer.contextDestroyed();
		charRenderer = null;
	}

	
	@Override
	public void renderContents(INamedOptions renderOptions) {
		IConsoleController consoleController = consoleControllerSupplier.get();
		if(consoleController == null) {
			logger.info("Draw was called but no console is available");
			return;
		}
		
		
		drawConsoleOverlay(consoleController);
	}

	
	@Override
	public void drawConsoleOverlay(IConsoleController consoleController) {
		if(!hasView()) {
			assert false : "Cannot draw without view";
			return;
		}
		
		IConsole console = consoleController.getConsole();
		if(!consoleController.isConsoleOpen()) {
			// Do not draw
			return;
		}
		
		
		IFont cFont = consoleController.getFont();
		if(cFont == null) {
			return;
		}
		
		
		// TODO: Refactor - Use console position?
		ICamera camera = customViewTransformer.getCurrentCamera();
		float width = camera.getWidth();
		float height = camera.getHeight();
		
		startX = 10f;
		startY = height * 0.75f;
		
		
		charRenderer.setColor(color);
		charRenderer.setFont(cFont);
		charRenderer.setSize(textSize, textSize);

		
		float hDist = textSize / 2.0f;
		float vDist = textSize;
		float currX = startX;
		float currY = startY;
		charRenderer.startBatch();
		try {
			String consolePrompt = console.getPrompt();
			int promptCodepointCount = consolePrompt.codePointCount(0, consolePrompt.length());
			
			// Prompt
			for(int k = 0; k < promptCodepointCount; k++) {
				int pcp = consolePrompt.codePointAt(k);

				if(currX + hDist > width) {
					currX = 0;
					currY += vDist;
				}
				
				charRenderer.setPosition(currX, currY);
				charRenderer.drawCodepoint(pcp);
				
				currX += hDist;
			}

			// Input
			for(int i = 0; i < console.getCodepointCount(); i++) {
				int cp = console.getCodepointAt(i);
				
				if(currX + hDist > width) {
					currX = 0;
					currY += vDist;
				}
				
				charRenderer.setPosition(currX, currY);
				charRenderer.drawCodepoint(cp);
				
				currX += hDist;
			}
			
		}
		finally {
			charRenderer.finishBatch();	
		}
	}
	
	
	public static class ConsoleOverlayRendererFactory implements IConsoleOverlayRendererFactory {
		
		@Override
		public ConsoleOverlayRenderer create(
				IEngineContext engineContext,
				ICodepointAssetManager codepointManager,
				ISupplier<ITextureCoordinator> textureCoordinatorSupplier,
				ISupplier<IConsoleController> consoleControllerSupplier
		) {
			return new ConsoleOverlayRenderer(
					engineContext.getLogging(),
					engineContext.getEngine(),
					codepointManager,
					textureCoordinatorSupplier,
					consoleControllerSupplier
			);
		}
		
		
		@Override
		public ConsoleOverlayRenderer createForGamestate(IGameState gamestate, ICodepointAssetManager codepointManager) {
			return new ConsoleOverlayRenderer(
					gamestate.getLogging(),
					gamestate.getEngine(),
					codepointManager,
					() -> gamestate.getStateRenderer().getTextureCoordinator(),
					GameStateSuppliers.ofControllerFromManager(gamestate, ControllerExtensionsValues.CONTROLLER_CONSOLE, IConsoleController.class)
			);
		}
		
		@Override
		public ConsoleOverlayRenderer createForGamestate(IGameState gamestate, ICodepointAssetManager codepointManager, ISupplier<IConsoleController> consoleControllerSupplier) {
			return new ConsoleOverlayRenderer(
					gamestate.getLogging(),
					gamestate.getEngine(),
					codepointManager,
					() -> gamestate.getStateRenderer().getTextureCoordinator(),
					consoleControllerSupplier
			);
		}
	}
	
}
