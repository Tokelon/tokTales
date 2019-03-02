package com.tokelon.toktales.android.data;

import java.util.HashMap;
import java.util.Map;

import com.tokelon.toktales.core.content.text.CodepointTexture;
import com.tokelon.toktales.core.content.text.ICodepointAsset;
import com.tokelon.toktales.core.content.text.ICodepointTexture;
import com.tokelon.toktales.core.content.text.ITextureFont;
import com.tokelon.toktales.core.game.model.IRectangle2i;
import com.tokelon.toktales.core.game.model.Rectangle2iImpl;
import com.tokelon.toktales.core.render.ITexture;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;

public class AndroidTextureFont implements ITextureFont {
	// TODO: Implement correct codepoint metrics
	
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

	
	private CodepointInfo getCodepointInfo(int codepoint) {
		CodepointInfo ci = codepointCache.get(codepoint);
		if(ci == null) {
			ci = new CodepointInfo();
			codepointCache.put(codepoint, ci);
		}
		
		return ci;
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
	public ICodepointAsset getCodepointAsset(int codepoint) {
		ICodepointTexture texture = getOrCreateCodepointTexture(codepoint);
		IRectangle2i bitmapBox = getCodepointBitmapBox(codepoint);
		
		return new AndroidCodepointAsset(texture, bitmapBox);
	}

	
	@Override
	public ITexture getCodepointTexture(int codepoint) {
		return getOrCreateCodepointTexture(codepoint);
	}
	
	
	private CodepointTexture getOrCreateCodepointTexture(int codepoint) {
		CodepointInfo cpInfo = getCodepointInfo(codepoint);
		if(cpInfo.texture == null) {
			cpInfo.texture = makeCodepointTexture(codepoint);
		}
		
		return cpInfo.texture;
	}
	
	
	private CodepointTexture makeCodepointTexture(int codepoint) {

		int cWidth = getCodepointPixelWidth(codepoint);
		int cHeight = getCodepointPixelHeight(codepoint);
		
		int bWidth = cWidth == 0 ? 1 : cWidth;
		int bHeight = cHeight == 0 ? 1 : cHeight;
		
		
		Bitmap bitmap = Bitmap.createBitmap(bWidth, bHeight, Bitmap.Config.ALPHA_8);
		Canvas canvas = new Canvas(bitmap);		// TODO: Replace with setBitmap()
		
		
		int topOffset = bHeight - getCodepointBitmapBox(codepoint).bottom();	// This is needed for characters like p, g etc that go under the baseline
		
		String cs = new String(new int[] { codepoint }, 0, 1);
		canvas.drawText(cs, 0, topOffset, paint);
		
		AndroidCodepointBitmap codepointBitmap = new AndroidCodepointBitmap(bitmap);
		CodepointTexture result = new CodepointTexture(codepointBitmap, 0, 0);
		
		return result;
	}
	

	@Override
	public IRectangle2i getCodepointBitmapBox(int codepoint) {
		
		CodepointInfo cpInfo = getCodepointInfo(codepoint);
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
		return getBitmapOffsetYFromBitmapBox(getCodepointBitmapBox(codepoint));
	}
	
	private int getBitmapOffsetYFromBitmapBox(IRectangle2i bitmapBox) {
		int codepointPixelHeight = Math.abs(bitmapBox.top() - bitmapBox.bottom());
		return Math.abs(fontTop) + bitmapBox.bottom() - codepointPixelHeight;
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

	@Override
	public int getCodepointLeftSideBearing(int codepoint) {
		return 0;
	}

	
	
	public class AndroidCodepointAsset implements ICodepointAsset {

		private final ICodepointTexture texture;
		private final IRectangle2i bitmapBox;

		public AndroidCodepointAsset(ICodepointTexture texture, IRectangle2i bitmapBox) {
			this.texture = texture;
			this.bitmapBox = bitmapBox;
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
			return Math.abs(bitmapBox.right() - bitmapBox.left());
		}

		@Override
		public int getPixelHeight() {
			return Math.abs(bitmapBox.top() - bitmapBox.bottom());
		}

		@Override
		public int getBitmapOffsetX() {
			return bitmapBox.left();
		}

		@Override
		public int getBitmapOffsetY() {
			return getBitmapOffsetYFromBitmapBox(bitmapBox);
		}

		@Override
		public int getAdvanceWidth() {
			// TODO: use logic from getCodepointAdvanceWidth
			return 0;
		}

		@Override
		public int getLeftSideBearing() {
			// TODO: Implement (see getCodepointAdvanceWidth())
			return 0;
		}
		
		@Override
		public void dispose() {
			texture.getBitmap().dispose();
		}
	}

	
	private class CodepointInfo {
		
		private CodepointTexture texture;
		private Rectangle2iImpl bounds;
	}

}
