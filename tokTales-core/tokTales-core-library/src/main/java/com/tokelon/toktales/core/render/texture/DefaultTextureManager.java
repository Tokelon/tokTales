package com.tokelon.toktales.core.render.texture;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

public class DefaultTextureManager implements ITextureManager {
	// TODO: Add optional logging

	
	private static final int DEFAULT_MANAGING_TEXTURE_INDEX = 1;
	
	private int managingTextureIndex = DEFAULT_MANAGING_TEXTURE_INDEX;


	private int loadedTexturesCount = 0;
	
	private final Map<ITexture, TextureInfo> textureMap = new HashMap<>();

	private final ITextureDriver textureDriver;
	
	@Inject
	public DefaultTextureManager(ITextureDriver textureDriver) {
		this.textureDriver = textureDriver;
	}
	
	public DefaultTextureManager(ITextureDriver textureDriver, int managingTextureIndex) {
		this.textureDriver = textureDriver;
		this.managingTextureIndex = managingTextureIndex;
	}
	
	
	@Override
	public int getManagingTextureIndex() {
		return managingTextureIndex;
	}
	
	@Override
	public ITextureDriver getTextureDriver() {
		return textureDriver;
	}
	
	@Override
	public boolean addTexture(ITexture texture) {
		if(texture == null) {
			throw new NullPointerException();
		}
		
		if(textureMap.containsKey(texture)) {
			return false;
		}
		else {
			textureMap.put(texture, new TextureInfo());
			return true;
		}
	}

	@Override
	public boolean addLoadedTexture(ITexture texture, int location) {
		if(texture == null) {
			throw new NullPointerException();
		}
		if(location <= 0) {
			throw new IllegalArgumentException("location must be > 0");
		}

		TextureInfo info = textureMap.get(texture);
		if(info == null) {
			info = new TextureInfo();
			textureMap.put(texture, info);
		}
		
		if(info.isLoaded()) {
			return false;
		}
		else {
			info.setLoaded(location);
			loadedTexturesCount++;
			return true;
		}
	}

	@Override
	public boolean loadTexture(ITexture texture) {
		if(texture == null) {
			throw new NullPointerException();
		}
		
		TextureInfo info = textureMap.get(texture);
		if(info == null) {
			info = new TextureInfo();
			textureMap.put(texture, info);
		}
		
		if(info.isLoaded()) {
			return false;
		}
		else {
			int location = textureDriver.loadTexture(texture, managingTextureIndex);
			info.setLoaded(location);
			loadedTexturesCount++;
			return true;
		}
	}

	
	@Override
	public boolean hasTexture(ITexture texture) {
		return textureMap.containsKey(texture);
	}

	@Override
	public boolean hasTextureLoaded(ITexture texture) {
		TextureInfo info = textureMap.get(texture);
		return info == null ? false : info.isLoaded();
	}

	@Override
	public int getTextureLocation(ITexture texture) {
		TextureInfo info = textureMap.get(texture);
		return info == null ? -1 : info.location;
	}

	@Override
	public int getTextureCount() {
		return textureMap.size();
	}

	@Override
	public int getLoadedTextureCount() {
		return loadedTexturesCount;
	}

	@Override
	public boolean removeTexture(ITexture texture) {
		if(texture == null) {
			throw new NullPointerException();
		}
		
		TextureInfo info = textureMap.get(texture);
		if(info == null) {
			return false;
		}
		else {
			if(info.isLoaded()) {
				textureDriver.unloadTexture(info.location);
				info.setUnloaded();
				loadedTexturesCount--;
			}
			
			textureMap.remove(texture);
			return true;
		}
	}
	
	@Override
	public boolean removeLoadedTexture(ITexture texture) {
		if(texture == null) {
			throw new NullPointerException();
		}
		
		TextureInfo info = textureMap.get(texture);
		if(info == null) {
			return false;
		}
		else {
			if(info.isLoaded()) {
				info.setUnloaded();
				loadedTexturesCount--;
			}
			
			textureMap.remove(texture);
			return true;
		}
	}

	@Override
	public boolean unloadTexture(ITexture texture) {
		if(texture == null) {
			throw new NullPointerException();
		}
		
		TextureInfo info = textureMap.get(texture);
		if(info == null) {
			return false;
		}
		else {
			if(info.isLoaded()) {
				textureDriver.unloadTexture(info.location);
				info.setUnloaded();
				loadedTexturesCount--;
			}
			
			return true;
		}
	}

	@Override
	public void removeAll() {
		for(ITexture texture: textureMap.keySet()) {
			removeTexture(texture);
		}
	}

	@Override
	public void unloadAll() {
		for(ITexture texture: textureMap.keySet()) {
			unloadTexture(texture);
		}
	}

	
	protected static class TextureInfo {
		private boolean loaded = false;
		private int location = -1;
		
		
		public void setLoaded(int location) {
			this.loaded = true;
			this.location = location;
		}
		
		public void setUnloaded() {
			this.loaded = false;
			this.location = -1;
		}
		
		public boolean isLoaded() {
			return loaded;
		}
	}

}
