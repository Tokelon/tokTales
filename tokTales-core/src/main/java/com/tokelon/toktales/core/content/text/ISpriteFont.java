package com.tokelon.toktales.core.content.text;

import com.tokelon.toktales.core.content.sprite.ISprite;

public interface ISpriteFont extends IFont {
	
	
	/**
	 * 
	 * @param codepoint
	 * @return
	 * @throws IllegalArgumentException If codepoint < 0.
	 */
	public ISprite getSpriteForCodepoint(int codepoint);

	public int getCharacterCount();

	
}
