package com.tokelon.toktales.core.content.manage.bitmap;

import java.nio.file.Path;

import com.tokelon.toktales.core.content.manage.files.FileKey;

public class BitmapFileKey extends FileKey implements IBitmapAssetKey {

	
	public BitmapFileKey(Path path) {
		super(path);
	}

}
