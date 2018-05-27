package com.tokelon.toktales.extens.def.core.game.states.localmap;

import javax.inject.Inject;

import com.tokelon.toktales.core.game.controller.ICameraController;
import com.tokelon.toktales.core.game.controller.IControllerManager;
import com.tokelon.toktales.core.game.controller.IPlayerController;
import com.tokelon.toktales.core.game.controller.map.IMapController;
import com.tokelon.toktales.core.game.states.BaseGamescene;
import com.tokelon.toktales.core.game.states.IControlHandler;
import com.tokelon.toktales.core.game.world.IWorldspace;
import com.tokelon.toktales.extens.def.core.game.logic.IConsoleInterpreter;

public class LocalMapGamescene extends BaseGamescene implements ILocalMapGamescene {

	
	private ILocalMapControlHandler customControlHandler;
	
	@Inject
	public LocalMapGamescene() {
		super();
		
		customControlHandler = new ILocalMapControlHandler.EmptyLocalMapControlHandler();
	}

	protected LocalMapGamescene(
			IWorldspace defaultWorldspace,
			ILocalMapControlHandler defaultControlHandler,
			IControllerManager defaultControllerManager,
			IPlayerController defaultPlayerController,
			ICameraController defaultCameraController,
			IMapController defaultMapController
	) {
		super(defaultWorldspace, defaultControlHandler, defaultControllerManager, defaultPlayerController, defaultCameraController, defaultMapController);
		
		customControlHandler = defaultControlHandler == null ? new ILocalMapControlHandler.EmptyLocalMapControlHandler() : defaultControlHandler;
	}
	

	/** The default implementation for this will:<br>
	 * 1. Enabled camera follow for the current player.<br>
	 * 
	 */
	@Override
	public void onAssign() {
		super.onAssign();
		
		/* Defaults for this scene */
		getCameraController().enableCameraFollow(getPlayerController().getPlayer());
	}
	


	/* We do not change the normal setter, so the base implementation will be able to use it.
	 * No type checking etc.
	 */
	/** Use instead of {@link #setSceneControlHandler(IControlHandler)}.
	 * 
	 * @param controlHandler
	 */
	protected void setSceneControlHandlerCustom(ILocalMapControlHandler controlHandler) {
		this.customControlHandler = controlHandler;
		
		setSceneControlHandler(controlHandler);
	}

	@Override
	public ILocalMapControlHandler getSceneControlHandler() {
		return customControlHandler;
	}

	@Override
	public IConsoleInterpreter getSceneConsoleInterpreter() {
		return null;
	}
	
}
