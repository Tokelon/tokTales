package com.tokelon.toktales.core.content;

import com.tokelon.toktales.core.content.sprite.ISpriteManager;
import com.tokelon.toktales.core.content.sprite.SpriteManager;
import com.tokelon.toktales.core.engine.IEngine;
import com.tokelon.toktales.core.engine.content.IContentService;
import com.tokelon.toktales.core.engine.storage.IStorageService;
import com.tokelon.toktales.core.prog.annotation.TokTalesRequired;

@TokTalesRequired
public class ContentManager implements IContentManager {

	private static final String TAG = ContentManager.class.getSimpleName();
	
	
	/* Managers */
	private ResourceManager managerResource;
	private SpriteManager managerSprite;
	
	
	public ContentManager(IEngine engine) {
		
		IStorageService storageService = engine.getStorageService();
		IContentService contentService = engine.getContentService();
		
		
		managerResource = new ResourceManager(contentService, storageService);
		managerSprite = new SpriteManager(contentService, storageService, managerResource);
		
		
		managerSprite.startLoading();
		
		//managerSprite.stopLoading();	// TODO: Stop this when terminating!
	}
	

	
	@Override
	public IResourceManager getResourceManager() {
		return managerResource;
	}
	
	@Override
	public ISpriteManager getSpriteManager() {
		return managerSprite;
	}

	

	@Override
	public void notifyResourcesChanged() {
		
		// Do something?
	}

}
