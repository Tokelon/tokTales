package com.tokelon.toktales.extensions.core.tale.state;

import javax.inject.Inject;

import com.tokelon.toktales.core.game.controller.ICameraController.ICameraControllerFactory;
import com.tokelon.toktales.core.game.controller.IControllerManager;
import com.tokelon.toktales.core.game.controller.IPlayerController;
import com.tokelon.toktales.core.game.controller.map.IMapController;
import com.tokelon.toktales.core.game.model.ICamera;
import com.tokelon.toktales.core.game.world.IWorldspace;
import com.tokelon.toktales.extensions.core.game.state.localmap.ILocalMapControlHandler;
import com.tokelon.toktales.extensions.core.game.state.localmap.LocalMapGamescene;
import com.tokelon.toktales.tools.core.procedure.checked.ISupplierCheckedProcedure;

public class TaleGamescene extends LocalMapGamescene implements ITaleGamescene {


	@Inject
	public TaleGamescene() {
		super();
	}
	
	protected TaleGamescene(
			IControllerManager defaultControllerManager,
			ICamera defaultCamera,
			ILocalMapControlHandler defaultControlHandler,
			IWorldspace defaultWorldspace,
			ICameraControllerFactory defaultCameraControllerFactory,
			IPlayerController defaultPlayerController,
			IMapController defaultMapController
	) {
		super(defaultControllerManager, defaultCamera, defaultControlHandler, defaultWorldspace, defaultCameraControllerFactory, defaultPlayerController, defaultMapController);
	}


	@Override
	public void runSetMap(ISupplierCheckedProcedure<ITaleGamescene, IMapController> procedure) throws Exception {
		IMapController mapController = procedure.run(this);
		
		// This will call onMapChange which will register map render callbacks
		setSceneMapController(mapController);
	}

}
