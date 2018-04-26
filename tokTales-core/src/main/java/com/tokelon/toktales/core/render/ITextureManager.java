package com.tokelon.toktales.core.render;

public interface ITextureManager {

	
	public void addTexture(IRenderTexture texture);
	
	public boolean hasTexture(IRenderTexture texture);

	public void removeTexture(IRenderTexture texture);
	
	public void clear();
	
	
	// TODO: Pass the texture index that should be used, instead of having a static one?
	public void bindTexture(IRenderTexture texture);
	
	public int getTextureIndex();
	
}
