package com.tokelon.toktales.test.core.game.state.enginestate;

import javax.inject.Inject;
import javax.inject.Provider;

import com.google.common.reflect.TypeToken;
import com.tokelon.toktales.core.game.state.BaseGamestate;
import com.tokelon.toktales.core.game.state.IControlHandler;
import com.tokelon.toktales.core.game.state.IControlScheme;
import com.tokelon.toktales.core.game.state.IGameStateInputHandler;
import com.tokelon.toktales.core.game.state.render.IGameStateRenderer;
import com.tokelon.toktales.core.game.state.scene.IGameSceneControl.IModifiableGameSceneControl;
import com.tokelon.toktales.test.core.game.state.enginestate.IEngineGamestateControlHandler.IEngineGamestateControlHandlerFactory;
import com.tokelon.toktales.test.core.game.state.enginestate.IEngineGamestateInputHandler.IEngineGamestateInputHandlerFactory;
import com.tokelon.toktales.test.core.game.state.enginestate.IEngineGamestateRenderer.IEngineGamestateRendererFactory;

public class EngineGamestate<T extends IEngineGamescene> extends BaseGamestate<T> implements IEngineGamestate {


	private final IEngineGamestateRendererFactory stateRenderFactory;
	private final IEngineGamestateControlHandlerFactory stateControlHandlerFactory;
	private final IEngineGamestateInputHandlerFactory stateInputHandlerFactory;
	
	@Inject
	protected EngineGamestate(
			IEngineGamestateRendererFactory renderFactory,
			IEngineGamestateInputHandlerFactory inputHandlerFactory,
			@IEngineGamestateType IControlScheme controlScheme,
			IEngineGamestateControlHandlerFactory controlHandlerFactory
	) {
		super(null, null, controlScheme, null);
		
		this.stateRenderFactory = renderFactory;
		this.stateControlHandlerFactory = controlHandlerFactory;
		this.stateInputHandlerFactory = inputHandlerFactory;
	}
	
	// Testing
	protected EngineGamestate(
			Provider<? extends T> defaultSceneProvider,
			IModifiableGameSceneControl<T> defaultSceneControl,
			IEngineGamestateRendererFactory renderFactory,
			IEngineGamestateInputHandlerFactory inputHandlerFactory,
			@IEngineGamestateType IControlScheme controlScheme,
			IEngineGamestateControlHandlerFactory controlHandlerFactory
	) {
		super(defaultSceneProvider, defaultSceneControl, null, null, controlScheme, null);
		
		this.stateRenderFactory = renderFactory;
		this.stateControlHandlerFactory = controlHandlerFactory;
		this.stateInputHandlerFactory = inputHandlerFactory;
	}
	
	
	@Override
	protected void initStateDependencies(
			IGameStateRenderer defaultRender,
			IGameStateInputHandler defaultInputHandler,
			IControlScheme defaultControlScheme,
			IControlHandler defaultControlHandler
	) {
		
		IEngineGamestateRenderer stateRenderer = stateRenderFactory.create(this);
		
		IControlHandler stateControlHandler = stateControlHandlerFactory.create(this);
		
		IGameStateInputHandler stateInputHandler = stateInputHandlerFactory.create(this);
		
		super.initStateDependencies(stateRenderer, stateInputHandler, defaultControlScheme, stateControlHandler);
		
		getLogger().debug("EngineGamestate of type [{}] has parameter T of type [{}, {}]", this.getClass(), getSceneTypeToken().getType(), getSceneTypeToken().getRawType());
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
