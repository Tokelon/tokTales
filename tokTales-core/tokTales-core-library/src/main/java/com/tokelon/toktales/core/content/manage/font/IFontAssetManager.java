package com.tokelon.toktales.core.content.manage.font;

import com.tokelon.toktales.tools.assets.key.IReadableAssetKey;
import com.tokelon.toktales.tools.assets.manager.IAssetManager;
import com.tokelon.toktales.tools.core.objects.options.IOptions;

public interface IFontAssetManager extends IAssetManager<IFontAsset, IFontAssetKey, IOptions> {


	public IFontAssetKey keyOf(IReadableAssetKey readableKey);
	
}
