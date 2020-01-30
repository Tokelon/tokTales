package com.tokelon.toktales.core.render;

import com.tokelon.toktales.core.content.text.ITextureFont;

public class FontTextSizeHelper {
	// TODO: Convert to instance class and inject?


	private FontTextSizeHelper() {}
	
	
	public static float getBestFontPixelHeight(ITextureFont font, float targetTextSizePixels) {
		float result;
		if(font.getFontTextSize() > 0) {
			result = font.getFontTextSize();
		}
		else if(targetTextSizePixels <= 32) {
			result = 32;
		}
		else if(targetTextSizePixels <= 64) {
			result = 64;
		}
		else if(targetTextSizePixels <= 128) {
			result = 128;
		}
		else {
			result = 256;
		}
		
		return result;
	}
	
}
