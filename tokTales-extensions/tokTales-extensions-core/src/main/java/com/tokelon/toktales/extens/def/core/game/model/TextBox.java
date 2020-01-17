package com.tokelon.toktales.extens.def.core.game.model;

import com.tokelon.toktales.core.content.text.ITextureFont;
import com.tokelon.toktales.core.game.model.entity.GameEntity;
import com.tokelon.toktales.core.game.model.entity.IGameEntity;

public class TextBox implements ITextBox {


	private static final String WORD_SPLIT_REGEX = " ";
	
	
	private final GameEntity mEntity;
	
	
	private float mSpaceWidthMod = 0.3f; //0.33333f;
	private float mCharSpacingMod = 0.03f;
	private float mLineSpacingMod = 0.0f;

	
	private float mTextSize;
	
	private float mSpaceWidth;
	private float mCharSpacing;
	private float mLineSpacing;
	
	private String mText = "";
	private String[] mWords = new String[0];
	
	private ITextureFont mFont;
	
	public TextBox(float worldWidth, float worldHeight, float textSize) {
		
		mEntity = new GameEntity(this);
		mEntity.setVisible(false);
		
		mEntity.setSize(worldWidth, worldHeight);
		
		setTextSize(textSize);
	}
	
	
	@Override
	public IGameEntity getEntity() {
		return mEntity;
	}
	
	
	@Override
	public void setText(String text) {
		this.mText = text;
		mWords = text.split(WORD_SPLIT_REGEX);
	}

	@Override
	public void clear() {
		this.mText = "";
	}


	@Override
	public String getText() {
		return mText;
	}
	
	
	@Override
	public void setTextSize(float worldSize) {
		this.mTextSize = worldSize;
		
		mCharSpacing = worldSize;
		mLineSpacing = worldSize;
		mSpaceWidth = worldSize;
	}
	
	
	@Override
	public float getTextSize() {
		return mTextSize;
	}
	
	
	@Override
	public float getSpaceWidth() {
		return mSpaceWidth * mSpaceWidthMod;
	}
	
	@Override
	public float getCharSpacing() {
		return mCharSpacing * mCharSpacingMod;
	}
	
	@Override
	public float getLineSpacing() {
		return mLineSpacing * mLineSpacingMod;
	}

	

	@Override
	public float getCharWidth(int codepointWidth, float fontPixelHeight) {
		float worldWidth = (float) codepointWidth * mTextSize / fontPixelHeight;
		return worldWidth;
	}
	
	@Override
	public float getCharHeight(int codepointHeight, float fontPixelHeight) {
		float worldHeight = (float) codepointHeight * mTextSize / fontPixelHeight;
		return worldHeight;
	}
	
	@Override
	public void setFont(ITextureFont spriteFont) {
		mFont = spriteFont;
	}
	
	@Override
	public ITextureFont getFont() {
		return mFont;
	}
	
	
	
	@Override
	public String getWord(int index) {
		if(index >= mWords.length) {
			return "";
		}
		else {
			return mWords[index];
		}
	}
	
	@Override
	public int getWordCount() {
		return mWords.length;
	}

		
	@Override
	public void setSpaceWidthModifier(float modifier) {
		this.mSpaceWidthMod = modifier;
	}

	@Override
	public void setCharSpacingModifier(float modifier) {
		this.mCharSpacingMod = modifier;		
	}

	@Override
	public void setLineSpacingModifier(float modifier) {
		this.mLineSpacingMod = modifier;		
	}


	@Override
	public float getSpaceWidthModifier() {
		return mSpaceWidthMod;
	}
	
	@Override
	public float getCharSpacingModifier() {
		return mCharSpacingMod;
	}

	@Override
	public float getLineSpacingModifier() {
		return mLineSpacingMod;
	}
	
}
