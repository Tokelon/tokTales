package com.tokelon.toktales.core.content.manage.sprite;

import java.io.InputStream;

import javax.inject.Inject;

import com.tokelon.toktales.core.content.manage.bitmap.IBitmapAsset;
import com.tokelon.toktales.core.content.manage.bitmap.IBitmapAssetDecoder;
import com.tokelon.toktales.core.content.sprite.ISpriteAsset;
import com.tokelon.toktales.core.content.sprite.SpriteAsset;
import com.tokelon.toktales.core.engine.content.ContentException;
import com.tokelon.toktales.core.render.Texture;
import com.tokelon.toktales.core.util.options.IOptions;

public class SpriteAssetDecoder implements ISpriteAssetDecoder {

	
	private final IBitmapAssetDecoder bitmapAssetDecoder;

	@Inject
	public SpriteAssetDecoder(IBitmapAssetDecoder bitmapAssetDecoder) {
		this.bitmapAssetDecoder = bitmapAssetDecoder;
	}
	
	
	@Override
	public ISpriteAsset decode(InputStream inputstream, ISpriteAssetKey key, IOptions options) throws ContentException {
		IBitmapAsset bitmapAsset = bitmapAssetDecoder.decode(inputstream, null, options); // TODO: Wrap key instead of passing null, if needed
		return new SpriteAsset(new Texture(bitmapAsset.getBitmap()));
	}

}
