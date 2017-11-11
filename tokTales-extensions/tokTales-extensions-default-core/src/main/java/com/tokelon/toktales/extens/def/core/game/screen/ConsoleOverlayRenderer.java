package com.tokelon.toktales.extens.def.core.game.screen;

import com.tokelon.toktales.core.content.IRGBAColor;
import com.tokelon.toktales.core.content.RGBAColorImpl;
import com.tokelon.toktales.core.content.text.ITextureFont;
import com.tokelon.toktales.core.game.controller.IConsoleController;
import com.tokelon.toktales.core.game.controller.IController;
import com.tokelon.toktales.core.game.model.Camera;
import com.tokelon.toktales.core.game.model.ICamera;
import com.tokelon.toktales.core.game.model.IConsole;
import com.tokelon.toktales.core.game.screen.view.DefaultViewTransformer;
import com.tokelon.toktales.core.game.screen.view.IScreenViewport;
import com.tokelon.toktales.core.game.screen.view.IViewTransformer;
import com.tokelon.toktales.core.game.states.IGameState;
import com.tokelon.toktales.core.render.AbstractRenderer;
import com.tokelon.toktales.core.render.CharRenderer;
import com.tokelon.toktales.core.util.INamedOptions;

public class ConsoleOverlayRenderer extends AbstractRenderer implements IConsoleOverlayRenderer {

	public static final String DEFAULT_CONSOLE_CONTROLLER_NAME = "console_controller";

	private String consoleControllername = DEFAULT_CONSOLE_CONTROLLER_NAME;
	

	private float startX;
	private float startY;
	
	private IRGBAColor color = RGBAColorImpl.createFromCode("FFF");
	private float textSize = 32f;
	

	private IViewTransformer customViewTransformer;
	
	private final CharRenderer charRenderer;
	
	private final IGameState gamestate;
	
	public ConsoleOverlayRenderer(IGameState gamestate) {
		this.gamestate = gamestate;
		
		charRenderer = new CharRenderer(gamestate.getEngine().getRenderService().getRenderAccess());
	}

	
	public void setConsoleControllerName(String name) {
		this.consoleControllername = name;
	}
	
	public String getConsoleControllerName() {
		return this.consoleControllername;
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
		IController controller = gamestate.getActiveScene().getControllerManager().getController(consoleControllername);
		if(!(controller instanceof IConsoleController)) {
			assert false : "Invalid type for console controller";
			return;
		}
		IConsoleController consoleController = (IConsoleController) controller;
		
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
	

	@Override
	protected void onContextCreated() {
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
	}

	
}
