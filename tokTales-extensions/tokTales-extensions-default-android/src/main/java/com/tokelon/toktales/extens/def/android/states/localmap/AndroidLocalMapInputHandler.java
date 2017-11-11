package com.tokelon.toktales.extens.def.android.states.localmap;

import com.tokelon.toktales.android.input.IAndroidInputRegistration.IScreenButtonCallback;
import com.tokelon.toktales.android.input.IAndroidInputRegistration.IScreenPressCallback;
import com.tokelon.toktales.core.game.states.IGameStateInputHandler;
import com.tokelon.toktales.core.game.world.ICrossDirection;
import com.tokelon.toktales.extens.def.core.game.states.localmap.ILocalMapControlHandler;
import com.tokelon.toktales.extens.def.core.game.states.localmap.ILocalMapGamestate;
import com.tokelon.toktales.android.input.TokelonTypeAInputs;

public class AndroidLocalMapInputHandler implements IGameStateInputHandler, IScreenButtonCallback, IScreenPressCallback {


	private final ILocalMapGamestate gamestate;
	
	public AndroidLocalMapInputHandler(ILocalMapGamestate gamestate) {
		this.gamestate = gamestate;
	}

	

	@Override
	public boolean invokeScreenButton(int vb, int action) {
		ILocalMapControlHandler controlHandler = gamestate.getStateControlHandler();
		
		String ca = gamestate.getStateControlScheme().map(vb);
		if(ILocalMapControlHandler.JUMP.equals(ca) && action == TokelonTypeAInputs.BUTTON_PRESS) {
			controlHandler.handleJump();
		}
		else if(ILocalMapControlHandler.INTERACT.equals(ca) && action == TokelonTypeAInputs.BUTTON_PRESS) {
			controlHandler.handleInteract();
		}
		else if(ILocalMapControlHandler.CONSOLE_TOGGLE.equals(ca) && action == TokelonTypeAInputs.BUTTON_PRESS) {
			controlHandler.handleConsoleToggle();	// TODO: Does not work for some reason
		}
		else if(ILocalMapControlHandler.DEBUG_OPEN.equals(ca) && action == TokelonTypeAInputs.BUTTON_PRESS) {
			controlHandler.handleDebugOpen();
		}
		else {
			int direction = keyToDirection(ca); // Will return 0 if ca not directional key
			if(direction <= 0) {
				return false;
			}
			
			if(action == TokelonTypeAInputs.BUTTON_PRESS) {
				controlHandler.handleMove(direction);
			}
			else if(action == TokelonTypeAInputs.BUTTON_RELEASE) {
				controlHandler.handleStopMove();
			}
		}
		
		return true;
	}

	
	
	@Override
	public boolean invokeScreenPress(double xpos, double ypos) {
		// Old screenPositionPressed()
		// TODO: Redo this whole logic
		
		
		/*
		try {
			// TODO: Check: What happens here if no map has been loaded yet?
			
			IMapPosition blockPosition = mapTool.screenPositionForMapPosition(x, y);
			if(blockPosition == null) {		// When the position is outside the map!
				return true;
			}
			
			
			TokTales.getLog().d(TAG, String.format("Position (%d,%d) pressed", blockPosition.x(), blockPosition.y()));
			
			TokTales.getGame().getEditorManager().getMapEditor().mapBlockSelectedAt(blockPosition);
		} catch (NoMapException e) {
			// Oh well?
			// Nothing to be done
			TokTales.getLog().e(TAG, "NoMapException was thrown. That should not have happen");
		}
		
		return true;
		*/
		return false;
	}


	private int keyToDirection(String ca) {
		switch (ca) {
		case ILocalMapControlHandler.MOVE_LEFT: return ICrossDirection.LEFT;
		case ILocalMapControlHandler.MOVE_UP: return ICrossDirection.UP;
		case ILocalMapControlHandler.MOVE_RIGHT: return ICrossDirection.RIGHT;
		case ILocalMapControlHandler.MOVE_DOWN: return ICrossDirection.DOWN;
		default:
			return 0;
		}
	}

	
}
