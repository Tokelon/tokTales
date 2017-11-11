package com.tokelon.toktales.tools.tiledmap;

import com.tokelon.toktales.core.content.sprite.ISprite;
import com.tokelon.toktales.core.game.model.map.elements.IMapElement;
import com.tokelon.toktales.tools.tiledmap.model.ITMXTile;

public interface ITiledTileElement extends IMapElement {

	public ITMXTile getTile();
	
	public ISprite getSprite();
	
}
