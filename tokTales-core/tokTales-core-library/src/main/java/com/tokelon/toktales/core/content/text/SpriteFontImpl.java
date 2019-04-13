package com.tokelon.toktales.core.content.text;

import com.tokelon.toktales.core.content.sprite.ISprite;
import com.tokelon.toktales.core.content.sprite.ISpriteset;
import com.tokelon.toktales.core.content.sprite.SpritesetSprite;
import com.tokelon.toktales.core.prog.annotation.Experimental;

@Experimental
public class SpriteFontImpl implements ISpriteFont {

	
	private SpritesetSprite[] spriteArray;
	
	private final ISpriteset mSpriteset;
	
	
	public SpriteFontImpl(ISpriteset fontSpriteset) {
		this.mSpriteset = fontSpriteset;
		
		initSprites(fontSpriteset);
	}
	
	private void initSprites(ISpriteset fontSpriteset) {
		int spriteCount = fontSpriteset.getSpriteCount();
		
		spriteArray = new SpritesetSprite[spriteCount];
		
		for(int i = 0; i < spriteCount; i++) {
			spriteArray[i] = new SpritesetSprite(i, fontSpriteset);
		}
	}
	
	
	@Override
	public int getCharacterCount() {
		return mSpriteset.getSpriteCount();
	}

	
	@Override
	public ISprite getSpriteForCodepoint(int codepoint) {
		if(codepoint < 0) {
			throw new IllegalArgumentException("codepoint must be >= 0");
		}
		
		if(codepoint >= getCharacterCount()) {
			// Return null or an error sprite
			return null;
		}
		
		
		SpritesetSprite sprite = spriteArray[codepoint];
		return sprite;
	}

	
	@Override
	public void dispose() {
	}

}
