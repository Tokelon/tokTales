package com.tokelon.toktales.core.content;

import com.tokelon.toktales.core.render.IRenderTexture;

public class TextureContainer implements IAssetContainer<IRenderTexture> {

	
	private final IRenderTexture texture;
	
	public TextureContainer(IRenderTexture texture) {
		this.texture = texture;
	}
	
	
	@Override
	public IRenderTexture getAsset() {
		return texture;
	}

	@Override
	public void freeAsset() {
		texture.getBitmap().dispose(); // Is this correct?
	}

}
