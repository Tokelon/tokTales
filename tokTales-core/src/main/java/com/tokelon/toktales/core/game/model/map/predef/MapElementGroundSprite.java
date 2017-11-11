package com.tokelon.toktales.core.game.model.map.predef;

import com.tokelon.toktales.core.content.sprite.ISprite;
import com.tokelon.toktales.core.game.graphic.GameGraphicTypes;
import com.tokelon.toktales.core.game.graphic.IBaseGraphic;
import com.tokelon.toktales.core.game.graphic.ISpriteGraphic;
import com.tokelon.toktales.core.game.model.map.elements.MapElementTypes;
import com.tokelon.toktales.core.game.model.map.elements.AbstractMapElement.AbstractMutableMapElement;

public class MapElementGroundSprite extends AbstractMutableMapElement implements IGroundElement, ISpriteGraphic {

	private static final IElementType ELEMENT_TYPE = MapElementTypes.TYPE_GROUND;
	private static final IGraphicType GRAPHIC_TYPE = GameGraphicTypes.TYPE_SPRITE;
	
	private final ISprite sprite;
	
	
	public MapElementGroundSprite(long id, ISprite sprite) {
		super(id, ELEMENT_TYPE);
		
		this.sprite = sprite;
	}
	
	
	@Override
	public IBaseGraphic getGraphic() {
		return this;
	}

	@Override
	public IGraphicType getGraphicType() {
		return GRAPHIC_TYPE;
	}

	@Override
	public ISprite getSprite() {
		return sprite;
	}

}
