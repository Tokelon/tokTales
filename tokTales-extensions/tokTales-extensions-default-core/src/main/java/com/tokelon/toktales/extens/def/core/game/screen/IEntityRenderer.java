package com.tokelon.toktales.extens.def.core.game.screen;

import com.tokelon.toktales.core.game.screen.ISegmentRenderer;
import com.tokelon.toktales.core.game.world.IWorldspace;

public interface IEntityRenderer extends ISegmentRenderer {

	public void drawEntities(IWorldspace worldspace);

	//public void drawEntity(IEntity entity);
	
}
