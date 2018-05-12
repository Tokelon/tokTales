package com.tokelon.toktales.core.content.text;

import com.tokelon.toktales.core.game.model.IRectangle2i;
import com.tokelon.toktales.core.render.ITexture;

public interface ITextureFont extends IFont {

	
	//public void buildFont(int fontPixelHeight);
	

	public int getFontPixelHeight();

	public int getFontPixelAscent();

	public int getFontPixelDescent();
	
	public int getFontPixelLineGap();
	
	
	public ITexture getTextureForCodepoint(int codepoint);

	public IRectangle2i getCodepointBitmapBox(int codepoint);
	

	public int getCodepointPixelWidth(int codepoint);
	
	public int getCodepointPixelHeight(int codepoint);


	public int getCodepointBitmapOffsetX(int codepoint);

	public int getCodepointBitmapOffsetY(int codepoint);

	public int getCodepointAdvanceWidth(int codepoint);
	

	public int getCodepointKernAdvance(int firstCodepoint, int secondCodepoint);
	
	
	//public getPixelSizeForString(String str);
	
	
}
