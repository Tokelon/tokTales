package com.tokelon.toktales.tools.assets.files;

import java.io.InputStream;
import java.nio.file.Path;

import com.tokelon.toktales.tools.assets.exception.AssetException;
import com.tokelon.toktales.tools.assets.reader.IManagedAssetReader;
import com.tokelon.toktales.tools.core.objects.options.IOptions;

public interface IPathAssetReader extends IManagedAssetReader {

	
	public InputStream readAsset(Path path, IOptions options) throws AssetException;

}
