package com.tokelon.toktales.extens.def.core.game.screen;

import com.tokelon.toktales.core.game.screen.ISegmentRenderer;
import com.tokelon.toktales.extens.def.core.game.controller.IDialogController;

public interface IDialogRenderer extends ISegmentRenderer {
	
	public void drawDialog(IDialogController controller);
	//public void drawDialog(IDialogController controller, String layer);
	
}
