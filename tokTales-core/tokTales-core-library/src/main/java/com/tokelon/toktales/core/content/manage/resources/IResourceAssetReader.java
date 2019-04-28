package com.tokelon.toktales.core.content.manage.resources;

import java.io.InputStream;

import com.tokelon.toktales.core.content.manage.IManagedAssetReader;
import com.tokelon.toktales.core.engine.content.ContentException;
import com.tokelon.toktales.core.resources.IResource;
import com.tokelon.toktales.core.storage.IStructuredLocation;
import com.tokelon.toktales.core.util.options.IOptions;

public interface IResourceAssetReader extends IManagedAssetReader {

	
	public InputStream readAsset(IResource resource, IOptions options) throws ContentException;
	
	// Really have this?
	public InputStream readAsset(IStructuredLocation location, String fileName, IOptions options) throws ContentException;
	
}
