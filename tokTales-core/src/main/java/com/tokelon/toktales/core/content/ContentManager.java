package com.tokelon.toktales.core.content;

import javax.inject.Inject;

import com.tokelon.toktales.core.content.sprite.ISpriteManager;
import com.tokelon.toktales.core.content.sprite.ISpriteManager.ISpriteManagerFactory;

public class ContentManager implements IContentManager {

	private static final String TAG = ContentManager.class.getSimpleName();
	
	
	private IResourceManager managerResource;
	private ISpriteManager managerSprite;
	
	@Inject
	public ContentManager(IResourceManager resourceManager, ISpriteManagerFactory spriteManagerFactory) {
		if(resourceManager == null || spriteManagerFactory == null) {
			throw new NullPointerException();
		}
		
		this.managerResource = resourceManager;
		this.managerSprite = spriteManagerFactory.create(resourceManager);
		

		managerSprite.startLoading();
		
		//managerSprite.stopLoading();	// TODO: Important - Stop this when terminating!
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
