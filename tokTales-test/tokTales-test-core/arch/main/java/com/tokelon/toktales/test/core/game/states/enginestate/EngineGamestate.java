package com.tokelon.toktales.test.core.game.states.enginestate;

import javax.inject.Inject;

import com.tokelon.toktales.core.game.screen.IStateRender;
import com.tokelon.toktales.core.game.states.BaseGamestate;
import com.tokelon.toktales.core.game.states.IControlHandler;
import com.tokelon.toktales.core.game.states.IControlScheme;
import com.tokelon.toktales.core.game.states.IGameStateInputHandler;
import com.tokelon.toktales.test.core.game.states.enginestate.IEngineGamestateControlHandler.IEngineGamestateControlHandlerFactory;
import com.tokelon.toktales.test.core.game.states.enginestate.IEngineGamestateInputHandler.IEngineGamestateInputHandlerFactory;
import com.tokelon.toktales.test.core.game.states.enginestate.IEngineGamestateRender.IEngineGamestateRenderFactory;

public class EngineGamestate extends BaseGamestate<IEngineGamescene> implements IEngineGamestate {
	
	private static final String TAG = "EngineGamestate";
	
	
	private final IEngineGamestateRenderFactory stateRenderFactory;
	private final IEngineGamestateControlHandlerFactory stateControlHandlerFactory;
	private final IEngineGamestateInputHandlerFactory stateInputHandlerFactory;
	
	@Inject
	public EngineGamestate(
			IEngineGamestateRenderFactory renderFactory,
			IEngineGamestateControlHandlerFactory controlHandlerFactory,
			IEngineGamestateInputHandlerFactory inputHandlerFactory,
			@IEngineGamestateType IControlScheme controlScheme
	) {
		super(IEngineGamescene.class, null, null, controlScheme, null);
		
		this.stateRenderFactory = renderFactory;
		this.stateControlHandlerFactory = controlHandlerFactory;
		this.stateInputHandlerFactory = inputHandlerFactory;
	}
	
	
	@Override
	protected void initStateDependencies(
			IStateRender defaultRender,
			IGameStateInputHandler defaultInputHandler,
			IControlScheme defaultControlScheme,
			IControlHandler defaultControlHandler
	) {
		
		IEngineGamestateRender stateRender = stateRenderFactory.create(this);
		
		IControlHandler stateControlHandler = stateControlHandlerFactory.create(this);
		
		IGameStateInputHandler stateInputHandler = stateInputHandlerFactory.create(this);
		
		super.initStateDependencies(stateRender, stateInputHandler, defaultControlScheme, stateControlHandler);
	}
	
	
	@Override
	protected String getTag() {
		return TAG + "_" + BASE_TAG;
	}
	
}
