package com.tokelon.toktales.core.content.sprite;

import com.tokelon.toktales.core.content.IAssetContainer;

public abstract class AbstractCache {


	public static final ISpriteAsset CACHE_SPECIAL_NOT_FOUND = new SpecialSpriteAsset();
	public static final ISpriteAsset CACHE_SPECIAL_LOAD_ERROR = new SpecialSpriteAsset();
	
	

	private static class SpecialSpriteAsset implements ISpriteAsset {

		@Override
		public IAssetContainer<?> getContent() {
			return null;
		}

		@Override
		public void dispose() {	}	
	}
	
}
