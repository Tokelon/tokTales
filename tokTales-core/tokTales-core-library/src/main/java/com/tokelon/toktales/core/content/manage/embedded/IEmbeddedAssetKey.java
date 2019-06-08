package com.tokelon.toktales.core.content.manage.embedded;

import com.tokelon.toktales.core.content.manage.keys.IReadableAssetKey;

public interface IEmbeddedAssetKey extends IReadableAssetKey {
	/* Use instead of IStringEmbeddedAssetKey?
	public boolean isStringContent();
	public String getStringContent();
	*/
	
	
	public byte[] getContent();
	
}
