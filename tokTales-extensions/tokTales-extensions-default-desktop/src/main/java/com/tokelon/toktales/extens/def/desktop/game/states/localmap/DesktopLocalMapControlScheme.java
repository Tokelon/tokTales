package com.tokelon.toktales.extens.def.desktop.game.states.localmap;

import com.tokelon.toktales.desktop.input.TInput;
import com.tokelon.toktales.extens.def.core.game.states.localmap.ILocalMapControlHandler;
import com.tokelon.toktales.extens.def.core.game.states.localmap.ILocalMapControlScheme;
import com.tokelon.toktales.extens.def.desktop.game.states.consover.DesktopConsoleOverlayControlScheme;

public class DesktopLocalMapControlScheme extends DesktopConsoleOverlayControlScheme implements ILocalMapControlScheme {

	@Override
	public String map(int vk) {
		String action = super.map(vk);
		if(!UNMAPPED.equals(action)) {
			return action;
		}
		
		switch (vk) {
		case TInput.VK_SPACE:
			return ILocalMapControlHandler.JUMP;
		case TInput.VK_E:
			return ILocalMapControlHandler.INTERACT;
		case TInput.VK_LEFT:
			return ILocalMapControlHandler.MOVE_LEFT;
		case TInput.VK_UP:
			return ILocalMapControlHandler.MOVE_UP;
		case TInput.VK_RIGHT:
			return ILocalMapControlHandler.MOVE_RIGHT;
		case TInput.VK_DOWN:
			return ILocalMapControlHandler.MOVE_DOWN;
		default:
			break;
		}
		
		return UNMAPPED;
	}

}
