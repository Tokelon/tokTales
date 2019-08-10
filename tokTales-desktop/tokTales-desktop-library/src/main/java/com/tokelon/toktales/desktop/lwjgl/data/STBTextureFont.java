package com.tokelon.toktales.desktop.lwjgl.data;

import java.nio.ByteBuffer;

import org.lwjgl.stb.STBTTFontinfo;
import org.lwjgl.stb.STBTruetype;

import com.tokelon.toktales.core.content.IDisposer;
import com.tokelon.toktales.core.content.text.ITextureFont;

public class STBTextureFont implements ITextureFont {
	/* Possible optimizations, improvements
	 * 
	 * - Use glyphs for improved performance
	 * - use subpixel precision
	 * - implement kerning
	 * 
	 */
	
	
	private boolean disposed = false;
	
	private float fontTextSize;
	
	// We have to keep a reference to this because STBTruetype only saves the pointer to it, so if it gets garbage collected it will crash.
	// http://forum.lwjgl.org/index.php?topic=6241.msg33357#msg33357
	private ByteBuffer fontDataBuffer;
	private final STBTTFontinfo fontInfo;
	private final int fontPixelAscent;
	private final int fontPixelDescent;
	private final int fontPixelLineGap;
	private final IDisposer<STBTextureFont> disposer;
	
	public STBTextureFont(ByteBuffer data, STBTTFontinfo fontInfo, int ascent, int descent, int lineGap, IDisposer<STBTextureFont> disposer) {
		this.fontDataBuffer = data;
		this.fontInfo = fontInfo;
		this.fontPixelAscent = ascent;
		this.fontPixelDescent = descent;
		this.fontPixelLineGap = lineGap;
		this.disposer = disposer;
	}
	
	
	public ByteBuffer getData() {
		return fontDataBuffer;
	}
	
	public STBTTFontinfo getFontInfo() {
		return fontInfo;
	}
	
	
	@Override
	public float getFontTextSize() {
		return fontTextSize;
	}
	
	@Override
	public float setFontTextSize(float textSize) {
		float previousTextSize = fontTextSize;
		fontTextSize = textSize;
		return previousTextSize;
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
	public void dispose() {
		if(!disposed) {
			disposed = true;
			
			disposer.dispose(this);
			
			fontDataBuffer = null;
		}
	}
	
	
	
	// Any of these useful ?

	private int getCodepointKernAdvance(int firstCodepoint, int secondCodepoint) {
		// Kerning does not seem to work? Always returns 0 -> I think that was something with how I used it, test again
		
		// Is the value scaled or unscaled?
		int kernValue = STBTruetype.stbtt_GetCodepointKernAdvance(fontInfo, firstCodepoint, secondCodepoint);
		return kernValue;
	}
	
	
	private void getFontBoundingBox() {
		//STBTruetype.stbtt_GetFontBoundingBox(info, x0, y0, x1, y1)
	}
	
	/*
	 *  "If stbtt_FindGlyphIndex returns 0, then there that character codepoint is not defined in the font."
	 * 
	 */
	
}
