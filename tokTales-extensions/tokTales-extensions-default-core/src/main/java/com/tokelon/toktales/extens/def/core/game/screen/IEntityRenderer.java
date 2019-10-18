package com.tokelon.toktales.extens.def.core.game.screen;

import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.game.screen.ISegmentRenderer;
import com.tokelon.toktales.core.game.states.IExtendedGameScene;
import com.tokelon.toktales.core.game.states.IGameState;
import com.tokelon.toktales.core.game.states.ITypedGameState;
import com.tokelon.toktales.core.game.world.IWorldspace;
import com.tokelon.toktales.core.render.ITextureCoordinator;
import com.tokelon.toktales.tools.core.inject.ISupplier;

public interface IEntityRenderer extends ISegmentRenderer {

	//public void drawEntity(IEntity entity);
	
	public void drawEntities(IWorldspace worldspace);
	
	
	public interface IEntityRendererFactory {

		public IEntityRenderer create(
				IEngineContext engineContext,
				ISupplier<ITextureCoordinator> textureCoordinatorSupplier,
				ISupplier<IWorldspace> worldspaceSupplier
		);

		public IEntityRenderer createForGamestate(IGameState gamestate, ISupplier<IWorldspace> worldspaceSupplier);

		public IEntityRenderer createForTypedGamestate(ITypedGameState<? extends IExtendedGameScene> typedGamestate);
	}
	
}
