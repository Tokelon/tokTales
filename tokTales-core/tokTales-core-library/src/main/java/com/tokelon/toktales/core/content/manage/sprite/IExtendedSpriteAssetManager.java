package com.tokelon.toktales.core.content.manage.sprite;

import com.tokelon.toktales.core.content.sprite.ISprite;
import com.tokelon.toktales.core.content.sprite.ISpriteAsset;
import com.tokelon.toktales.core.engine.content.ContentException;
import com.tokelon.toktales.core.engine.content.ContentNotFoundException;
import com.tokelon.toktales.core.util.options.IOptions;

public interface IExtendedSpriteAssetManager extends ISpriteAssetManager {

	
	// TODO: Change method names to getSpriteAsset* ?
	
	public ISpriteAsset getAsset(ISprite key);
	public ISpriteAsset getAsset(ISprite key, IOptions options);
	
	public ISpriteAsset getAssetOrError(ISprite key) throws ContentNotFoundException;
	
	public ISpriteAsset getAssetLoadIfNeeded(ISprite key);
	public ISpriteAsset getAssetLoadIfNeeded(ISprite key, IOptions options);
	
	public ISpriteAsset getAssetLoadIfNeededOrError(ISprite key) throws ContentException;
	public ISpriteAsset getAssetLoadIfNeededOrError(ISprite key, IOptions options) throws ContentException;
	
}
