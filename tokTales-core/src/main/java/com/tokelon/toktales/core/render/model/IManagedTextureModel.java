package com.tokelon.toktales.core.render.model;

import com.tokelon.toktales.core.render.IRenderTexture;
import com.tokelon.toktales.core.render.ITextureManager;

public interface IManagedTextureModel extends ITexturedModel {
	
	// TODO: Extend from IRenderTextureModel ?
	
	
	public void setTextureManager(ITextureManager textureManager);
	
	// method for all targets | probably better individual methods
	//public void setBitmapModelTarget(targets...);
	
	public void setTargetTexture(IRenderTexture texture);

	
	public ITextureManager getTextureManager();
	
	public IRenderTexture getTargetTexture();

	
}
