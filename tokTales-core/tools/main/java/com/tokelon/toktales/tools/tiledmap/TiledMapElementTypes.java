package com.tokelon.toktales.tools.tiledmap;

import com.tokelon.toktales.core.game.model.map.elements.MapElementTypeImpl;
import com.tokelon.toktales.core.game.model.map.elements.IMapElement.IElementType;

public final class TiledMapElementTypes {

	private TiledMapElementTypes() {}

	
	public static final String ID_TYPE_TILED_TILE = "tiled_tile";
	public static final String ID_TYPE_TILED_OBJECT = "tiled_object";
	
	
	public static final IElementType TYPE_TILED_TILE = new MapElementTypeImpl(ID_TYPE_TILED_TILE);

	
}
