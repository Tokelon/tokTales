package com.tokelon.toktales.core.game.graphic;

public interface IBaseGraphic {

	// IGraphic ? or IWorldGraphic
	
	public IGraphicType getGraphicType();
	
	
	public interface IGraphicType {
		
		public String getTypeID();
		
		public boolean matches(IGraphicType type);
	}
	
}
