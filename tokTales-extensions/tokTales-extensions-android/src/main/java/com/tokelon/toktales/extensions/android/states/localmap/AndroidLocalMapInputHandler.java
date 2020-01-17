package com.tokelon.toktales.extensions.android.states.localmap;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.tokelon.toktales.android.input.TokelonTypeAInputs;
import com.tokelon.toktales.android.input.dispatch.IAndroidInputRegistration.IScreenButtonCallback;
import com.tokelon.toktales.android.input.dispatch.IAndroidInputRegistration.IScreenPressCallback;
import com.tokelon.toktales.android.input.events.IScreenButtonInputEvent;
import com.tokelon.toktales.android.input.events.IScreenPressInputEvent;
import com.tokelon.toktales.core.game.world.ICrossDirection;
import com.tokelon.toktales.extensions.android.states.integration.AndroidConsoleIntegrationInputHandler;
import com.tokelon.toktales.extensions.core.game.states.localmap.ILocalMapControlHandler;
import com.tokelon.toktales.extensions.core.game.states.localmap.ILocalMapGamestate;
import com.tokelon.toktales.extensions.core.game.states.localmap.ILocalMapInputHandler;

public class AndroidLocalMapInputHandler extends AndroidConsoleIntegrationInputHandler implements ILocalMapInputHandler, IScreenButtonCallback, IScreenPressCallback {


	private final ILocalMapGamestate gamestate;
	
	@Inject
	public AndroidLocalMapInputHandler(@Assisted ILocalMapGamestate gamestate) {
		super(() -> gamestate.getIntegrationConsole());
		
		this.gamestate = gamestate;
	}


	@Override
	public boolean handleScreenButtonInput(IScreenButtonInputEvent event) {
		boolean handled = super.handleScreenButtonInput(event);
		if(handled) {
			return true;
		}
		
		int button = event.getButton();
		int action = event.getAction();
		
		ILocalMapControlHandler controlHandler = gamestate.getStateControlHandler();
		
		String ca = gamestate.getStateControlScheme().map(button);
		if(ILocalMapControlHandler.JUMP.equals(ca) && action == TokelonTypeAInputs.BUTTON_PRESS) {
			controlHandler.handleJump();
		}
		else if(ILocalMapControlHandler.INTERACT.equals(ca) && action == TokelonTypeAInputs.BUTTON_PRESS) {
			controlHandler.handleInteract();
		}
		else if(ILocalMapControlHandler.DEBUG_OPEN.equals(ca) && action == TokelonTypeAInputs.BUTTON_PRESS) {
			controlHandler.handleDebugOpen();
		}
		else {
			// Implement camera moving when some debug state is enabled?
			// Implement camera movement and zoom with drag and pinch?
			
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
	public boolean handleScreenPressInput(IScreenPressInputEvent event) {
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
