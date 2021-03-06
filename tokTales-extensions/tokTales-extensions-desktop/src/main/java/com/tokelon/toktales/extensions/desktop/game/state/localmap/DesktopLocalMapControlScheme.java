package com.tokelon.toktales.extensions.desktop.game.state.localmap;

import com.tokelon.toktales.desktop.input.TInput;
import com.tokelon.toktales.extensions.core.game.state.localmap.ILocalMapControlHandler;
import com.tokelon.toktales.extensions.core.game.state.localmap.ILocalMapControlScheme;
import com.tokelon.toktales.extensions.desktop.game.state.integration.DesktopConsoleIntegrationControlScheme;

public class DesktopLocalMapControlScheme extends DesktopConsoleIntegrationControlScheme implements ILocalMapControlScheme {

	
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
		case TInput.VK_A:
			return ILocalMapControlHandler.CAMERA_LEFT;
		case TInput.VK_W:
			return ILocalMapControlHandler.CAMERA_UP;
		case TInput.VK_D:
			return ILocalMapControlHandler.CAMERA_RIGHT;
		case TInput.VK_S:
			return ILocalMapControlHandler.CAMERA_DOWN;
		default:
			break;
		}
		
		return UNMAPPED;
	}

}
