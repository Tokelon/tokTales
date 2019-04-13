package com.tokelon.toktales.core.content.manage.font;

import java.nio.file.Path;

import com.tokelon.toktales.core.content.manage.files.FileKey;

public class TextureFontFileKey extends FileKey implements ITextureFontAssetKey {

	
	public TextureFontFileKey(Path path) {
		super(path);
	}

}
