package com.tokelon.toktales.android.data;

import java.io.InputStream;

import com.tokelon.toktales.core.content.manage.IAssetDecoder;
import com.tokelon.toktales.core.content.manage.sound.ISoundAsset;
import com.tokelon.toktales.core.content.manage.sound.ISoundAssetKey;
import com.tokelon.toktales.core.engine.content.ContentException;
import com.tokelon.toktales.core.util.options.INamedOptions;

public class AndroidSoundDecoder implements IAssetDecoder<ISoundAsset, ISoundAssetKey, INamedOptions> {
	// TODO: Implement
	
	
	@Override
	public ISoundAsset decode(InputStream inputstream, ISoundAssetKey key) throws ContentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ISoundAsset decode(InputStream inputstream, ISoundAssetKey key, INamedOptions options) throws ContentException {
		// TODO Auto-generated method stub
		return null;
	}

}
