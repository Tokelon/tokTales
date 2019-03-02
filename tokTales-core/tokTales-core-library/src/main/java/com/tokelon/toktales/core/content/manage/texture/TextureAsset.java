package com.tokelon.toktales.core.content.manage.texture;

import com.tokelon.toktales.core.render.ITexture;

public class TextureAsset implements ITextureAsset {

	
	private final ITexture texture;

	public TextureAsset(ITexture texture) {
		this.texture = texture;
	}

	@Override
	public ITexture getTexture() {
		return texture;
	}
	

	@Override
	public void dispose() {
		// TODO: call dispose on the texture instead?
		texture.getBitmap().dispose();
	}

}
