package com.tokelon.toktales.desktop.lwjgl.render;

import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameterf;
import static org.lwjgl.opengl.GL11.glPixelStorei;
import static org.lwjgl.opengl.GL11.glDeleteTextures;
import static org.lwjgl.opengl.GL13.GL_CLAMP_TO_BORDER;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.opengl.GL11;

import com.tokelon.toktales.core.render.IRenderTexture;
import com.tokelon.toktales.core.render.ITextureManager;
import com.tokelon.toktales.core.render.ITextureManagerFactory;
import com.tokelon.toktales.core.util.IParams;
import com.tokelon.toktales.desktop.content.IBitmapTexture;

public class GLBitmapTextureManager implements ITextureManager {

	public static final String TAG = "GLBitmapTextureManager";
	
	private final Map<IBitmapTexture, Integer> textureCacheMap = new HashMap<IBitmapTexture, Integer>();
	
	private final int glTextureIndex;
	
	
	/**
	 * 
	 * @param glTextureIndex Must be one of i of GLES20.GL_TEXTUREi
	 */
	public GLBitmapTextureManager(int glTextureIndex) {
		this.glTextureIndex = glTextureIndex;
	}
	
	
	/** Also binds the texture.
	 * 
	 * @param texture
	 */
	@Override
	public void addTexture(IRenderTexture texture) {
		
		
		if(!(texture instanceof IBitmapTexture)) {
			throw new IllegalArgumentException("Texture type is not supported: use IBitmapTexture");
		}
		
		IBitmapTexture bitmapTexture = (IBitmapTexture) texture;
		

		// Generate a new texture
		int textureId = glGenTextures();
		
		glActiveTexture(GL_TEXTURE0 + glTextureIndex);
		glBindTexture(GL_TEXTURE_2D, textureId);
		

		// TODO: Is this static? if yes make it a class variable
		glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, bitmapTexture.getUnpackAlignment());

		glTexImage2D(GL_TEXTURE_2D, 0, bitmapTexture.getInternalFormat(), bitmapTexture.getWidth(), bitmapTexture.getHeight(), 0, bitmapTexture.getFormat(), GL_UNSIGNED_BYTE, bitmapTexture.getData());

		//OpenGLUtils.checkGLErrors(TAG);
		

		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_BORDER);
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_BORDER);
		
		textureCacheMap.put(bitmapTexture, textureId);
	}

	
	@Override
	public boolean hasTexture(IRenderTexture texture) {
		return textureCacheMap.containsKey(texture);
	}
	
	

	@Override
	public void removeTexture(IRenderTexture texture) {
		// TODO: Test this
		
		Integer texId = textureCacheMap.remove(texture);
		if(texId == null) {
			throw new IllegalArgumentException("Cannot delete texture: texture not in cache");
		}
		
		deleteTexture(texId);
	}
	
	private void deleteTexture(int texture) {
		glDeleteTextures(texture);
	}
	
	
	
	@Override
	public void bindTexture(IRenderTexture texture) {
		Integer texId = textureCacheMap.get(texture);
		if(texId == null) {
			throw new IllegalArgumentException("Cannot bind texture: texture not in cache");
		}
		
		bindTextureId(texId);
	}
	
	
	private void bindTextureId(int textureId) {
		glActiveTexture(GL_TEXTURE0 + glTextureIndex);
		glBindTexture(GL_TEXTURE_2D, textureId);
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
		
		@Override
		public ITextureManager newTextureManager(IParams params) {
			return new GLBitmapTextureManager(OpenGLUtils.aquireGLTextureIndex());
		}
	}
	
	
}
