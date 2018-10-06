package com.tokelon.toktales.core.game.model.map.elements;

import com.tokelon.toktales.core.game.model.map.elements.IMapElement.IElementType;


public class MapElementTypeImpl implements IMapElement.IElementType {

	
	private final String typeID;
	
	public MapElementTypeImpl(String typeID) {
		this.typeID = typeID;
	}
	
	@Override
	public String getTypeID() {
		return typeID;
	}
	
	@Override
	public boolean matches(IElementType type) {
		return typeID.equals(type.getTypeID());
	}

}
