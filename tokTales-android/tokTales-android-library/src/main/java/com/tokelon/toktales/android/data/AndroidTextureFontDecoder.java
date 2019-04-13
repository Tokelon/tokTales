package com.tokelon.toktales.android.data;

import java.io.InputStream;

import com.tokelon.toktales.core.content.manage.files.IFileKey;
import com.tokelon.toktales.core.content.manage.font.ITextureFontAsset;
import com.tokelon.toktales.core.content.manage.font.ITextureFontAssetDecoder;
import com.tokelon.toktales.core.content.manage.font.ITextureFontAssetKey;
import com.tokelon.toktales.core.content.manage.font.TextureFontAssetImpl;
import com.tokelon.toktales.core.engine.content.ContentException;
import com.tokelon.toktales.core.util.options.IOptions;

import android.graphics.Typeface;

public class AndroidTextureFontDecoder implements ITextureFontAssetDecoder {
	
	public static final int DEFAULT_FONT_PIXEL_HEIGHT = 32;


	@Override
	public ITextureFontAsset decode(InputStream inputstream, ITextureFontAssetKey key, IOptions options) throws ContentException {
		int textSize = options == null ? DEFAULT_FONT_PIXEL_HEIGHT : options.getAsOrDefault(OPTION_FONT_PIXEL_HEIGHT, DEFAULT_FONT_PIXEL_HEIGHT, Integer.class);
		
		if(key instanceof IFileKey) {
			IFileKey fileKey = (IFileKey) key;
			Typeface typeface = Typeface.createFromFile(fileKey.getPath().toString()); // Does this throw any exceptions?
			
			AndroidTextureFont font = AndroidTextureFont.create(typeface, textSize);
			return new TextureFontAssetImpl(font);
		}
		else {
			// TODO: Typeface has no factory method with an inputstream
			// We have to write out to a tmp file and then pass the path to that...
			return null;
		}
	}

}
