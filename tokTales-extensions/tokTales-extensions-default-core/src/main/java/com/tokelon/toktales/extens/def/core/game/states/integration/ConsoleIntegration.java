package com.tokelon.toktales.extens.def.core.game.states.integration;

import com.tokelon.toktales.core.game.controller.IConsoleController;
import com.tokelon.toktales.core.game.screen.order.IRenderCallback;
import com.tokelon.toktales.core.game.screen.order.IRenderOrder;
import com.tokelon.toktales.core.game.states.IGameState;
import com.tokelon.toktales.extens.def.core.game.screen.ConsoleOverlayRenderer.ConsoleOverlayRendererFactory;
import com.tokelon.toktales.extens.def.core.game.states.integration.IConsoleIntegrationControlHandler.IConsoleIntegrationControlHandlerFactory;
import com.tokelon.toktales.extens.def.core.game.screen.IConsoleOverlayRenderer;

public class ConsoleIntegration implements IConsoleIntegration {

	
	public static final String CONSOLE_OVERLAY_RENDERER_NAME = "console_integration_overlay_renderer";
	
	private static final double DEFAULT_CONSOLE_OVERLAY_RENDER_POSITION = 100d;

	
	private double consoleOverlayRenderPosition = DEFAULT_CONSOLE_OVERLAY_RENDER_POSITION;
	
	private final ConsoleRenderCallback consoleRenderCallback;
	
	private final IGameState gamestate;
	private final IConsoleController consoleController;
	private final IConsoleIntegrationControlHandler controlHandler;
	
	public ConsoleIntegration(IGameState gamestate, IConsoleController consoleController, IConsoleIntegrationControlHandlerFactory controlHandlerFactory) {
		this.gamestate = gamestate;
		this.consoleController = consoleController;
		this.controlHandler = controlHandlerFactory.create(this);
		
		this.consoleRenderCallback = new ConsoleRenderCallback(gamestate, consoleController);
	}
	
	
	public double getConsoleOverlayRenderPosition() {
		return consoleOverlayRenderPosition;
	}
	
	public void setConsoleOverlayRenderPosition(double position) {
		this.consoleOverlayRenderPosition = position;
	}
	
	
	@Override
	public IGameState getGamestate() {
		return gamestate;
	}
	
	@Override
	public IConsoleController getConsoleController() {
		return consoleController;
	}

	@Override
	public IConsoleIntegrationControlHandler getConsoleControlHandler() {
		return controlHandler;
	}
	
	
	
	@Override
	public void onStateEngage(IGameState gamestate) {
		consoleRenderCallback.register();
	}
	
	@Override
	public void onStateDisengage(IGameState gamestate) {
		consoleRenderCallback.unregister();
	}
	
	

	private static class ConsoleRenderCallback implements IRenderCallback {
		
		private final IConsoleOverlayRenderer coRenderer;
		
		private IGameState gamestate;
		private final IConsoleController coController;
		
		public ConsoleRenderCallback(IGameState gamestate, IConsoleController consoleController) {
			this.gamestate = gamestate;
			coController = consoleController;
			
			coRenderer = new ConsoleOverlayRendererFactory().createForGamestate(gamestate, () -> consoleController);
		}
		
		public void register() {
			gamestate.getStateRender().addManagedRenderer(CONSOLE_OVERLAY_RENDERER_NAME, coRenderer);
			gamestate.getRenderOrder().getStackForLayer(IRenderOrder.LAYER_TOP).addCallbackAt(DEFAULT_CONSOLE_OVERLAY_RENDER_POSITION, this);
		}
		
		public void unregister() {
			gamestate.getStateRender().removeManagedRenderer(CONSOLE_OVERLAY_RENDERER_NAME);
			gamestate.getRenderOrder().getStackForLayer(IRenderOrder.LAYER_TOP).removeCallbackAt(DEFAULT_CONSOLE_OVERLAY_RENDER_POSITION);
		}
		
		@Override
		public void renderCall(String layerName, double stackPosition) {
			coRenderer.drawConsoleOverlay(coController);
		}

		@Override
		public String getDescription() {
			return "renders the console overlay";
		}
	}

}
