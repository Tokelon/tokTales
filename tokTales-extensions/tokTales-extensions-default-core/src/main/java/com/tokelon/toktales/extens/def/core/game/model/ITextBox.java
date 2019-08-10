package com.tokelon.toktales.extens.def.core.game.model;

import com.tokelon.toktales.core.content.text.ITextureFont;
import com.tokelon.toktales.core.game.model.entity.IGameEntity;

public interface ITextBox {

	
	public IGameEntity getEntity();
	
	
	public void setText(String text);
	
	public void clear();
	
	//public void append(String str);
	//public void append(char c);

	public String getText();


	public void setTextSize(float worldSize);
	public float getTextSize();
	
	
	public float getSpaceWidth();
	
	public float getCharSpacing();
	
	public float getLineSpacing();
	
	

	// Keep those like this?
	public float getCharWidth(int codepointWidth, float fontPixelHeight);
	public float getCharHeight(int codepointWidth, float fontPixelHeight);
	
	
	// Maybe make font just IFont?
	public void setFont(ITextureFont spriteFont);
	public ITextureFont getFont();
	
	
	/**
	 * 
	 * @param index
	 * @return Returns the word at index, or the empty string if the index >= word count
	 */
	public String getWord(int index);
	
	public int getWordCount();

	
	public void setSpaceWidthModifier(float modifier);
	public void setCharSpacingModifier(float modifier);
	public void setLineSpacingModifier(float modifier);

	public float getSpaceWidthModifier();
	public float getCharSpacingModifier();
	public float getLineSpacingModifier();
	
	
}
