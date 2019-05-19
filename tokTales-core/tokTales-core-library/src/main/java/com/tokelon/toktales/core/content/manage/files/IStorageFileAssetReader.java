package com.tokelon.toktales.core.content.manage.files;

import java.io.InputStream;
import java.nio.file.Path;

import com.tokelon.toktales.core.content.manage.IManagedAssetReader;
import com.tokelon.toktales.core.engine.content.ContentException;
import com.tokelon.toktales.core.util.options.IOptions;

public interface IStorageFileAssetReader extends IManagedAssetReader {

	
	public InputStream readAsset(Path storagePath, IOptions options) throws ContentException;

}
