package com.tokelon.toktales.extens.def.core.game.model;

import com.tokelon.toktales.core.content.text.ISpriteFont;
import com.tokelon.toktales.core.game.model.entity.GameEntity;
import com.tokelon.toktales.core.game.model.entity.IGameEntity;

public class SprifoTextBox implements ISprifoTextBox {

	public static final int DEFAULT_TEXT_SIZE_POINTS = 12;
	
	private static final float PT_TO_PX_MULT = 8.0f / 6.0f;
	
	private static final String WORD_SPLIT_REGEX = " ";
	
	
	
	private final GameEntity mEntity;
	
	private int mTextSize = DEFAULT_TEXT_SIZE_POINTS;
	
	private float mWidthSpacingMod = 1.0f;
	private float mHeightSpacingMod = 1.0f;

	
	private String mText = "";
	private String[] mWords = new String[0];
	
	private ISpriteFont mFont;
	
	public SprifoTextBox(float worldWidth, float worldHeight, int textSize) {
		
		mEntity = new GameEntity(this);
		
		mEntity.setSize(worldWidth, worldHeight);
		
		mTextSize = textSize;
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
	public float getCharWidth() {
		return convertPointsToCoords(mTextSize) * mWidthSpacingMod;
	}

	@Override
	public float getCharHeight() {
		return convertPointsToCoords(mTextSize) * mHeightSpacingMod;
	}


	@Override
	public void setTextSize(int points) {
		this.mTextSize = points;
	}

	@Override
	public int getTextSize() {
		return mTextSize;
	}

	
	@Override
	public void setFont(ISpriteFont spriteFont) {
		mFont = spriteFont;
	}
	
	@Override
	public ISpriteFont getFont() {
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
	public void setWidthSpacingModifier(float widthModifier) {
		this.mWidthSpacingMod = widthModifier;
	}
	
	@Override
	public void setHeightSpacingModifier(float heightModifier) {
		this.mHeightSpacingMod = heightModifier;
	}
	
	
	private static int convertPointsToCoords(int points) {
		return Math.round((float)points * PT_TO_PX_MULT);
	}
	
	private static int convertCoordsToPoints(int pixels) {
		return Math.round((float)pixels / PT_TO_PX_MULT);
	}
	
	
}
