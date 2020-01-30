package com.tokelon.toktales.core.game.state;

import com.tokelon.toktales.core.game.controller.ICameraController;
import com.tokelon.toktales.core.game.controller.IController;
import com.tokelon.toktales.core.game.controller.IControllerChangeListener;
import com.tokelon.toktales.core.game.controller.IControllerManager;
import com.tokelon.toktales.core.game.controller.IPlayerController;
import com.tokelon.toktales.core.game.controller.map.IMapController;
import com.tokelon.toktales.core.values.ControllerValues;

public class DefaultControllersSetterInterceptor implements IControllerChangeListener {
	// TODO: Add logging
	
	
	private final ExtendedGamescene extendedGamescene;

	public DefaultControllersSetterInterceptor(ExtendedGamescene extendedGamescene) {
		this.extendedGamescene = extendedGamescene;
	}

	
	private void checkNewController(String controllerId, IController controller) {
		if(ControllerValues.CONTROLLER_CAMERA.equals(controllerId) && controller instanceof ICameraController) {
			this.extendedGamescene.setSceneCameraController((ICameraController) controller);
		}
		else if(ControllerValues.CONTROLLER_PLAYER.equals(controllerId) && controller instanceof IPlayerController) {
			this.extendedGamescene.setScenePlayerController((IPlayerController) controller);
		}
		else if(ControllerValues.CONTROLLER_MAP.equals(controllerId) && controller instanceof IMapController) {
			this.extendedGamescene.setSceneMapController((IMapController) controller);
		}
	}
	
	@Override
	public void onControllerAdd(IControllerManager manager, String controllerId, IController addedController) {
		checkNewController(controllerId, addedController);
	}
	
	@Override
	public void onControllerChange(IControllerManager manager, String controllerId, IController oldController, IController newController) {
		checkNewController(controllerId, newController);
	}
	
	@Override
	public void onControllerRemove(IControllerManager manager, String controllerId, IController removedController) {
		// Nothing yet
	}
	
}