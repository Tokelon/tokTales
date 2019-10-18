package com.tokelon.toktales.tools.assets.files;

import java.nio.file.Path;

import com.tokelon.toktales.tools.assets.key.IReadableAssetKey;

public interface IPathKey extends IReadableAssetKey {

	
	public Path getPath();
	
}
