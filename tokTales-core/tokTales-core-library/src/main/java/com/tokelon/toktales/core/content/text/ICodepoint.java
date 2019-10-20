package com.tokelon.toktales.core.content.text;

import com.tokelon.toktales.core.game.model.IRectangle2i;
import com.tokelon.toktales.core.render.ITexture;
import com.tokelon.toktales.tools.core.dispose.IDisposable;

public interface ICodepoint extends IDisposable {
	/* TODO: Add with scaling calculation?
	public int getFontScaledAscent();
	public int getFontScaledDescent();
	public int getFontScaledLineGap();
	public float getFontScale()
	*/
	
	
	public ITexture getTexture();
	public IRectangle2i getBitmapBox();

	public float getFontPixelHeight();
	
	public int getPixelWidth();
	public int getPixelHeight();
	// Change these two to float?
	public int getBitmapOffsetX();
	public int getBitmapOffsetY();
	
	public int getAdvanceWidth();
	public int getLeftSideBearing();
	
}
