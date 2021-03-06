package com.tokelon.toktales.core.content.manage.sound;

import com.tokelon.toktales.tools.assets.key.IReadableAssetKey;
import com.tokelon.toktales.tools.assets.manager.IAssetManager;
import com.tokelon.toktales.tools.core.objects.options.INamedOptions;

public interface ISoundAssetManager extends IAssetManager<ISoundAsset, ISoundAssetKey, INamedOptions> {
	//public ISoundAsset getAsset(Path path); // Add this for object pooling?


	public ISoundAssetKey keyOf(IReadableAssetKey readableKey);
	
}
