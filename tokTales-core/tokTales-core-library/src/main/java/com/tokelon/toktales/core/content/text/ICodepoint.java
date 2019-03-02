package com.tokelon.toktales.core.content.text;

import com.tokelon.toktales.core.content.IDisposable;
import com.tokelon.toktales.core.game.model.IRectangle2i;
import com.tokelon.toktales.core.render.ITexture;

public interface ICodepoint extends IDisposable {


	//public int getCodepoint();
	
	public ITexture getTexture();
	public IRectangle2i getBitmapBox();
	
	public int getPixelWidth();
	public int getPixelHeight();
	public int getBitmapOffsetX();
	public int getBitmapOffsetY();
	
	public int getAdvanceWidth();
	public int getLeftSideBearing();

	

	//public int getFontPixelHeight();
	//public ITextureFont getFont();
	
}
