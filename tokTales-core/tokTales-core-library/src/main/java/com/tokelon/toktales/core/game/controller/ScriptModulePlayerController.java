package com.tokelon.toktales.core.game.controller;

import com.tokelon.toktales.core.game.model.IPlayer;
import com.tokelon.toktales.core.script.ScriptModuleBase;
import com.tokelon.toktales.tools.script.IScriptModule;
import com.tokelon.toktales.tools.script.ScriptErrorException;

public class ScriptModulePlayerController extends ScriptModuleBase implements IPlayerController {

	public static final String TAG = "ScriptModulePlayerController";
	
	public static final String FUNCTION_SETUP = "setup";
	public static final String FUNCTION_CONTROLLER_RELOAD = "controllerReload"; // TODO: Fix
	public static final String FUNCTION_ACTION = "action";
	public static final String FUNCTION_GET_PLAYER = "getPlayer";
	public static final String FUNCTION_PLAYER_LOOK = "playerLook";
	public static final String FUNCTION_PLAYER_START_MOVING = "playerStartMoving";
	public static final String FUNCTION_PLAYER_STOP_MOVING = "playerStopMoving";
	public static final String FUNCTION_PLAYER_JUMP = "playerJump";
	
	
	public ScriptModulePlayerController(IScriptModule playerControllerModule) {
		super(playerControllerModule);
	}

	

	@Override
	public void onControllerAdd(IControllerManager manager, String controllerId, IController addedController) {
		if(addedController == this) {
			setup(manager);
		}
	}
	
	@Override
	public void onControllerChange(IControllerManager manager, String controllerId, IController oldController, IController newController) {
		onControllerChange(manager, controllerId);
	}
	
	@Override
	public void onControllerRemove(IControllerManager manager, String controllerId, IController removedController) {
		if(removedController == this) {
			// Do something?
		}
	}
	
	
	private void setup(IControllerManager cm) {
		try {
			getScriptModule().callFunction(FUNCTION_SETUP, cm);
		} catch (ScriptErrorException e) {
			reportError(TAG, FUNCTION_SETUP, "setup() failed to run: " +e.getMessage());
		}
	}
	
	private void onControllerChange(IControllerManager cm, String controllerId) {
		try {
			getScriptModule().callFunction(FUNCTION_CONTROLLER_RELOAD);
		} catch (ScriptErrorException e) {
			reportError(TAG, FUNCTION_CONTROLLER_RELOAD, "controllerReload() failed to run: " +e.getMessage());
		}
	}
	
	@Override
	public void action(String action, Object... args) {
		try {
			// TODO: This might not work because it will pass 2 arguments one the action and two the args array
			getScriptModule().callFunction(FUNCTION_ACTION, action, args);
		} catch (ScriptErrorException e) {
			reportError(TAG, FUNCTION_ACTION, "action() failed to run: " +e.getMessage());
		}
	}

	
	@Override
	public IPlayer getPlayer() {
		try {
			Object result = getScriptModule().callFunction(FUNCTION_GET_PLAYER);
			
			if(result instanceof IPlayer) {
				return (IPlayer) result;
			}
			else {
				reportError(TAG, FUNCTION_GET_PLAYER, "getPlayer did not return IPlayer");
				return null;
			}
			
		} catch (ScriptErrorException e) {
			reportError(TAG, FUNCTION_GET_PLAYER, "getPlayer() failed to run: " +e.getMessage());
			return null;
		}
	}

	@Override
	public boolean playerLook(int direction) {
		try {
			Object result = getScriptModule().callFunction(FUNCTION_PLAYER_LOOK, direction);
			
			if(result instanceof Boolean) {
				return (Boolean) result;
			}
			else {
				reportError(TAG, FUNCTION_PLAYER_LOOK, "playerLook() did not return boolean");
				return false;
			}
		} catch (ScriptErrorException e) {
			reportError(TAG, FUNCTION_PLAYER_LOOK, "playerLook() failed to run: " +e.getMessage());
			return false;
		}
	}

	@Override
	public boolean playerStartMoving(int direction) {
		try {
			Object result = getScriptModule().callFunction(FUNCTION_PLAYER_START_MOVING, direction);
			
			if(result instanceof Boolean) {
				return (Boolean) result;
			}
			else {
				reportError(TAG, FUNCTION_PLAYER_START_MOVING, "playerStartMoving() did not return boolean");
				return false;
			}
		} catch (ScriptErrorException e) {
			reportError(TAG, FUNCTION_PLAYER_START_MOVING, "playerStartMoving() failed to run: " +e.getMessage());
			return false;
		}
	}

	@Override
	public boolean playerStopMoving() {
		try {
			Object result = getScriptModule().callFunction(FUNCTION_PLAYER_STOP_MOVING);
			
			if(result instanceof Boolean) {
				return (Boolean) result;
			}
			else {
				reportError(TAG, FUNCTION_PLAYER_STOP_MOVING, "playerStopMoving() did not return boolean");
				return false;
			}
		} catch (ScriptErrorException e) {
			reportError(TAG, FUNCTION_PLAYER_STOP_MOVING, "playerStopMoving() failed to run: " +e.getMessage());
			return false;
		}
	}

	@Override
	public boolean playerJump() {
		try {
			Object result = getScriptModule().callFunction(FUNCTION_PLAYER_JUMP);
			
			if(result instanceof Boolean) {
				return (Boolean) result;
			}
			else {
				reportError(TAG, FUNCTION_PLAYER_JUMP, "playerJump() did not return boolean");
				return false;
			}
		} catch (ScriptErrorException e) {
			reportError(TAG, FUNCTION_PLAYER_JUMP, "playerJump() failed to run: " +e.getMessage());
			return false;
		}
	}
	
}
