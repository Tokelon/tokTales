package com.tokelon.toktales.core.content.manage.contents;

import com.tokelon.toktales.core.location.IApplicationLocation;
import com.tokelon.toktales.tools.assets.key.IReadableAssetKey;

public interface IContentKey extends IReadableAssetKey {


	public String getName();
	
	public IApplicationLocation getLocation();
	
}
