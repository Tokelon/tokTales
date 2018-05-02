package com.tokelon.toktales.android.render.opengl.gl20;

import javax.inject.Inject;

import com.tokelon.toktales.android.data.IAndroidBitmap;
import com.tokelon.toktales.core.content.IBitmap;
import com.tokelon.toktales.core.render.IRenderTexture;
import com.tokelon.toktales.core.render.opengl.gl20.GLTextureDriver;
import com.tokelon.toktales.core.render.opengl.gl20.IGL11;
import com.tokelon.toktales.core.render.opengl.gl20.IGL13;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLUtils;

public class AndroidGLTextureDriver extends GLTextureDriver {

	
	private final int[] texNameArrayCreate = new int[1];

	
	@Inject
	public AndroidGLTextureDriver(IGL11 gl11, IGL13 gl13) {
		super(gl11, gl13);
	}
	
	
	@Override
	public int loadTexture(IRenderTexture texture, int textureIndex) {
		if(texture == null) {
			throw new NullPointerException();
		}
		if(textureIndex < 0) {
			throw new IllegalArgumentException("textureIndex must be >= 0");
		}
		
		int locationResult;
		
		IBitmap textureImage = texture.getBitmap();
        if((textureImage instanceof IAndroidBitmap)) {
        	
    		// Generate a new texture
            GLES20.glGenTextures(1, texNameArrayCreate, 0);
            int textureLocation = texNameArrayCreate[0];
    		
            
            // Bind texture
    		GLES20.glActiveTexture(GLES20.GL_TEXTURE0 + textureIndex);
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureLocation);


            int unpackAlignment = texture.getUnpackAlignment();
    		GLES20.glPixelStorei(GLES20.GL_UNPACK_ALIGNMENT, unpackAlignment);

        	
    		// Set the texture
        	IAndroidBitmap bitmapImage = (IAndroidBitmap) textureImage;
    		Bitmap bitmap = bitmapImage.getBitmap();
    		
    		GLUtils.texImage2D(
    				GLES20.GL_TEXTURE_2D,
    				0, // level
    				bitmap,
    				0); // border
    		

            // Set filtering
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, texture.getFilterMin());	//GL_NEAREST
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, texture.getFilterMag());	//GL_NEAREST

            // Set wrapping mode
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, texture.getWrapS()); //GL_CLAMP_TO_EDGE
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, texture.getWrapT()); //GL_CLAMP_TO_EDGE
    		
            locationResult = textureLocation; 
		}
        else {
        	locationResult = super.loadTexture(texture, textureIndex);
        }
        
		return locationResult;
	}

}
