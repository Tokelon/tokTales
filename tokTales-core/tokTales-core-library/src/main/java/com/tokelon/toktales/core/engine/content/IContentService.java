package com.tokelon.toktales.core.engine.content;

import java.io.InputStream;

import com.tokelon.toktales.core.content.graphics.IBitmap;
import com.tokelon.toktales.core.engine.IEngineService;
import com.tokelon.toktales.core.game.model.IRectangle2i;
import com.tokelon.toktales.core.render.ITexture;
import com.tokelon.toktales.core.resources.IListing;
import com.tokelon.toktales.core.storage.IApplicationLocation;

public interface IContentService extends IEngineService {

	
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
	
	
	
	public ITexture cropTexture(ITexture texture, IRectangle2i bounds);
	
	public IBitmap cropBitmap(IBitmap bitmap, IRectangle2i bounds);
	
}
