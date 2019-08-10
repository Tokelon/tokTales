package com.tokelon.toktales.desktop.lwjgl.data;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.stb.STBTTFontinfo;
import org.lwjgl.stb.STBTruetype;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import com.google.common.io.ByteStreams;
import com.tokelon.toktales.core.content.manage.font.ITextureFontAsset;
import com.tokelon.toktales.core.content.manage.font.ITextureFontAssetDecoder;
import com.tokelon.toktales.core.content.manage.font.ITextureFontAssetKey;
import com.tokelon.toktales.core.content.manage.font.TextureFontAssetImpl;
import com.tokelon.toktales.core.content.text.ITextureFont;
import com.tokelon.toktales.core.engine.content.ContentException;
import com.tokelon.toktales.core.util.options.IOptions;
import com.tokelon.toktales.desktop.lwjgl.LWJGLException;

public class STBTextureFontDecoder implements ITextureFontAssetDecoder {

	public static final int DEFAULT_FONT_PIXEL_HEIGHT = 0;
	

	@Override
	public ITextureFontAsset decode(InputStream inputstream, ITextureFontAssetKey key, IOptions options) throws ContentException {
		int fontPixelHeight = options == null ? DEFAULT_FONT_PIXEL_HEIGHT : options.getAsOrDefault(OPTION_FONT_PIXEL_HEIGHT, DEFAULT_FONT_PIXEL_HEIGHT, Integer.class);
		
		try {
			byte[] bytes = ByteStreams.toByteArray(inputstream);
			
			ITextureFont font = createFont(bytes, fontPixelHeight);
			return new TextureFontAssetImpl(font);
		} catch (LWJGLException e) {
			throw new ContentException(e);
		} catch (IOException ioe) {
			throw new ContentException(ioe);
		}
	}
	
	
	public ITextureFont createFont(byte[] bytes, int fontPixelHeight) throws LWJGLException {
		ByteBuffer buffer = MemoryUtil.memAlloc(bytes.length);
		buffer.put(bytes);
		buffer.flip();
		
		
		STBTTFontinfo fontInfo = STBTTFontinfo.create();
		
		boolean success = STBTruetype.stbtt_InitFont(fontInfo, buffer);
		if(!success) {
			MemoryUtil.memFree(buffer);

			throw new LWJGLException("Failed to initialize font: STBTruetype.stbtt_InitFont() returned false");
		}
		
		try(MemoryStack stack = MemoryStack.stackPush()) {
			IntBuffer ascentBuffer = stack.callocInt(1);
			IntBuffer descentBuffer = stack.callocInt(1);
			IntBuffer lineGapBuffer = stack.callocInt(1);

			STBTruetype.stbtt_GetFontVMetrics(fontInfo, ascentBuffer, descentBuffer, lineGapBuffer);

			STBTextureFont result = new STBTextureFont(buffer, fontInfo, ascentBuffer.get(0), descentBuffer.get(0), lineGapBuffer.get(0), (font) -> MemoryUtil.memFree(font.getData()));
			result.setFontTextSize(fontPixelHeight);
			return result;
		}
	}

}
