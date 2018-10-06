package com.tokelon.toktales.core.content.sprite;

import com.tokelon.toktales.core.content.ISpecialContent;

public interface ISpriteLoaderReceiver {

	public void specialLoaded(ISpecialContent special, ISpriteAsset spriteAsset);
	
	public void failedToLoadSpecial(ISpecialContent special);
	
	
	public void spriteLoaded(ISprite sprite, ISpriteAsset spriteAsset);
	
	public void spriteNotFound(ISprite sprite);
	
	public void failedToLoadSprite(ISprite sprite, String errorMessage);
	
}
