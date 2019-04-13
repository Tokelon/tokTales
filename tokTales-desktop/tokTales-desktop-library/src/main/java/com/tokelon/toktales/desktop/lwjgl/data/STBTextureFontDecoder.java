package com.tokelon.toktales.desktop.lwjgl.data;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import org.lwjgl.system.MemoryStack;

import com.google.common.io.ByteStreams;
import com.tokelon.toktales.core.content.manage.font.ITextureFontAsset;
import com.tokelon.toktales.core.content.manage.font.ITextureFontAssetDecoder;
import com.tokelon.toktales.core.content.manage.font.ITextureFontAssetKey;
import com.tokelon.toktales.core.content.manage.font.TextureFontAssetImpl;
import com.tokelon.toktales.core.engine.content.ContentException;
import com.tokelon.toktales.core.util.options.IOptions;

public class STBTextureFontDecoder implements ITextureFontAssetDecoder {

	public static final int DEFAULT_FONT_PIXEL_HEIGHT = 64;
	

	@Override
	public ITextureFontAsset decode(InputStream inputstream, ITextureFontAssetKey key, IOptions options) throws ContentException {
		int fontPixelHeight = options == null ? DEFAULT_FONT_PIXEL_HEIGHT : options.getAsOrDefault(OPTION_FONT_PIXEL_HEIGHT, DEFAULT_FONT_PIXEL_HEIGHT, Integer.class);
		
		try(MemoryStack stack = MemoryStack.stackPush()) {
			// Read input stream into byte array
			byte[] byteArray = ByteStreams.toByteArray(inputstream);
			
			// Put byte array into direct buffer (managed memory)
			ByteBuffer buffer = stack.malloc(byteArray.length);
			buffer.put(byteArray);
			buffer.flip();
			
			// Create font by parsing the buffer data
			STBTextureFont font = new STBTextureFont(fontPixelHeight);
			font.initializeFont(buffer);
			return new TextureFontAssetImpl(font);
		} catch (IOException ioe) {
			throw new ContentException(ioe);
		}
	}

}
