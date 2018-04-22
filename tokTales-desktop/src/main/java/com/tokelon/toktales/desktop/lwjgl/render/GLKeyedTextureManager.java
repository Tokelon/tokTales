package com.tokelon.toktales.desktop.lwjgl.render;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import com.tokelon.toktales.core.render.IKeyedTextureManager;
import com.tokelon.toktales.core.render.IKeyedTextureManagerFactory;
import com.tokelon.toktales.core.render.IRenderTexture;
import com.tokelon.toktales.core.util.IParams;

public class GLKeyedTextureManager<K> implements IKeyedTextureManager<K> {
	
	public static final String TAG = "GLKeyedTextureManager";
	
	
	private final Map<K, Integer> textureMap = new HashMap<K, Integer>();
	
	
	private final int glTextureIndex;
	
	/**
	 * @param glTextureIndex Must be one of i of IGL20.GL_TEXTUREi
	 */
	public GLKeyedTextureManager(int glTextureIndex) {
		this.glTextureIndex = glTextureIndex;
	}
	
	
	
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
		int textureId = GL11.glGenTextures();
		

		GL13.glActiveTexture(GL13.GL_TEXTURE0 + glTextureIndex);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureId);
		
		
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
		
		
		textureMap.put(key, textureId);
	}
	
	
	public int getNameForTexture(K key) {
		return textureMap.get(key);
	}
	
	
	@Override
	public boolean hasTextureFor(K key) {
		return textureMap.containsKey(key);
	}
	
	
	@Override
	public void removeTextureFor(K key) {
		// TODO: Test this

		Integer texName = textureMap.remove(key);
		if(texName == null) {
			throw new IllegalArgumentException("Cannot bind texture: Key does not have texture cached");
		}
		
		deleteTexture(texName);
	}
	
	private void deleteTexture(int texture) {
		GL11.glDeleteTextures(texture);
	}
	
	
	@Override
	public void bindTextureFor(K key) {
		Integer texName = textureMap.get(key);
		if(texName == null) {
			throw new IllegalArgumentException("Cannot bind texture: Key does not have texture cached");
		}
		
		bindTexture(texName);
	}
	
	
	public void bindTexture(int textureId) {
		
		GL13.glActiveTexture(GL13.GL_TEXTURE0 + glTextureIndex);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureId);
	}
	
	
	@Override
	public void clear() {
		for(Integer texture: textureMap.values()) {
			deleteTexture(texture);
		}
		
		textureMap.clear();
	}
	
	@Override
	public int getTextureIndex() {
		return glTextureIndex;
	}
	

	
	public static class GLKeyedTextureManagerFactory implements IKeyedTextureManagerFactory {
		// TODO: Inject OpenGLUtils and do not use statically, also replace in all other texture managers

		@Override
		public <T> IKeyedTextureManager<T> newKeyedTextureManager(Class<T> keyClass, IParams params) {
			return new GLKeyedTextureManager<T>(OpenGLUtils.aquireGLTextureIndex()); 
		}
	}
	
}
