package com.tokelon.toktales.core.content.manage.resources;

import com.tokelon.toktales.core.resources.IResource;
import com.tokelon.toktales.tools.assets.key.IReadableAssetKey;

public interface IResourceKey extends IReadableAssetKey {

	
	public IResource getResource();
	
}
