package com.tokelon.toktales.extens.def.core.game.screen;

import java.util.function.Supplier;

import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.game.controller.IPlayerController;
import com.tokelon.toktales.core.game.screen.ISegmentRenderer;
import com.tokelon.toktales.core.game.states.IExtendedGameScene;
import com.tokelon.toktales.core.game.states.IGameState;
import com.tokelon.toktales.core.game.states.ITypedGameState;
import com.tokelon.toktales.core.render.ITextureCoordinator;

public interface IPlayerRenderer extends ISegmentRenderer {

	
	public void drawPlayer(IPlayerController playerController);
	
	
	public interface IPlayerRendererFactory {

		public IPlayerRenderer create(
				IEngineContext engineContext,
				Supplier<ITextureCoordinator> textureCoordinatorSupplier,
				Supplier<IPlayerController> playerControllerSupplier
		);

		public IPlayerRenderer createForGamestate(IGameState gamestate);

		public IPlayerRenderer createForTypedGamestate(ITypedGameState<? extends IExtendedGameScene> typedGamestate);
	}
	
}
