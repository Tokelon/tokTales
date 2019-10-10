package com.tokelon.toktales.core.content.manage.sound;

import com.tokelon.toktales.core.content.manage.IAssetManager;
import com.tokelon.toktales.tools.core.objects.options.INamedOptions;

public interface ISoundAssetManager extends IAssetManager<ISoundAsset, ISoundAssetKey, INamedOptions> {

	
	//public ISoundAsset getAsset(Path path); // Add this for object pooling?
	
}
