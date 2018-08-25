package com.tokelon.toktales.desktop.lwjgl.data;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;

import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBTTFontinfo;
import org.lwjgl.stb.STBTruetype;

import com.tokelon.toktales.core.content.IDisposable;
import com.tokelon.toktales.core.content.text.CodepointTexture;
import com.tokelon.toktales.core.content.text.ICodepointAsset;
import com.tokelon.toktales.core.content.text.ICodepointTexture;
import com.tokelon.toktales.core.content.text.ITextureFont;
import com.tokelon.toktales.core.game.model.IRectangle2i;
import com.tokelon.toktales.core.game.model.IRectangle2i.IMutableRectangle2i;
import com.tokelon.toktales.core.game.model.Rectangle2iImpl;
import com.tokelon.toktales.core.render.ITexture;

public class STBTextureFont implements ITextureFont {
	/* A few possible optimizations, improvements
	 * 
	 * - Use glyphs for improved performance
	 * - free bitmaps
	 * - use subpixel precision
	 * - implement kerning
	 * - Use native buffers (MemUtils)
	 * 
	 */
	
	
	// THIS IS IT!!! This is the cause of the font bug that caused crashes!
	// We have to keep a reference to this because STBTruetype only saves the pointer to it,
	// so if it gets garbage collected it will crash!
	// http://forum.lwjgl.org/index.php?topic=6241.msg33357#msg33357
	private ByteBuffer fontDataBuffer;

	
	private Map<Integer, CodepointInfo> codepointCache;
	
	private STBTTFontinfo fontInfo;
	
	private float fontScale;
	
	private final int fontPixelHeight;
	
	private int fontPixelAscent;
	private int fontPixelDescent;
	private int fontPixelLineGap;
	
	
	public STBTextureFont(int fontPixelHeight) {
		this.fontPixelHeight = fontPixelHeight;
		
		codepointCache = new HashMap<Integer, CodepointInfo>();
	}
	
	
	public void initializeFont(ByteBuffer fontData) {
		if(fontDataBuffer != null) {
			throw new IllegalStateException("Font was already initialized");
		}
		fontDataBuffer = fontData;

		
		fontInfo = STBTTFontinfo.create();
		
		boolean result = STBTruetype.stbtt_InitFont(fontInfo, fontData);
		
		if(!result) { // error
			throw new IllegalArgumentException("Failed to initialize font");
		}
		
		
		fontScale = STBTruetype.stbtt_ScaleForPixelHeight(fontInfo, fontPixelHeight);
		//fontScale = 0.1f;
		//fontScale = 1.0f;
		
	
		// Init font metrics
		IntBuffer ascentBuffer = BufferUtils.createIntBuffer(1);
		IntBuffer descentBuffer = BufferUtils.createIntBuffer(1);
		IntBuffer lineGapBuffer = BufferUtils.createIntBuffer(1);
		
		STBTruetype.stbtt_GetFontVMetrics(fontInfo, ascentBuffer, descentBuffer, lineGapBuffer);
		
		fontPixelAscent = Math.round(ascentBuffer.get(0) * fontScale);
		fontPixelDescent = Math.round(descentBuffer.get(0) * fontScale);
		fontPixelLineGap = Math.round(lineGapBuffer.get(0) * fontScale);
	}
	
	
	private CodepointInfo getCodepointInfo(int codepoint) {
		CodepointInfo ci = codepointCache.get(codepoint);
		if(ci == null) {
			ci = new CodepointInfo();
			codepointCache.put(codepoint, ci);
		}
		
		return ci;
	}

	
	public void clearCodepointCache() {
		for(CodepointInfo cp: codepointCache.values()) {
			STBTruetype.stbtt_FreeBitmap(cp.texture.getBitmap().getData()); // Needs user data?
		}
		
		codepointCache.clear();
	}
	
	public void free() {
		clearCodepointCache();
	}
	

	@Override
	public int getFontPixelHeight() {
		return fontPixelHeight;
	}
	
	@Override
	public int getFontPixelAscent() {
		return fontPixelAscent;
	}
	
	@Override
	public int getFontPixelDescent() {
		return fontPixelDescent;
	}
	
	@Override
	public int getFontPixelLineGap() {
		return fontPixelLineGap;
	}
	
	
	@Override
	public ICodepointAsset getCodepointAsset(int codepoint) {
		ICodepointTexture texture = makeCodepointTexture(codepoint);
		IRectangle2i bitmapBox = new Rectangle2iImpl().set(getCodepointBitmapBox(codepoint)); 
		
		int advanceWidth = getCodepointAdvanceWidth(codepoint);
		int leftSideBearing = getCodepointLeftSideBearing(codepoint);
		
		return new STBCodepointAsset(texture, bitmapBox, advanceWidth, leftSideBearing);
	}
	
	@Override
	public ITexture getTextureForCodepoint(int codepoint) {
		return getOrCreateCodepointTexture(codepoint);
	}
	
	
	private CodepointTexture getOrCreateCodepointTexture(int codepoint) {
		CodepointInfo cpInfo = getCodepointInfo(codepoint);
		if(cpInfo.texture != null) {
			return cpInfo.texture;
		}
		
		cpInfo.texture = makeCodepointTexture(codepoint);
		return cpInfo.texture;
	}

	
	private CodepointTexture makeCodepointTexture(int codepoint) {
		//STBTruetype.stbtt_MakeCodepointBitmap(info, output, out_w, out_h, out_stride, scale_x, scale_y, codepoint)

		IntBuffer width = BufferUtils.createIntBuffer(1);
		IntBuffer height = BufferUtils.createIntBuffer(1);
		IntBuffer xoffset = BufferUtils.createIntBuffer(1);
		IntBuffer yoffset = BufferUtils.createIntBuffer(1);

		ByteBuffer bitmap = STBTruetype.stbtt_GetCodepointBitmap(fontInfo, fontScale, fontScale, codepoint, width, height, xoffset, yoffset);

		CodepointTexture texture = CodepointTexture.create(bitmap, width.get(0), height.get(0), xoffset.get(0), yoffset.get(0));
		
		return texture;
	}
	

	@Override
	public IRectangle2i getCodepointBitmapBox(int codepoint) {
		CodepointInfo cpInfo = getCodepointInfo(codepoint);
		
		if(cpInfo.codepointBox == null) {
			cpInfo.codepointBox = new Rectangle2iImpl();
			makeCodepointBitmapBox(codepoint, cpInfo.codepointBox);
		}
		
		return cpInfo.codepointBox;
	}
	

	private void makeCodepointBitmapBox(int codepoint, IMutableRectangle2i boxResult) {
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
		
		boxResult.set(left.get(0), top.get(0), right.get(0), bottom.get(0));
	}
	
	
	
	@Override
	public int getCodepointPixelWidth(int codepoint) {
		return getCodepointBitmapBox(codepoint).width();
	}
	
	@Override
	public int getCodepointPixelHeight(int codepoint) {
		IRectangle2i rect = getCodepointBitmapBox(codepoint);
		return -rect.height(); // We count from top to bottom
	}
	

	@Override
	public int getCodepointBitmapOffsetX(int codepoint) {
		return getOrCreateCodepointTexture(codepoint).getOriginOffsetX();
	}
	
	@Override
	public int getCodepointBitmapOffsetY(int codepoint) {
		return getOrCreateCodepointTexture(codepoint).getOriginOffsetY() + getFontPixelAscent(); // Add the font ascend so it counts from the top instead of the origin
	}
	

	@Override
	public int getCodepointAdvanceWidth(int codepoint) {
		return getCodepointMetrics(codepoint).advanceWidth;
	}
	
	@Override
	public int getCodepointLeftSideBearing(int codepoint) {
		return getCodepointMetrics(codepoint).leftSideBearing;
	}
	
	
	private CodepointInfo getCodepointMetrics(int codepoint) {
		CodepointInfo ci = getCodepointInfo(codepoint);
		if(ci.advanceWidth != -1 && ci.leftSideBearing != -1) {
			return ci;
		}
		
		
		IntBuffer advanceWidthBuffer = BufferUtils.createIntBuffer(1);
		IntBuffer leftSideBearingBuffer = BufferUtils.createIntBuffer(1);	// Do something with this?
		
		STBTruetype.stbtt_GetCodepointHMetrics(fontInfo, codepoint, advanceWidthBuffer, leftSideBearingBuffer);
		
		
		ci.advanceWidth = advanceWidthBuffer.get(0);
		ci.leftSideBearing = leftSideBearingBuffer.get(0);
		
		return ci;
	}
	

	@Override
	public int getCodepointKernAdvance(int firstCodepoint, int secondCodepoint) {
		// Kerning does not seem to work? Always returns 0
		
		// Is the value scaled or unscaled?
		int kernValue = STBTruetype.stbtt_GetCodepointKernAdvance(fontInfo, firstCodepoint, secondCodepoint);
		return kernValue;
	}
	
	
	
	// Any of these useful ?
	
	private void getFontBoundingBox() {
		//STBTruetype.stbtt_GetFontBoundingBox(info, x0, y0, x1, y1)
	}
	
	/*
	 *  "If stbtt_FindGlyphIndex returns 0, then there that character codepoint is not defined in the font."
	 * 
	 */
	
	
	public class STBCodepointAsset implements ICodepointAsset, IDisposable {

		private final ICodepointTexture texture;
		private final IRectangle2i bitmapBox;
		private final int advanceWidth;
		private final int leftSideBearing;

		public STBCodepointAsset(ICodepointTexture texture, IRectangle2i bitmapBox, int advanceWidth, int leftSideBearing) {
			this.texture = texture;
			this.bitmapBox = bitmapBox;
			this.advanceWidth = advanceWidth;
			this.leftSideBearing = leftSideBearing;
		}
		
		
		@Override
		public ITexture getTexture() {
			return texture;
		}

		@Override
		public IRectangle2i getBitmapBox() {
			return bitmapBox;
		}

		@Override
		public int getPixelWidth() {
			return bitmapBox.width();
		}

		@Override
		public int getPixelHeight() {
			return -bitmapBox.height(); // We count from top to bottom
		}

		@Override
		public int getBitmapOffsetX() {
			return texture.getOriginOffsetX();
		}

		@Override
		public int getBitmapOffsetY() {
			return texture.getOriginOffsetY() + getFontPixelAscent(); // Add the font ascend so it counts from the top instead of the origin;
		}

		@Override
		public int getAdvanceWidth() {
			return advanceWidth;
		}

		@Override
		public int getLeftSideBearing() {
			return leftSideBearing;
		}
		
		@Override
		public void dispose() {
			STBTruetype.stbtt_FreeBitmap(texture.getBitmap().getData()); // Needs user data?
		}
	}

	
	private class CodepointInfo {
		
		private CodepointTexture texture = null;
		
		private IMutableRectangle2i codepointBox = null;
		
		private int advanceWidth = -1;
		private int leftSideBearing = -1;
	}
	
}
