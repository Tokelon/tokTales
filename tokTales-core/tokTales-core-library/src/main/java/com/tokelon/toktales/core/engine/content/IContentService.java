package com.tokelon.toktales.core.engine.content;

import java.io.InputStream;

import com.tokelon.toktales.core.engine.IEngineService;
import com.tokelon.toktales.core.location.IApplicationLocation;
import com.tokelon.toktales.core.resources.IListing;

public interface IContentService extends IEngineService {


	public String getRoot();

	/* The fact to this returns a string array is a pure android limitation.
	 * There should be additional support for systems where it is possible to use File classes and such.
	 * 
	 */
	public String[] listAssetDir(IApplicationLocation location) throws ContentException;
	
	// This should be supported as well. (Either unsupported by Android or emulated in some way).
	//public File[] listAssetDir(IApplicationLocation location) throws ContentException;
	
	
	public IListing createAssetListing(IApplicationLocation location) throws ContentException;
	
	
	public InputStream readAppFileOnAssets(IApplicationLocation location, String fileName) throws ContentException;

	public InputStream tryReadAppFileOnAssets(IApplicationLocation location, String fileName);
	
}
