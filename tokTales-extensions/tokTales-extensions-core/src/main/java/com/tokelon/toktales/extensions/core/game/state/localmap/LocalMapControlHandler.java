package com.tokelon.toktales.extensions.core.game.state.localmap;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.engine.ui.IDebugUIExtension;
import com.tokelon.toktales.core.engine.ui.IUIService;
import com.tokelon.toktales.core.game.state.IControlHandler.EmptyControlHandler;

public class LocalMapControlHandler extends EmptyControlHandler implements ILocalMapControlHandler {


	private final ILocalMapGamestate gamestate;
	private final ILogger logger;
	
	@Inject
	public LocalMapControlHandler(@Assisted ILocalMapGamestate gamestate) {
		this.gamestate = gamestate;
		this.logger = gamestate.getLogging().getLogger(getClass());
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
	public boolean handleCameraMove(int direction) {
		if(!gamestate.getActiveScene().getSceneControlHandler().handleCameraMove(direction)) {
			gamestate.getActiveScene().getCameraController().cameraStartMoving(direction);
		}
		
		return true;
	}
	
	@Override
	public boolean handleCameraStopMove() {
		if(!gamestate.getActiveScene().getSceneControlHandler().handleCameraStopMove()) {
			gamestate.getActiveScene().getCameraController().cameraStopMoving();
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
				logger.error("Error opening context menu:", e);
			}
		}
		
		return true;
	}
	
}
