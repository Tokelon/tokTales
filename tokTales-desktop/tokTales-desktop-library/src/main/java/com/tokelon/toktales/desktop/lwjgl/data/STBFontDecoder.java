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
import com.tokelon.toktales.core.content.manage.font.IFontAsset;
import com.tokelon.toktales.core.content.manage.font.IFontAssetDecoder;
import com.tokelon.toktales.core.content.manage.font.IFontAssetKey;
import com.tokelon.toktales.core.content.manage.font.FontAssetImpl;
import com.tokelon.toktales.core.content.text.IFont;
import com.tokelon.toktales.desktop.lwjgl.LWJGLException;
import com.tokelon.toktales.tools.assets.exception.AssetException;
import com.tokelon.toktales.tools.core.objects.options.IOptions;

public class STBFontDecoder implements IFontAssetDecoder {

	public static final int DEFAULT_FONT_PIXEL_HEIGHT = 0;
	

	@Override
	public IFontAsset decode(InputStream inputstream, IFontAssetKey key, IOptions options) throws AssetException {
		int fontPixelHeight = options == null ? DEFAULT_FONT_PIXEL_HEIGHT : options.getAsOrDefault(OPTION_FONT_PIXEL_HEIGHT, DEFAULT_FONT_PIXEL_HEIGHT, Integer.class);
		
		try {
			byte[] bytes = ByteStreams.toByteArray(inputstream);
			
			IFont font = createFont(bytes, fontPixelHeight);
			return new FontAssetImpl(font);
		} catch (LWJGLException e) {
			throw new AssetException(e);
		} catch (IOException ioe) {
			throw new AssetException(ioe);
		}
	}
	
	
	public IFont createFont(byte[] bytes, int fontPixelHeight) throws LWJGLException {
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

			// Use STBTTFontinfo.free() for disposer?
			STBFont result = new STBFont(buffer, fontInfo, ascentBuffer.get(0), descentBuffer.get(0), lineGapBuffer.get(0), (font) -> MemoryUtil.memFree(font.getData()));
			result.setFontTextSize(fontPixelHeight);
			return result;
		}
	}

}
