package com.tokelon.toktales.extens.def.core.game.screen;

import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.game.controller.map.IMapController;
import com.tokelon.toktales.core.game.screen.ISegmentRenderer;
import com.tokelon.toktales.core.game.states.IExtendedGameScene;
import com.tokelon.toktales.core.game.states.IGameState;
import com.tokelon.toktales.core.game.states.ITypedGameState;
import com.tokelon.toktales.core.render.ITextureCoordinator;
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
