package com.tokelon.toktales.core.game.controller;

import com.tokelon.toktales.core.game.model.IPlayer;
import com.tokelon.toktales.core.script.ScriptInstanceBase;
import com.tokelon.toktales.tools.core.script.IScriptInstance;
import com.tokelon.toktales.tools.core.script.ScriptErrorException;

public class ScriptInstancePlayerController extends ScriptInstanceBase implements IPlayerController {


	public static final String METHOD_SETUP = "setup";
	public static final String METHOD_CONTROLLER_RELOAD = "controllerReload"; // TODO: fix
	public static final String METHOD_ACTION = "action";
	public static final String METHOD_GET_PLAYER = "getPlayer";
	public static final String METHOD_PLAYER_LOOK = "playerLook";
	public static final String METHOD_PLAYER_START_MOVING = "playerStartMoving";
	public static final String METHOD_PLAYER_STOP_MOVING = "playerStopMoving";
	public static final String METHOD_PLAYER_JUMP = "playerJump";
	
	
	public ScriptInstancePlayerController(IScriptInstance playerControllerInstance) {
		super(playerControllerInstance);
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
			getScriptInstance().callMethod(METHOD_SETUP, cm);
		} catch (ScriptErrorException e) {
			reportError(METHOD_SETUP, "setup() failed to run:", e);
		}
	}
	
	
	private void onControllerChange(IControllerManager cm, String controllerId) {
		try {
			getScriptInstance().callMethod(METHOD_CONTROLLER_RELOAD);
		} catch (ScriptErrorException e) {
			reportError(METHOD_CONTROLLER_RELOAD, "onControllerChange() failed to run:", e);
		}
	}
	
	@Override
	public void action(String action, Object... args) {
		try {
			// TODO: This might not work because it will pass 2 arguments one the action and two the args array
			getScriptInstance().callMethod(METHOD_ACTION, action, args);
		} catch (ScriptErrorException e) {
			reportError(METHOD_ACTION, "action() failed to run:", e);
		}
	}

	
	@Override
	public IPlayer getPlayer() {
		try {
			Object result = getScriptInstance().callMethod(METHOD_GET_PLAYER);
			
			if(result instanceof IPlayer) {
				return (IPlayer) result;
			}
			else {
				reportError(METHOD_GET_PLAYER, "getPlayer did not return IPlayer");
				return null;
			}
			
		} catch (ScriptErrorException e) {
			reportError(METHOD_GET_PLAYER, "getPlayer() failed to run:", e);
			return null;
		}
	}

	@Override
	public boolean playerLook(int direction) {
		try {
			Object result = getScriptInstance().callMethod(METHOD_PLAYER_LOOK, direction);
			
			if(result instanceof Boolean) {
				return (Boolean) result;
			}
			else {
				reportError(METHOD_PLAYER_LOOK, "playerLook() did not return boolean");
				return false;
			}
		} catch (ScriptErrorException e) {
			reportError(METHOD_PLAYER_LOOK, "playerLook() failed to run:", e);
			return false;
		}
	}

	@Override
	public boolean playerStartMoving(int direction) {
		try {
			Object result = getScriptInstance().callMethod(METHOD_PLAYER_START_MOVING, direction);
			
			if(result instanceof Boolean) {
				return (Boolean) result;
			}
			else {
				reportError(METHOD_PLAYER_START_MOVING, "playerStartMoving() did not return boolean");
				return false;
			}
		} catch (ScriptErrorException e) {
			reportError(METHOD_PLAYER_START_MOVING, "playerStartMoving() failed to run:", e);
			return false;
		}
	}

	@Override
	public boolean playerStopMoving() {
		try {
			Object result = getScriptInstance().callMethod(METHOD_PLAYER_STOP_MOVING);
			
			if(result instanceof Boolean) {
				return (Boolean) result;
			}
			else {
				reportError(METHOD_PLAYER_STOP_MOVING, "playerStopMoving() did not return boolean");
				return false;
			}
		} catch (ScriptErrorException e) {
			reportError(METHOD_PLAYER_STOP_MOVING, "playerStopMoving() failed to run:", e);
			return false;
		}
	}

	@Override
	public boolean playerJump() {
		try {
			Object result = getScriptInstance().callMethod(METHOD_PLAYER_JUMP);
			
			if(result instanceof Boolean) {
				return (Boolean) result;
			}
			else {
				reportError(METHOD_PLAYER_JUMP, "playerJump() did not return boolean");
				return false;
			}
		} catch (ScriptErrorException e) {
			reportError(METHOD_PLAYER_JUMP, "playerJump() failed to run:", e);
			return false;
		}
	}
	
}
