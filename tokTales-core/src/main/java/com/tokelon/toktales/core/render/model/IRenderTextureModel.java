package com.tokelon.toktales.core.render.model;

import com.tokelon.toktales.core.render.IRenderTexture;

public interface IRenderTextureModel extends ITexturedModel {

	/* TODO: Replace with IManagedTextureModel and delete
	 * discourage using unmanaged textures for performance reasons
	 * 
	 * if there is too much work to maintain a texture manager, provide a global one
	 * 
	 * 
	 */
	
	public void setTargetTexture(IRenderTexture texture);

	public IRenderTexture getTargetTexture();
	
}
