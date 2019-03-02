package com.tokelon.toktales.core.content.sprite;

import com.tokelon.toktales.core.content.IAssetContainer;
import com.tokelon.toktales.core.content.IGraphicsAsset;


public interface ISpriteAsset extends IGraphicsAsset {

	public IAssetContainer<?> getContent();

	
	// Maybe add?
	//public boolean isSpecialContent();
	
}
