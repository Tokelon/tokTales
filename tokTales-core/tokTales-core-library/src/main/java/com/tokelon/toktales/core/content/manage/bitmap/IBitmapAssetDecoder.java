package com.tokelon.toktales.core.content.manage.bitmap;

import com.tokelon.toktales.core.content.manage.IAssetDecoder;
import com.tokelon.toktales.core.engine.content.IGraphicLoadingOptions;
import com.tokelon.toktales.tools.core.objects.options.IOptions;

public interface IBitmapAssetDecoder extends IAssetDecoder<IBitmapAsset, IBitmapAssetKey, IOptions> {

	
	public static final String OPTION_GRAPHIC_LOADING = IGraphicLoadingOptions.class.getSimpleName();
	
}
