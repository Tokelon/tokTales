package com.tokelon.toktales.core.content.manage.sprite;

public class SpriteAssetKey implements ISpriteAssetKey {
	
	
	private final String spriteName;
	private final ISpritesetAssetKey spritesetKey;
	private final int spritesetIndex;

	public SpriteAssetKey(String spriteName) {
		this.spriteName = spriteName;
		this.spritesetKey = null;
		this.spritesetIndex = -1;
	}
	
	public SpriteAssetKey(String spriteName, ISpritesetAssetKey spritesetKey, int spritesetIndex) {
		this.spriteName = spriteName;
		this.spritesetKey = spritesetKey;
		this.spritesetIndex = spritesetIndex;
	}
	
	
	@Override
	public String getSpriteName() {
		return spriteName;
	}

	@Override
	public boolean isSpriteset() {
		return spritesetKey != null;
	}

	@Override
	public ISpritesetAssetKey getSpritesetKey() {
		return spritesetKey;
	}

	@Override
	public int getSpritesetIndex() {
		return spritesetIndex;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((spriteName == null) ? 0 : spriteName.hashCode());
		result = prime * result + spritesetIndex;
		result = prime * result + ((spritesetKey == null) ? 0 : spritesetKey.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof ISpriteAssetKey)) {
			return false;
		}
		ISpriteAssetKey other = (ISpriteAssetKey) obj;
		
		if (spriteName == null) {
			if (other.getSpriteName() != null) {
				return false;
			}
		} else if (!spriteName.equals(other.getSpriteName())) {
			return false;
		}
		
		if (spritesetIndex != other.getSpritesetIndex()) {
			return false;
		}
		
		if (spritesetKey == null) {
			if (other.getSpritesetKey() != null) {
				return false;
			}
		} else if (!spritesetKey.equals(other.getSpritesetKey())) {
			return false;
		}
		
		return true;
	}

}
