package com.tokelon.toktales.desktop.lwjgl.data;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import org.lwjgl.system.MemoryStack;

import com.google.common.io.ByteStreams;
import com.tokelon.toktales.core.content.manage.bitmap.BitmapAssetImpl;
import com.tokelon.toktales.core.content.manage.bitmap.IBitmapAsset;
import com.tokelon.toktales.core.content.manage.bitmap.IBitmapAssetDecoder;
import com.tokelon.toktales.core.content.manage.bitmap.IBitmapAssetKey;
import com.tokelon.toktales.core.engine.content.ContentException;
import com.tokelon.toktales.core.util.options.IOptions;
import com.tokelon.toktales.desktop.lwjgl.LWJGLException;

public class STBBitmapDecoder implements IBitmapAssetDecoder {
	// TODO: Implement options?

	@Override
	public IBitmapAsset decode(InputStream inputstream, IBitmapAssetKey key, IOptions options) throws ContentException {
		try(MemoryStack stack = MemoryStack.stackPush()) {
			// Read input stream into byte array
			byte[] byteArray = ByteStreams.toByteArray(inputstream);
			
			// Put byte array into direct buffer (managed memory)
			ByteBuffer buffer = stack.malloc(byteArray.length);
			buffer.put(byteArray);
			buffer.flip();
			
			// Create image by parsing the buffer data
			STBStandardImage image = STBStandardImage.initFrom(buffer);
			return new BitmapAssetImpl(image);
		} catch (LWJGLException e) {
			throw new ContentException(e);
		} catch (IOException ioe) {
			throw new ContentException(ioe);
		}
	}

}
