package com.tokelon.toktales.core.content;

import com.tokelon.toktales.core.content.manage.bitmap.IBitmapAssetManager;
import com.tokelon.toktales.core.content.manage.codepoint.ICodepointAssetManager;
import com.tokelon.toktales.core.content.manage.font.IFontAssetManager;
import com.tokelon.toktales.core.content.manage.sprite.ISpriteAssetManager;
import com.tokelon.toktales.core.render.ITextureManager;

public interface IContentManager {


	public void startLoaders();
	public void stopLoaders();

	
	public IResourceManager getResourceManager();
	
	public IBitmapAssetManager getBitmapAssetManager();
	
	public ISpriteAssetManager getSpriteAssetManager();

	public ITextureManager getTextureManager(); // Move somewhere else? IRenderService?
	
	public IFontAssetManager getFontAssetManager();
	
	public ICodepointAssetManager getCodepointAssetManager();

}
