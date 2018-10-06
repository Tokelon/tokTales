package com.tokelon.toktales.core.game.model.map.entities;

import com.tokelon.toktales.core.game.logic.map.IMapCoverageState;
import com.tokelon.toktales.core.game.model.entity.IGameEntity;


public interface IMapEntity extends IGameEntity {
	
	
	// Direct result from world coordinates - No setter
	public IMapCoverage getMapCoverage();
	
	public IMapCoverageState getCoverageState();
	
	
	
	
	
	public interface IEntityType {
		
		public Class<? extends IMapEntity> getTypeClass();
		
		
		public boolean matches(IEntityType type);
		
		/* equivalent to
		 * this instanceof type.getMyClass()
		 * 
		 */
		public boolean isCompatibleWith(IEntityType type);
		
		
		//public boolean matches(Class<? extends IMapEntity> clazz);
	}
	
	
}
