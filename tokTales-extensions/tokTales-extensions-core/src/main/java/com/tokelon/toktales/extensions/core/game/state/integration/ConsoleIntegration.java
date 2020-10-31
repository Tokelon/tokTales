package com.tokelon.toktales.extensions.core.game.state.integration;

import com.tokelon.toktales.core.content.manage.codepoint.ICodepointAssetManager;
import com.tokelon.toktales.core.game.controller.IConsoleController;
import com.tokelon.toktales.core.game.state.IGameState;
import com.tokelon.toktales.core.render.IRenderCall;
import com.tokelon.toktales.core.render.order.IRenderOrder;
import com.tokelon.toktales.extensions.core.game.renderer.IConsoleOverlayRenderer;
import com.tokelon.toktales.extensions.core.game.renderer.ConsoleOverlayRenderer.ConsoleOverlayRendererFactory;
import com.tokelon.toktales.extensions.core.game.state.integration.IConsoleIntegrationControlHandler.IConsoleIntegrationControlHandlerFactory;

public class ConsoleIntegration implements IConsoleIntegration {

	
	public static final String CONSOLE_OVERLAY_RENDERER_NAME = "console_integration_overlay_renderer";
	
	private static final double DEFAULT_CONSOLE_OVERLAY_RENDER_POSITION = 100d;

	
	private double consoleOverlayRenderPosition = DEFAULT_CONSOLE_OVERLAY_RENDER_POSITION;
	
	private final ConsoleRenderCallback consoleRenderCallback;
	
	private final IGameState gamestate;
	private final IConsoleController consoleController;
	private final IConsoleIntegrationControlHandler controlHandler;
	
	public ConsoleIntegration(ICodepointAssetManager codepointManager, IGameState gamestate, IConsoleController consoleController, IConsoleIntegrationControlHandlerFactory controlHandlerFactory) {
		this.gamestate = gamestate;
		this.consoleController = consoleController;
		this.controlHandler = controlHandlerFactory.create(this);
		
		this.consoleRenderCallback = new ConsoleRenderCallback(codepointManager, gamestate, consoleController);
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
	
	

	private static class ConsoleRenderCallback implements IRenderCall {
		
		private final IConsoleOverlayRenderer renderer;
		
		private final IGameState gamestate;
		private final IConsoleController controller;
		
		public ConsoleRenderCallback(ICodepointAssetManager codepointManager, IGameState gamestate, IConsoleController consoleController) {
			this.gamestate = gamestate;
			this.controller = consoleController;
			
			renderer = new ConsoleOverlayRendererFactory().createForGamestate(gamestate, codepointManager, () -> consoleController);
		}
		
		public void register() {
			gamestate.getStateRenderer().addManagedRenderer(CONSOLE_OVERLAY_RENDERER_NAME, renderer);
			gamestate.getRenderOrder().getStackForLayer(IRenderOrder.LAYER_TOP).addCallbackAt(DEFAULT_CONSOLE_OVERLAY_RENDER_POSITION, this);
		}
		
		public void unregister() {
			gamestate.getStateRenderer().removeManagedRenderer(CONSOLE_OVERLAY_RENDERER_NAME);
			gamestate.getRenderOrder().getStackForLayer(IRenderOrder.LAYER_TOP).removeCallbackAt(DEFAULT_CONSOLE_OVERLAY_RENDER_POSITION);
		}
		
		@Override
		public void render() {
			renderer.drawConsoleOverlay(controller);
		}

		@Override
		public String getDescription() {
			return "renders the console overlay";
		}
	}

}
