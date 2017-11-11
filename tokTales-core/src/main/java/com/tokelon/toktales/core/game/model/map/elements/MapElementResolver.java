package com.tokelon.toktales.core.game.model.map.elements;

import com.tokelon.toktales.core.game.model.map.predef.IGroundElement;

public final class MapElementResolver {

	private MapElementResolver() {}

	
	public static final Class<IGraphicElement> CLASS_TYPE_GRAPHIC = IGraphicElement.class;
	public static final Class<IEntityElement> CLASS_TYPE_ENTITY = IEntityElement.class;

	
	
	
	
	@SuppressWarnings("unchecked")
	public static <T extends IMapElement> T resolve(IMapElement element, Class<T> type) {
		
		if(!(type.isInstance(element))) {
			throw new IllegalArgumentException("Given type does not match requested element type");
		}
		
		return (T) element;
	}
	
	
	
	
	// TODO: Use or remove
	
	private static Class<? extends IMapElement> translate(String type) {
		
		if(MapElementTypes.TYPE_GROUND.getTypeID().equals(type)) {
			return IGroundElement.class;
		}
		
		return null;
	}
	
	/*
	
	public static <T extends IMapElement> T resolve(IMapElement element, Class<T> type) {
		return null;
	}
	*/
}
