package com.tokelon.toktales.extens.def.core.game.screen;

import java.util.function.Supplier;

import com.tokelon.toktales.core.content.IRGBAColor;
import com.tokelon.toktales.core.content.RGBAColorImpl;
import com.tokelon.toktales.core.content.text.ITextureFont;
import com.tokelon.toktales.core.engine.IEngine;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.engine.render.IRenderService;
import com.tokelon.toktales.core.game.controller.IConsoleController;
import com.tokelon.toktales.core.game.model.Camera;
import com.tokelon.toktales.core.game.model.ICamera;
import com.tokelon.toktales.core.game.model.IConsole;
import com.tokelon.toktales.core.game.screen.view.DefaultViewTransformer;
import com.tokelon.toktales.core.game.screen.view.IScreenViewport;
import com.tokelon.toktales.core.game.screen.view.IViewTransformer;
import com.tokelon.toktales.core.game.states.GameStateSuppliers;
import com.tokelon.toktales.core.game.states.IGameState;
import com.tokelon.toktales.core.render.AbstractRenderer;
import com.tokelon.toktales.core.render.CharRenderer;
import com.tokelon.toktales.core.render.ITextureCoordinator;
import com.tokelon.toktales.core.util.INamedOptions;
import com.tokelon.toktales.extens.def.core.values.ControllerExtensionsValues;

public class ConsoleOverlayRenderer extends AbstractRenderer implements IConsoleOverlayRenderer {

	public static final String TAG = "ConsoleOverlayRenderer";

	
	private float startX;
	private float startY;
	
	private IRGBAColor color = RGBAColorImpl.createFromCode("FFF");
	private float textSize = 32f;
	

	private IViewTransformer customViewTransformer;
	
	private CharRenderer charRenderer;
	
	private final ILogger logger;
	private final IRenderService renderService;
	private final Supplier<ITextureCoordinator> textureCoordinatorSupplier;
	private final Supplier<IConsoleController> consoleControllerSupplier;
	
	public ConsoleOverlayRenderer(
			ILogger logger,
			IEngine engine,
			Supplier<ITextureCoordinator> textureCoordinatorSupplier,
			Supplier<IConsoleController> consoleControllerSupplier
	) {
		this.logger = logger;
		this.renderService = engine.getRenderService();
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
		charRenderer = new CharRenderer(renderService.getRenderAccess(), textureCoordinatorSupplier.get());
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
	public void prepare(long currentTimeMillis) {
		if(!hasView()) {
			assert false : "Cannot prepare without view";
			return;
		}
		
		ICamera camera = customViewTransformer.getCurrentCamera();
		
		startX = 10f;
		startY = camera.getHeight() * 0.75f;
	}
	

	@Override
	public void drawLayer(INamedOptions options, String layerName) {
		// Not supported
		assert false : "Not supported";
	}

	
	@Override
	public void drawFull(INamedOptions options) {
		IConsoleController consoleController = consoleControllerSupplier.get();
		if(consoleController == null) {
			logger.i(TAG, "Draw was called but no console is available");
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
		
		
		ITextureFont cFont = consoleController.getFont();
		if(cFont == null) {
			assert false : "No font for console controller";
			return;
		}
		
		
		ICamera camera = customViewTransformer.getCurrentCamera();
		float width = camera.getWidth();
		float height = camera.getHeight();
		
		charRenderer.setColor(color);
		charRenderer.setFont(cFont);
		charRenderer.setSize(textSize, textSize);

		
		float hDist = textSize / 2.0f;
		float vDist = textSize;
		float currX = startX;
		float currY = startY;
		charRenderer.startBatchDraw();
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
			charRenderer.finishBatchDraw();	
		}
	}
	
	
	public static class ConsoleOverlayRendererFactory implements IConsoleOverlayRendererFactory {
		
		@Override
		public ConsoleOverlayRenderer create(
				IEngineContext engineContext,
				Supplier<ITextureCoordinator> textureCoordinatorSupplier,
				Supplier<IConsoleController> consoleControllerSupplier
		) {
			return new ConsoleOverlayRenderer(
					engineContext.getLog(),
					engineContext.getEngine(),
					textureCoordinatorSupplier,
					consoleControllerSupplier
			);
		}
		
		
		@Override
		public ConsoleOverlayRenderer createForGamestate(IGameState gamestate) {
			return new ConsoleOverlayRenderer(
					gamestate.getLog(),
					gamestate.getEngine(),
					() -> gamestate.getStateRender().getTextureCoordinator(),
					GameStateSuppliers.ofControllerFromManager(gamestate, ControllerExtensionsValues.CONTROLLER_CONSOLE, IConsoleController.class)
			);
		}
		
		@Override
		public ConsoleOverlayRenderer createForGamestate(IGameState gamestate, Supplier<IConsoleController> consoleControllerSupplier) {
			return new ConsoleOverlayRenderer(
					gamestate.getLog(),
					gamestate.getEngine(),
					() -> gamestate.getStateRender().getTextureCoordinator(),
					consoleControllerSupplier
			);
		}
	}
	
}
