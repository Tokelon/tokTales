package com.tokelon.toktales.core.content;

import com.tokelon.toktales.core.content.sprite.ISpriteAsset;

public class GenericAsset implements ISpriteAsset {

	private final IAssetContainer<?> content;
	
	public GenericAsset(IAssetContainer<?> content) {
		this.content = content;
	}
	
	@Override
	public IAssetContainer<?> getContent() {
		return content;
	}

	@Override
	public void dispose() {
		content.freeAsset();
	}

}
