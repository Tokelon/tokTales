package com.tokelon.toktales.android.content;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.inject.Inject;

import com.google.common.io.ByteStreams;
import com.tokelon.toktales.core.content.manage.font.IFontAsset;
import com.tokelon.toktales.core.content.manage.font.IFontAssetDecoder;
import com.tokelon.toktales.core.content.manage.font.IFontAssetKey;
import com.tokelon.toktales.core.content.manage.font.FontAssetImpl;
import com.tokelon.toktales.core.engine.storage.IStorageService;
import com.tokelon.toktales.core.engine.storage.StorageException;
import com.tokelon.toktales.tools.assets.exception.AssetException;
import com.tokelon.toktales.tools.assets.files.IFileKey;
import com.tokelon.toktales.tools.core.objects.options.IOptions;

import android.graphics.Typeface;

public class AndroidFontDecoder implements IFontAssetDecoder {
	// TODO: Implement support for loading from assets via Typeface.createFromAsset()
	
	public static final int DEFAULT_FONT_PIXEL_HEIGHT = 32;
	
	
	private final IStorageService storageService;
	
	@Inject
	public AndroidFontDecoder(IStorageService storageService) {
		this.storageService = storageService;
	}
	
	
	@Override
	public IFontAsset decode(InputStream inputstream, IFontAssetKey key, IOptions options) throws AssetException {
		int textSize = options == null ? DEFAULT_FONT_PIXEL_HEIGHT : options.getAsOrDefault(OPTION_FONT_PIXEL_HEIGHT, DEFAULT_FONT_PIXEL_HEIGHT, Integer.class);
		
		if(key instanceof IFileKey) {
			IFileKey fileKey = (IFileKey) key;
			Typeface typeface = Typeface.createFromFile(fileKey.getFile().getPath()); // Does this throw any exceptions?
			
			AndroidFont font = new AndroidFont(typeface, textSize);
			return new FontAssetImpl(font);
		}
		else {
			// TODO: Convert to cache file and do not delete?
			File tmpFile;
			try {
				tmpFile = storageService.createTempFile("font", ".ttf");
			} catch (StorageException se) {
				throw new AssetException(se);
			}
			
			try {
				try(FileOutputStream outputstream = new FileOutputStream(tmpFile)) {
					ByteStreams.copy(inputstream, outputstream);
				} catch (IOException e) {
					throw new AssetException(e);
				}
				
				Typeface typeface = Typeface.createFromFile(tmpFile); // Does this throw any exceptions?
				
				AndroidFont font = new AndroidFont(typeface, textSize);
				return new FontAssetImpl(font);
			}
			finally {
				tmpFile.delete();
			}
		}
	}

}
