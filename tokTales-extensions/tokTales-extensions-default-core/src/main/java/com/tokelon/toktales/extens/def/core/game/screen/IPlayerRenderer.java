package com.tokelon.toktales.extens.def.core.game.screen;

import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.game.controller.IPlayerController;
import com.tokelon.toktales.core.game.screen.ISegmentRenderer;
import com.tokelon.toktales.core.game.states.IExtendedGameScene;
import com.tokelon.toktales.core.game.states.IGameState;
import com.tokelon.toktales.core.game.states.ITypedGameState;
import com.tokelon.toktales.core.render.ITextureCoordinator;
import com.tokelon.toktales.tools.core.inject.ISupplier;

public interface IPlayerRenderer extends ISegmentRenderer {

	
	public void drawPlayer(IPlayerController playerController);
	
	
	public interface IPlayerRendererFactory {

		public IPlayerRenderer create(
				IEngineContext engineContext,
				ISupplier<ITextureCoordinator> textureCoordinatorSupplier,
				ISupplier<IPlayerController> playerControllerSupplier
		);

		public IPlayerRenderer createForGamestate(IGameState gamestate);

		public IPlayerRenderer createForTypedGamestate(ITypedGameState<? extends IExtendedGameScene> typedGamestate);
	}
	
}
