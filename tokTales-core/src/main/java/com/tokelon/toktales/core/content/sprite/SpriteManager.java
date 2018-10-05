package com.tokelon.toktales.core.content.sprite;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import com.tokelon.toktales.core.content.IContentManager;
import com.tokelon.toktales.core.content.IResourceManager;
import com.tokelon.toktales.core.content.ISpecialContent;
import com.tokelon.toktales.core.engine.content.ContentException;
import com.tokelon.toktales.core.engine.content.ContentLoadException;
import com.tokelon.toktales.core.engine.content.ContentNotFoundException;
import com.tokelon.toktales.core.engine.content.IContentService;
import com.tokelon.toktales.core.engine.content.IGraphicLoadingOptions;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.engine.storage.IStorageService;
import com.tokelon.toktales.core.engine.storage.StorageException;
import com.tokelon.toktales.core.storage.IApplicationLocation;
import com.tokelon.toktales.core.storage.IStructuredLocation;
import com.tokelon.toktales.core.storage.utils.ApplicationLocationWrapper;

public class SpriteManager implements ISpriteManager, ISpriteLoaderReceiver {

	public static final String TAG = SpriteManager.class.getSimpleName();
	
	private static final String SPRITE_LOADER_THREAD_NAME = "Sprite Loader";
	
	private static final ISpecialContent SPECIAL_SPRITE_EMPTY = IContentManager.SpecialContent.SPRITE_EMPTY;
	private static final ISpecialContent SPECIAL_SPRITE_NOT_FOUND = IContentManager.SpecialContent.SPRITE_NOT_FOUND;
	private static final ISpecialContent SPECIAL_SPRITE_LOAD_ERROR = IContentManager.SpecialContent.SPRITE_LOAD_ERROR;
	
	
	private final ILogger logger;
	private final IContentService contentService;
	private final IStorageService storageService;
	
	
	private final SpriteLoader spriteLoader;

	
	private final Map<ISpecialContent, ISpriteAsset> specials;
	
	private final SpriteCache spriteCache;
	private final SpritesetCache spritesetCache;
	

	@Inject
	public SpriteManager(ILogger logger, IContentService contentService, IStorageService storageService, IResourceManager resourceManager) {
		if(logger == null || contentService == null || storageService == null || resourceManager == null) {
			throw new NullPointerException();
		}
		
		this.logger = logger;
		this.contentService = contentService;
		this.storageService = storageService;

		
		spriteCache = new SpriteCache(16);
		spritesetCache = new SpritesetCache(16);
		
		specials = Collections.synchronizedMap(new HashMap<ISpecialContent, ISpriteAsset>());
		
		spriteLoader = new SpriteLoader(logger, contentService, storageService, resourceManager, this);
	}
	
	
	@Override
	public void startLoading() {
		spriteLoader.addSpecial(SPECIAL_SPRITE_EMPTY);
		spriteLoader.addSpecial(SPECIAL_SPRITE_NOT_FOUND);
		spriteLoader.addSpecial(SPECIAL_SPRITE_LOAD_ERROR);
		
		Thread loaderThread = new Thread(spriteLoader, SPRITE_LOADER_THREAD_NAME);
		loaderThread.setDaemon(true);
		loaderThread.start();
	}
	
	@Override
	public void stopLoading() {
		spriteLoader.stopLoader();
	}
	

	
	@Override
	public void runClearAll() {
		logger.i(TAG, "SpriteCache: Clearing all");
		
		spriteCache.runClearAll();
		spritesetCache.runClearAll();
	}
	
	@Override
	public void runClearMissing() {
		logger.i(TAG, "SpriteCache: Clearing missing");
		
		spriteCache.runClearErrorSprites();
		spritesetCache.runClearErrorSprites();
	}
	
	
	
	@Override
	public void specialLoaded(ISpecialContent special, ISpriteAsset spriteAsset) {
		specials.put(special, spriteAsset);
		
	}
	
	@Override
	public void failedToLoadSpecial(ISpecialContent special) {
		logger.e(TAG, "!! Failed to load special: " +special.toString());
		
		specials.put(special, AbstractCache.CACHE_SPECIAL_LOAD_ERROR);
	}
	
	
	@Override
	public void spriteLoaded(ISprite sprite, ISpriteAsset spriteAsset) {
		if(sprite.isEnclosed()) {
			spritesetCache.store(sprite.getSpriteset(), spriteAsset);
		}
		else {
			spriteCache.store(sprite, spriteAsset);	
		}
		
	}
	
	@Override
	public void spriteNotFound(ISprite sprite) {
		logger.w(TAG, "Sprite not found: " +sprite.getSpriteName());
		
		if(sprite.isEnclosed()) {
			spritesetCache.store(sprite.getSpriteset(), AbstractCache.CACHE_SPECIAL_NOT_FOUND);
		}
		else {
			spriteCache.store(sprite, AbstractCache.CACHE_SPECIAL_NOT_FOUND);
		}
	}
	
	@Override
	public void failedToLoadSprite(ISprite sprite, String errorMessage) {
		logger.w(TAG, "Failed to load sprite (" +sprite.getSpriteName() + ") with error: " +errorMessage);
		
		if(sprite.isEnclosed()) {
			spritesetCache.store(sprite.getSpriteset(), AbstractCache.CACHE_SPECIAL_LOAD_ERROR);
		}
		else {
			spriteCache.store(sprite, AbstractCache.CACHE_SPECIAL_LOAD_ERROR);
		}
	}
	

	
	

	
	
	
	@Override
	public ISprite[] getSpriteList(IStructuredLocation location, int maxResults, int index) throws ContentException, StorageException {
		ApplicationLocationWrapper locationWrapper = ApplicationLocationWrapper.getObjectPool().newObject();
		locationWrapper.objectReset();
		
		
		ISprite[] result;
		switch (location.getPrefix()) {
		case INTERNAL:
		case ASSET:
			
			locationWrapper.setActualLocation(location);
			result = getSpriteListInternal(locationWrapper, maxResults, index);
			break;
		case EXTERNAL:
			
			locationWrapper.setActualLocation(location);
			result = getSpriteListExternal(locationWrapper, maxResults, index);
			break;
		default:
			
			throw new ContentNotFoundException("Unable to get sprite list: Unknown prefix");
		}

		ApplicationLocationWrapper.getObjectPool().free(locationWrapper);
		return result;
	}
	
	@Override
	public ISprite[] getSpriteListInternal(IApplicationLocation location, int maxResults, int index) throws ContentException {
		
		String[] list = contentService.listAssetDir(location);
		
		return makeSpriteList(list, maxResults, index);
	}
	
	@Override
	public ISprite[] getSpriteListExternal(IApplicationLocation location, int maxResults, int index) throws StorageException {
		
		String[] list = storageService.listAppDirOnExternal(location);
		
		return makeSpriteList(list, maxResults, index);
	}
	
	
	private ISprite[] makeSpriteList(String[] list, int maxResults, int index) {
		
		// TODO: Test this function
		int actualResults = index+maxResults > list.length ? list.length-index : maxResults;
		
		
		ISprite[] sprites = new SpriteImpl[actualResults];
		
		for(int i=0; i < actualResults; i++) {
			sprites[i] = new SpriteImpl(list[index+i]);
		}

		return sprites;
	}

	
	
	
	private ISpriteAsset ingoingFilter(ISprite sprite) {
		//if(sprite.getSpriteName().startsWith("#")) {
		ISpriteAsset sa = null;
		
		if(sprite.getSpriteName().equals("#emptysprite")) {	// TODO: Implement this better
			sa = specials.get(SPECIAL_SPRITE_EMPTY);
		}
		
		return sa;
	}
	
	private ISpriteAsset outgoingFilter(ISpriteAsset asset) {
		if(asset == AbstractCache.CACHE_SPECIAL_NOT_FOUND) {
			asset = specials.get(SPECIAL_SPRITE_NOT_FOUND);
		}
		else if(asset == AbstractCache.CACHE_SPECIAL_LOAD_ERROR) {
			asset = specials.get(SPECIAL_SPRITE_LOAD_ERROR);
		}
		
		return asset;
	}
	
	private void outgoingFilterError(ISpriteAsset asset) throws ContentNotFoundException, ContentLoadException {
		if(asset == AbstractCache.CACHE_SPECIAL_NOT_FOUND) {
			throw new ContentNotFoundException();
		}
		else if(asset == AbstractCache.CACHE_SPECIAL_LOAD_ERROR) {
			throw new ContentLoadException();
		}
	}
	

	
	@Override
	public ISpriteAsset getSpriteAsset(ISprite sprite) {
		return getSpriteAsset(sprite, null);
	}
	
	@Override
	public ISpriteAsset getSpriteAsset(ISprite sprite, IGraphicLoadingOptions options) {
		ISpriteAsset sa;
		
		if((sa = ingoingFilter(sprite)) != null) {
			return sa;
		}
		
		
		if(sprite.isEnclosed()) {
			if(spritesetCache.contains(sprite.getSpriteset())) {
				sa = spritesetCache.retrieve(sprite.getSpriteset());
			}
		}
		else {
			if(spriteCache.contains(sprite)) {
				sa = spriteCache.retrieve(sprite);
			}
		}
		
		
		if(sa == null) {
			spriteLoader.addInQueue(sprite, options);	// Add in loader queue
		}
		else {
			sa = outgoingFilter(sa);
		}
		
		
		return sa;
	}
	
	@Override
	public ISpriteAsset getSpriteAssetOrError(ISprite sprite) throws ContentNotFoundException, ContentLoadException {
		ISpriteAsset sa;
		
		if((sa = ingoingFilter(sprite)) != null) {
			return sa;
		}
		
		
		
		if(sprite.isEnclosed()) {
			if(spritesetCache.contains(sprite.getSpriteset())) {
				sa = spritesetCache.retrieve(sprite.getSpriteset());
			}
		}
		else {
			if(spriteCache.contains(sprite)) {
				sa = spriteCache.retrieve(sprite);
			}
		}
		
		
		if(sa == null) {
			throw new ContentNotFoundException();
		}
		else {
			outgoingFilterError(sa);
		}
		
		return sa;
	}


	@Override
	public ISpriteAsset getSpriteAssetLoadIfNeeded(ISprite sprite) {
		return getSpriteAssetLoadIfNeeded(sprite, null);
	}
	
	@Override
	public ISpriteAsset getSpriteAssetLoadIfNeeded(ISprite sprite, IGraphicLoadingOptions options) {
		ISpriteAsset sa;
		
		if((sa = ingoingFilter(sprite)) != null) {
			return sa;
		}
		
		
		if(sprite.isEnclosed()) {
			
			if(spritesetCache.contains(sprite.getSpriteset())) {
				sa = spritesetCache.retrieve(sprite.getSpriteset());
				
				sa = outgoingFilter(sa);
			}
			else {
				try {
					sa = spriteLoader.loadSprite(sprite, options);

					spritesetCache.store(sprite.getSpriteset(), sa);

				} catch(ContentNotFoundException cnfe) {
					sa = specials.get(SPECIAL_SPRITE_NOT_FOUND);
					spritesetCache.store(sprite.getSpriteset(), AbstractCache.CACHE_SPECIAL_NOT_FOUND);
				} catch(ContentLoadException cle) {
					sa = specials.get(SPECIAL_SPRITE_LOAD_ERROR);
					spritesetCache.store(sprite.getSpriteset(), AbstractCache.CACHE_SPECIAL_LOAD_ERROR);
				} catch(ContentException ce) {
					// Set the load error sprite
					sa = specials.get(SPECIAL_SPRITE_LOAD_ERROR);
					spritesetCache.store(sprite.getSpriteset(), AbstractCache.CACHE_SPECIAL_LOAD_ERROR);
				}
			}
		}
		else {

			if(spriteCache.contains(sprite)) {	// Has been loaded
				sa = spriteCache.retrieve(sprite);
				
				sa = outgoingFilter(sa);
			}
			else {	// Not loaded
				try {
					sa = spriteLoader.loadSprite(sprite, options);

					// Put into cache
					spriteCache.store(sprite, sa);

				} catch(ContentNotFoundException cnfe) {
					sa = specials.get(SPECIAL_SPRITE_NOT_FOUND);
					spriteCache.store(sprite, AbstractCache.CACHE_SPECIAL_NOT_FOUND);
				} catch(ContentLoadException cle) {
					sa = specials.get(SPECIAL_SPRITE_LOAD_ERROR);
					spriteCache.store(sprite, AbstractCache.CACHE_SPECIAL_LOAD_ERROR);
				} catch(ContentException ce) {
					
					sa = specials.get(SPECIAL_SPRITE_LOAD_ERROR);
					spriteCache.store(sprite, AbstractCache.CACHE_SPECIAL_LOAD_ERROR);
				}
			}
		}
		
		
		return sa;
	}
	

	
	@Override
	public ISpriteAsset getSpriteAssetOrErrorLoadIfNeeded(ISprite sprite) throws ContentNotFoundException, ContentLoadException, ContentException {
		return getSpriteAssetOrErrorLoadIfNeeded(sprite, null);
	}

	@Override
	public ISpriteAsset getSpriteAssetOrErrorLoadIfNeeded(ISprite sprite, IGraphicLoadingOptions options) throws ContentNotFoundException, ContentLoadException, ContentException {
		ISpriteAsset sa;
		
		if(sprite.isEnclosed()) {
			
			if(spritesetCache.contains(sprite.getSpriteset())) {
				sa = spritesetCache.retrieve(sprite.getSpriteset());
				
				outgoingFilterError(sa);
			}
			else {
				sa = spriteLoader.loadSprite(sprite, options);
				
				spritesetCache.store(sprite.getSpriteset(), sa);
			}
		}
		else {
			
			if(spriteCache.contains(sprite)) {	// Has been loaded
				sa = spriteCache.retrieve(sprite);
				
				outgoingFilterError(sa);
			}
			else {	// Not loaded
				sa = spriteLoader.loadSprite(sprite, options);

				// Put into cache
				spriteCache.store(sprite, sa);	// Do I need to store the sprite as special_not_found in this case?
			}
			
		}
		
		return sa;
	}
	
	
	
	@Override
	public boolean hasSprite(ISprite sprite) {
		if(sprite.isEnclosed()) {
			return spritesetCache.contains(sprite.getSpriteset());
		}
		else {
			return spriteCache.contains(sprite);
		}
	}


	@Override
	public ISpriteLoader getLoader() {
		return spriteLoader;
	}
	
	
	@Override
	public boolean assetIsSpecial(ISpriteAsset asset) {
		return asset == specials.get(SPECIAL_SPRITE_NOT_FOUND) || asset == specials.get(SPECIAL_SPRITE_LOAD_ERROR);
	}
	
	
	public static class SpriteManagerFactory implements ISpriteManagerFactory {
		private final ILogger logger;
		private final IContentService contentService;
		private final IStorageService storageService;

		@Inject
		public SpriteManagerFactory(ILogger logger, IContentService contentService, IStorageService storageService) {
			this.logger = logger;
			this.contentService = contentService;
			this.storageService = storageService;
		}
		
		@Override
		public ISpriteManager create(IResourceManager resourceManager) {
			return new SpriteManager(logger, contentService, storageService, resourceManager);
		}
	}

}
