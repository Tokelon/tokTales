package com.tokelon.toktales.extens.def.core.game.model;

import com.tokelon.toktales.core.content.text.ISpriteFont;
import com.tokelon.toktales.core.game.model.entity.IGameEntity;

public interface ISprifoTextBox {

	
	public IGameEntity getEntity();
	
	
	public void setText(String text);
	
	public void clear();
	
	//public void append(String str);
	//public void append(char c);

	public String getText();
	
	public float getCharWidth();
	public float getCharHeight();
	
	
	public void setTextSize(int points);
	public int getTextSize();

	// Maybe make font just IFont?
	public void setFont(ISpriteFont spriteFont);
	public ISpriteFont getFont();
	
	
	/**
	 * 
	 * @param index
	 * @return Returns the word at index, or the empty string if the index >= word count
	 */
	public String getWord(int index);
	
	public int getWordCount();
	
	
	public void setWidthSpacingModifier(float widthModifier);
	public void setHeightSpacingModifier(float heightModifier);

	
	
}
