package com.tokelon.toktales.core.render.model;

import com.tokelon.toktales.core.render.IRenderTexture;
import com.tokelon.toktales.core.render.ITextureCoordinator;

public class ManagedTextureModel extends TexturedModel implements IManagedTextureModel {


	private ITextureCoordinator textureCoordinator;
	private IRenderTexture texture;
	

	@Override
	public void setTextureCoordinator(ITextureCoordinator textureCoordinator) {
		this.textureCoordinator = textureCoordinator;
	}

	@Override
	public void setTargetTexture(IRenderTexture texture) {
		this.texture = texture;
	}


	@Override
	public ITextureCoordinator getTextureCoordinator() {
		return textureCoordinator;
	}

	@Override
	public IRenderTexture getTargetTexture() {
		return texture;
	}

}
