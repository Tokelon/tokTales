package com.tokelon.toktales.core.engine.content;

import java.io.File;
import java.io.InputStream;

import com.tokelon.toktales.core.content.IAssetContainer;
import com.tokelon.toktales.core.content.ISpecialContent;
import com.tokelon.toktales.core.content.text.ITextureFont;
import com.tokelon.toktales.core.engine.IEngineService;
import com.tokelon.toktales.core.render.IRenderTexture;
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
	
	
	
	
	/** Does not throw any exceptions.
	 * 
	 * @param location
	 * @param fileName
	 * @return The loaded asset, or null if an error occurs.
	 */
	public IAssetContainer<?> lookForGraphicAssetAndLoad(IApplicationLocation location, String fileName);
	
	public IAssetContainer<?> lookForGraphicAssetAndLoad(IApplicationLocation location, String fileName, IGraphicLoadingOptions options); 
	//findAndLoadResource()
	
	
	//public InputStream loadAsset(String assetPath) throws IOException;
	
	public IAssetContainer<?> loadGraphicAsset(IApplicationLocation location, String fileName) throws ContentLoadException, ContentException;
	
	public IAssetContainer<?> loadGraphicAsset(IApplicationLocation location, String fileName, IGraphicLoadingOptions options) throws ContentLoadException, ContentException;
	
	
	
	public IAssetContainer<?> loadSpecialContent(ISpecialContent specialContent) throws ContentException;
	

	public IAssetContainer<?> loadGraphicAssetFromSource(InputStream source) throws ContentLoadException, ContentException;

	public IAssetContainer<?> loadGraphicAssetFromSource(InputStream source, IGraphicLoadingOptions options) throws ContentLoadException, ContentException;

	
	public IRenderTexture extractAssetTexture(IAssetContainer<?> container);


	public ITextureFont lookForFontAndLoad(IApplicationLocation location, String filename);
	//public ITextureFont lookForFontAndLoad(IApplicationLocation location, String filename, IFontLoadingOptions options);
	
	public ITextureFont loadFont(IApplicationLocation location, String filename) throws ContentException;
	
	public ITextureFont loadFontFromFile(File file) throws ContentException;
	
}
