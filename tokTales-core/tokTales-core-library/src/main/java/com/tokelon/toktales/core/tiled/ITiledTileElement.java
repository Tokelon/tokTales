package com.tokelon.toktales.core.tiled;

import com.tokelon.toktales.core.content.sprite.ISprite;
import com.tokelon.toktales.core.game.model.map.elements.IMapElement;
import com.tokelon.toktales.tools.core.tiled.model.ITMXTile;

public interface ITiledTileElement extends IMapElement {

	public ITMXTile getTile();
	
	public ISprite getSprite();
	
}
