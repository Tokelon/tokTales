package com.tokelon.toktales.extens.def.core.game.screen;

import com.tokelon.toktales.core.game.controller.IConsoleController;
import com.tokelon.toktales.core.game.screen.ISegmentRenderer;

public interface IConsoleOverlayRenderer extends ISegmentRenderer {

	//public ICamera getCustomCamera();
	//public void enableCustomCamera(boolean enable);
	
	public void drawConsoleOverlay(IConsoleController consoleController);
	
	
}
