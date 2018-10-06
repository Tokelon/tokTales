package com.tokelon.toktales.core.game.model.map;

import com.tokelon.toktales.core.game.model.map.elements.IMapElement;
import com.tokelon.toktales.core.game.model.map.elements.MapElementTypes;
import com.tokelon.toktales.core.game.model.map.elements.IMapElement.IElementType;

public class Level10Reference implements ILevelReference {
	
	//Level10Structure

	
	/* 1-3 : Ground
	 * 4-6 : Object
	 * 7-9 : Player / NPC / Special
	 * 10 : ?
	 * 
	 */
	
	private static final int LOWEST_LEVEL = 1;
	private static final int HIGHEST_LEVEL = 10;
	
	
	private static final Level10Reference INSTANCE = new Level10Reference();
	
	
	private Level10Reference() { }
	
	public static Level10Reference getInstance() {
		return INSTANCE;
	}
	
	
	
	private enum Reference {
		R_GROUND(MapElementTypes.TYPE_GROUND, 1),
		
		R_OBJECT(MapElementTypes.TYPE_OBJECT, 4),
		
		R_ENTITY(MapElementTypes.TYPE_ENTITY, 7),
		//R_NPC(MapElementTypes.TYPE_NPC, 9),
		R_PLAYER(MapElementTypes.TYPE_PLAYER, 8),
		
		
		R_SPECIAL(MapElementTypes.TYPE_SPECIAL, 9),
		
		R_SELECTION(MapElementTypes.TYPE_SELECTION, 10),
		;
		
		
		private final IElementType type;
		private final int level;
		
		private Reference(IElementType type, int level) {
			this.type = type;
			this.level = level;
		}

		
		public static Reference structureForType(IElementType elementType) {
			for(Reference ref: Reference.values()) {
				if(ref.type.matches(elementType)) {
					return ref;
				}
			}
			
			return null;
		}
		
	}

	
	
	@Override
	public int levelForElement(IMapElement element) {
		return levelForElementType(element.getElementType());
	}

	@Override
	public int levelForElementType(IElementType elementType) {
		Reference res = Reference.structureForType(elementType);
		return res == null ? -1 : res.level;
	}

	@Override
	public int getLowestLevel() {
		return LOWEST_LEVEL;
	}

	@Override
	public int getHighestLevel() {
		return HIGHEST_LEVEL;
	}

}
