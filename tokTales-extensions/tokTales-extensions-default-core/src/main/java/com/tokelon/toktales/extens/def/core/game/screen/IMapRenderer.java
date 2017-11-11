package com.tokelon.toktales.extens.def.core.game.screen;

import com.tokelon.toktales.core.game.controller.map.IMapController;
import com.tokelon.toktales.core.game.screen.ISegmentRenderer;

public interface IMapRenderer extends ISegmentRenderer {

	//public void drawElementSprite(ISprite sprite, DrawingMeta dmeta)
	
	public void drawMapLayer(IMapController mapController, String layerName);
	
}
