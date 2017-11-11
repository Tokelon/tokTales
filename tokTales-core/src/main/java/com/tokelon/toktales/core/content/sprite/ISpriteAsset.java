package com.tokelon.toktales.core.content.sprite;

import com.tokelon.toktales.core.content.IAssetContainer;
import com.tokelon.toktales.core.content.IGraphicAsset;


public interface ISpriteAsset extends IGraphicAsset {

	public IAssetContainer<?> getContent();

	
	// Maybe add?
	//public boolean isSpecialContent();
	
}
