package com.tokelon.toktales.android.render.opengl;

import java.util.HashMap;
import java.util.Map;

import com.tokelon.toktales.android.data.IAndroidBitmap;
import com.tokelon.toktales.core.content.IBitmap;
import com.tokelon.toktales.core.render.IKeyedTextureManager;
import com.tokelon.toktales.core.render.IKeyedTextureManagerFactory;
import com.tokelon.toktales.core.render.IRenderTexture;
import com.tokelon.toktales.core.util.IParams;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLUtils;

public class GLKeyedTextureManager<K> implements IKeyedTextureManager<K> {

	public static final String TAG = "GLKeyedTextureManager";
	
	
	private final Map<K, Integer> textureMap = new HashMap<K, Integer>();
	
	private final int[] texNameArrayCreate = new int[1];
	private final int[] texNameArrayDelete = new int[1];
	

	private final int glTextureIndex;

	/**
	 * @param glTextureIndex Must be one of i of GLES20.GL_TEXTUREi
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
			
		}
		
		// Generate a new texture
        GLES20.glGenTextures(1, texNameArrayCreate, 0);
        int newTextureName = texNameArrayCreate[0];
		
        
        // Bind texture
		GLES20.glActiveTexture(GLES20.GL_TEXTURE0 + glTextureIndex);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, newTextureName);


        int unpackAlignment = texture.getUnpackAlignment();
		GLES20.glPixelStorei(GLES20.GL_UNPACK_ALIGNMENT, unpackAlignment);

        
		// Set the texture
		IBitmap textureImage = texture.getBitmap();
        if((textureImage instanceof IAndroidBitmap)) {
        	IAndroidBitmap bitmapImage = (IAndroidBitmap) textureImage;
    		Bitmap bitmap = bitmapImage.getBitmap();
    		
    		GLUtils.texImage2D(
    				GLES20.GL_TEXTURE_2D,
    				0, // level
    				bitmap,
    				0); // border
		}
        else {
    		int textureFormat = texture.getTextureFormat();   // GL_RGBA
    		int internalFormat = texture.getInternalFormat(); // GL_RGBA
    		int dataType = texture.getDataType();             // GL_UNSIGNED_BYTE
    		int width = textureImage.getWidth();
    		int height = textureImage.getHeight();
    		
        	GLES20.glTexImage2D(
        			GLES20.GL_TEXTURE_2D,
        			0, // level
    				internalFormat,
    				width,
    				height,
    				0, // border
    				textureFormat,
    				dataType,
    				textureImage.getData());
        }
		
        
        
        // Set filtering
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, texture.getFilterMin());	//GL_NEAREST
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, texture.getFilterMag());	//GL_NEAREST

        // Set wrapping mode
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, texture.getWrapS()); //GL_CLAMP_TO_EDGE
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, texture.getWrapT()); //GL_CLAMP_TO_EDGE
		
        
        textureMap.put(key, newTextureName);
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
		Integer texName = textureMap.remove(key);
		if(texName == null) {
			throw new IllegalArgumentException("Cannot delete texture: texture not in cache");
		}
		
		deleteTexture(texName);
	}
	
	private void deleteTexture(int textureName) {
		texNameArrayDelete[0] = textureName;
		GLES20.glDeleteTextures(1, texNameArrayDelete, 0);
	}
	
	
	@Override
	public void bindTextureFor(K key) {
		Integer texName = textureMap.get(key);
		if(texName == null) {
			throw new IllegalArgumentException("Cannot bind texture: texture not in cache");
		}
		
		bindTexture(texName);
	}
	
	public void bindTexture(int textureName) {
		GLES20.glActiveTexture(GLES20.GL_TEXTURE0 + glTextureIndex);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureName);
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
