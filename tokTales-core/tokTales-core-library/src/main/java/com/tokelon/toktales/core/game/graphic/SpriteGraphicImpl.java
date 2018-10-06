package com.tokelon.toktales.core.game.graphic;

import com.tokelon.toktales.core.content.sprite.ISprite;

public class SpriteGraphicImpl extends AbstractGraphic implements ISpriteGraphic {

	private static final IGraphicType GRAPHIC_TYPE = GameGraphicTypes.TYPE_SPRITE;
	
	private final ISprite sprite;
	
	
	public SpriteGraphicImpl(ISprite sprite) {
		super(GRAPHIC_TYPE);
		
		this.sprite = sprite;
	}
	
	@Override
	public ISprite getSprite() {
		return sprite;
	}

}
