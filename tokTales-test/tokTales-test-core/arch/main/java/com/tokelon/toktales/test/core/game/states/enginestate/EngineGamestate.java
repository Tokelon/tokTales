package com.tokelon.toktales.test.core.game.states.enginestate;

import javax.inject.Inject;
import javax.inject.Provider;

import com.google.common.reflect.TypeToken;
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
	
	@Inject
	protected EngineGamestate(
			IEngineGamestateRenderFactory renderFactory,
			IEngineGamestateInputHandlerFactory inputHandlerFactory,
			@IEngineGamestateType IControlScheme controlScheme,
			IEngineGamestateControlHandlerFactory controlHandlerFactory
	) {
		super(null, null, controlScheme, null);
		
		this.stateRenderFactory = renderFactory;
		this.stateControlHandlerFactory = controlHandlerFactory;
		this.stateInputHandlerFactory = inputHandlerFactory;
		
		System.out.println(String.format("EngineGamestate of type [%s] has parameter T of type [%s, %s]", this.getClass(), getSceneTypeToken().getType(), getSceneTypeToken().getRawType()));
	}
	
	// Testing
	protected EngineGamestate(
			Provider<? extends T> defaultSceneProvider,
			IModifiableGameSceneControl<T> defaultSceneControl,
			IEngineGamestateRenderFactory renderFactory,
			IEngineGamestateInputHandlerFactory inputHandlerFactory,
			@IEngineGamestateType IControlScheme controlScheme,
			IEngineGamestateControlHandlerFactory controlHandlerFactory
	) {
		super(defaultSceneProvider, defaultSceneControl, null, null, controlScheme, null);
		
		this.stateRenderFactory = renderFactory;
		this.stateControlHandlerFactory = controlHandlerFactory;
		this.stateInputHandlerFactory = inputHandlerFactory;
		
		System.out.println(String.format("EngineGamestate of type [%s] has parameter T of type [%s, %s]", this.getClass(), getSceneTypeToken().getType(), getSceneTypeToken().getRawType()));
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

	
	/* Expose for testing purposes only */
	
	@Override
	public TypeToken<T> getSceneTypeToken() {
		return super.getSceneTypeToken();
	}
	
	@Override
	public Class<? super T> getSceneTypeClass() {
		return super.getSceneTypeClass();
	}
	
	@Override
	public Provider<? extends T> getSceneProvider() {
		return super.getSceneProvider();
	}
	
}
