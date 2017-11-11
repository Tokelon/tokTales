package com.tokelon.toktales.desktop.game.states;

import com.tokelon.toktales.desktop.input.IDesktopInputRegistration.ICharInputCallback;
import com.tokelon.toktales.desktop.input.IDesktopInputRegistration.ICursorMoveCallback;
import com.tokelon.toktales.desktop.input.IDesktopInputRegistration.IKeyInputCallback;
import com.tokelon.toktales.desktop.input.IDesktopInputRegistration.IMouseButtonCallback;
import com.tokelon.toktales.core.game.states.GameStateControl;
import com.tokelon.toktales.core.game.states.IGameStateInput;
import com.tokelon.toktales.desktop.input.IDesktopInputService;

public class DesktopGameStateManager extends GameStateControl {

	
	public DesktopGameStateManager(IDesktopInputService inputService) {
		inputService.getInputDispatcher().registerMouseButtonCallback(new GamestateControlMouseButtonCallback());
		inputService.getInputDispatcher().registerCursorMoveCallback(new GamestateControlCursorMoveCallback());
		inputService.getInputDispatcher().registerKeyInputCallback(new GamestateControlKeyInputCallback());
		inputService.getInputDispatcher().registerCharInputCallback(new GamestateControlCharInputCallback());
	}
	
	
	private class GamestateControlMouseButtonCallback implements IMouseButtonCallback {
		
		@Override
		public boolean invokeMouseButton(int vb, int action) {
			IGameStateInput currentStateInput = getActiveState().getStateInput();
			
			if(currentStateInput instanceof IDesktopGameStateInput) {
				IDesktopGameStateInput desktopStateInput = (IDesktopGameStateInput) currentStateInput;
				return desktopStateInput.getMasterMouseButtonCallback().invokeMouseButton(vb, action);
			}
			
			return false;
		}
	}
	
	private class GamestateControlCursorMoveCallback implements ICursorMoveCallback {

		@Override
		public boolean invokeCursorMove(double xpos, double ypos) {
			IGameStateInput currentStateInput = getActiveState().getStateInput();
			
			if(currentStateInput instanceof IDesktopGameStateInput) {
				IDesktopGameStateInput desktopStateInput = (IDesktopGameStateInput) currentStateInput;
				return desktopStateInput.getMasterCursorMoveCallback().invokeCursorMove(xpos, ypos);
			}
			
			return false;
		}
	}
	
	private class GamestateControlKeyInputCallback implements IKeyInputCallback {

		@Override
		public boolean invokeKeyInput(int vk, int action) {
			IGameStateInput currentStateInput = getActiveState().getStateInput();
			
			if(currentStateInput instanceof IDesktopGameStateInput) {
				IDesktopGameStateInput desktopStateInput = (IDesktopGameStateInput) currentStateInput;
				return desktopStateInput.getMasterKeyInputCallback().invokeKeyInput(vk, action);
			}
			
			return false;
		}
	}
	
	private class GamestateControlCharInputCallback implements ICharInputCallback {

		@Override
		public boolean invokeCharInput(int codepoint) {
			IGameStateInput currentStateInput = getActiveState().getStateInput();
			
			if(currentStateInput instanceof IDesktopGameStateInput) {
				IDesktopGameStateInput desktopStateInput = (IDesktopGameStateInput) currentStateInput;
				return desktopStateInput.getMasterCharInputCallback().invokeCharInput(codepoint);
			}
			
			return false;
		}
	}
	
	
}
