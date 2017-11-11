package com.tokelon.toktales.android.data;

import java.util.HashMap;
import java.util.Map;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;

import com.tokelon.toktales.android.render.opengl.AndroidBitmapTexture;
import com.tokelon.toktales.core.content.text.ITextureFont;
import com.tokelon.toktales.core.game.model.IRectangle2i;
import com.tokelon.toktales.core.game.model.Rectangle2iImpl;
import com.tokelon.toktales.core.render.IRenderTexture;

public class AndroidTextureFont implements ITextureFont {

	
	private Map<Integer, CodepointInfo> codepointCache;
	
	private Paint paint;
	
	private int fontHeight;
	private int fontTop;
	private int fontBottom;
	private int fontAscent;
	private int fontDescent;
	private int fontLineGap;
	
	
	private boolean aaEnabled = false;
	
	
	private final float textSize;
	
	public AndroidTextureFont(float textSize) {
		this.textSize = textSize;
		
		codepointCache = new HashMap<Integer, CodepointInfo>();
	}
	
	
	public void setAntiAlias(boolean enabled) {
		aaEnabled = enabled;
	}
	
	
	public void initialize(Typeface typeface) {
		
		paint = new Paint();
		paint.setAntiAlias(aaEnabled);
		paint.setTextSize(textSize);
		paint.setColor(Color.WHITE);
		paint.setTypeface(typeface);
		
		
		Paint.FontMetrics fontMetrics = paint.getFontMetrics();
		fontAscent = (int)Math.ceil(fontMetrics.ascent);
		fontDescent = (int)Math.ceil(fontMetrics.descent);
		fontTop = (int)Math.ceil(fontMetrics.top);
		fontBottom = (int)Math.ceil(fontMetrics.bottom);
		

		/*
		Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
		fontAscent = fontMetrics.ascent;
		fontDescent = fontMetrics.descent;
		fontTop = fontMetrics.top;
		fontBottom = fontMetrics.bottom;
		*/
		//fontHeight = fontDescent - fontAscent + fontMetrics.leading;
		fontHeight = fontBottom - fontTop;
		
		fontLineGap = Math.round(paint.getFontSpacing());
		
		//paint.getTextWidths(text, widths)
		//paint.getTextBounds(text, index, count, bounds)
		//paint.
	}
	
	
	
	@Override
	public int getFontPixelHeight() {
		return fontHeight;
	}

	@Override
	public int getFontPixelAscent() {
		return fontAscent;
	}

	@Override
	public int getFontPixelDescent() {
		return fontDescent;
	}

	@Override
	public int getFontPixelLineGap() {
		return fontLineGap;
	}

	@Override
	public IRenderTexture getTextureForCodepoint(int codepoint) {
		return codepointTexture(codepoint);
	}
	
	
	private AndroidBitmapTexture codepointTexture(int codepoint) {
		CodepointInfo cpInfo = codepointInfo(codepoint);
		if(cpInfo.texture == null) {
			cpInfo.texture = makeCodepointTexture(codepoint);
		}
		
		return cpInfo.texture;
	}
	
	
	private AndroidBitmapTexture makeCodepointTexture(int codepoint) {

		int cWidth = getCodepointPixelWidth(codepoint);
		int cHeight = getCodepointPixelHeight(codepoint);
		
		int bWidth = cWidth == 0 ? 1 : cWidth;
		int bHeight = cHeight == 0 ? 1 : cHeight;
		
		
		Bitmap bitmap = Bitmap.createBitmap(bWidth, bHeight, Bitmap.Config.ALPHA_8);
		Canvas canvas = new Canvas(bitmap);		// TODO: Replace with setBitmap()
		
		
		int topOffset = bHeight - getCodepointBitmapBox(codepoint).bottom();	// This is needed for characters like p, g etc that go under the baseline
		
		String cs = new String(new int[] { codepoint }, 0, 1);
		canvas.drawText(cs, 0, topOffset, paint);
		
		AndroidBitmapTexture result = new AndroidBitmapTexture(bitmap);
		result.setUnpackAlignment(1);
		
		return result;
	}
	

	@Override
	public IRectangle2i getCodepointBitmapBox(int codepoint) {
		
		CodepointInfo cpInfo = codepointInfo(codepoint);
		if(cpInfo.bounds == null) {
			cpInfo.bounds = new Rectangle2iImpl();
			makeCodepointBitmapBox(codepoint, cpInfo.bounds);
		}
		
		return cpInfo.bounds;
	}
	
	
	private void makeCodepointBitmapBox(int codepoint, Rectangle2iImpl boundsResult) {
		String cs = new String(new int[] { codepoint }, 0, 1);
		Rect bounds = new Rect();
		paint.getTextBounds(cs, 0, 1, bounds);
		
		boundsResult.set(bounds.left, bounds.top, bounds.right, bounds.bottom);
	}
	

	@Override
	public int getCodepointPixelWidth(int codepoint) {
		IRectangle2i rect = getCodepointBitmapBox(codepoint);
		return Math.abs(rect.right() - rect.left());
	}

	@Override
	public int getCodepointPixelHeight(int codepoint) {
		
		IRectangle2i rect = getCodepointBitmapBox(codepoint);
		return Math.abs(rect.top() - rect.bottom());
	}

	@Override
	public int getCodepointBitmapOffsetX(int codepoint) {
		return getCodepointBitmapBox(codepoint).left();
	}

	@Override
	public int getCodepointBitmapOffsetY(int codepoint) {
		
		IRectangle2i bounds = getCodepointBitmapBox(codepoint);
		return Math.abs(fontTop) + getCodepointBitmapBox(codepoint).bottom() - getCodepointPixelHeight(codepoint);
		//return fontHeight - Math.abs(bounds.top);// - getCodepointBitmapBox(codepoint).bottom;
		//return 0;
		//return fontHeight - ((fontBottom - getCodepointBitmapBox(codepoint).bottom) + getCodepointPixelHeight(codepoint));
	}

	@Override
	public int getCodepointAdvanceWidth(int codepoint) {
		// TODO: Test this
		
		String cs = new String(new int[] { codepoint }, 0, 1);
		
		float[] widths = new float[1];
		paint.getTextWidths(cs, widths);
		
		return Math.round(widths[0]);
	}

	@Override
	public int getCodepointKernAdvance(int firstCodepoint, int secondCodepoint) {
		return 0;
	}

	
	private CodepointInfo codepointInfo(int codepoint) {
		CodepointInfo ci = codepointCache.get(codepoint);
		if(ci == null) {
			ci = new CodepointInfo();
			codepointCache.put(codepoint, ci);
		}
		
		return ci;
	}

	
	private class CodepointInfo {
		
		private AndroidBitmapTexture texture;
		private Rectangle2iImpl bounds;
	}

	
}
