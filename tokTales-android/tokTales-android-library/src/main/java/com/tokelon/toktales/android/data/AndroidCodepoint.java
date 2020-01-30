package com.tokelon.toktales.android.data;

import com.tokelon.toktales.core.content.text.ICodepoint;
import com.tokelon.toktales.core.content.text.ICodepointTexture;
import com.tokelon.toktales.core.game.model.IRectangle2i;
import com.tokelon.toktales.core.render.texture.ITexture;

public class AndroidCodepoint implements ICodepoint {


	private boolean disposed = false;

	private ICodepointTexture texture;
	private final IRectangle2i bitmapBox;
	private final float fontPixelHeight;
	private final int advanceWidth;
	private final int leftSideBearing;
	private final int bitmapOffsetX;
	private final int bitmapOffsetY;

	public AndroidCodepoint(ICodepointTexture texture, IRectangle2i bitmapBox, float fontPixelHeight, int advanceWidth, int leftSideBearing, int bitmapOffsetX, int bitmapOffsetY) {
		this.texture = texture;
		this.bitmapBox = bitmapBox;
		this.fontPixelHeight = fontPixelHeight;
		this.advanceWidth = advanceWidth;
		this.leftSideBearing = leftSideBearing;
		this.bitmapOffsetX = bitmapOffsetX;
		this.bitmapOffsetY = bitmapOffsetY;
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
	public float getFontPixelHeight() {
		return fontPixelHeight;
	}

	@Override
	public int getPixelWidth() {
		return bitmapBox.width();
	}

	@Override
	public int getPixelHeight() {
		return bitmapBox.height(); // We count from bottom to top
	}

	@Override
	public int getBitmapOffsetX() {
		return bitmapOffsetX;
	}

	@Override
	public int getBitmapOffsetY() {
		return bitmapOffsetY;
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
		if(!disposed) {
			disposed = true;
			
			texture.getBitmap().dispose();
			
			this.texture = null;
		}
	}

}
