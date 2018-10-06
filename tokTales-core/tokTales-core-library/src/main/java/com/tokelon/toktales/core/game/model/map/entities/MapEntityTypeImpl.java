package com.tokelon.toktales.core.game.model.map.entities;

import com.tokelon.toktales.core.game.model.map.entities.IMapEntity.IEntityType;

public class MapEntityTypeImpl implements IEntityType {

	private final Class<? extends IMapEntity> myClass;
	
	public MapEntityTypeImpl(Class<? extends IMapEntity> myClass) {
		this.myClass = myClass;
	}
	
	
	@Override
	public Class<? extends IMapEntity> getTypeClass() {
		return myClass;
	}


	@Override
	public boolean isCompatibleWith(IEntityType type) {
		return type.getClass().isInstance(this);
	}


	@Override
	public boolean matches(IEntityType type) {
		return myClass.equals(type.getTypeClass());
	}
	
}
