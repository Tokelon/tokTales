package com.tokelon.toktales.extens.def.core.game.screen;

import com.tokelon.toktales.core.game.controller.IPlayerController;
import com.tokelon.toktales.core.game.screen.ISegmentRenderer;
import com.tokelon.toktales.core.game.world.IWorldspace;

public interface IDebugRenderer extends ISegmentRenderer {

	
	public void drawDebug(IPlayerController playerController, IWorldspace worldspace);
	
	
	public void drawGrid();
	
	public void drawCameraOrigin();
	
	public void drawPlayerCollisionBox(IPlayerController playerController);
	
	public void drawEntitiesCollisionBoxes(IWorldspace worldspace);
	
}
