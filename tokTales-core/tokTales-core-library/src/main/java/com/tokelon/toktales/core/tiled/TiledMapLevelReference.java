package com.tokelon.toktales.core.tiled;

import com.tokelon.toktales.core.game.model.map.ILevelReference;
import com.tokelon.toktales.core.game.model.map.elements.IMapElement;
import com.tokelon.toktales.core.game.model.map.elements.MapElementTypes;
import com.tokelon.toktales.core.game.model.map.elements.IMapElement.IElementType;

public class TiledMapLevelReference implements ILevelReference {


	private static final int LOWEST_LEVEL = 1;
	
	private final int levelCount;
	
	public int levelForElementTypePlayer;
	public int levelForElementTypeSelection;
	public int levelForElementTypeOther;
	
	
	public TiledMapLevelReference(int levelCount) {
		if(levelCount < 3) {
			throw new IllegalArgumentException("A minimum of 3 levels is required");
		}
		
		this.levelCount = levelCount;
		this.levelForElementTypePlayer = levelCount - 2;
		this.levelForElementTypeSelection = levelCount - 1;
		this.levelForElementTypeOther = levelCount;
	}
	
	
	
	@Override
	public int levelForElement(IMapElement element) {
		return levelForElementType(element.getElementType());
	}

	@Override
	public int levelForElementType(IElementType elementType) {
		
		if(MapElementTypes.TYPE_PLAYER.matches(elementType)) {
			return levelForElementTypePlayer;
		}
		else if(MapElementTypes.TYPE_SELECTION.matches(elementType)) {
			return levelForElementTypeSelection;
		}
		else {
			return levelForElementTypeOther; 
		}
	}

	@Override
	public int getLowestLevel() {
		return LOWEST_LEVEL;
	}

	@Override
	public int getHighestLevel() {
		return levelCount;
	}

}
