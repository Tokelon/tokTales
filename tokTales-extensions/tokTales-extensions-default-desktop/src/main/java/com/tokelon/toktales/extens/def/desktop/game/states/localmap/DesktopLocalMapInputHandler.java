package com.tokelon.toktales.extens.def.desktop.game.states.localmap;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.tokelon.toktales.core.game.world.ICrossDirection;
import com.tokelon.toktales.desktop.input.TInput;
import com.tokelon.toktales.extens.def.core.game.states.localmap.ILocalMapControlHandler;
import com.tokelon.toktales.extens.def.core.game.states.localmap.ILocalMapGamestate;
import com.tokelon.toktales.extens.def.core.game.states.localmap.ILocalMapInputHandler;
import com.tokelon.toktales.extens.def.desktop.game.states.consover.DesktopConsoleOverlayInputHandler;

public class DesktopLocalMapInputHandler extends DesktopConsoleOverlayInputHandler implements ILocalMapInputHandler {

	public static final String TAG = "DesktopLocalMapInput";
	
	
	private final ILocalMapGamestate gamestate;

	private String lastKeyDownMove = "";
	private String lastKeyDownCameraMove = "";
	
	@Inject
	public DesktopLocalMapInputHandler(@Assisted ILocalMapGamestate gamestate) {
		super(gamestate);
		
		this.gamestate = gamestate;
	}
	
	
	
	@Override
	public boolean invokeKeyInput(int vk, int action) {
		ILocalMapControlHandler controlHandler = gamestate.getStateControlHandler();
		
		boolean handled = super.invokeKeyInput(vk, action);
		if(handled) {
			return true;
		}
		
		String ca = gamestate.getStateControlScheme().map(vk);
		if(ILocalMapControlHandler.JUMP.equals(ca) && action == TInput.KEY_PRESS) {
			controlHandler.handleJump();
		}
		else if(ILocalMapControlHandler.INTERACT.equals(ca) && action == TInput.KEY_PRESS) {
			controlHandler.handleInteract();
		}
		else if(ILocalMapControlHandler.MOVE_LEFT.equals(ca) ||
				ILocalMapControlHandler.MOVE_UP.equals(ca) ||
				ILocalMapControlHandler.MOVE_RIGHT.equals(ca) ||
				ILocalMapControlHandler.MOVE_DOWN.equals(ca)
		) {
			int direction = keyToDirection(ca);
			if(direction <= 0) {
				return false;
			}
			
			if(action == TInput.KEY_PRESS) {
				lastKeyDownMove = ca;
				controlHandler.handleMove(direction);
			}
			else if(action == TInput.KEY_RELEASE) {
				if(lastKeyDownMove.equals(ca)) {
					controlHandler.handleStopMove();
				}
			}
		}
		else if(ILocalMapControlHandler.CAMERA_LEFT.equals(ca) ||
				ILocalMapControlHandler.CAMERA_UP.equals(ca) ||
				ILocalMapControlHandler.CAMERA_RIGHT.equals(ca) ||
				ILocalMapControlHandler.CAMERA_DOWN.equals(ca)
		) {
			int direction = keyToDirection(ca);
			if(direction <= 0) {
				return false;
			}
			
			if(action == TInput.KEY_PRESS) {
				lastKeyDownCameraMove = ca;
				controlHandler.handleCameraMove(direction);
			}
			else if(action == TInput.KEY_RELEASE) {
				if(lastKeyDownCameraMove.equals(ca)) {
					controlHandler.handleCameraStopMove();
				}
			}
		}

		return true;
	}

	
	private int keyToDirection(String ca) {
		switch (ca) {
		case ILocalMapControlHandler.MOVE_LEFT: return ICrossDirection.LEFT;
		case ILocalMapControlHandler.MOVE_UP: return ICrossDirection.UP;
		case ILocalMapControlHandler.MOVE_RIGHT: return ICrossDirection.RIGHT;
		case ILocalMapControlHandler.MOVE_DOWN: return ICrossDirection.DOWN;
		
		case ILocalMapControlHandler.CAMERA_LEFT: return ICrossDirection.LEFT;
		case ILocalMapControlHandler.CAMERA_UP: return ICrossDirection.UP;
		case ILocalMapControlHandler.CAMERA_RIGHT: return ICrossDirection.RIGHT;
		case ILocalMapControlHandler.CAMERA_DOWN: return ICrossDirection.DOWN;
		default:
			return 0;
		}
	}
	
}
