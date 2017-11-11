package com.tokelon.toktales.extens.def.android.states.localmap;

import com.tokelon.toktales.android.input.TokelonTypeAInputs;
import com.tokelon.toktales.core.game.states.IControlScheme;
import com.tokelon.toktales.extens.def.core.game.states.localmap.ILocalMapControlHandler;

public class AndroidLocalMapControlScheme implements IControlScheme {

	@Override
	public String map(int vk) {
		switch(vk) {
		case TokelonTypeAInputs.VB_A:
			return ILocalMapControlHandler.JUMP;
		case TokelonTypeAInputs.VB_B:
			return ILocalMapControlHandler.INTERACT;
		case TokelonTypeAInputs.VB_SP1:
			return ILocalMapControlHandler.CONSOLE_TOGGLE;
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
