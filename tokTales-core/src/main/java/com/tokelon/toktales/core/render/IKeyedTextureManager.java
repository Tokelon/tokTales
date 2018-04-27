package com.tokelon.toktales.core.render;

public interface IKeyedTextureManager<K> {


	public void addTexture(K key, IRenderTexture texture);
	
	public boolean hasTextureFor(K key);
	
	public IRenderTexture getTextureFor(K key);
	
	public void removeTextureFor(K key);
	
	public void clear();
	
	
	// TODO: Pass the texture index that should be used, instead of having a static one?
	public void bindTextureFor(K key);
	
	public int getTextureIndex();
	
}
