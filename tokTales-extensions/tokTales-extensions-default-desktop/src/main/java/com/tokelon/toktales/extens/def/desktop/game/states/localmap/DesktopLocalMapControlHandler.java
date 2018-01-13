package com.tokelon.toktales.extens.def.desktop.game.states.localmap;

import com.tokelon.toktales.extens.def.core.game.states.localmap.LocalMapControlHandler;
import com.tokelon.toktales.extens.def.core.game.states.localmap.LocalMapGamestate;

public class DesktopLocalMapControlHandler extends LocalMapControlHandler {

	public DesktopLocalMapControlHandler(LocalMapGamestate gamestate) {
		super(gamestate);
	}

	@Override
	public boolean handleConsoleToggle() {
		if(getConsoleController().isConsoleOpen()) {
			getConsoleController().backspace();		// TODO: Fix that we have to remove the last pressed char because there is not stopping key events
		}
		
		super.handleConsoleToggle();
		return true;
	}
	
	
}
