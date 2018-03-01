package com.tokelon.toktales.extens.def.core.game.states.localmap;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.engine.ui.IDebugUIExtension;
import com.tokelon.toktales.core.engine.ui.IUIService;
import com.tokelon.toktales.extens.def.core.game.states.consover.ConsoleOverlayControlHandler;

public class LocalMapControlHandler extends ConsoleOverlayControlHandler implements ILocalMapControlHandler {
	
	public static final String TAG = "LocalMapControlHandler";
	
	private final ILocalMapGamestate gamestate;

	
	@Inject
	public LocalMapControlHandler(@Assisted ILocalMapGamestate gamestate) {
		super(gamestate);
		
		this.gamestate = gamestate;
	}

	
	
	@Override
	public boolean handleAction(String target, String action, Object... params) {
		if(!gamestate.getActiveScene().getSceneControlHandler().handleAction(target, action, params)) {
			return super.handleAction(target, action, params);
		}
		
		return true;
	}

	
	@Override
	public boolean handleJump() {
		if(!gamestate.getActiveScene().getSceneControlHandler().handleJump()) {
			/*
			ICamera camera = TokTales.getGame().getGamestate().getCamera();
			float zoom = camera.getZoom() > 1.0f ? 1.0f : 2.0f;
			camera.setZoom(zoom, false);
			*/
			
			//cm.getPlayerController().action("player_rotate");
			
			gamestate.getActiveScene().getPlayerController().playerJump();	
		}
		
		return true;
	}

	@Override
	public boolean handleInteract() {
		return gamestate.getActiveScene().getSceneControlHandler().handleInteract();
	}

	@Override
	public boolean handleMove(int direction) {
		if(!gamestate.getActiveScene().getSceneControlHandler().handleMove(direction)) {
			gamestate.getActiveScene().getPlayerController().playerLook(direction);
			gamestate.getActiveScene().getPlayerController().playerStartMoving(direction);
		}
		
		return true;
	}

	@Override
	public boolean handleStopMove() {
		if(!gamestate.getActiveScene().getSceneControlHandler().handleStopMove()) {
			gamestate.getActiveScene().getPlayerController().playerStopMoving();
		}
		
		return true;
	}
	
	@Override
	public boolean handleDebugOpen() {
		if(!gamestate.getActiveScene().getSceneControlHandler().handleDebugOpen()) {
			IUIService uiService = gamestate.getEngine().getUIService();
			
			try {
				uiService.getExtensionByTypeOrFail(IDebugUIExtension.class).openContextMenu();
			} catch (EngineException e) {
				gamestate.getLog().e(TAG, "Error opening context menu: " + e.getMessage());
			}
		}
		
		return true;
	}

	
	
	/* Methods from ConsoleOverlayControlHandler, because it does not consider the game state scene */
	
	@Override
	public boolean handleConsoleClear() {
		if(!gamestate.getActiveScene().getSceneControlHandler().handleConsoleClear()) {
			return super.handleConsoleClear();
		}
		
		return true;
	}
	
	@Override
	public boolean handleConsoleDelete() {
		if(!gamestate.getActiveScene().getSceneControlHandler().handleConsoleDelete()) {
			return super.handleConsoleDelete();
		}
		
		return true;
	}
	
	@Override
	public boolean handleConsoleEnter() {
		if(!gamestate.getActiveScene().getSceneControlHandler().handleConsoleEnter()) {
			return super.handleConsoleEnter();
		}
		
		return true;
	}
	
	@Override
	public boolean handleConsoleInput(int codepoint) {
		if(!gamestate.getActiveScene().getSceneControlHandler().handleConsoleInput(codepoint)) {
			return super.handleConsoleInput(codepoint);
		}
		
		return true;
	}
	
	@Override
	public boolean handleConsoleToggle() {
		if(!gamestate.getActiveScene().getSceneControlHandler().handleConsoleToggle()) {
			return super.handleConsoleToggle();
		}
		
		return true;
	}
	
	
}
