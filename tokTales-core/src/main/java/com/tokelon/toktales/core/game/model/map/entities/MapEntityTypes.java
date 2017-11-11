package com.tokelon.toktales.core.game.model.map.entities;

import com.tokelon.toktales.core.game.model.map.entities.IMapEntity.IEntityType;

public final class MapEntityTypes {

	public MapEntityTypes() {}
	
	
	//public static final Class<IGraphicEntity> CLASS_TYPE_GRAPHIC = IGraphicEntity.class;
	
	public static final Class<IPlayerEntity> CLASS_TYPE_PLAYER = IPlayerEntity.class;

	
	//public static final IEntityType TYPE_GRAPHIC = new MapEntityTypeImpl(CLASS_TYPE_GRAPHIC);
	
	public static final IEntityType TYPE_PLAYER = new MapEntityTypeImpl(CLASS_TYPE_PLAYER);
	
}
