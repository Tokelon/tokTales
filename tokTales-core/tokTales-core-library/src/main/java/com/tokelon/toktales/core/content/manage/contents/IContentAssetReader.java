package com.tokelon.toktales.core.content.manage.contents;

import java.io.InputStream;

import com.tokelon.toktales.core.location.IApplicationLocation;
import com.tokelon.toktales.tools.assets.exception.AssetException;
import com.tokelon.toktales.tools.assets.reader.IManagedAssetReader;
import com.tokelon.toktales.tools.core.objects.options.IOptions;

public interface IContentAssetReader extends IManagedAssetReader {


	public InputStream readAsset(String name, IApplicationLocation location, IOptions options) throws AssetException;
	
}
