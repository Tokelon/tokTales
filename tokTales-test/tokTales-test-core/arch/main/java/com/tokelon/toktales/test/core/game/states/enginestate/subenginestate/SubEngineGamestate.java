package com.tokelon.toktales.test.core.game.states.enginestate.subenginestate;

import javax.inject.Inject;
import javax.inject.Provider;

import com.tokelon.toktales.core.game.states.IControlScheme;
import com.tokelon.toktales.core.game.states.IGameSceneControl.IModifiableGameSceneControl;
import com.tokelon.toktales.test.core.game.states.enginestate.EngineGamestate;
import com.tokelon.toktales.test.core.game.states.enginestate.IEngineGamestateControlHandler.IEngineGamestateControlHandlerFactory;
import com.tokelon.toktales.test.core.game.states.enginestate.IEngineGamestateInputHandler.IEngineGamestateInputHandlerFactory;
import com.tokelon.toktales.test.core.game.states.enginestate.IEngineGamestateRender.IEngineGamestateRenderFactory;

public class SubEngineGamestate extends EngineGamestate<ISubEngineGamescene> implements ISubEngineGamestate {

	
	@Inject
	public SubEngineGamestate(
			IEngineGamestateRenderFactory renderFactory,
			IEngineGamestateInputHandlerFactory inputHandlerFactory,
			@IEngineGamestateType IControlScheme controlScheme,
			IEngineGamestateControlHandlerFactory controlHandlerFactory
	) {
		super(ISubEngineGamescene.class, renderFactory, inputHandlerFactory, controlScheme, controlHandlerFactory);
	}


	// Testing
	protected SubEngineGamestate(
			Provider<SubEngineGamescene> defaultSceneProvider, // Use the implementation type here, as an example
			IModifiableGameSceneControl<ISubEngineGamescene> defaultSceneControl,
			IEngineGamestateRenderFactory renderFactory,
			IEngineGamestateInputHandlerFactory inputHandlerFactory,
			@IEngineGamestateType IControlScheme controlScheme,
			IEngineGamestateControlHandlerFactory controlHandlerFactory
	) {
		super(ISubEngineGamescene.class, defaultSceneProvider, defaultSceneControl, renderFactory, inputHandlerFactory, controlScheme, controlHandlerFactory);
	}
	
}
