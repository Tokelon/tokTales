package com.tokelon.toktales.core.render.model;

import com.tokelon.toktales.core.render.IRenderTexture;

public class RenderTextureModel extends TexturedModel implements IRenderTextureModel {

	private IRenderTexture texture;
	
	@Override
	public void setTargetTexture(IRenderTexture texture) {
		this.texture = texture;
	}

	@Override
	public IRenderTexture getTargetTexture() {
		return texture;
	}

}
