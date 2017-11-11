package com.tokelon.toktales.core.render.model;

import com.tokelon.toktales.core.render.IRenderTexture;
import com.tokelon.toktales.core.render.ITextureManager;

public class ManagedTextureModel extends TexturedModel implements IManagedTextureModel {

	private ITextureManager textureManager;
	
	private IRenderTexture texture;
	
	
	@Override
	public void setTextureManager(ITextureManager textureManager) {
		this.textureManager = textureManager;
	}

	@Override
	public void setTargetTexture(IRenderTexture texture) {
		this.texture = texture;
	}

	
	@Override
	public ITextureManager getTextureManager() {
		return textureManager;
	}

	@Override
	public IRenderTexture getTargetTexture() {
		return texture;
	}

}
