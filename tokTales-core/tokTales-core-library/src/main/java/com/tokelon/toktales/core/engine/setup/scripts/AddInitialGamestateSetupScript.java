package com.tokelon.toktales.core.engine.setup.scripts;

import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.game.states.InitialGamestate;
import com.tokelon.toktales.core.values.GameStateValues;

public class AddInitialGamestateSetupScript implements ISetupScript {

	@Override
	public void run(IEngineContext context) throws EngineException {

		// Resolve and add initial state
		InitialGamestate initialState = context.getInjector().getInstance(InitialGamestate.class);

		context.getGame().getStateControl().addState(GameStateValues.STATE_INITIAL, initialState);
		context.getGame().getStateControl().changeState(GameStateValues.STATE_INITIAL);
	}

}
