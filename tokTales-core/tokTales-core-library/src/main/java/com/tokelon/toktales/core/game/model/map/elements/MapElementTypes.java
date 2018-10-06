package com.tokelon.toktales.core.game.model.map.elements;

import com.tokelon.toktales.core.game.model.map.elements.IMapElement.IElementType;
import com.tokelon.toktales.core.values.MapElementValues;

public final class MapElementTypes {

	private MapElementTypes() {}
	
	
	public static final IElementType TYPE_GROUND = new MapElementTypeImpl(MapElementValues.MAP_ELEMENT_TYPE_ID_GROUND);
	public static final IElementType TYPE_OBJECT = new MapElementTypeImpl(MapElementValues.MAP_ELEMENT_TYPE_ID_OBJECT);
	
	public static final IElementType TYPE_ENTITY = new MapElementTypeImpl(MapElementValues.MAP_ELEMENT_TYPE_ID_ENTITY);
	public static final IElementType TYPE_NPC = new MapElementTypeImpl(MapElementValues.MAP_ELEMENT_TYPE_ID_NPC);
	public static final IElementType TYPE_PLAYER = new MapElementTypeImpl(MapElementValues.MAP_ELEMENT_TYPE_ID_PLAYER);
	
	public static final IElementType TYPE_SPECIAL = new MapElementTypeImpl(MapElementValues.MAP_ELEMENT_TYPE_ID_SPECIAL);
	public static final IElementType TYPE_SELECTION = new MapElementTypeImpl(MapElementValues.MAP_ELEMENT_TYPE_ID_SELECTION);
	
	public static final IElementType TYPE_GRAPHIC = new MapElementTypeImpl("graphic");

	
}
