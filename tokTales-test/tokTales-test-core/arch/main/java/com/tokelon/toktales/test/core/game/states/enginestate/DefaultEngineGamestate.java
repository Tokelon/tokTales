package com.tokelon.toktales.test.core.game.states.enginestate;

import javax.inject.Inject;

import com.tokelon.toktales.core.game.states.IControlScheme;
import com.tokelon.toktales.test.core.game.states.enginestate.IEngineGamestateControlHandler.IEngineGamestateControlHandlerFactory;
import com.tokelon.toktales.test.core.game.states.enginestate.IEngineGamestateInputHandler.IEngineGamestateInputHandlerFactory;
import com.tokelon.toktales.test.core.game.states.enginestate.IEngineGamestateRender.IEngineGamestateRenderFactory;

public class DefaultEngineGamestate extends EngineGamestate<IEngineGamescene> {

	
	@Inject
	public DefaultEngineGamestate(
			IEngineGamestateRenderFactory renderFactory,
			IEngineGamestateInputHandlerFactory inputHandlerFactory,
			@IEngineGamestateType IControlScheme controlScheme,
			IEngineGamestateControlHandlerFactory controlHandlerFactory
	) {
		super(renderFactory, inputHandlerFactory, controlScheme, controlHandlerFactory);
	}

}
