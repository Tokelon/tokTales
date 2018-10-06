package com.tokelon.toktales.core.game.graphic;

import com.tokelon.toktales.core.game.graphic.IBaseGraphic.IGraphicType;
import com.tokelon.toktales.core.values.MapGraphicValues;

public final class GameGraphicTypes {

	private GameGraphicTypes() {}
	
	
	public static final IGraphicType TYPE_COLOR = new GraphicTypeImpl(MapGraphicValues.MAP_GRAPHIC_TYPE_ID_COLOR);
	public static final IGraphicType TYPE_SPRITE = new GraphicTypeImpl(MapGraphicValues.MAP_GRAPHIC_TYPE_ID_SPRITERES);
	
}
