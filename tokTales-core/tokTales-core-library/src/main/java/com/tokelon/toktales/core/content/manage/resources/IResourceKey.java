package com.tokelon.toktales.core.content.manage.resources;

import com.tokelon.toktales.core.content.manage.keys.IReadableAssetKey;
import com.tokelon.toktales.core.resources.IResource;

public interface IResourceKey extends IReadableAssetKey {

	
	public IResource getResource();
	
}
