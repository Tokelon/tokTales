package com.tokelon.toktales.tools.tiledmap;

import com.tokelon.toktales.core.game.model.map.IBlock;
import com.tokelon.toktales.core.game.model.map.elements.IMapElement;

public class TiledMapBlock implements IBlock {

	private final int mLevelCount;
	
	private final IMapElement[] elementArray;
	
	private int mDominatingWalkable = IMapElement.WALKABLE_YES;
	
	
	public TiledMapBlock(int levelCount) {
		this.mLevelCount = levelCount;
		
		elementArray = new IMapElement[levelCount];
	}
	
	
	

	private void checkLevel(int l) {
		if(l < 1 || l > mLevelCount) {
			throw new IllegalArgumentException("Invalid level for block: " +l);
		}
	}

	private IMapElement element(int level) {
		return elementArray[level-1];
	}
	
	private void setElement(int level, IMapElement element) {
		elementArray[level-1] = element;
	}
	
	
	
	@Override
	public int getLevelCount() {
		return mLevelCount;
	}

	@Override
	public IMapElement getElementOnLevel(int level) {
		checkLevel(level);
		return element(level);
	}

	@Override
	public IMapElement removeElementOnLevel(int level) {
		checkLevel(level);

		IMapElement oldEl = element(level);
		setElement(level, null);
		
		mDominatingWalkable = deriveWalkable();
		return oldEl;
	}

	@Override
	public void setElementOnLevel(IMapElement element, int level) {
		if(element == null) {
			throw new NullPointerException();
		}
		checkLevel(level);
				
		setElement(level, element);
		
		mDominatingWalkable = deriveWalkable();
	}

	@Override
	public boolean hasElementOnLevel(int level) {
		return element(level) != null;
	}

	
	@Override
	public boolean isWalkableForPlayer() {
		return mDominatingWalkable == IMapElement.WALKABLE_YES || mDominatingWalkable == IMapElement.WALKABLE_PLAYER_ONLY;
	}
	
	
	
	private synchronized int deriveWalkable() {
		
		int result = IMapElement.WALKABLE_YES;
		for(IMapElement el: elementArray) {
			if(result == IMapElement.WALKABLE_NO) {
				return IMapElement.WALKABLE_NO;
			}
			
			if(el != null) {
				switch (el.getWalkable()) {
				case IMapElement.WALKABLE_NO:
					result = IMapElement.WALKABLE_NO;
					break;
				case IMapElement.WALKABLE_NPC_ONLY:
					if(result == IMapElement.WALKABLE_PLAYER_ONLY) {
						result = IMapElement.WALKABLE_NO;
					}
					else {
						result = IMapElement.WALKABLE_NPC_ONLY;
					}
					break;
					
				case IMapElement.WALKABLE_PLAYER_ONLY:
					if(result == IMapElement.WALKABLE_NPC_ONLY) {
						result = IMapElement.WALKABLE_NO;
					}
					else {
						result = IMapElement.WALKABLE_PLAYER_ONLY;
					}
				default:
					break;
				}
			}
		}
		
		return result;
	}
	
	@Override
	public String toString() {

		StringBuilder builder = new StringBuilder();

		for(int i = elementArray.length - 1; i >= 0; i--) {
			builder.append((elementArray[i] == null ? null : elementArray[i].toString()) + "\n");
		}
		
		return builder.toString();
	}
	

}
