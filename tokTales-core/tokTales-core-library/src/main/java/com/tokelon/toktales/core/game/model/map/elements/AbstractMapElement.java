package com.tokelon.toktales.core.game.model.map.elements;


public abstract class AbstractMapElement implements IMapElement {

	
	protected long mID;
	protected IElementType mType;
	
	protected int mWalkable = WALKABLE_YES;
	protected boolean mVisible = true;
	
	
	public AbstractMapElement(long id, IElementType type) {
		this.mID = id;
		this.mType = type;
	}
	
	
	@Override
	public long getElementID() {
		return mID;
	}

	@Override
	public IElementType getElementType() {
		return mType;
	}
	
	@Override
	public int getWalkable() {
		return mWalkable;
	}
	
	@Override
	public boolean isVisible() {
		return mVisible;
	}
	
	
	@Override
	public String toString() {
		return String.format("MapElement (id, type): %d | %s", mID, mType.toString());
	}
	
	
	public static class AbstractMutableMapElement extends AbstractMapElement {
		
		public AbstractMutableMapElement(long id, IElementType type) {
			super(id, type);
		}
		
		
		public void setElementID(long id) {
			this.mID = id;
		}
		
		public void setElementType(IElementType elementType) {
			this.mType = elementType;
		}
		
		public void setWalkable(int walkable) {
			this.mWalkable = walkable;
		}
		
		public void setVisible(boolean visible) {
			this.mVisible = visible;
		}

	}
	
}
