package com.tokelon.toktales.core.content.manage.embedded;

import java.io.InputStream;

import com.tokelon.toktales.tools.assets.exception.AssetException;
import com.tokelon.toktales.tools.assets.reader.IManagedAssetReader;
import com.tokelon.toktales.tools.core.objects.options.IOptions;

public interface IEmbeddedAssetReader extends IManagedAssetReader {

	
	public InputStream readAsset(byte[] content, IOptions options) throws AssetException;
	
	public InputStream readAsset(String content, IOptions options) throws AssetException;
	
}
