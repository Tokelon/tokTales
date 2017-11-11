package com.tokelon.toktales.core.render;

public interface IKeyedTextureManager<K> {


	public void addTexture(K key, IRenderTexture texture);
	
	public void removeTextureFor(K key);
	
	public void bindTextureFor(K key);
	
	public boolean hasTextureFor(K key);
	
	
	public void clear();
	
	public int getTextureIndex();
	
}
