package com.tokelon.toktales.core.content.manage.embedded;

import com.tokelon.toktales.tools.assets.key.IReadableAssetKey;

public interface IEmbeddedAssetKey extends IReadableAssetKey {
	/* Use instead of IStringEmbeddedAssetKey?
	public boolean isStringContent();
	public String getStringContent();
	*/
	
	
	public byte[] getContent();
	
}
