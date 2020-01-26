package com.tokelon.toktales.core.content.manage.bitmap;

import com.tokelon.toktales.tools.assets.key.IReadableAssetKey;
import com.tokelon.toktales.tools.assets.manager.IAssetManager;
import com.tokelon.toktales.tools.core.objects.options.IOptions;

public interface IBitmapAssetManager extends IAssetManager<IBitmapAsset, IBitmapAssetKey, IOptions> {


	public IBitmapAssetKey keyOf(IReadableAssetKey readableKey);
	
}
