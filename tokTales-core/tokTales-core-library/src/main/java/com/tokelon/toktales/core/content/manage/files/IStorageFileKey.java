package com.tokelon.toktales.core.content.manage.files;

import java.nio.file.Path;

import com.tokelon.toktales.core.content.manage.keys.IReadableAssetKey;

public interface IStorageFileKey extends IReadableAssetKey {

	
	public Path getStoragePath();
	
}
