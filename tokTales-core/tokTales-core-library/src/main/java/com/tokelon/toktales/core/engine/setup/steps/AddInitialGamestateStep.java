package com.tokelon.toktales.core.engine.setup.steps;

import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.engine.setup.ISetupStep;
import com.tokelon.toktales.core.game.state.InitialGamestate;
import com.tokelon.toktales.core.values.GameStateValues;

public class AddInitialGamestateStep implements ISetupStep {


	@Override
	public void onBuildUp(IEngineContext engineContext) throws EngineException {
		// Resolve and add initial state
		InitialGamestate initialState = engineContext.getInjector().getInstance(InitialGamestate.class);

		engineContext.getGame().getStateControl().addState(GameStateValues.STATE_INITIAL, initialState);
		engineContext.getGame().getStateControl().changeState(GameStateValues.STATE_INITIAL);
	}

	
	@Override
	public void onTearDown(IEngineContext engineContext) throws EngineException {
		// Nothing
	}
	
}
