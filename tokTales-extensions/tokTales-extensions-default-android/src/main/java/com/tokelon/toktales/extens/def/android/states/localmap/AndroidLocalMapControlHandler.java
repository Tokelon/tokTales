package com.tokelon.toktales.extens.def.android.states.localmap;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.engine.ui.IConsoleUIExtension;
import com.tokelon.toktales.core.engine.ui.IUIService;
import com.tokelon.toktales.extens.def.core.game.states.localmap.ILocalMapGamestate;
import com.tokelon.toktales.extens.def.core.game.states.localmap.LocalMapControlHandler;

public class AndroidLocalMapControlHandler extends LocalMapControlHandler {

	
	private final ILocalMapGamestate localMapGamestate;
	
	@Inject
	public AndroidLocalMapControlHandler(@Assisted ILocalMapGamestate gamestate) {
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
