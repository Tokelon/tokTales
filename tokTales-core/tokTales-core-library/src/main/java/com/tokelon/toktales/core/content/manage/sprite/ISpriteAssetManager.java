package com.tokelon.toktales.core.content.manage.sprite;

import com.tokelon.toktales.core.content.sprite.ISpriteAsset;
import com.tokelon.toktales.tools.assets.key.IReadableAssetKey;
import com.tokelon.toktales.tools.assets.manager.IAssetManager;
import com.tokelon.toktales.tools.core.objects.options.IOptions;

public interface ISpriteAssetManager extends IAssetManager<ISpriteAsset, ISpriteAssetKey, IOptions> {


	public ISpriteAssetKey keyOf(String spriteName, IReadableAssetKey readableKey);
	
}
