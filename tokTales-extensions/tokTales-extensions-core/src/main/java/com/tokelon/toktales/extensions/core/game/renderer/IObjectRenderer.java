package com.tokelon.toktales.extensions.core.game.renderer;

import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.game.controller.map.IMapController;
import com.tokelon.toktales.core.game.states.IExtendedGameScene;
import com.tokelon.toktales.core.game.states.IGameState;
import com.tokelon.toktales.core.game.states.ITypedGameState;
import com.tokelon.toktales.core.game.states.render.ISegmentRenderer;
import com.tokelon.toktales.core.render.texture.ITextureCoordinator;
import com.tokelon.toktales.tools.core.inject.ISupplier;

public interface IObjectRenderer extends ISegmentRenderer {

	
	public void drawObjectsOnMapLayer(IMapController mapController, String layerName);
	
	
	public interface IObjectRendererFactory {

		public IObjectRenderer create(
				IEngineContext engineContext,
				ISupplier<ITextureCoordinator> textureCoordinatorSupplier,
				ISupplier<IMapController> mapControllerSupplier
		);

		public IObjectRenderer createForGamestate(IGameState gamestate);

		public IObjectRenderer createForTypedGamestate(ITypedGameState<? extends IExtendedGameScene> gamestate);
	}
	
}
