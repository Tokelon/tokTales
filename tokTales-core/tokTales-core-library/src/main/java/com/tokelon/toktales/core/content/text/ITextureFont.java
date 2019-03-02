package com.tokelon.toktales.core.content.text;

import com.tokelon.toktales.core.game.model.IRectangle2i;
import com.tokelon.toktales.core.render.ITexture;

public interface ITextureFont extends IFont {
	// TODO: Implement flag for enable/disable caching?
	//public void setCachingEnabled(boolean enabled);
	//public boolean isCachingEnabled();
	//public void clearCache();
	
	
	public int getFontPixelHeight();
	public int getFontPixelAscent();
	public int getFontPixelDescent();
	public int getFontPixelLineGap();
	

	public ICodepoint getCodepoint(int codepoint);
	
	
	public ITexture getCodepointTexture(int codepoint);

	public IRectangle2i getCodepointBitmapBox(int codepoint);
	

	public int getCodepointPixelWidth(int codepoint);
	public int getCodepointPixelHeight(int codepoint);

	public int getCodepointBitmapOffsetX(int codepoint);
	public int getCodepointBitmapOffsetY(int codepoint);

	public int getCodepointAdvanceWidth(int codepoint);
	public int getCodepointLeftSideBearing(int codepoint);
	

	public int getCodepointKernAdvance(int firstCodepoint, int secondCodepoint);
	
	
	
	//public void buildFont(int fontPixelHeight);
	
	//public getPixelSizeForString(String str);
	
}
