package com.tokelon.toktales.core.content.manage.font;

import com.tokelon.toktales.core.content.manage.keys.IReadDelegateAssetKey;
import com.tokelon.toktales.core.content.manage.keys.IReadableAssetKey;

public class ReadDelegateTextureFontAssetKey implements ITextureFontAssetKey, IReadDelegateAssetKey {

	
	private final IReadableAssetKey readableKey;

	public ReadDelegateTextureFontAssetKey(IReadableAssetKey readableKey) {
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
		if (!(obj instanceof ITextureFontAssetKey)) {
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
