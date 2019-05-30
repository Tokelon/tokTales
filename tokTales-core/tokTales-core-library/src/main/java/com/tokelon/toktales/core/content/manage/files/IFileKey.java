package com.tokelon.toktales.core.content.manage.files;

import java.io.File;

import com.tokelon.toktales.core.content.manage.keys.IReadableAssetKey;

public interface IFileKey extends IReadableAssetKey {

	
	public File getFile();
	
}
