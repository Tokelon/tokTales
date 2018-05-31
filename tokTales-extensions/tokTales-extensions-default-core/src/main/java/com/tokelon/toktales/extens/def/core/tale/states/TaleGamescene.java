package com.tokelon.toktales.extens.def.core.tale.states;

import javax.inject.Inject;

import com.tokelon.toktales.core.game.controller.ICameraController;
import com.tokelon.toktales.core.game.controller.IControllerManager;
import com.tokelon.toktales.core.game.controller.IPlayerController;
import com.tokelon.toktales.core.game.controller.map.IMapController;
import com.tokelon.toktales.core.game.world.IWorldspace;
import com.tokelon.toktales.extens.def.core.game.states.localmap.ILocalMapControlHandler;
import com.tokelon.toktales.extens.def.core.game.states.localmap.LocalMapGamescene;

public class TaleGamescene extends LocalMapGamescene implements ITaleGamescene {

	
	@Inject
	public TaleGamescene() {
		super();
	}
	
	protected TaleGamescene(
			IWorldspace defaultWorldspace,
			ILocalMapControlHandler defaultControlHandler,
			IControllerManager defaultControllerManager,
			IPlayerController defaultPlayerController,
			ICameraController defaultCameraController,
			IMapController defaultMapController
	) {
		super(defaultWorldspace, defaultControlHandler, defaultControllerManager, defaultPlayerController, defaultCameraController, defaultMapController);
	}



	@Override
	public void setMap(IMapController mapController) {
		// This will call onMapChange which will register map render callbacks
		setSceneMapController(mapController);
	}

}
