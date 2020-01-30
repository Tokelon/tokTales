package com.tokelon.toktales.core.game.state;

import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.engine.log.LoggingManager;
import com.tokelon.toktales.core.game.controller.ICameraController;
import com.tokelon.toktales.core.game.controller.IController;
import com.tokelon.toktales.core.game.controller.IPlayerController;
import com.tokelon.toktales.core.game.controller.map.IMapController;
import com.tokelon.toktales.core.game.world.IWorldspace;
import com.tokelon.toktales.core.values.ControllerValues;
import com.tokelon.toktales.tools.core.inject.ISupplier;

public class GameStateSuppliers {


	protected GameStateSuppliers() {
		throw new UnsupportedOperationException();
	}
	
	private static final ILogger logger = LoggingManager.getLogger(GameStateSuppliers.class);
	
	
	
	public static ISupplier<IWorldspace> ofWorldspaceFromGamestate(ITypedGameState<? extends IExtendedGameScene> gamestate) {
		return () -> gamestate.getActiveScene().getWorldspace();
	}
	
	public static ISupplier<ICameraController> ofCameraControllerFromGamestate(ITypedGameState<? extends IExtendedGameScene> gamestate) {
		return () -> gamestate.getActiveScene().getCameraController();
	}
	
	public static ISupplier<IPlayerController> ofPlayerControllerFromGamestate(ITypedGameState<? extends IExtendedGameScene> gamestate) {
		return () -> gamestate.getActiveScene().getPlayerController();
	}
	
	public static ISupplier<IMapController> ofMapControllerFromGamestate(ITypedGameState<? extends IExtendedGameScene> gamestate) {
		return () -> gamestate.getActiveScene().getMapController();
	}
	

	@SuppressWarnings("unchecked")
	public static <T extends IController> ISupplier<T> ofControllerFromManager(IGameState gamestate, String controllerId, Class<T> controllerType) {
		return () -> {
			IController controller = gamestate.getActiveScene().getControllerManager().getController(controllerId);
			if(controller == null) {
				logger.error("Cannot supply: No controller for id [{}] is available at this time", controllerId);
				return null;
			}
			else if(!controllerType.isInstance(controller)) {
				logger.error("Cannot supply: Controller for id [{}] is not of type [{}]", controllerId, controllerType);
				return null;
			}
			
			T result = (T) controller;
			return result;
		};
	}
	
	
	public static ISupplier<ICameraController> ofCameraControllerFromManager(IGameState gamestate) {
		return ofControllerFromManager(gamestate, ControllerValues.CONTROLLER_CAMERA, ICameraController.class);
	}
	
	public static ISupplier<ICameraController> ofCameraControllerFromManager(IGameState gamestate, String controllerId) {
		return ofControllerFromManager(gamestate, controllerId, ICameraController.class);
	}

	
	public static ISupplier<IPlayerController> ofPlayerControllerFromManager(IGameState gamestate) {
		return ofControllerFromManager(gamestate, ControllerValues.CONTROLLER_PLAYER, IPlayerController.class);
	}
	
	public static ISupplier<IPlayerController> ofPlayerControllerFromManager(IGameState gamestate, String controllerId) {
		return ofControllerFromManager(gamestate, controllerId, IPlayerController.class);
	}
	
	
	public static ISupplier<IMapController> ofMapControllerFromManager(IGameState gamestate) {
		return ofControllerFromManager(gamestate, ControllerValues.CONTROLLER_PLAYER, IMapController.class);
	}
	
	public static ISupplier<IMapController> ofMapControllerFromManager(IGameState gamestate, String controllerId) {
		return ofControllerFromManager(gamestate, controllerId, IMapController.class);
	}
	
}
