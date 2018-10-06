package com.tokelon.toktales.core.game.model.map;

import com.tokelon.toktales.core.game.model.map.elements.IMapElement;

interface IElementStack {
	
	// TODO: Either use or remove

	public void addOnTop(IMapElement topElement);
	
	public void addOnBottom(IMapElement bottomElement);
	
	public void addOnLevel(IMapElement element, int level);
	
	
	public int getSize();
	
	public int getHighestLevel();
	
	public int getLowestLevel();
	
}
