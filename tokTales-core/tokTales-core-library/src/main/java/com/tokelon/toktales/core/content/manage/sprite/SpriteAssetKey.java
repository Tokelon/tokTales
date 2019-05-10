package com.tokelon.toktales.core.content.manage.sprite;

public class SpriteAssetKey implements ISpriteAssetKey {
	
	
	private final String spriteName;

	public SpriteAssetKey(String spriteName) {
		this.spriteName = spriteName;
	}
	
	
	@Override
	public String getName() {
		return spriteName;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((spriteName == null) ? 0 : spriteName.hashCode());
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
			if (other.getName() != null) {
				return false;
			}
		} else if (!spriteName.equals(other.getName())) {
			return false;
		}
		
		return true;
	}

}
