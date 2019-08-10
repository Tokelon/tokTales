package com.tokelon.toktales.android.data;

import java.io.InputStream;

import com.tokelon.toktales.core.content.manage.codepoint.CodepointAsset;
import com.tokelon.toktales.core.content.manage.codepoint.ICodepointAsset;
import com.tokelon.toktales.core.content.manage.codepoint.ICodepointAssetDecoder;
import com.tokelon.toktales.core.content.manage.codepoint.ICodepointAssetKey;
import com.tokelon.toktales.core.content.text.CodepointTexture;
import com.tokelon.toktales.core.content.text.ICodepoint;
import com.tokelon.toktales.core.engine.content.ContentException;
import com.tokelon.toktales.core.game.model.IRectangle2i;
import com.tokelon.toktales.core.game.model.Rectangle2iImpl;
import com.tokelon.toktales.core.util.options.INamedOptions;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class AndroidCodepointDecoder implements ICodepointAssetDecoder {
	// TODO: Implement correct codepoint metrics

	public static final String OPTION_CODEPOINT_AA_ENABLED = "Option_Font_AA_Enabled";
	public static final String OPTION_CODEPOINT_PAINT = "Option_Codepoint_Paint";

	public static final boolean DEFAULT_CODEPOINT_AA_ENABLED = false;
	

	@Override
	public ICodepointAsset decode(InputStream inputstream, ICodepointAssetKey key, INamedOptions options) throws ContentException {
		if(!(key.getFont() instanceof AndroidTextureFont)) {
			throw new IllegalArgumentException("font must be of type: " + AndroidTextureFont.class.getSimpleName());
		}
		AndroidTextureFont textureFont = (AndroidTextureFont) key.getFont();
		int codepoint = key.getCodepoint();
		float fontPixelHeight = key.getFontPixelHeight();
		
		ICodepoint fontCodepoint = createCodepoint(textureFont, codepoint, fontPixelHeight, options);
		return new CodepointAsset(fontCodepoint);
	}
	
	
	public ICodepoint createCodepoint(AndroidTextureFont textureFont, int codepoint, float fontPixelHeight, INamedOptions options) {
		Paint codepointPaint = makePaint(textureFont, fontPixelHeight, options);
		return createCodepointWithPaint(codepointPaint, codepoint, fontPixelHeight, options);
	}
	
	public ICodepoint createCodepointWithPaint(Paint codepointPaint, int codepoint, float fontPixelHeight, INamedOptions options) {
		IRectangle2i codepointBitmapBox = makeCodepointBitmapBox(codepoint, codepointPaint);
		CodepointTexture codepointTexture = makeCodepointTexture(codepoint, codepointPaint, codepointBitmapBox);
		
		int codepointAdvanceWidth = getCodepointAdvanceWidth(codepoint, codepointPaint);
		int codepointLeftSideBearing = 0; // TODO: Implement like getCodepointAdvanceWidth -> Not sure if Android equivalent exists
		int codepointBitmapOffsetX = codepointBitmapBox.left();
		int codepointBitmapOffsetY = getBitmapOffsetYFromBitmapBox(codepointBitmapBox, codepointPaint.getFontMetricsInt().top);
		
		
		return new AndroidCodepoint(codepointTexture, codepointBitmapBox, fontPixelHeight, codepointAdvanceWidth, codepointLeftSideBearing, codepointBitmapOffsetX, codepointBitmapOffsetY);
	}

	
	private Paint makePaint(AndroidTextureFont textureFont, float fontPixelHeight, INamedOptions options) {
		Paint paint;
		Paint optionPaint = options == null ? null : options.getAs(OPTION_CODEPOINT_PAINT, Paint.class);
		if(optionPaint == null) {
			paint = new Paint(textureFont.getPaint());
			
			boolean aaEnabled = options == null ? DEFAULT_CODEPOINT_AA_ENABLED : options.getAsOrDefault(OPTION_CODEPOINT_AA_ENABLED, DEFAULT_CODEPOINT_AA_ENABLED, Boolean.class);	
			paint.setAntiAlias(aaEnabled);
		}
		else {
			paint = new Paint(optionPaint);
			paint.setTypeface(textureFont.getTypeface());
		}
		
		paint.setTextSize(fontPixelHeight);
		return paint;
	}

	private CodepointTexture makeCodepointTexture(int codepoint, Paint paint, IRectangle2i codepointBitmapBox) {
		int cWidth = Math.abs(codepointBitmapBox.right() - codepointBitmapBox.left());
		int cHeight = Math.abs(codepointBitmapBox.top() - codepointBitmapBox.bottom());
		
		int bWidth = cWidth == 0 ? 1 : cWidth;
		int bHeight = cHeight == 0 ? 1 : cHeight;
		
		
		Bitmap bitmap = Bitmap.createBitmap(bWidth, bHeight, Bitmap.Config.ALPHA_8);
		Canvas canvas = new Canvas(bitmap);
		
		int topOffset = bHeight - codepointBitmapBox.bottom();	// This is needed for characters like p, g etc that go under the baseline
		
		String cs = new String(new int[] { codepoint }, 0, 1);
		canvas.drawText(cs, 0, topOffset, paint);
		
		AndroidCodepointBitmap codepointBitmap = new AndroidCodepointBitmap(bitmap);
		CodepointTexture result = new CodepointTexture(codepointBitmap, 0, 0);
		
		return result;
	}

	private IRectangle2i makeCodepointBitmapBox(int codepoint, Paint paint) {
		String cs = new String(new int[] { codepoint }, 0, 1); // TODO: Improve this if possible
		Rect bounds = new Rect();
		paint.getTextBounds(cs, 0, 1, bounds);
		
		return new Rectangle2iImpl().set(bounds.left, bounds.top, bounds.right, bounds.bottom);
	}
	

	private int getBitmapOffsetYFromBitmapBox(IRectangle2i bitmapBox, int fontTop) {
		int codepointPixelHeight = Math.abs(bitmapBox.top() - bitmapBox.bottom());
		return Math.abs(fontTop) + bitmapBox.bottom() - codepointPixelHeight;
		//return fontHeight - Math.abs(bounds.top);// - getCodepointBitmapBox(codepoint).bottom;
		//return 0;
		//return fontHeight - ((fontBottom - getCodepointBitmapBox(codepoint).bottom) + getCodepointPixelHeight(codepoint));
	}

	public int getCodepointAdvanceWidth(int codepoint, Paint paint) {
		// TODO: Test this
		
		String cs = new String(new int[] { codepoint }, 0, 1);
		
		float[] widths = new float[1];
		paint.getTextWidths(cs, widths);
		
		return Math.round(widths[0]);
	}
	
}
