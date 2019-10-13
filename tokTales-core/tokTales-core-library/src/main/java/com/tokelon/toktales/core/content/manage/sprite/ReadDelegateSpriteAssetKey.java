package com.tokelon.toktales.core.content.manage.sprite;

import com.tokelon.toktales.tools.assets.key.IReadDelegateAssetKey;
import com.tokelon.toktales.tools.assets.key.IReadableAssetKey;

public class ReadDelegateSpriteAssetKey implements ISpriteAssetKey, IReadDelegateAssetKey {
	
	
	private final String spriteName;
	private final IReadableAssetKey readableAssetKey;

	public ReadDelegateSpriteAssetKey(String spriteName, IReadableAssetKey readableAssetKey) {
		this.spriteName = spriteName;
		this.readableAssetKey = readableAssetKey;
	}

	
	@Override
	public String getName() {
		return spriteName;
	}

	
	@Override
	public IReadableAssetKey getReadableKey() {
		return readableAssetKey;
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
