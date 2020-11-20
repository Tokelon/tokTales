package com.tokelon.toktales.core.render.renderer;

import com.tokelon.toktales.core.content.graphics.IRGBAColor;
import com.tokelon.toktales.core.content.text.IFont;

public interface ICharRenderer extends IBatchRenderer {
	// Maybe use ICharPaint object that contains the properties below and pass when drawing?


	/** Draws the given char at the current position.
	 * 
	 * @param ch
	 * @return The world width that the char was drawn at, or 0 if nothing was drawn.
	 */
	public float drawChar(char ch);
	
	/** Draws the given codepoint at the current position.
	 * 
	 * @param codepoint
	 * @return The world width that the codepoint was drawn at, or 0 if nothing was drawn.
	 */
	public float drawCodepoint(int codepoint);

	
	public void setFont(IFont font);
	
	public void setColor(IRGBAColor color);
	
	public void setPosition(float wx, float wy);
	
	public void setSize(float wWidth, float wHeight);
	
}
