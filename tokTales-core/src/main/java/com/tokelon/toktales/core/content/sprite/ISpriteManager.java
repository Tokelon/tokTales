package com.tokelon.toktales.core.content.sprite;

import com.tokelon.toktales.core.content.IResourceManager;
import com.tokelon.toktales.core.engine.content.ContentException;
import com.tokelon.toktales.core.engine.content.ContentLoadException;
import com.tokelon.toktales.core.engine.content.ContentNotFoundException;
import com.tokelon.toktales.core.engine.content.IGraphicLoadingOptions;
import com.tokelon.toktales.core.engine.storage.StorageException;
import com.tokelon.toktales.core.storage.IApplicationLocation;
import com.tokelon.toktales.core.storage.IStructuredLocation;

public interface ISpriteManager {

	
	public interface ISpriteManagerFactory {
		
		public ISpriteManager create(IResourceManager resourceManager);
	}
	
	
	
	public void startLoading();
	public void stopLoading();
	
	
	public ISprite[] getSpriteList(IStructuredLocation location, int maxResults, int index) throws ContentException, StorageException;

	public ISprite[] getSpriteListInternal(IApplicationLocation location, int maxResults, int index) throws ContentException;
	
	public ISprite[] getSpriteListExternal(IApplicationLocation location, int maxResults, int index) throws StorageException;
	
	
	public ISpriteLoader getLoader();
	
	

	/** Returns the asset for the given sprite, or:<br> 
	 * (a) If the asset for this sprite has not been loaded, return null and puts the sprite in the loader queue.<br>
	 * (b) If the asset failed to load (not found, load error etc.) returns the appropriate error sprite asset.
	 * 
	 * @param sprite
	 * @return
	 */
	public ISpriteAsset getSpriteAsset(ISprite sprite);
	
	public ISpriteAsset getSpriteAsset(ISprite sprite, IGraphicLoadingOptions options);
	
	
	/** Returns the asset for the given sprite, or:<br>
	 * Throws a ContentNotFoundException if the asset for this sprite has not been loaded.
	 * 
	 * @param sprite
	 * @return
	 * @throws ContentNotFoundException
	 * @throws ContentLoadException
	 */
	public ISpriteAsset getSpriteAssetOrError(ISprite sprite) throws ContentNotFoundException, ContentLoadException;
		
	
	/** Returns the asset for the given sprite. If not loaded yet, loads it.<br>
	 * Returns an error asset if the loading failed.
	 * 
	 * @param sprite
	 * @param loadIfNecessary
	 * @return
	 */
	public ISpriteAsset getSpriteAssetLoadIfNeeded(ISprite sprite);
	
	public ISpriteAsset getSpriteAssetLoadIfNeeded(ISprite sprite, IGraphicLoadingOptions options);
	
	
	
	/** Returns the asset for the given sprite. If not loaded yet, loads it.<br>
	 * Throws an exception if there is an error while loading.
	 * 
	 * @param sprite
	 * @return
	 * @throws ContentNotFoundException
	 * @throws ContentLoadException
	 * @throws ContentException
	 */
	public ISpriteAsset getSpriteAssetOrErrorLoadIfNeeded(ISprite sprite) throws ContentNotFoundException, ContentLoadException, ContentException;
	
	public ISpriteAsset getSpriteAssetOrErrorLoadIfNeeded(ISprite sprite, IGraphicLoadingOptions options) throws ContentNotFoundException, ContentLoadException, ContentException;

	
	public boolean hasSprite(ISprite sprite);

	public void runClearAll();
	
	public void runClearMissing();
	
	
	public boolean assetIsSpecial(ISpriteAsset asset);
	
	
	
	//public ISpriteAsset loadSprite(ISprite sprite);

	//public ISprite findSprite(String name);	
	
}
