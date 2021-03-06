package com.tokelon.toktales.core.content.manage.resources;

import java.io.InputStream;

import com.tokelon.toktales.core.location.IUniformLocation;
import com.tokelon.toktales.core.resources.IResource;
import com.tokelon.toktales.tools.assets.exception.AssetException;
import com.tokelon.toktales.tools.assets.reader.IManagedAssetReader;
import com.tokelon.toktales.tools.core.objects.options.IOptions;

public interface IResourceAssetReader extends IManagedAssetReader {

	
	public InputStream readAsset(IResource resource, IOptions options) throws AssetException;
	
	// Really have this?
	public InputStream readAsset(IUniformLocation location, String fileName, IOptions options) throws AssetException;
	
}
