package com.tokelon.toktales.core.game.model.map;

import com.tokelon.toktales.core.game.logic.data.NoDataException;
import com.tokelon.toktales.core.game.model.map.elements.IMapElement;

public interface IBlock {

	public int getLevelCount();
	
	public IMapElement getElementOnLevel(int level);
	
	/**
	 * 
	 * @param level
	 * @return
	 * @throws NoDataException
	 */
	public IMapElement removeElementOnLevel(int level);
	
	public void setElementOnLevel(IMapElement element, int level);
	
	// Add element at the right level automatically?
	//public void addElement(IMapElement element);
	
	public boolean hasElementOnLevel(int level);
	
	//public boolean isFree();
	
	//public int getWalkable();
	
	public boolean isWalkableForPlayer();

}
