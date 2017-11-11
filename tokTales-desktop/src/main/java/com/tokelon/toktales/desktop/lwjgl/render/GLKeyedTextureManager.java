package com.tokelon.toktales.desktop.lwjgl.render;

import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_RGBA2;
import static org.lwjgl.opengl.GL11.GL_RGBA4;
import static org.lwjgl.opengl.GL11.GL_RGBA8;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDeleteTextures;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameterf;
import static org.lwjgl.opengl.GL13.GL_CLAMP_TO_BORDER;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

import java.util.HashMap;
import java.util.Map;

import com.tokelon.toktales.core.render.IKeyedTextureManager;
import com.tokelon.toktales.core.render.IKeyedTextureManagerFactory;
import com.tokelon.toktales.core.render.IRenderTexture;
import com.tokelon.toktales.core.util.IParams;
import com.tokelon.toktales.desktop.lwjgl.data.STBStandardImage;

public class GLKeyedTextureManager<K> implements IKeyedTextureManager<K> {
	
	public static final String TAG = "GLTextureManager";
	
	
	private final Map<K, Integer> mTextureCacheMap = new HashMap<K, Integer>();
	
	private final int glTextureIndex;
	
	
	/**
	 * 
	 * @param glTextureIndex Must be one of i of GLES20.GL_TEXTUREi
	 */
	public GLKeyedTextureManager(int glTextureIndex) {
		this.glTextureIndex = glTextureIndex;
	}
	
	
	
	/** Also binds the texture.
	 * 
	 * @param sprite
	 * @param texture
	 */
	@Override
	public void addTexture(K key, IRenderTexture texture) {
		
		
		if(!(texture instanceof IImageTexture)) {
			throw new IllegalArgumentException("Texture type is not supported: use IImageTexture");
		}
		IImageTexture imageTexture = (IImageTexture) texture;
		STBStandardImage textureImage = imageTexture.getImage();

		// Generate a new texture
		int textureId = glGenTextures();
		

		glActiveTexture(GL_TEXTURE0 + glTextureIndex);
		glBindTexture(GL_TEXTURE_2D, textureId);
		

		
		// What does this do?
		//glPixelStorei(GL_UNPACK_ALIGNMENT, textureImage.getNumComponents());

		
		int internalFormat = getTextureFormat(textureImage.getNumComponents());
		int format = GL_RGBA;
		
		glTexImage2D(GL_TEXTURE_2D, 0, internalFormat, textureImage.getWidth(), textureImage.getHeight(), 0, format, GL_UNSIGNED_BYTE, textureImage.getData());
		//glTexImage2D(GL_TEXTURE_2D, 0, internalFormat, 54, 54, 0, format, GL_UNSIGNED_BYTE, textureImage.getData());

		//OpenGLUtils.checkGLErrors(TAG);

		

		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_BORDER);
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_BORDER);
		
		
		mTextureCacheMap.put(key, textureId);
		
	}
	
	/* TODO: Delete
	private void addFontTexture(K key, CodepointTexture texture) {
		
		// Generate a new texture
		int textureId = glGenTextures();
		

		glActiveTexture(GL_TEXTURE0 + glTextureIndex);
		glBindTexture(GL_TEXTURE_2D, textureId);
		

		
		// What does this do?
		//glPixelStorei(GL_UNPACK_ALIGNMENT, textureImage.getNumComponents());

		// Shoud store here in GL_RED and then in the shader use the red value
		int internalFormat = GL11.GL_ALPHA;
		int format = GL11.GL_ALPHA;
		
		GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
		glTexImage2D(GL_TEXTURE_2D, 0, internalFormat, texture.getWidth(), texture.getHeight(), 0, format, GL_UNSIGNED_BYTE, texture.getData());
		//glTexImage2D(GL_TEXTURE_2D, 0, internalFormat, 54, 54, 0, format, GL_UNSIGNED_BYTE, textureImage.getData());

		OpenGLUtils.checkGLErrors(TAG);

		

		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_BORDER);
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_BORDER);
		
		
		mTextureCacheMap.put(key, textureId);
		
	}
	*/
	
	public int getNameForTexture(K key) {
		return mTextureCacheMap.get(key);
	}
	
	
	
	@Override
	public boolean hasTextureFor(K key) {
		return mTextureCacheMap.containsKey(key);
	}
	
	
	@Override
	public void removeTextureFor(K key) {
		// TODO: Test this

		Integer texName = mTextureCacheMap.remove(key);
		if(texName == null) {
			throw new IllegalArgumentException("Cannot bind texture: Key does not have texture cached");
		}
		
		deleteTexture(texName);
	}
	
	private void deleteTexture(int texture) {
		glDeleteTextures(texture);
	}
	
	
	@Override
	public void bindTextureFor(K key) {
		Integer texName = mTextureCacheMap.get(key);
		if(texName == null) {
			throw new IllegalArgumentException("Cannot bind texture: Key does not have texture cached");
		}
		
		bindTexture(texName);
	}
	
	
	public void bindTexture(int textureId) {
		
		glActiveTexture(GL_TEXTURE0 + glTextureIndex);
		glBindTexture(GL_TEXTURE_2D, textureId);
	}
	
	
	@Override
	public void clear() {
		for(Integer texture: mTextureCacheMap.values()) {
			deleteTexture(texture);
		}
		
		mTextureCacheMap.clear();
	}
	
	@Override
	public int getTextureIndex() {
		return glTextureIndex;
	}
	
	
	
	private static int getTextureFormat(int components) {
		int format;
		switch (components) {
		case 1:
			format = GL_RGBA;
			break;
		case 2:
			format = GL_RGBA2;
			break;
		case 4:
			format = GL_RGBA4;
			break;
		case 8:
			format = GL_RGBA8;
			break;
		default:
			// What to do?
			format = GL_RGBA;
			break;
		}
		
		return format;
	}
	
	
	
	public static class GLTextureManagerFactory implements IKeyedTextureManagerFactory {

		@Override
		public <T> IKeyedTextureManager<T> newKeyedTextureManager(Class<T> keyClass, IParams params) {
			return new GLKeyedTextureManager<T>(OpenGLUtils.aquireGLTextureIndex()); 
		}
	}

	
}
