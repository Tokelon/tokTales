package com.tokelon.toktales.extens.def.android.states.localmap;

import com.tokelon.toktales.core.engine.TokTales;
import com.tokelon.toktales.core.engine.ui.IUIConsoleExtension;
import com.tokelon.toktales.extens.def.core.game.states.localmap.LocalMapControlHandler;
import com.tokelon.toktales.extens.def.core.game.states.localmap.LocalMapGamestate;

public class AndroidLocalMapControlHandler extends LocalMapControlHandler {

	
	public AndroidLocalMapControlHandler(LocalMapGamestate gamestate) {
		super(gamestate);
	}
	
	@Override
	public boolean handleConsoleToggle() {
		super.handleConsoleToggle();

		if(isConsoleOpen()) {
			IUIConsoleExtension cons = TokTales.getEngine().getUIService().getExtensionAs("console", IUIConsoleExtension.class);
			cons.openConsoleInput(getConsoleController());
		}
		
		return true;
	}
	

}
