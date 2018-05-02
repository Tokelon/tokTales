package com.tokelon.toktales.desktop.lwjgl.render;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import com.tokelon.toktales.core.render.IKeyedTextureManager;
import com.tokelon.toktales.core.render.IRenderTexture;
import com.tokelon.toktales.core.util.IParams;

public class GLKeyedTextureManager<K> implements IKeyedTextureManager<K> {
	
	public static final String TAG = "GLKeyedTextureManager";
	
	
	private final int managingTextureIndex = 1;
	
	private final Map<K, TextureEntry> textureMap = new HashMap<>();
	
	
	/** Also binds the texture.
	 * 
	 * @param key
	 * @param texture
	 */
	@Override
	public void addTexture(K key, IRenderTexture texture) {
		if(key == null || texture == null) {
			throw new NullPointerException();
		}
		
		// Generate a new texture
		int textureLocation = GL11.glGenTextures();
		

		GL13.glActiveTexture(GL13.GL_TEXTURE0 + managingTextureIndex);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureLocation);
		
		
		int unpackAlignment = texture.getUnpackAlignment();
		GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, unpackAlignment);

		int textureFormat = texture.getTextureFormat();   // GL_RGBA
		int internalFormat = texture.getInternalFormat(); // GL_RGBA
		int dataType = texture.getDataType();             // GL_UNSIGNED_BYTE
		int width = texture.getBitmap().getWidth();
		int height = texture.getBitmap().getHeight();
		
		GL11.glTexImage2D(
				GL11.GL_TEXTURE_2D,
				0, // level
				internalFormat,
				width,
				height,
				0, // border
				textureFormat,
				dataType,
				texture.getBitmap().getData());

		//OpenGLUtils.checkGLErrors(TAG);


		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, texture.getFilterMin()); //GL_NEAREST
		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, texture.getFilterMag()); //GL_NEAREST
		
		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, texture.getWrapS()); //GL_CLAMP_TO_EDGE
		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, texture.getWrapT()); //GL_CLAMP_TO_EDGE
		
		
		TextureEntry entry = new TextureEntry(texture, textureLocation);
		textureMap.put(key, entry);
	}
	
	
	@Override
	public boolean hasTextureFor(K key) {
		return textureMap.containsKey(key);
	}
	
	
	@Override
	public IRenderTexture getTextureFor(K key) {
		TextureEntry entry = textureMap.get(key);
		return entry == null ? null : entry.texture;
	}
	
	
	@Override
	public void removeTextureFor(K key) {
		TextureEntry textureEntry = textureMap.remove(key);
		if(textureEntry == null) {
			throw new IllegalArgumentException("Cannot delete texture: texture not in cache");
		}
		
		deleteTexture(textureEntry.location);
	}
	
	private void deleteTexture(int texture) {
		GL11.glDeleteTextures(texture);
	}
	
	
	@Override
	public void bindTextureFor(K key, int textureIndex) {
		TextureEntry textureEntry = textureMap.get(key);
		if(textureEntry == null) {
			throw new IllegalArgumentException("Cannot bind texture: texture not in cache");
		}
		
		bindTexture(textureEntry.location, textureIndex);
	}
	
	
	public void bindTexture(int textureId, int textureIndex) {
		GL13.glActiveTexture(GL13.GL_TEXTURE0 + textureIndex);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureId);
	}
	
	
	@Override
	public void clear() {
		for(TextureEntry entry: textureMap.values()) {
			deleteTexture(entry.location);
		}
		
		textureMap.clear();
	}
	
	
	protected static class TextureEntry {
		protected final IRenderTexture texture;
		protected final int location;
		
		public TextureEntry(IRenderTexture texture, int location) {
			this.texture = texture;
			this.location = location;
		}
	}

	
	public static class GLKeyedTextureManagerFactory implements IKeyedTextureManagerFactory {

		@Override
		public <T> IKeyedTextureManager<T> newKeyedTextureManager(Class<T> keyClass, IParams params) {
			return new GLKeyedTextureManager<T>(); 
		}
	}
	
}
