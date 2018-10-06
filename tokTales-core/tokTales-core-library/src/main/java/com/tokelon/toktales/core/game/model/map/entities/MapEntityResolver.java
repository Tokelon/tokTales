package com.tokelon.toktales.core.game.model.map.entities;

public final class MapEntityResolver {

	public MapEntityResolver() {}
	
	
	public static <T extends IMapEntity> T castEntity(IMapEntity entity, Class<T> type) {
		
		if(!(type.isInstance(entity))) {
			throw new IllegalArgumentException("Given type does not match requested entity type");
		}
		
		return type.cast(entity);
	}
	
}
