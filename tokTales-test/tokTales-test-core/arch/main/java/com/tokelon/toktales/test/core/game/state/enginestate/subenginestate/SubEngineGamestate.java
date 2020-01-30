package com.tokelon.toktales.test.core.game.state.enginestate.subenginestate;

import javax.inject.Inject;
import javax.inject.Provider;

import com.tokelon.toktales.core.game.state.IControlScheme;
import com.tokelon.toktales.core.game.state.scene.IGameSceneControl.IModifiableGameSceneControl;
import com.tokelon.toktales.test.core.game.state.enginestate.EngineGamestate;
import com.tokelon.toktales.test.core.game.state.enginestate.IEngineGamestateControlHandler.IEngineGamestateControlHandlerFactory;
import com.tokelon.toktales.test.core.game.state.enginestate.IEngineGamestateInputHandler.IEngineGamestateInputHandlerFactory;
import com.tokelon.toktales.test.core.game.state.enginestate.IEngineGamestateRenderer.IEngineGamestateRendererFactory;

public class SubEngineGamestate extends EngineGamestate<ISubEngineGamescene> implements ISubEngineGamestate {

	
	@Inject
	public SubEngineGamestate(
			IEngineGamestateRendererFactory renderFactory,
			IEngineGamestateInputHandlerFactory inputHandlerFactory,
			@IEngineGamestateType IControlScheme controlScheme,
			IEngineGamestateControlHandlerFactory controlHandlerFactory
	) {
		super(renderFactory, inputHandlerFactory, controlScheme, controlHandlerFactory);
	}


	// Testing
	protected SubEngineGamestate(
			Provider<SubEngineGamescene> defaultSceneProvider, // Use the implementation type here, as an example
			IModifiableGameSceneControl<ISubEngineGamescene> defaultSceneControl,
			IEngineGamestateRendererFactory renderFactory,
			IEngineGamestateInputHandlerFactory inputHandlerFactory,
			@IEngineGamestateType IControlScheme controlScheme,
			IEngineGamestateControlHandlerFactory controlHandlerFactory
	) {
		super(defaultSceneProvider, defaultSceneControl, renderFactory, inputHandlerFactory, controlScheme, controlHandlerFactory);
	}
	
}
