package com.tokelon.toktales.android.data;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.inject.Inject;

import com.google.common.io.ByteStreams;
import com.tokelon.toktales.core.content.manage.files.IFileKey;
import com.tokelon.toktales.core.content.manage.font.ITextureFontAsset;
import com.tokelon.toktales.core.content.manage.font.ITextureFontAssetDecoder;
import com.tokelon.toktales.core.content.manage.font.ITextureFontAssetKey;
import com.tokelon.toktales.core.content.manage.font.TextureFontAssetImpl;
import com.tokelon.toktales.core.engine.content.ContentException;
import com.tokelon.toktales.core.engine.storage.IStorageService;
import com.tokelon.toktales.core.engine.storage.StorageException;
import com.tokelon.toktales.core.util.options.IOptions;

import android.graphics.Typeface;

public class AndroidTextureFontDecoder implements ITextureFontAssetDecoder {
	// TODO: Implement support for loading from assets via Typeface.createFromAsset()
	
	public static final int DEFAULT_FONT_PIXEL_HEIGHT = 32;
	
	
	private final IStorageService storageService;
	
	@Inject
	public AndroidTextureFontDecoder(IStorageService storageService) {
		this.storageService = storageService;
	}
	
	
	@Override
	public ITextureFontAsset decode(InputStream inputstream, ITextureFontAssetKey key, IOptions options) throws ContentException {
		int textSize = options == null ? DEFAULT_FONT_PIXEL_HEIGHT : options.getAsOrDefault(OPTION_FONT_PIXEL_HEIGHT, DEFAULT_FONT_PIXEL_HEIGHT, Integer.class);
		
		if(key instanceof IFileKey) {
			IFileKey fileKey = (IFileKey) key;
			Typeface typeface = Typeface.createFromFile(fileKey.getFile().getPath()); // Does this throw any exceptions?
			
			AndroidTextureFont font = new AndroidTextureFont(typeface, textSize);
			return new TextureFontAssetImpl(font);
		}
		else {
			// TODO: Convert to cache file and do not delete?
			File tmpFile;
			try {
				tmpFile = storageService.createTempFile("font", ".ttf");
			} catch (StorageException se) {
				throw new ContentException(se);
			}
			
			try {
				try(FileOutputStream outputstream = new FileOutputStream(tmpFile)) {
					ByteStreams.copy(inputstream, outputstream);
				} catch (IOException e) {
					throw new ContentException(e);
				}
				
				Typeface typeface = Typeface.createFromFile(tmpFile); // Does this throw any exceptions?
				
				AndroidTextureFont font = new AndroidTextureFont(typeface, textSize);
				return new TextureFontAssetImpl(font);
			}
			finally {
				tmpFile.delete();
			}
		}
	}

}
