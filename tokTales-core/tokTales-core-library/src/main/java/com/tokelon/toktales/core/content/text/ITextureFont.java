package com.tokelon.toktales.core.content.text;

public interface ITextureFont extends IFont {
	// TODO: Add?
	//public int getCodepointKernAdvance(int firstCodepoint, int secondCodepoint);

	
	/**
	 * @return The current text size, in pixels.
	 */
	public float getFontTextSize();
	
	/** Sets the current text size to the given value.
	 * 
	 * @param textSize The text size, in pixels.
	 * @return The previous value.
	 */
	public float setFontTextSize(float textSize);

	
	public int getFontPixelAscent();
	public int getFontPixelDescent();
	public int getFontPixelLineGap();
	
}
