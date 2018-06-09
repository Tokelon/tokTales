package com.tokelon.toktales.core.game.states;

import java.util.function.Supplier;

import com.tokelon.toktales.core.game.controller.ICameraController;
import com.tokelon.toktales.core.game.controller.IController;
import com.tokelon.toktales.core.game.controller.IPlayerController;
import com.tokelon.toktales.core.game.controller.map.IMapController;
import com.tokelon.toktales.core.game.world.IWorldspace;
import com.tokelon.toktales.core.values.ControllerValues;

public class GameStateSuppliers {

	public static final String TAG = "GameStateSuppliers";
	
	protected GameStateSuppliers() {
		throw new UnsupportedOperationException();
	}
	
	
	
	
	public static Supplier<IWorldspace> ofWorldspaceFromGamestate(ITypedGameState<? extends IExtendedGameScene> gamestate) {
		return () -> gamestate.getActiveScene().getWorldspace();
	}
	
	public static Supplier<ICameraController> ofCameraControllerFromGamestate(ITypedGameState<? extends IExtendedGameScene> gamestate) {
		return () -> gamestate.getActiveScene().getCameraController();
	}
	
	public static Supplier<IPlayerController> ofPlayerControllerFromGamestate(ITypedGameState<? extends IExtendedGameScene> gamestate) {
		return () -> gamestate.getActiveScene().getPlayerController();
	}
	
	public static Supplier<IMapController> ofMapControllerFromGamestate(ITypedGameState<? extends IExtendedGameScene> gamestate) {
		return () -> gamestate.getActiveScene().getMapController();
	}
	

	@SuppressWarnings("unchecked")
	public static <T extends IController> Supplier<T> ofControllerFromManager(IGameState gamestate, String controllerId, Class<T> controllerType) {
		return () -> {
			IController controller = gamestate.getActiveScene().getControllerManager().getController(controllerId);
			if(controller == null) {
				gamestate.getLog().e(TAG, String.format("Cannot supply: No controller for id [%s] is available at this time", controllerId));
				return null;
			}
			else if(!controllerType.isInstance(controller)) {
				gamestate.getLog().e(TAG, String.format("Cannot supply: Controller for id [%s] is not of type [%s]", controllerId, controllerType));
				return null;
			}
			
			T result = (T) controller;
			return result;
		};
	}
	
	
	public static Supplier<ICameraController> ofCameraControllerFromManager(IGameState gamestate) {
		return ofControllerFromManager(gamestate, ControllerValues.CONTROLLER_CAMERA, ICameraController.class);
	}
	
	public static Supplier<ICameraController> ofCameraControllerFromManager(IGameState gamestate, String controllerId) {
		return ofControllerFromManager(gamestate, controllerId, ICameraController.class);
	}

	
	public static Supplier<IPlayerController> ofPlayerControllerFromManager(IGameState gamestate) {
		return ofControllerFromManager(gamestate, ControllerValues.CONTROLLER_PLAYER, IPlayerController.class);
	}
	
	public static Supplier<IPlayerController> ofPlayerControllerFromManager(IGameState gamestate, String controllerId) {
		return ofControllerFromManager(gamestate, controllerId, IPlayerController.class);
	}
	
	
	public static Supplier<IMapController> ofMapControllerFromManager(IGameState gamestate) {
		return ofControllerFromManager(gamestate, ControllerValues.CONTROLLER_PLAYER, IMapController.class);
	}
	
	public static Supplier<IMapController> ofMapControllerFromManager(IGameState gamestate, String controllerId) {
		return ofControllerFromManager(gamestate, controllerId, IMapController.class);
	}
	
}
