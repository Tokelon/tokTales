package com.tokelon.toktales.core.render.model;

import com.tokelon.toktales.core.render.IRenderTexture;
import com.tokelon.toktales.core.render.ITextureCoordinator;

public interface IManagedTextureModel extends ITexturedModel {

	
	public void setTextureCoordinator(ITextureCoordinator textureCoordinator);
	
	public void setTargetTexture(IRenderTexture texture);

	
	public ITextureCoordinator getTextureCoordinator();
	
	public IRenderTexture getTargetTexture();
	
}
