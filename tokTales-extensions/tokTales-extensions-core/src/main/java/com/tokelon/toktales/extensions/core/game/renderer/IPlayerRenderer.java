package com.tokelon.toktales.extensions.core.game.renderer;

import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.game.controller.IPlayerController;
import com.tokelon.toktales.core.game.state.IGameState;
import com.tokelon.toktales.core.game.state.ITypedGameState;
import com.tokelon.toktales.core.game.state.scene.IExtendedGameScene;
import com.tokelon.toktales.core.render.renderer.ISegmentRenderer;
import com.tokelon.toktales.core.render.texture.ITextureCoordinator;
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
