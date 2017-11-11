package com.tokelon.toktales.desktop.lwjgl.render;

import com.tokelon.toktales.desktop.lwjgl.data.STBStandardImage;

public class STBImageTexture implements IImageTexture {

	private final STBStandardImage image;
	
	public STBImageTexture(STBStandardImage image) {
		this.image = image;
	}
	
	@Override
	public STBStandardImage getImage() {
		return image;
	}

}
