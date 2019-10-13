package com.tokelon.toktales.core.content.manage.sound;

import com.tokelon.toktales.tools.assets.key.IReadDelegateAssetKey;
import com.tokelon.toktales.tools.assets.key.IReadableAssetKey;

public class ReadDelegateSoundAssetKey implements ISoundAssetKey, IReadDelegateAssetKey {

	
	private final IReadableAssetKey readableKey;

	public ReadDelegateSoundAssetKey(IReadableAssetKey readableKey) {
		this.readableKey = readableKey;
	}
	
	
	@Override
	public Object getReadableKey() {
		return readableKey;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((readableKey == null) ? 0 : readableKey.hashCode());
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
		if (!(obj instanceof ISoundAssetKey)) {
			return false;
		}
		if (!(obj instanceof IReadDelegateAssetKey)) {
			return false;
		}
		IReadDelegateAssetKey other = (IReadDelegateAssetKey) obj;
		
		if (readableKey == null) {
			if (other.getReadableKey() != null) {
				return false;
			}
		} else if (!readableKey.equals(other.getReadableKey())) {
			return false;
		}
		
		return true;
	}

}
