package com.tokelon.toktales.tools.assets.files;

import java.io.File;
import java.io.InputStream;

import com.tokelon.toktales.tools.assets.exception.AssetException;
import com.tokelon.toktales.tools.assets.reader.IManagedAssetReader;
import com.tokelon.toktales.tools.core.objects.options.IOptions;

public interface IFileAssetReader extends IManagedAssetReader {

	
	public InputStream readAsset(File file, IOptions options) throws AssetException;
	
}
