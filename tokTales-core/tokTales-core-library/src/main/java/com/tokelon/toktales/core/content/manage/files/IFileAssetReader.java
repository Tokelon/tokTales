package com.tokelon.toktales.core.content.manage.files;

import java.io.File;
import java.io.InputStream;

import com.tokelon.toktales.core.content.manage.IManagedAssetReader;
import com.tokelon.toktales.core.engine.content.AssetException;
import com.tokelon.toktales.tools.core.objects.options.IOptions;

public interface IFileAssetReader extends IManagedAssetReader {

	
	public InputStream readAsset(File file, IOptions options) throws AssetException;
	
}
