package com.tokelon.toktales.core.content.sprite;

import com.tokelon.toktales.core.engine.content.ContentException;
import com.tokelon.toktales.core.engine.content.ContentLoadException;
import com.tokelon.toktales.core.engine.content.ContentNotFoundException;
import com.tokelon.toktales.core.engine.content.IGraphicLoadingOptions;
import com.tokelon.toktales.core.engine.storage.StorageException;
import com.tokelon.toktales.core.storage.IApplicationLocation;

public interface ISpriteLoader {

	// Needed in the interface? or implementation detail
	//public void resetResources(IResourceView resourceView);
	
	public void addInQueue(ISprite sprite, IGraphicLoadingOptions options);

	
	/* TODO: Add options object for loading, something like LoadingOptions.
	 * 
	 */
	
	/** Ignores any resources attached to the sprite and only uses the defined resources for lookup.
	 * 
	 * @param sprite
	 * @param options
	 * @return
	 * @throws ContentNotFoundException
	 * @throws ContentLoadException
	 * @throws ContentException
	 */
	public ISpriteAsset loadSprite(ISprite sprite, IGraphicLoadingOptions options) throws ContentNotFoundException, ContentLoadException, ContentException;
	
	/** If consiserAttachedResource is true, will only use the resource attached to the sprite for lookup.
	 * 
	 * @param sprite
	 * @param considerAttachedResource
	 * @param options
	 * @return
	 * @throws ContentNotFoundException
	 * @throws ContentLoadException
	 * @throws ContentException
	 */
	public ISpriteAsset loadSprite(ISprite sprite, boolean considerAttachedResource, IGraphicLoadingOptions options) throws ContentNotFoundException, ContentLoadException, ContentException;

	public ISpriteAsset loadSpriteInternal(IApplicationLocation location, String fileName, IGraphicLoadingOptions options) throws ContentLoadException, ContentException;

	public ISpriteAsset loadSpriteExternal(IApplicationLocation location, String fileName, IGraphicLoadingOptions options) throws StorageException, ContentLoadException, ContentException;

}
