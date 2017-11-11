package com.tokelon.toktales.android.render.opengl;

import java.util.HashMap;
import java.util.Map;

import com.tokelon.toktales.core.render.IKeyedTextureManager;
import com.tokelon.toktales.core.render.IKeyedTextureManagerFactory;
import com.tokelon.toktales.core.render.IRenderTexture;
import com.tokelon.toktales.core.util.IParams;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLUtils;

public class GLKeyedTextureManager<K> implements IKeyedTextureManager<K> {

	private final Map<K, Integer> mTextureCacheMap = new HashMap<K, Integer>();
	
	private final int mGLTextureIndex;

	private final int[] texNameArrayDelete = new int[1];

	
	/**
	 * 
	 * @param glTextureIndex Must be one of i of GLES20.GL_TEXTUREi
	 */
	public GLKeyedTextureManager(int glTextureIndex) {
		this.mGLTextureIndex = glTextureIndex;
	}
	
	
	/** Also binds the texture.
	 * 
	 * @param sprite
	 * @param bitmap
	 */
	@Override
	public void addTexture(K key, IRenderTexture texture) {
		
		if(!(texture instanceof IAndroidBitmapTexture)) {
			throw new IllegalArgumentException("Texture type is not supported: use IAndroidBitmapTexture");
		}
		IAndroidBitmapTexture bitmapTexture = (IAndroidBitmapTexture) texture;
		Bitmap bitmap = bitmapTexture.getBitmap();
		
		
		// Generate a new texture
		int[] textureNames = new int[1];	// 1 texture
        GLES20.glGenTextures(1, textureNames, 0);
        
        int newTextureName = textureNames[0];
		
        
        // Bind texture
		GLES20.glActiveTexture(GLES20.GL_TEXTURE0 + mGLTextureIndex);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, newTextureName);

        // Set the texture
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
        
        
        
        // Set filtering
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);	//GL_LINEAR
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);	//GL_NEAREST

        // Set wrapping mode
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);
		
        
        mTextureCacheMap.put(key, newTextureName);
	}
	
	
	public int getNameForTexture(K key) {
		return mTextureCacheMap.get(key);
	}
	
	
	
	@Override
	public boolean hasTextureFor(K key) {
		return mTextureCacheMap.containsKey(key);
	}
	
	
	@Override
	public void removeTextureFor(K key) {
		Integer texName = mTextureCacheMap.remove(key);
		if(texName == null) {
			throw new IllegalArgumentException("Cannot remove texture: Key does not have texture cached");
		}
		
		deleteTexture(texName);
	}
	
	private void deleteTexture(int textureName) {
		texNameArrayDelete[0] = textureName;
		GLES20.glDeleteTextures(1, texNameArrayDelete, 0);	// !! Array creation
	}
	
	
	@Override
	public void bindTextureFor(K key) {
		Integer texName = mTextureCacheMap.get(key);
		if(texName == null) {
			throw new IllegalArgumentException("Cannot bind texture: Key does not have texture cached");
		}
		
		bindTexture(texName);
	}
	
	public void bindTexture(int textureName) {
		GLES20.glActiveTexture(GLES20.GL_TEXTURE0 + mGLTextureIndex);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureName);
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
		return mGLTextureIndex;
	}
	
	
	public static class GLTextureManagerFactory implements IKeyedTextureManagerFactory {

		@Override
		public <T> IKeyedTextureManager<T> newKeyedTextureManager(Class<T> keyClass, IParams params) {
			return new GLKeyedTextureManager<T>(OpenGLUtils.aquireGLTextureIndex());
		}
	}
	
	
}
