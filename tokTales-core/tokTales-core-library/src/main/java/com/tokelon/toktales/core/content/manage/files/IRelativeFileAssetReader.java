package com.tokelon.toktales.core.content.manage.files;

import java.io.File;
import java.io.InputStream;

import com.tokelon.toktales.core.content.manage.IManagedAssetReader;
import com.tokelon.toktales.core.engine.content.ContentException;
import com.tokelon.toktales.core.util.options.IOptions;

public interface IRelativeFileAssetReader extends IManagedAssetReader {

	
	public InputStream readAsset(File file, Object parentIdentifier, IOptions options) throws ContentException;
	
}
