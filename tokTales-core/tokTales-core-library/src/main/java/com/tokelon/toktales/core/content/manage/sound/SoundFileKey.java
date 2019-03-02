package com.tokelon.toktales.core.content.manage.sound;

import java.nio.file.Path;

import com.tokelon.toktales.core.content.manage.files.FileKey;

public class SoundFileKey extends FileKey implements ISoundAssetKey {

	
	public SoundFileKey(Path path) {
		super(path);
	}

}
