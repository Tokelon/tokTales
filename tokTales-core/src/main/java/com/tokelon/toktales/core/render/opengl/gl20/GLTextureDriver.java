package com.tokelon.toktales.core.render.opengl.gl20;

import javax.inject.Inject;

import com.tokelon.toktales.core.render.IRenderTexture;
import com.tokelon.toktales.core.render.ITextureDriver;

public class GLTextureDriver implements ITextureDriver {
	// Cache info values?
	// Add checks for texture index values?
	
	
	private final IGL11 gl11;
	private final IGL13 gl13;

	@Inject
	public GLTextureDriver(IGL11 gl11, IGL13 gl13) {
		this.gl11 = gl11;
		this.gl13 = gl13;
	}
	
	
	@Override
	public int loadTexture(IRenderTexture texture, int textureIndex) {
		if(texture == null) {
			throw new NullPointerException();
		}
		
		int textureLocation = gl11.glGenTextures();
		
		//gl13.glActiveTexture(textureIndex); // Causes text rendering glitch
		gl13.glActiveTexture(IGL13.GL_TEXTURE0 + textureIndex);
		gl11.glBindTexture(IGL11.GL_TEXTURE_2D, textureLocation);
		
		
		int unpackAlignment = texture.getUnpackAlignment();
		gl11.glPixelStorei(IGL11.GL_UNPACK_ALIGNMENT, unpackAlignment);
		
		int textureFormat = texture.getTextureFormat();   // GL_RGBA
		int internalFormat = texture.getInternalFormat(); // GL_RGBA
		int dataType = texture.getDataType();             // GL_UNSIGNED_BYTE
		int width = texture.getBitmap().getWidth();
		int height = texture.getBitmap().getHeight();
		
		gl11.glTexImage2D(
				IGL11.GL_TEXTURE_2D,
				0, // level
				internalFormat,
				width,
				height,
				0, // border
				textureFormat,
				dataType,
				texture.getBitmap().getData());
		
		//OpenGLUtils.checkGLErrors(TAG); // TODO: Use!

		
		// TODO: Does this need to be done when binding?
		gl11.glTexParameterf(IGL11.GL_TEXTURE_2D, IGL11.GL_TEXTURE_MIN_FILTER, texture.getFilterMin());
		gl11.glTexParameterf(IGL11.GL_TEXTURE_2D, IGL11.GL_TEXTURE_MAG_FILTER, texture.getFilterMag());
		
		gl11.glTexParameterf(IGL11.GL_TEXTURE_2D, IGL11.GL_TEXTURE_WRAP_S, texture.getWrapS());
		gl11.glTexParameterf(IGL11.GL_TEXTURE_2D, IGL11.GL_TEXTURE_WRAP_T, texture.getWrapT());
		
		
		return textureLocation;
	}

	@Override
	public void unloadTexture(int textureLocation) {
		gl11.glDeleteTextures(textureLocation);
	}

	@Override
	public void bindTexture(int textureLocation, int textureIndex) {
		gl13.glActiveTexture(IGL13.GL_TEXTURE0 + textureIndex);
		gl11.glBindTexture(IGL11.GL_TEXTURE_2D, textureLocation);
	}

	@Override
	public void unbindTexture(int textureIndex) {
		gl13.glActiveTexture(IGL13.GL_TEXTURE0 + textureIndex);
		gl11.glBindTexture(IGL11.GL_TEXTURE_2D, 0);
	}
	

	@Override
	public int getTextureBoundToIndex(int textureIndex) {
		gl13.glActiveTexture(IGL13.GL_TEXTURE0 + textureIndex);
		return gl11.glGetInteger(IGL11.GL_TEXTURE_BINDING_2D);
	}
	
	@Override
	public int getMaxTextureSize() {
		return gl11.glGetInteger(IGL11.GL_MAX_TEXTURE_SIZE);
	}

	@Override
	public int getMaxTextureUnits() {
		return gl11.glGetInteger(IGL20.GL_MAX_TEXTURE_IMAGE_UNITS);
	}
	
	@Override
	public int getMaxCombinedTextureUnits() {
		return gl11.glGetInteger(IGL20.GL_MAX_COMBINED_TEXTURE_IMAGE_UNITS);
	}

}
