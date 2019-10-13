package com.tokelon.toktales.core.content.manage.files;

import java.io.InputStream;
import java.nio.file.Path;

import com.tokelon.toktales.tools.assets.exception.AssetException;
import com.tokelon.toktales.tools.assets.reader.IManagedAssetReader;
import com.tokelon.toktales.tools.core.objects.options.IOptions;

public interface IRelativePathAssetReader extends IManagedAssetReader {

	
	public InputStream readAsset(Path path, Object parentIdentifier, IOptions options) throws AssetException;
	
}
