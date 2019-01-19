package com.tokelon.toktales.core.game.model.map;

import com.tokelon.toktales.core.game.model.map.elements.IMapElement;

public class Level10Block implements IBlock {
	

	private static final int LEVEL_COUNT = 10;

	//private final ReentrantLock lock = new ReentrantLock();

	
	private final IMapElement[] elements;
	
	public Level10Block() {
		elements = new IMapElement[LEVEL_COUNT];		
	}
	
	
	private void checkLevel(int l) {
		if(l < 1 || l > LEVEL_COUNT) {
			throw new IllegalArgumentException("Invalid level for block: " +l);
		}
	}

	private IMapElement element(int level) {
		return elements[level-1];
	}
	
	private void setElement(int level, IMapElement element) {
		elements[level-1] = element;
	}
	
	
	
	@Override
	public int getLevelCount() {
		return LEVEL_COUNT;
	}

	@Override
	public IMapElement getElementOnLevel(int level) {
		checkLevel(level);
		return element(level);
	}

	@Override
	public IMapElement removeElementOnLevel(int level) {
		checkLevel(level);
		
		IMapElement el = element(level);
		setElement(level, null);
		return el;
	}

	@Override
	public void setElementOnLevel(IMapElement element, int level) {
		checkLevel(level);
		setElement(level, element);
	}

	
	@Override
	public boolean hasElementOnLevel(int level) {
		checkLevel(level);
		return element(level) != null;
	}
	
	@Override
	public boolean isWalkableForPlayer() {
		// TODO Auto-generated method stub
		return true;
	}
	
}
