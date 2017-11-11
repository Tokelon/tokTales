package com.tokelon.toktales.core.game.graphic;

import com.tokelon.toktales.core.game.graphic.IBaseGraphic.IGraphicType;

public class GraphicTypeImpl implements IGraphicType {

	private final String typeID;
	
	public GraphicTypeImpl(String typeID) {
		this.typeID = typeID;
	}
	
	@Override
	public String getTypeID() {
		return typeID;
	}

	@Override
	public boolean matches(IGraphicType type) {
		return typeID.equals(type.getTypeID());
	}

}
