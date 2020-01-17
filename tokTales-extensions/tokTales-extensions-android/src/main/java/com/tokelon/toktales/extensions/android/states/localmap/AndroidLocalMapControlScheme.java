package com.tokelon.toktales.extensions.android.states.localmap;

import com.tokelon.toktales.android.input.TokelonTypeAInputs;
import com.tokelon.toktales.extensions.android.states.integration.AndroidConsoleIntegrationControlScheme;
import com.tokelon.toktales.extensions.core.game.states.localmap.ILocalMapControlHandler;
import com.tokelon.toktales.extensions.core.game.states.localmap.ILocalMapControlScheme;

public class AndroidLocalMapControlScheme extends AndroidConsoleIntegrationControlScheme implements ILocalMapControlScheme {

	
	@Override
	public String map(int vk) {
		String action = super.map(vk);
		if(!UNMAPPED.equals(action)) {
			return action;
		}
		
		switch(vk) {
		case TokelonTypeAInputs.VB_A:
			return ILocalMapControlHandler.JUMP;
		case TokelonTypeAInputs.VB_B:
			return ILocalMapControlHandler.INTERACT;
		case TokelonTypeAInputs.VB_SET:
			return ILocalMapControlHandler.DEBUG_OPEN;
		case TokelonTypeAInputs.VB_LEFT:
			return ILocalMapControlHandler.MOVE_LEFT;
		case TokelonTypeAInputs.VB_UP:
			return ILocalMapControlHandler.MOVE_UP;
		case TokelonTypeAInputs.VB_RIGHT:
			return ILocalMapControlHandler.MOVE_RIGHT;
		case TokelonTypeAInputs.VB_DOWN:
			return ILocalMapControlHandler.MOVE_DOWN;
		}
		
		return UNMAPPED;
	}

	@Override
	public String interpret(int vk, int action) {
		return UNRESOLVED;
	}
	
}
