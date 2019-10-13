package com.tokelon.toktales.core.content.manage.files;

import java.io.File;

import com.tokelon.toktales.tools.assets.key.IReadableAssetKey;

public interface IRelativeFileKey extends IReadableAssetKey {

	
	public File getFile();

	public Object getParentIdentifier();
	
}
