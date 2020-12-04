package com.tokelon.toktales.extensions.core.game.renderer;

import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.game.state.IGameState;
import com.tokelon.toktales.core.game.state.ITypedGameState;
import com.tokelon.toktales.core.game.state.scene.IExtendedGameScene;
import com.tokelon.toktales.core.game.world.IWorldspace;
import com.tokelon.toktales.core.render.renderer.IMultiRenderer;
import com.tokelon.toktales.core.render.texture.ITextureCoordinator;
import com.tokelon.toktales.tools.core.inject.ISupplier;

public interface IEntityRenderer extends IMultiRenderer {


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
