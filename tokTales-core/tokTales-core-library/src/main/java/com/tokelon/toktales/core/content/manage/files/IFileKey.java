package com.tokelon.toktales.core.content.manage.files;

import java.nio.file.Path;

import com.tokelon.toktales.core.content.manage.keys.IReadableAssetKey;

public interface IFileKey extends IReadableAssetKey {

	
	public Path getPath();
	
}
