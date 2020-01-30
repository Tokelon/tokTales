package com.tokelon.toktales.core.render.model;

import com.tokelon.toktales.core.render.texture.ITexture;
import com.tokelon.toktales.core.render.texture.ITextureCoordinator;

public interface IManagedTextureModel extends ITexturedModel {

	
	public void setTextureCoordinator(ITextureCoordinator textureCoordinator);
	
	public void setTargetTexture(ITexture texture);

	
	public ITextureCoordinator getTextureCoordinator();
	
	public ITexture getTargetTexture();
	
}
