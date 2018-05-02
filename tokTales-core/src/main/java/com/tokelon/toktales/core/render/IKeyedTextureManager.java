package com.tokelon.toktales.core.render;

import com.tokelon.toktales.core.util.IParams;

public interface IKeyedTextureManager<K> {
	
	
	public void bindTextureFor(K key, int textureIndex);

	
	public void addTexture(K key, IRenderTexture texture);
	
	public boolean hasTextureFor(K key);
	
	public IRenderTexture getTextureFor(K key);
	
	public void removeTextureFor(K key);
	
	public void clear();

	
	
	public interface IKeyedTextureManagerFactory {
		
		public <T> IKeyedTextureManager<T> newKeyedTextureManager(Class<T> keyClass, IParams params);
	}
	
}
