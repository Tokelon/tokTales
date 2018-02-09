package com.tokelon.toktales.extens.def.android.states.localmap;

import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.engine.ui.IConsoleUIExtension;
import com.tokelon.toktales.core.engine.ui.IUIService;
import com.tokelon.toktales.extens.def.core.game.states.localmap.LocalMapControlHandler;
import com.tokelon.toktales.extens.def.core.game.states.localmap.LocalMapGamestate;

public class AndroidLocalMapControlHandler extends LocalMapControlHandler {

	
	private final LocalMapGamestate localMapGamestate;
	
	public AndroidLocalMapControlHandler(LocalMapGamestate gamestate) {
		super(gamestate);
		
		this.localMapGamestate = gamestate;
	}
	
	
	@Override
	public boolean handleConsoleToggle() {
		super.handleConsoleToggle();

		if(isConsoleOpen()) {
			IUIService uiService = localMapGamestate.getEngine().getUIService();
			
			try {
				uiService.getExtensionByTypeOrFail(IConsoleUIExtension.class).openConsoleInput(getConsoleController());
			} catch (EngineException e) {
				localMapGamestate.getLog().e(TAG, "Error opening console input: " + e.getMessage());
			}
		}
		
		return true;
	}
	

}
