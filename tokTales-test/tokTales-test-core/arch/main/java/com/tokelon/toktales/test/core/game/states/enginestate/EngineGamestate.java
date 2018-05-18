package com.tokelon.toktales.test.core.game.states.enginestate;

import javax.inject.Inject;

import com.tokelon.toktales.core.game.screen.IStateRender;
import com.tokelon.toktales.core.game.states.BaseGamestate;
import com.tokelon.toktales.core.game.states.IControlHandler;
import com.tokelon.toktales.core.game.states.IControlScheme;
import com.tokelon.toktales.core.game.states.IGameScene;
import com.tokelon.toktales.core.game.states.IGameStateInputHandler;
import com.tokelon.toktales.core.game.states.IGameSceneControl.IGameSceneControlFactory;
import com.tokelon.toktales.core.game.states.IGameSceneControl.IModifiableGameSceneControl;
import com.tokelon.toktales.test.core.game.states.enginestate.IEngineGamestateControlHandler.IEngineGamestateControlHandlerFactory;
import com.tokelon.toktales.test.core.game.states.enginestate.IEngineGamestateInputHandler.IEngineGamestateInputHandlerFactory;
import com.tokelon.toktales.test.core.game.states.enginestate.IEngineGamestateRender.IEngineGamestateRenderFactory;

public class EngineGamestate extends BaseGamestate implements IEngineGamestate {

	private static final String TAG = "EngineGamestate";
	
	
	private final IGameSceneControlFactory sceneControlFactory;
	private final IEngineGamestateRenderFactory stateRenderFactory;
	private final IEngineGamestateControlHandlerFactory stateControlHandlerFactory;
	private final IEngineGamestateInputHandlerFactory stateInputHandlerFactory;
	
	@Inject
	public EngineGamestate(
			IGameSceneControlFactory sceneControlFactory,
			IEngineGamestateRenderFactory renderFactory,
			IEngineGamestateControlHandlerFactory controlHandlerFactory,
			IEngineGamestateInputHandlerFactory inputHandlerFactory,
			@IEngineGamestateType IControlScheme controlScheme
	) {
		super(null, null, null, controlScheme, null);
		
		this.sceneControlFactory = sceneControlFactory;
		this.stateRenderFactory = renderFactory;
		this.stateControlHandlerFactory = controlHandlerFactory;
		this.stateInputHandlerFactory = inputHandlerFactory;
	}
	
	
	@Override
	protected void initStateDependencies(
			IModifiableGameSceneControl<? extends IGameScene> defaultSceneControl,
			IStateRender defaultRender,
			IGameStateInputHandler defaultInputHandler,
			IControlScheme defaultControlScheme,
			IControlHandler defaultControlHandler
	) {
		IModifiableGameSceneControl<? extends IGameScene> stateSceneControl = sceneControlFactory.createModifiable();
		
		IEngineGamestateRender stateRender = stateRenderFactory.create(this);
		
		IControlHandler stateControlHandler = stateControlHandlerFactory.create(this);
		
		IGameStateInputHandler stateInputHandler = stateInputHandlerFactory.create(this);
		
		super.initStateDependencies(stateSceneControl, stateRender, stateInputHandler, defaultControlScheme, stateControlHandler);
	}
	
	
	@Override
	protected String getTag() {
		return TAG + "_" + BASE_TAG;
	}
	
}
