package com.tokelon.toktales.core.render;

import com.tokelon.toktales.core.content.graphics.IRGBAColor;
import com.tokelon.toktales.core.content.text.ITextureFont;

public interface ICharRenderer extends IChunkRenderer {
	// Maybe use ICharPaint object that contains the properties below and pass when drawing?


	public void drawChar(char ch);
	
	public void drawCodepoint(int codepoint);

	
	public void setFont(ITextureFont font);
	
	public void setColor(IRGBAColor color);
	
	public void setPosition(float wx, float wy);
	
	public void setSize(float wWidth, float wHeight);
	
}
