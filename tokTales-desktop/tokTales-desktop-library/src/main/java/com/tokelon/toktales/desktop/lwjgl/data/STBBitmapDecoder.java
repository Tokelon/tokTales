package com.tokelon.toktales.desktop.lwjgl.data;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import com.google.common.io.ByteStreams;
import com.tokelon.toktales.core.content.manage.bitmap.BitmapAssetImpl;
import com.tokelon.toktales.core.content.manage.bitmap.IBitmapAsset;
import com.tokelon.toktales.core.content.manage.bitmap.IBitmapAssetDecoder;
import com.tokelon.toktales.core.content.manage.bitmap.IBitmapAssetKey;
import com.tokelon.toktales.core.engine.content.ContentException;
import com.tokelon.toktales.desktop.lwjgl.LWJGLException;
import com.tokelon.toktales.tools.core.objects.options.IOptions;

public class STBBitmapDecoder implements IBitmapAssetDecoder {
	// TODO: Implement options?

	@Override
	public IBitmapAsset decode(InputStream inputstream, IBitmapAssetKey key, IOptions options) throws ContentException {
		try {
			// Read input stream into byte array
			byte[] byteArray = ByteStreams.toByteArray(inputstream);
			
			// Put byte array into direct buffer (managed memory)
			ByteBuffer buffer = MemoryUtil.memAlloc(byteArray.length);
			try {
				buffer.put(byteArray);
				buffer.flip();

				// Create image by parsing the buffer data
				ISTBBitmap image = createBitmap(buffer);
				return new BitmapAssetImpl(image);
			}
			finally {
				MemoryUtil.memFree(buffer);
			}
		} catch (LWJGLException e) {
			throw new ContentException(e);
		} catch (IOException ioe) {
			throw new ContentException(ioe);
		}
	}


	public ISTBBitmap createBitmap(ByteBuffer buffer) throws LWJGLException {
		try(MemoryStack stack = MemoryStack.stackPush()) {
			IntBuffer width = stack.callocInt(1);
			IntBuffer height = stack.callocInt(1);
			IntBuffer imageChannels = stack.callocInt(1);
			
			int desiredChannels = 4; // Always load as RGBA
			ByteBuffer imageBuffer = STBImage.stbi_load_from_memory(buffer, width, height, imageChannels, desiredChannels);
			if(imageBuffer == null) {
				throw new LWJGLException("Failed to load image: " + STBImage.stbi_failure_reason());
			}
			
			return new STBBitmap(imageBuffer, width.get(0), height.get(0), desiredChannels, imageChannels.get(0), (image) -> STBImage.stbi_image_free(image.getData()));
		}
	}
	
}
