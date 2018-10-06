package com.tokelon.toktales.core.content.sprite;

import com.tokelon.toktales.core.resources.IResource;

public class SpritesetSprite implements ISprite {

	
	private final int mIndex;
	private final String mName;
	private final ISpriteset mSpriteset;
	
	private IResource mResource;
	
	/**
	 * 
	 * @param name
	 * @param spriteset
	 * @throws NullPointerException If name or spriteset is null.
	 */
	public SpritesetSprite(int spritesetIndex, ISpriteset spriteset) {
		if(spritesetIndex < 0) {
			throw new IllegalArgumentException("spritesetIndex must be >= 0");
		}
		else if(spriteset == null) {
			throw new NullPointerException();
		}
		
		this.mName = Integer.toString(spritesetIndex);
		this.mIndex = spritesetIndex;
		this.mSpriteset = spriteset;
	}
	
	
	@Override
	public String getSpriteName() {
		return mName;
	}

	@Override
	public boolean hasResourceAttached() {
		return mResource != null;
	}

	@Override
	public IResource getResource() {
		return mResource;
	}

	@Override
	public boolean isEnclosed() {
		return true;
	}

	@Override
	public ISpriteset getSpriteset() {
		return mSpriteset;
	}

	@Override
	public int getSpritesetIndex() {
		return mIndex;
	}

	
	@Override
	public boolean equals(Object o) {
		if(this == o) {
			return true;
		}
		if(!(o instanceof ISprite)) {
			return false;
		}
		ISprite that = (ISprite) o;
		
		
		if(!mSpriteset.equals(that.getSpriteset())) {
			return false;
		}

		if(mIndex != that.getSpritesetIndex()) {
			return false;
		}
		
		return true;
	}
	
	@Override
	public int hashCode() {
		int res = 17;
		res += 11 * mIndex;
		res += 13 * mSpriteset.hashCode();
		return res;
	}
	
	
}
