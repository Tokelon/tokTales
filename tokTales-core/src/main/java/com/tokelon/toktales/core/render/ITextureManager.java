package com.tokelon.toktales.core.render;

public interface ITextureManager {

	
	public void addTexture(IRenderTexture texture);
	
	public boolean hasTexture(IRenderTexture texture);

	public void removeTexture(IRenderTexture texture);
	
	public void bindTexture(IRenderTexture texture);

	
	public void clear();
	
	public int getTextureIndex();
	
}
