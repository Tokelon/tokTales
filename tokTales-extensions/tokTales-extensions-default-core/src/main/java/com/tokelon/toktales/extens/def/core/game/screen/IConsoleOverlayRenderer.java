package com.tokelon.toktales.extens.def.core.game.screen;

import com.tokelon.toktales.core.content.text.ICodepointManager;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.game.controller.IConsoleController;
import com.tokelon.toktales.core.game.screen.ISegmentRenderer;
import com.tokelon.toktales.core.game.states.IGameState;
import com.tokelon.toktales.core.render.ITextureCoordinator;
import com.tokelon.toktales.core.util.function.Supplier;

public interface IConsoleOverlayRenderer extends ISegmentRenderer {

	//public ICamera getCustomCamera();
	//public void enableCustomCamera(boolean enable);
	
	public void drawConsoleOverlay(IConsoleController consoleController);
	
	
	public interface IConsoleOverlayRendererFactory {

		public IConsoleOverlayRenderer create(
				IEngineContext engineContext,
				ICodepointManager codepointManager,
				Supplier<ITextureCoordinator> textureCoordinatorSupplier,
				Supplier<IConsoleController> consoleControllerSupplier
		);

		public IConsoleOverlayRenderer createForGamestate(
				IGameState gamestate,
				ICodepointManager codepointManager
		);

		public IConsoleOverlayRenderer createForGamestate(
				IGameState gamestate,
				ICodepointManager codepointManager,
				Supplier<IConsoleController> consoleControllerSupplier
		);
	}
	
}
