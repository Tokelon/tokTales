package com.tokelon.toktales.desktop.lwjgl.data;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBTTFontinfo;
import org.lwjgl.stb.STBTruetype;

import com.tokelon.toktales.core.content.IDisposer;
import com.tokelon.toktales.core.content.manage.codepoint.CodepointAsset;
import com.tokelon.toktales.core.content.manage.codepoint.ICodepointAsset;
import com.tokelon.toktales.core.content.manage.codepoint.ICodepointAssetDecoder;
import com.tokelon.toktales.core.content.manage.codepoint.ICodepointAssetKey;
import com.tokelon.toktales.core.content.text.CodepointTexture;
import com.tokelon.toktales.core.content.text.ICodepoint;
import com.tokelon.toktales.core.content.text.ICodepointTexture;
import com.tokelon.toktales.core.engine.content.ContentException;
import com.tokelon.toktales.core.game.model.IRectangle2i;
import com.tokelon.toktales.core.game.model.Rectangle2iImpl;
import com.tokelon.toktales.core.util.options.INamedOptions;

public class STBCodepointDecoder implements ICodepointAssetDecoder {
	// TODO: Use proper memory calls


	@Override
	public ICodepointAsset decode(InputStream inputstream, ICodepointAssetKey key, INamedOptions options) throws ContentException {
		if(!(key.getFont() instanceof STBTextureFont)) {
			throw new IllegalArgumentException("font must be of type: " + STBTextureFont.class.getSimpleName());
		}
		STBTextureFont textureFont = (STBTextureFont) key.getFont();
		int codepoint = key.getCodepoint();
		float fontPixelHeight = key.getFontPixelHeight();
		
		ICodepoint fontCodepoint = createCodepoint(textureFont, codepoint, fontPixelHeight);
		return new CodepointAsset(fontCodepoint);
	}
	
	
	public ICodepoint createCodepoint(STBTextureFont textureFont, int codepoint, float fontPixelHeight) {
		STBTTFontinfo fontInfo = textureFont.getFontInfo();
		
		float fontScale = STBTruetype.stbtt_ScaleForPixelHeight(fontInfo, fontPixelHeight);
		
		ICodepointTexture texture = makeCodepointTexture(fontInfo, codepoint, fontScale);
		IRectangle2i bitmapBox = makeCodepointBitmapBox(fontInfo, codepoint, fontScale);

		
		// Metrics
		IntBuffer advanceWidthBuffer = BufferUtils.createIntBuffer(1);
		IntBuffer leftSideBearingBuffer = BufferUtils.createIntBuffer(1);	// Do something with this?
		
		STBTruetype.stbtt_GetCodepointHMetrics(fontInfo, codepoint, advanceWidthBuffer, leftSideBearingBuffer);
		// Do these need to be multiplied by fontScale?
		int advanceWidth = advanceWidthBuffer.get(0);
		int leftSideBearing = leftSideBearingBuffer.get(0);
		int bitmapOffsetX = texture.getOriginOffsetX();
		int bitmapOffsetY = texture.getOriginOffsetY() + Math.round(textureFont.getFontPixelAscent() * fontScale); // Add the font ascend so it counts from the top instead of the origin;
		
		// TODO: Check disposer
		IDisposer<STBCodepoint> disposer = (cp) -> STBTruetype.stbtt_FreeBitmap(cp.getTexture().getBitmap().getData()); // Needs user data?
		return new STBCodepoint(texture, bitmapBox, fontPixelHeight, advanceWidth, leftSideBearing, bitmapOffsetX, bitmapOffsetY, disposer);
	}
	
	
	private CodepointTexture makeCodepointTexture(STBTTFontinfo fontInfo, int codepoint, float fontScale) {
		//STBTruetype.stbtt_MakeCodepointBitmap(info, output, out_w, out_h, out_stride, scale_x, scale_y, codepoint)

		IntBuffer width = BufferUtils.createIntBuffer(1);
		IntBuffer height = BufferUtils.createIntBuffer(1);
		IntBuffer xoffset = BufferUtils.createIntBuffer(1);
		IntBuffer yoffset = BufferUtils.createIntBuffer(1);

		ByteBuffer bitmap = STBTruetype.stbtt_GetCodepointBitmap(fontInfo, fontScale, fontScale, codepoint, width, height, xoffset, yoffset);

		CodepointTexture texture = CodepointTexture.create(bitmap, width.get(0), height.get(0), xoffset.get(0), yoffset.get(0));
		
		return texture;
	}
	

	private IRectangle2i makeCodepointBitmapBox(STBTTFontinfo fontInfo, int codepoint, float fontScale) {
		// BitmapBox coordinates are calculated from the glyph origin
		// Glyph origin = fontAscend
		// bottom is yoffset
		// right is xoffset ?
		// top - bottom = height
		// right - left = width
		
		
		IntBuffer left = BufferUtils.createIntBuffer(1);
		IntBuffer top = BufferUtils.createIntBuffer(1);
		IntBuffer right = BufferUtils.createIntBuffer(1);
		IntBuffer bottom = BufferUtils.createIntBuffer(1);
		
		STBTruetype.stbtt_GetCodepointBitmapBox(fontInfo, codepoint, fontScale, fontScale, left, bottom, right, top);
		
		return new Rectangle2iImpl().set(left.get(0), top.get(0), right.get(0), bottom.get(0));
	}
	
}
