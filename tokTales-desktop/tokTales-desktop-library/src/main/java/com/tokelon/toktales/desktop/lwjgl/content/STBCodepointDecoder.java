package com.tokelon.toktales.desktop.lwjgl.content;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.stb.STBTTFontinfo;
import org.lwjgl.stb.STBTruetype;
import org.lwjgl.system.MemoryStack;

import com.tokelon.toktales.core.content.manage.codepoint.CodepointAsset;
import com.tokelon.toktales.core.content.manage.codepoint.ICodepointAsset;
import com.tokelon.toktales.core.content.manage.codepoint.ICodepointAssetDecoder;
import com.tokelon.toktales.core.content.manage.codepoint.ICodepointAssetKey;
import com.tokelon.toktales.core.content.text.CodepointTexture;
import com.tokelon.toktales.core.content.text.ICodepoint;
import com.tokelon.toktales.core.content.text.ICodepointTexture;
import com.tokelon.toktales.core.game.model.IRectangle2i;
import com.tokelon.toktales.core.game.model.Rectangle2iImpl;
import com.tokelon.toktales.tools.assets.exception.AssetException;
import com.tokelon.toktales.tools.core.dispose.IDisposer;
import com.tokelon.toktales.tools.core.objects.options.INamedOptions;

public class STBCodepointDecoder implements ICodepointAssetDecoder {


	@Override
	public ICodepointAsset decode(InputStream inputstream, ICodepointAssetKey key, INamedOptions options) throws AssetException {
		if(!(key.getFont() instanceof STBFont)) {
			throw new IllegalArgumentException("font must be of type: " + STBFont.class.getSimpleName());
		}
		STBFont font = (STBFont) key.getFont();
		int codepoint = key.getCodepoint();
		float fontPixelHeight = key.getFontPixelHeight();
		
		ICodepoint fontCodepoint = createCodepoint(font, codepoint, fontPixelHeight);
		return new CodepointAsset(fontCodepoint);
	}
	
	
	public ICodepoint createCodepoint(STBFont font, int codepoint, float fontPixelHeight) {
		STBTTFontinfo fontInfo = font.getFontInfo();
		float fontScale = STBTruetype.stbtt_ScaleForPixelHeight(fontInfo, fontPixelHeight);
		
		ICodepointTexture texture = makeCodepointTexture(fontInfo, codepoint, fontScale);
		IRectangle2i bitmapBox = makeCodepointBitmapBox(fontInfo, codepoint, fontScale);

		
		// Metrics
		int advanceWidth, leftSideBearing;
		try(MemoryStack stack = MemoryStack.stackPush()) {
			IntBuffer advanceWidthBuffer = stack.callocInt(1);
			IntBuffer leftSideBearingBuffer = stack.callocInt(1);
			
			STBTruetype.stbtt_GetCodepointHMetrics(fontInfo, codepoint, advanceWidthBuffer, leftSideBearingBuffer);
			
			advanceWidth = advanceWidthBuffer.get(0);
			leftSideBearing = leftSideBearingBuffer.get(0);
		}
		
		// Do these need to be multiplied by fontScale?
		int bitmapOffsetX = texture.getOriginOffsetX();
		int bitmapOffsetY = texture.getOriginOffsetY() + Math.round(font.getFontPixelAscent() * fontScale); // Add the font ascend so it counts from the top instead of the origin;
		
		// TODO: Check disposer
		IDisposer<STBCodepoint> disposer = (cp) -> STBTruetype.stbtt_FreeBitmap(cp.getTexture().getBitmap().getData()); // Needs user data?
		return new STBCodepoint(texture, bitmapBox, fontPixelHeight, advanceWidth, leftSideBearing, bitmapOffsetX, bitmapOffsetY, disposer);
	}
	
	
	private CodepointTexture makeCodepointTexture(STBTTFontinfo fontInfo, int codepoint, float fontScale) {
		//STBTruetype.stbtt_MakeCodepointBitmap(info, output, out_w, out_h, out_stride, scale_x, scale_y, codepoint)

		try(MemoryStack stack = MemoryStack.stackPush()) {
			IntBuffer widthBuffer = stack.callocInt(1);
			IntBuffer heightBuffer = stack.callocInt(1);
			IntBuffer xoffsetBuffer = stack.callocInt(1);
			IntBuffer yoffsetBuffer = stack.callocInt(1);
			
			ByteBuffer bitmap = STBTruetype.stbtt_GetCodepointBitmap(fontInfo, fontScale, fontScale, codepoint, widthBuffer, heightBuffer, xoffsetBuffer, yoffsetBuffer);
			
			CodepointTexture texture = CodepointTexture.create(bitmap, widthBuffer.get(0), heightBuffer.get(0), xoffsetBuffer.get(0), yoffsetBuffer.get(0));
			return texture;
		}
	}
	

	private IRectangle2i makeCodepointBitmapBox(STBTTFontinfo fontInfo, int codepoint, float fontScale) {
		// BitmapBox coordinates are calculated from the glyph origin
		// Glyph origin = fontAscend
		// bottom is yoffset
		// right is xoffset ?
		// top - bottom = height
		// right - left = width
		
		try(MemoryStack stack = MemoryStack.stackPush()) {
			IntBuffer left = stack.callocInt(1);
			IntBuffer top = stack.callocInt(1);
			IntBuffer right = stack.callocInt(1);
			IntBuffer bottom = stack.callocInt(1);
			
			STBTruetype.stbtt_GetCodepointBitmapBox(fontInfo, codepoint, fontScale, fontScale, left, bottom, right, top);
			
			return new Rectangle2iImpl().set(left.get(0), top.get(0), right.get(0), bottom.get(0));
		}
	}
	
}
