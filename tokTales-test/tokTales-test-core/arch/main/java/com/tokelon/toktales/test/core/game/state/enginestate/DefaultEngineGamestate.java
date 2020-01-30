package com.tokelon.toktales.test.core.game.state.enginestate;

import javax.inject.Inject;

import com.tokelon.toktales.core.game.state.IControlScheme;
import com.tokelon.toktales.test.core.game.state.enginestate.IEngineGamestateControlHandler.IEngineGamestateControlHandlerFactory;
import com.tokelon.toktales.test.core.game.state.enginestate.IEngineGamestateInputHandler.IEngineGamestateInputHandlerFactory;
import com.tokelon.toktales.test.core.game.state.enginestate.IEngineGamestateRenderer.IEngineGamestateRendererFactory;

public class DefaultEngineGamestate extends EngineGamestate<IEngineGamescene> {

	
	@Inject
	public DefaultEngineGamestate(
			IEngineGamestateRendererFactory renderFactory,
			IEngineGamestateInputHandlerFactory inputHandlerFactory,
			@IEngineGamestateType IControlScheme controlScheme,
			IEngineGamestateControlHandlerFactory controlHandlerFactory
	) {
		super(renderFactory, inputHandlerFactory, controlScheme, controlHandlerFactory);
	}

}
