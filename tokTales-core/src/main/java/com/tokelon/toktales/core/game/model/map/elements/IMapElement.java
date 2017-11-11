package com.tokelon.toktales.core.game.model.map.elements;


public interface IMapElement {


	public static final int WALKABLE_NO = 1;
	public static final int WALKABLE_YES = 2;
	public static final int WALKABLE_PLAYER_ONLY = 3;
	public static final int WALKABLE_NPC_ONLY = 4;
	
	
	
	public long getElementID();
	
	public IElementType getElementType();

	
	public int getWalkable();
	
	public boolean isVisible();
	

	
	public interface IElementType {
		
		public String getTypeID();
		
		public boolean matches(IElementType type);
		
		//public int getTypeLevel();
		//public ILevelReference getLevelReference();
	}
	
}
