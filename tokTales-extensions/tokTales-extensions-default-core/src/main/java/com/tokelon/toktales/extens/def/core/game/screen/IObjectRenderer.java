package com.tokelon.toktales.extens.def.core.game.screen;

import com.tokelon.toktales.core.game.controller.map.IMapController;
import com.tokelon.toktales.core.game.screen.ISegmentRenderer;

public interface IObjectRenderer extends ISegmentRenderer {

	public void drawObjectsOnMapLayer(IMapController mapController, String layerName);
	
}
