package com.tokelon.toktales.core.render.model;

import com.tokelon.toktales.core.render.ITexture;
import com.tokelon.toktales.core.render.ITextureCoordinator;

public class ManagedTextureModel extends TexturedModel implements IManagedTextureModel {


	private ITextureCoordinator textureCoordinator;
	private ITexture texture;
	

	@Override
	public void setTextureCoordinator(ITextureCoordinator textureCoordinator) {
		this.textureCoordinator = textureCoordinator;
	}

	@Override
	public void setTargetTexture(ITexture texture) {
		this.texture = texture;
	}


	@Override
	public ITextureCoordinator getTextureCoordinator() {
		return textureCoordinator;
	}

	@Override
	public ITexture getTargetTexture() {
		return texture;
	}

}
