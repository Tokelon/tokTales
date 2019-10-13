package com.tokelon.toktales.core.content.manage.files;

import java.io.File;
import java.io.InputStream;

import com.tokelon.toktales.tools.assets.exception.AssetException;
import com.tokelon.toktales.tools.assets.reader.IManagedAssetReader;
import com.tokelon.toktales.tools.core.objects.options.IOptions;

public interface IRelativeFileAssetReader extends IManagedAssetReader {

	
	public InputStream readAsset(File file, Object parentIdentifier, IOptions options) throws AssetException;
	
}
