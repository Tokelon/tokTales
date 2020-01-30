package com.tokelon.toktales.extensions.core.game.renderer;

import com.tokelon.toktales.core.content.manage.codepoint.ICodepointAssetManager;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.game.controller.IConsoleController;
import com.tokelon.toktales.core.game.states.IGameState;
import com.tokelon.toktales.core.game.states.render.ISegmentRenderer;
import com.tokelon.toktales.core.render.texture.ITextureCoordinator;
import com.tokelon.toktales.tools.core.inject.ISupplier;

public interface IConsoleOverlayRenderer extends ISegmentRenderer {

	//public ICamera getCustomCamera();
	//public void enableCustomCamera(boolean enable);
	
	public void drawConsoleOverlay(IConsoleController consoleController);
	
	
	public interface IConsoleOverlayRendererFactory {

		public IConsoleOverlayRenderer create(
				IEngineContext engineContext,
				ICodepointAssetManager codepointManager,
				ISupplier<ITextureCoordinator> textureCoordinatorSupplier,
				ISupplier<IConsoleController> consoleControllerSupplier
		);

		public IConsoleOverlayRenderer createForGamestate(
				IGameState gamestate,
				ICodepointAssetManager codepointManager
		);

		public IConsoleOverlayRenderer createForGamestate(
				IGameState gamestate,
				ICodepointAssetManager codepointManager,
				ISupplier<IConsoleController> consoleControllerSupplier
		);
	}
	
}
