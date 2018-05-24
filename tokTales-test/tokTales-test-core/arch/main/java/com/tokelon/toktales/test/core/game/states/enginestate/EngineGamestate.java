package com.tokelon.toktales.test.core.game.states.enginestate;

import javax.inject.Inject;
import javax.inject.Provider;

import com.tokelon.toktales.core.game.screen.IStateRender;
import com.tokelon.toktales.core.game.states.BaseGamestate;
import com.tokelon.toktales.core.game.states.IControlHandler;
import com.tokelon.toktales.core.game.states.IControlScheme;
import com.tokelon.toktales.core.game.states.IGameSceneControl.IModifiableGameSceneControl;
import com.tokelon.toktales.core.game.states.IGameStateInputHandler;
import com.tokelon.toktales.test.core.game.states.enginestate.IEngineGamestateControlHandler.IEngineGamestateControlHandlerFactory;
import com.tokelon.toktales.test.core.game.states.enginestate.IEngineGamestateInputHandler.IEngineGamestateInputHandlerFactory;
import com.tokelon.toktales.test.core.game.states.enginestate.IEngineGamestateRender.IEngineGamestateRenderFactory;

public class EngineGamestate<T extends IEngineGamescene> extends BaseGamestate<T> implements IEngineGamestate {
	
	private static final String TAG = "EngineGamestate";
	
	
	private final IEngineGamestateRenderFactory stateRenderFactory;
	private final IEngineGamestateControlHandlerFactory stateControlHandlerFactory;
	private final IEngineGamestateInputHandlerFactory stateInputHandlerFactory;
	
	
	public EngineGamestate(
			Class<T> sceneType,
			IEngineGamestateRenderFactory renderFactory,
			IEngineGamestateInputHandlerFactory inputHandlerFactory,
			@IEngineGamestateType IControlScheme controlScheme,
			IEngineGamestateControlHandlerFactory controlHandlerFactory
	) {
		super(sceneType, null, null, controlScheme, null);
		
		this.stateRenderFactory = renderFactory;
		this.stateControlHandlerFactory = controlHandlerFactory;
		this.stateInputHandlerFactory = inputHandlerFactory;
	}
	
	@Inject
	protected EngineGamestate(
			IEngineGamestateRenderFactory renderFactory,
			IEngineGamestateInputHandlerFactory inputHandlerFactory,
			@IEngineGamestateType IControlScheme controlScheme,
			IEngineGamestateControlHandlerFactory controlHandlerFactory
	) {
		this(getSceneClass(), renderFactory, inputHandlerFactory, controlScheme, controlHandlerFactory);
	}
	
	
	// Testing
	protected EngineGamestate(
			Class<T> sceneType,
			Provider<? extends T> defaultSceneProvider,
			IModifiableGameSceneControl<T> defaultSceneControl,
			IEngineGamestateRenderFactory renderFactory,
			IEngineGamestateInputHandlerFactory inputHandlerFactory,
			@IEngineGamestateType IControlScheme controlScheme,
			IEngineGamestateControlHandlerFactory controlHandlerFactory
	) {
		super(sceneType, defaultSceneProvider, defaultSceneControl, null, null, controlScheme, null);
		
		this.stateRenderFactory = renderFactory;
		this.stateControlHandlerFactory = controlHandlerFactory;
		this.stateInputHandlerFactory = inputHandlerFactory;
	}
	
	
	@SuppressWarnings("unchecked")
	private static <S extends IEngineGamescene> Class<S> getSceneClass() {
		// For some reason the compiler does not understand that the class of IEngineGamescene is valid without cast
		return (Class<S>) IEngineGamescene.class;
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

	
	/* For testing purposes only */
	
	@Override
	public Class<T> getSceneType() {
		return super.getSceneType();
	}
	
	@Override
	public Provider<? extends T> getSceneProvider() {
		return super.getSceneProvider();
	}
	
}
