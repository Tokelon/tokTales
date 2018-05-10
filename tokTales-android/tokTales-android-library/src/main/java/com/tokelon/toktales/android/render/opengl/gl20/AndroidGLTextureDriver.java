package com.tokelon.toktales.android.render.opengl.gl20;

import javax.inject.Inject;

import com.tokelon.toktales.android.data.IAndroidBitmap;
import com.tokelon.toktales.core.content.IBitmap;
import com.tokelon.toktales.core.render.IRenderTexture;
import com.tokelon.toktales.core.render.ITextureDriver;
import com.tokelon.toktales.core.render.opengl.IGLErrorUtils;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLUtils;

/** Texture driver optimized for Android.
 */
public class AndroidGLTextureDriver implements ITextureDriver {

	
	// Use buffers instead of arrays?
	private final int[] glArrayCreate = new int[1];
	private final int[] glArrayDelete = new int[1];
	private final int[] glArrayTextureIndex = new int[1];
	private final int[] glArrayTextureSize = new int[1];
	private final int[] glArrayTextureUnits = new int[1];
	private final int[] glArrayTextureCombinedUnits = new int[1];
	
	private final IGLErrorUtils glErrorUtils;
	
	@Inject
	public AndroidGLTextureDriver(IGLErrorUtils glErrorUtils) {
		this.glErrorUtils = glErrorUtils;
	}
	
	
	@Override
	public int loadTexture(IRenderTexture texture, int textureIndex) {
		if(texture == null) {
			throw new NullPointerException();
		}
		if(textureIndex < 0) {
			throw new IllegalArgumentException("textureIndex must be >= 0");
		}
		
		glErrorUtils.logGLErrors("before loadTexture");
		

		// Generate a new texture
        GLES20.glGenTextures(1, glArrayCreate, 0);
        int textureLocation = glArrayCreate[0];
        
        
        // Bind texture
		GLES20.glActiveTexture(GLES20.GL_TEXTURE0 + textureIndex);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureLocation);

        
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
		
        
		glErrorUtils.assertNoGLErrors();

		return textureLocation;
	}


	@Override
	public void unloadTexture(int textureLocation) {
		glArrayDelete[0] = textureLocation;
		GLES20.glDeleteTextures(1, glArrayDelete, 0);
	}

	@Override
	public void bindTexture(int textureLocation, int textureIndex) {
		GLES20.glActiveTexture(GLES20.GL_TEXTURE0 + textureIndex);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureLocation);
	}

	@Override
	public void unbindTexture(int textureIndex) {
		GLES20.glActiveTexture(GLES20.GL_TEXTURE0 + textureIndex);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
	}


	@Override
	public int getTextureBoundToIndex(int textureIndex) {
		GLES20.glActiveTexture(GLES20.GL_TEXTURE0 + textureIndex);
		GLES20.glGetIntegerv(GLES20.GL_TEXTURE_BINDING_2D, glArrayTextureIndex, 0);
		return glArrayTextureIndex[0];
	}

	@Override
	public int getMaxTextureSize() {
		GLES20.glGetIntegerv(GLES20.GL_MAX_TEXTURE_SIZE, glArrayTextureSize, 0);
		return glArrayTextureSize[0];
	}

	@Override
	public int getMaxTextureUnits() {
		GLES20.glGetIntegerv(GLES20.GL_MAX_TEXTURE_IMAGE_UNITS, glArrayTextureUnits, 0);
		return glArrayTextureUnits[0];
	}

	@Override
	public int getMaxCombinedTextureUnits() {
		GLES20.glGetIntegerv(GLES20.GL_MAX_COMBINED_TEXTURE_IMAGE_UNITS, glArrayTextureCombinedUnits, 0);
		return glArrayTextureCombinedUnits[0];
	}

}
