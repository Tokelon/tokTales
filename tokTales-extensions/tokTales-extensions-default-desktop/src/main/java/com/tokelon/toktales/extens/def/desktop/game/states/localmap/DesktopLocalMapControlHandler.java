package com.tokelon.toktales.extens.def.desktop.game.states.localmap;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.tokelon.toktales.extens.def.core.game.states.localmap.ILocalMapGamestate;
import com.tokelon.toktales.extens.def.core.game.states.localmap.LocalMapControlHandler;

public class DesktopLocalMapControlHandler extends LocalMapControlHandler {

	
	@Inject
	public DesktopLocalMapControlHandler(@Assisted ILocalMapGamestate gamestate) {
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
