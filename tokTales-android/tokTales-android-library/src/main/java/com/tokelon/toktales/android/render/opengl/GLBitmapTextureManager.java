package com.tokelon.toktales.android.render.opengl;

import java.util.HashMap;
import java.util.Map;

import com.tokelon.toktales.core.render.IRenderTexture;
import com.tokelon.toktales.core.render.ITextureManager;
import com.tokelon.toktales.core.render.ITextureManagerFactory;
import com.tokelon.toktales.core.util.IParams;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLUtils;

public class GLBitmapTextureManager implements ITextureManager {

	private final Map<IAndroidBitmapTexture, Integer> textureCacheMap = new HashMap<IAndroidBitmapTexture, Integer>();

	private final int glTextureIndex;
	
	private final int[] texNameArrayDelete = new int[1];

	
	/**
	 * 
	 * @param glTextureIndex Must be one of i of GLES20.GL_TEXTUREi
	 */
	public GLBitmapTextureManager(int glTextureIndex) {
		this.glTextureIndex = glTextureIndex;
	}
	
	
	
	/** Also binds the texture.
	 * 
	 */
	@Override
	public void addTexture(IRenderTexture texture) {
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
		GLES20.glActiveTexture(GLES20.GL_TEXTURE0 + glTextureIndex);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, newTextureName);

        // Use this ?
        GLES20.glPixelStorei(GLES20.GL_UNPACK_ALIGNMENT, bitmapTexture.getUnpackAlignment());
        
        // Set the texture
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
        
        
        
        // Set filtering
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);	//GL_LINEAR
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);	//GL_NEAREST

        // Set wrapping mode
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);
		
		textureCacheMap.put(bitmapTexture, newTextureName);
	}

	@Override
	public boolean hasTexture(IRenderTexture texture) {
		return textureCacheMap.containsKey(texture);
	}



	@Override
	public void removeTexture(IRenderTexture texture) {
		Integer texName = textureCacheMap.remove(texture);
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
	public void bindTexture(IRenderTexture texture) {
		Integer texName = textureCacheMap.get(texture);
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
		for(Integer texture: textureCacheMap.values()) {
			deleteTexture(texture);
		}
		
		textureCacheMap.clear();
	}
	
	@Override
	public int getTextureIndex() {
		return glTextureIndex;
	}

	
	public static class GLBitmapTextureManagerFactory implements ITextureManagerFactory {

		// TODO: Inject OpenGLUtils and do not use statically, also replace in all other texture managers
		
		@Override
		public ITextureManager newTextureManager(IParams params) {
			return new GLBitmapTextureManager(OpenGLUtils.aquireGLTextureIndex());
		}
	}


}
