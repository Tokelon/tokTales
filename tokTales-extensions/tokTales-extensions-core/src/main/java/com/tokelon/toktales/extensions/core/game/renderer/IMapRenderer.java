package com.tokelon.toktales.extensions.core.game.renderer;

import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.game.controller.map.IMapController;
import com.tokelon.toktales.core.game.state.IExtendedGameScene;
import com.tokelon.toktales.core.game.state.IGameState;
import com.tokelon.toktales.core.game.state.ITypedGameState;
import com.tokelon.toktales.core.game.state.render.ISegmentRenderer;
import com.tokelon.toktales.core.game.world.IWorld;
import com.tokelon.toktales.core.render.texture.ITextureCoordinator;
import com.tokelon.toktales.tools.core.inject.ISupplier;

public interface IMapRenderer extends ISegmentRenderer {

	//public void drawElementSprite(ISprite sprite, DrawingMeta dmeta)
	
	public void drawMapLayer(IMapController mapController, String layerName);
	
	
	
	public interface IMapRendererFactory {

		public IMapRenderer create(
				IEngineContext engineContext,
				IWorld world,
				ISupplier<ITextureCoordinator> textureCoordinatorSupplier,
				ISupplier<IMapController> mapControllerSupplier
		);

		
		public IMapRenderer createForGamestate(IGameState gamestate);

		public IMapRenderer createForTypedGamestate(ITypedGameState<? extends IExtendedGameScene> typedGamestate);
	}
	
}
