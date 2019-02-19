package com.tokelon.toktales.extens.def.core.game.states.localmap;

import javax.inject.Inject;

import com.tokelon.toktales.core.game.controller.ICameraController.ICameraControllerFactory;
import com.tokelon.toktales.core.game.controller.IControllerManager;
import com.tokelon.toktales.core.game.controller.IPlayerController;
import com.tokelon.toktales.core.game.controller.map.IMapController;
import com.tokelon.toktales.core.game.model.ICamera;
import com.tokelon.toktales.core.game.states.ExtendedGamescene;
import com.tokelon.toktales.core.game.states.IControlHandler;
import com.tokelon.toktales.core.game.world.IWorldspace;
import com.tokelon.toktales.extens.def.core.game.logic.IConsoleInterpreter;

public class LocalMapGamescene extends ExtendedGamescene implements ILocalMapGamescene {

	
	private ILocalMapControlHandler customControlHandler;
	
	@Inject
	public LocalMapGamescene() {
		super();
		
		customControlHandler = new ILocalMapControlHandler.EmptyLocalMapControlHandler();
	}

	
	protected LocalMapGamescene(
			IControllerManager defaultControllerManager,
			ICamera defaultCamera,
			ILocalMapControlHandler defaultControlHandler,
			IWorldspace defaultWorldspace,
			ICameraControllerFactory defaultCameraControllerFactory,
			IPlayerController defaultPlayerController,
			IMapController defaultMapController
	) {
		super(defaultControllerManager, defaultCamera, defaultControlHandler, defaultWorldspace, defaultCameraControllerFactory, defaultPlayerController, defaultMapController);
		
		customControlHandler = defaultControlHandler == null ? new ILocalMapControlHandler.EmptyLocalMapControlHandler() : defaultControlHandler;
	}
	
	
	@Override
	protected void initSceneDependencies(IControllerManager defaultControllerManager, ICamera defaultCamera, IControlHandler defaultControlHandler) {
		// We have to set our custom control handler here, by checking the provided one and, if incompatible, setting the default
		ILocalMapControlHandler controlHandler;
		if(defaultControlHandler instanceof ILocalMapControlHandler) {
			controlHandler = (ILocalMapControlHandler) defaultControlHandler;
		}
		else {
			// if the default control handler is incompatible, we replace it with the default one, to avoid having it be different from our custom one
			getLog().e(getTag(), String.format("The control handler of type [%s] is not compatible with [%s] and will be overriden by the default control handler", defaultControlHandler.getClass(), ILocalMapControlHandler.class));
			controlHandler = customControlHandler;
		}
		
		// First set our custom one
		customControlHandler = controlHandler;

		// Secondly pass it down
		super.initSceneDependencies(defaultControllerManager, defaultCamera, controlHandler);
	}

	
	/** The default implementation for this will:<br>
	 * 1. Enabled camera follow for the current player.<br>
	 * 
	 */
	@Override
	public void onAssign() {
		super.onAssign();
		
		/* Defaults for this scene */
		getCameraController().enableCameraFollow(getPlayerController().getPlayer().getActor());
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
