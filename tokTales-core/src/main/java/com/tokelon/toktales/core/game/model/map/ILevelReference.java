package com.tokelon.toktales.core.game.model.map;

import com.tokelon.toktales.core.game.model.map.elements.IMapElement;
import com.tokelon.toktales.core.game.model.map.elements.IMapElement.IElementType;

/** The lowest level must ALWAYS be smaller equals (<=) the highest level.
 * 
 */
public interface ILevelReference {

	//IMapStructure
	
	public int levelForElement(IMapElement element);

	public int levelForElementType(IElementType elementType);
	
	
	public int getLowestLevel();
	
	public int getHighestLevel();

	
	//public int levelOfElementType(IMapElement.ElementType elementType);
	//public int whichLevelForElementType(IMapElement.ElementType elementType);

	//public int whichLevelForElementClass(Class<IMapElement> elementClass);
	
	//public ElementType whichElementsOnLevel(int level);
	
}
