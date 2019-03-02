package com.tokelon.toktales.core.render;

import com.tokelon.toktales.core.content.graphics.IRGBAColor;
import com.tokelon.toktales.core.content.text.ITextureFont;

public interface ICharRenderer extends IChunkRenderer {

	
	public void drawChar(char ch);
	
	//public void drawChar(int codepoint);

	
	// Maybe use a TPaint object that has these values ?
	//public void drawChar(char ch, TPaint paint);
	
	
	public void setFont(ITextureFont font);
	
	public void setColor(IRGBAColor color);
	
	public void setPosition(float wx, float wy);
	
	public void setSize(float wWidth, float wHeight);
	
	
}
