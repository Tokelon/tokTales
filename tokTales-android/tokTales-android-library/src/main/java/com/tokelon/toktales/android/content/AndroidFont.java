package com.tokelon.toktales.android.content;

import com.tokelon.toktales.core.content.text.IFont;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

public class AndroidFont implements IFont {


	private final Paint paint;
	
	protected AndroidFont(Typeface typeface, float fontTextSize) {
		this.paint = new Paint();
		
		paint.setTypeface(typeface);
		paint.setTextSize(fontTextSize);
		paint.setColor(Color.WHITE);
	}
	
	protected AndroidFont(Paint paint) {
		this.paint = new Paint(paint);
	}
	
	
	public Typeface getTypeface() {
		return paint.getTypeface();
	}
	
	public Paint getPaint() {
		return paint;
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
