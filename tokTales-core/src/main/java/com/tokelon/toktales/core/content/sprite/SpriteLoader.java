package com.tokelon.toktales.core.content.sprite;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

import com.tokelon.toktales.core.content.GenericAsset;
import com.tokelon.toktales.core.content.IAssetContainer;
import com.tokelon.toktales.core.content.IResourceManager;
import com.tokelon.toktales.core.content.ISpecialContent;
import com.tokelon.toktales.core.engine.TokTales;
import com.tokelon.toktales.core.engine.content.ContentException;
import com.tokelon.toktales.core.engine.content.ContentLoadException;
import com.tokelon.toktales.core.engine.content.ContentNotFoundException;
import com.tokelon.toktales.core.engine.content.IContentService;
import com.tokelon.toktales.core.engine.content.IGraphicLoadingOptions;
import com.tokelon.toktales.core.engine.storage.IStorageService;
import com.tokelon.toktales.core.engine.storage.StorageException;
import com.tokelon.toktales.core.prog.annotation.ProgRequiresLog;
import com.tokelon.toktales.core.resources.IResource;
import com.tokelon.toktales.core.resources.IResourceLookup;
import com.tokelon.toktales.core.resources.ResourceLookup;
import com.tokelon.toktales.core.resources.ResourceTypeFilter;
import com.tokelon.toktales.core.resources.IListing.FileDescriptor;
import com.tokelon.toktales.core.storage.IApplicationLocation;
import com.tokelon.toktales.core.storage.IStructuredLocation;
import com.tokelon.toktales.core.storage.utils.ApplicationLocationWrapper;

@ProgRequiresLog
public class SpriteLoader implements ISpriteLoader, Runnable {

	public static final String TAG = "SpriteLoader";
	
	private final LinkedBlockingQueue<ISprite> loadingQueue = new LinkedBlockingQueue<ISprite>();
	private final LinkedBlockingQueue<ISpecialContent> specialLoadingQueue = new LinkedBlockingQueue<ISpecialContent>();

	private final Map<ISprite, IGraphicLoadingOptions> optionsMap = Collections.synchronizedMap(new HashMap<ISprite, IGraphicLoadingOptions>());
	
	private final Set<ISpriteset> spritesetLoading = Collections.synchronizedSet(new HashSet<ISpriteset>());
	
	

	private final IContentService contentService;
	private final IStorageService storageService;
	private final ISpriteLoaderReceiver spriteLoaderReceiver;
	private final IResourceManager resourceManager;
	
	
	private boolean running = true;
	
	
	public SpriteLoader(IContentService contentService, IStorageService storageService, IResourceManager resourceManager, ISpriteLoaderReceiver spriteLoaderReceiver) {
		if(contentService == null || storageService == null || resourceManager == null || resourceManager == null || spriteLoaderReceiver == null) {
			throw new NullPointerException();
		}
		
		this.contentService = contentService;
		this.storageService = storageService;
		this.spriteLoaderReceiver = spriteLoaderReceiver;
		this.resourceManager = resourceManager;
	}
	
	
	
	public void stopLoader() {
		running = false;
	}
	
	public void addSpecial(ISpecialContent special) {
		if(!specialLoadingQueue.contains(special)) {
			specialLoadingQueue.add(special);
		}
		
	}
	
	@Override
	public void addInQueue(ISprite sprite, IGraphicLoadingOptions options) {
		if(sprite.isEnclosed() && !spritesetLoading.contains(sprite.getSpriteset())) {
			TokTales.getLog().d(TAG, String.format("Sprite \"%s\" from spriteset \"%s\" was added to loading queue", sprite.getSpriteName(), sprite.getSpriteset().getSpritesetName()));
			
			spritesetLoading.add(sprite.getSpriteset());
			optionsMap.put(sprite, options);	// This need to be before add !! (synchronization)
			loadingQueue.add(sprite);
		}
		else if(!sprite.isEnclosed() && !loadingQueue.contains(sprite)) {
			TokTales.getLog().d(TAG, String.format("Sprite \"%s\" was added to loading queue", sprite.getSpriteName()));
			
			optionsMap.put(sprite, options);	// This need to be before add !! (synchronization)
			loadingQueue.add(sprite);			
		}
		
	}
	
	
	
	@Override
	public void run() {
		running = true;
		
		while(running) {
			
			
			ISpecialContent specialAsset = specialLoadingQueue.poll();
			if(specialAsset != null) {
				loadAndStoreSpecial(specialAsset);
			}
			else {
				try {
					/* If I remove these here, they could be added again for loading while we are in the loadAndStore function?
					 * TODO: Check this
					 */
					
					//System.out.println("Polling for sprite...");
					// Could use peek(), and remove() after loadAndStore but peek doesn't wait, rather it returns null if the list is empty
					ISprite nextSprite = loadingQueue.take();
					IGraphicLoadingOptions opt = optionsMap.remove(nextSprite);
					
					//System.out.println("Sprite found. Loading...");
					loadAndStore(nextSprite, opt);
					

					/* Remove the spriteset AFTER loading it so that it wont be added to the loading queue again
					 */
					if(nextSprite.isEnclosed()) {
						spritesetLoading.remove(nextSprite.getSpriteset());	
					}
					
					
					
					
					/* DO NOT check if it's already loaded
					 * instead only add once to loading queue
					if(spriteManager.hasSprite(nextSprite)) {
						continue;
					}
					else {
						loadAndStore(nextSprite);
					}*/
				} catch (InterruptedException e) {
					// ?
					TokTales.getLog().d(TAG, "Loader thread was interrupted while waiting!");
				}
			}
		}		
		
		TokTales.getLog().d(TAG, "Loader Thread terminated");
	}
	
	
	private void loadAndStoreSpecial(ISpecialContent special) {
		ISpriteAsset sa = null;
		
		try {
			sa = loadSpecialSprite(special);
			
			spriteLoaderReceiver.specialLoaded(special, sa);
		} catch (ContentException ce) {
			
			// This is bad
			//Prog.gHub().gLog().e(TAG, "Failed to load special sprite: " +special);
			spriteLoaderReceiver.failedToLoadSpecial(special);
		}
	}
	
	
	private ISpriteAsset loadSpecialSprite(ISpecialContent specialContent) throws ContentException {
		
		IAssetContainer<?> ac = contentService.loadSpecialContent(specialContent);
		
		ISpriteAsset result = new GenericAsset(ac);
		return result;
	}


	

	private void loadAndStore(ISprite sprite, IGraphicLoadingOptions options) {
		ISpriteAsset sa = null;
		
		try {
			sa = loadSprite(sprite, options);
			
			spriteLoaderReceiver.spriteLoaded(sprite, sa);
			
		} catch (ContentNotFoundException cnfe) {
			
			spriteLoaderReceiver.spriteNotFound(sprite);
			
		} catch (ContentLoadException cle) {
			
			//Prog.gHub().gLog().e(TAG, "Failed to load sprite: " +sprite);
			spriteLoaderReceiver.failedToLoadSprite(sprite, cle.getMessage());
		} catch (ContentException ce) {
			
			spriteLoaderReceiver.failedToLoadSprite(sprite, ce.getMessage());
		}
	}
	

	

	@Override
	public ISpriteAsset loadSprite(ISprite sprite, IGraphicLoadingOptions options) throws ContentNotFoundException, ContentLoadException, ContentException {
		// TODO: Why the fuck was this false??
		// Why do not consider attached resource by default?
		return loadSprite(sprite, true, options);
	}
	
	@Override
	public ISpriteAsset loadSprite(ISprite sprite, boolean considerAttachedResource, IGraphicLoadingOptions options) throws ContentNotFoundException, ContentLoadException, ContentException {
		if(sprite.isEnclosed()) {
	
			ISpriteset spriteset = sprite.getSpriteset();
			
			if(considerAttachedResource && spriteset.hasResourceAttached()) {

				IAssetContainer<?> ac = tryLoadResource(spriteset.getSpritesetName(), spriteset.getResource(), options);
				
				if(ac == null) {
					throw new ContentNotFoundException();
				} else {
					return new GenericAsset(ac);
				}
			}
			else {
				return loadSpriteFromListing(spriteset.getSpritesetName(), options);
			}
		}
		else {
			if(considerAttachedResource && sprite.hasResourceAttached()) {

				IAssetContainer<?> ac = tryLoadResource(sprite.getSpriteName(), sprite.getResource(), options);
				
				if(ac == null) {
					throw new ContentNotFoundException();
				} else {
					return new GenericAsset(ac);
				}
			}
			else {
				return loadSpriteFromListing(sprite.getSpriteName(), options);
				//return loadSpriteFromResources(sprite);
			}
		}
		
	}	
	
	
	private ISpriteAsset loadSpriteFromListing(String spriteName, IGraphicLoadingOptions options) throws ContentException {
		
		// Use pool
		ResourceLookup spriteLookup = ResourceLookup.getObjectPool().newObject();	// Get the object
		
		// Set the object data
		spriteLookup.setFilterType(ResourceTypeFilter.F_SPRITE);
		spriteLookup.setNameMatchingType(IResourceLookup.STRING_FILTER_TYPE_EQUALS);
		spriteLookup.setFilterName(spriteName);


		
		FileDescriptor fd = resourceManager.lookForFileMatching(spriteLookup);	// Use the object
		ResourceLookup.getObjectPool().free(spriteLookup);		// Free the object
		
		if(fd == null) {
			throw new ContentNotFoundException();
		}
		
		IAssetContainer<?> ac = loadResource(fd.getName(), fd.getLocation(), options);		
		return new GenericAsset(ac);
	}
	

	
	
	// TODO:
	// Should replace the "try" functions with the ones who throw errors?
	private IAssetContainer<?> loadResource(String fileName, IStructuredLocation location, IGraphicLoadingOptions options) throws ContentException {
		IAssetContainer<?> ac = null;
		ApplicationLocationWrapper locationWrapper = ApplicationLocationWrapper.getObjectPool().newObject();
		locationWrapper.objectReset();
		
		switch(location.getPrefix()) {
		case INTERNAL:	// Have internal the same as asset?
		case ASSET:
			locationWrapper.setActualLocation(location);
			ac = contentService.loadGraphicAsset(locationWrapper, fileName, options);	// was lookForGraphicAssetAndLoad()
			
			break;
		case EXTERNAL:
			locationWrapper.setActualLocation(location);
			
			InputStream extSprite = null;
			try {
				extSprite = storageService.tryReadAppFileOnExternal(locationWrapper, fileName);
				
				
				if(extSprite != null) {	// Content was found
					ac = contentService.loadGraphicAssetFromSource(extSprite, options);	// throws ContentLoadException
				}
			}
			finally {
				if(extSprite != null) {
					try {
						extSprite.close();
					} catch (IOException e) {
						// Nothing to do
					}
				}
			}
			
			break;
		default:
			throw new ContentNotFoundException(String.format("Unable to load file \"%s\" from location \"%s\" (Unsupported location)", fileName, location.getOriginalValue()));
		}
		
		ApplicationLocationWrapper.getObjectPool().free(locationWrapper);
		return ac;
	}
	
	
	
	/*
	
	private ISpriteAsset loadSpriteFromResources(ISprite sprite) throws ContentNotFoundException, ContentLoadException {
		ISpriteAsset result;
		
		
		GenericApplicationLocation relLocation = new GenericApplicationLocation();
		IAssetContainer<?> ac = null;
		
		Iterator<IResource> it = resources.iterator();
		while(it.hasNext() && ac == null) {
			IResource r = it.next();
			
			ac = loadResource(sprite, r, relLocation);
		}
		
		if(ac == null) {
			throw new ContentNotFoundException();
		}
	
		result = new GenericAsset(ac);
		return result;
	}
	
	*/
	
	private IAssetContainer<?> tryLoadResource(String spriteName, IResource resource, IGraphicLoadingOptions options) throws ContentLoadException, ContentException {
		IAssetContainer<?> ac = null;
		ApplicationLocationWrapper locationWrapper = ApplicationLocationWrapper.getObjectPool().newObject();
		locationWrapper.objectReset();
		
		switch(resource.getLocation().getPrefix()) {
		case INTERNAL:	// Have internal the same as asset?
		case ASSET:
			locationWrapper.setActualLocation(resource.getLocation());
			ac = contentService.lookForGraphicAssetAndLoad(locationWrapper, spriteName, options);
			
			break;
		case EXTERNAL:
			locationWrapper.setActualLocation(resource.getLocation());
			
			InputStream extSprite = null;
			try {
				extSprite = storageService.tryReadAppFileOnExternal(locationWrapper, spriteName);
				
				
				if(extSprite != null) {	// Content was found
					ac = contentService.loadGraphicAssetFromSource(extSprite, options);	// throws ContentLoadException
				}
			}
			finally {
				if(extSprite != null) {
					try {
						extSprite.close();
					} catch (IOException e) {
						// Nothing to do
					}
				}
			}
			
			break;
		default:
			TokTales.getLog().w(TAG, "Ignoring resource: " +resource.getName() +" (Unsupported location)");
			break;
		}
		
		ApplicationLocationWrapper.getObjectPool().free(locationWrapper);
		return ac;
	}
	
	
	
	@Override
	public ISpriteAsset loadSpriteInternal(IApplicationLocation location, String fileName, IGraphicLoadingOptions options) throws ContentLoadException, ContentException {
		
		IAssetContainer<?> ac = contentService.loadGraphicAsset(location, fileName, options);
		
		ISpriteAsset result = new GenericAsset(ac);
		return result;
	}
	
	
	@Override
	public ISpriteAsset loadSpriteExternal(IApplicationLocation location, String fileName, IGraphicLoadingOptions options) throws StorageException, ContentLoadException, ContentException {
		ISpriteAsset result;
		
		IAssetContainer<?> ac;
		InputStream extSpriteStream = null;
		try {
			extSpriteStream = storageService.readAppFileOnExternal(location, fileName);
			ac = contentService.loadGraphicAssetFromSource(extSpriteStream, options);
		}
		finally {
			if(extSpriteStream != null) {
				try {
					extSpriteStream.close();
				} catch (IOException e) {
					// Nothing to do
				}
			}
		}
		
		result = new GenericAsset(ac);
		return result;
	}
		
}
