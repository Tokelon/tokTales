package com.tokelon.toktales.desktop.content;

import com.tokelon.toktales.core.content.IAssetContainer;
import com.tokelon.toktales.desktop.lwjgl.data.STBStandardImage;

public class STBImageContainer implements IAssetContainer<STBStandardImage> {

	
	private final STBStandardImage image;
	
	public STBImageContainer(STBStandardImage image) {
		this.image = image;
	}
	
	
	
	@Override
	public STBStandardImage getAsset() {
		return image;
	}

	@Override
	public void freeAsset() {
		image.free();
	}

}
