package com.tokelon.toktales.core.content;

import com.tokelon.toktales.core.render.ITexture;

public class TextureContainer implements IAssetContainer<ITexture> {

	
	private final ITexture texture;
	
	public TextureContainer(ITexture texture) {
		this.texture = texture;
	}
	
	
	@Override
	public ITexture getAsset() {
		return texture;
	}

	@Override
	public void freeAsset() {
		texture.getBitmap().dispose(); // Is this correct?
	}

}
