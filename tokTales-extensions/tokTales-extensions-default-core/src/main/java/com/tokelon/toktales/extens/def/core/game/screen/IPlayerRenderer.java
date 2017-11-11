package com.tokelon.toktales.extens.def.core.game.screen;

import com.tokelon.toktales.core.game.controller.IPlayerController;
import com.tokelon.toktales.core.game.screen.ISegmentRenderer;

public interface IPlayerRenderer extends ISegmentRenderer {

	
	public void drawPlayer(IPlayerController playerController);
	
}
