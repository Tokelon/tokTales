package com.tokelon.toktales.android.data;

import com.tokelon.toktales.core.content.text.ITextureFont;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

public class AndroidTextureFont implements ITextureFont {


	private final Paint paint;
	
	protected AndroidTextureFont(Typeface typeface, float fontTextSizePixels) {
		this.paint = new Paint();
		
		paint.setTypeface(typeface);
		paint.setTextSize(fontTextSizePixels);
		paint.setColor(Color.WHITE);
	}
	
	protected AndroidTextureFont(Paint paint) {
		this.paint = new Paint(paint);
	}
	
	
	public Typeface getTypeface() {
		return paint.getTypeface();
	}
	
	public Paint getPaint() {
		return paint;
	}
	
	public float getFontTextSizePixels() {
		return paint.getTextSize();
	}

	
	@Override
	public float getFontTextSize() {
		return paint.getTextSize();
	}
	
	@Override
	public float setFontTextSize(float textSize) {
		float previousTextSize = paint.getTextSize();
		paint.setTextSize(textSize);
		return previousTextSize;
	}
	
	
	@Override
	public int getFontPixelAscent() {
		return (int) Math.ceil(paint.ascent());
	}

	@Override
	public int getFontPixelDescent() {
		return (int) Math.ceil(paint.descent());
	}

	@Override
	public int getFontPixelLineGap() {
		return (int) Math.ceil(paint.getFontSpacing());
	}
	
	
	@Override
	public void dispose() {
		// Nothing to do here
	}

}
