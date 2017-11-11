package com.tokelon.toktales.core.game.model.map.elements;

public class MapElementImpl extends AbstractMapElement {


	public MapElementImpl(long id, IElementType type) {
		super(id, type);
	}
	
	
	public void setWalkable(int walkable) {
		this.mWalkable = walkable;
	}
	
	public void setVisible(boolean visible) {
		this.mVisible = visible;
	}
	
}
