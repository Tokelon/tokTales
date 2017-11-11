package com.tokelon.toktales.core.game.states;

import com.tokelon.toktales.core.engine.IEngine;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.engine.TokTales;
import com.tokelon.toktales.core.engine.input.IInputCallback;
import com.tokelon.toktales.core.engine.input.InputCallbackException;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.engine.render.ISurfaceHandler;
import com.tokelon.toktales.core.game.IGame;
import com.tokelon.toktales.core.game.screen.AbstractStateRender;
import com.tokelon.toktales.core.game.screen.IStateRender;
import com.tokelon.toktales.core.game.screen.order.IRenderOrder;
import com.tokelon.toktales.core.game.screen.order.RenderOrder;
import com.tokelon.toktales.core.game.screen.order.RenderRunner;
import com.tokelon.toktales.core.game.states.IGameSceneControl.IModifiableGameSceneControl;

/**
 *
 * Notes:<br>
 * 1. Protected set*() methods should not be called outside the constructor ?
 *
 */
public class BaseGamestate implements IGameState {

	public static final String TAG = "BaseGamestate";
	
	public static final String INITIAL_SCENE_NAME = "base_gamestate_initial_scene";

	
	
	private final IEngineContext engineContext;
	
	private final IEngine engine;
	private final ILogger log;
	private final IGame game;

	
	private final IRenderOrder stateRenderOrder;
	private final RenderRunner renderRunner;
	
	private final IModifiableGameSceneControl<IGameScene> stateSceneControl;
	
	
	private IStateRender stateRender;
	
	private IGameStateInput stateInput;
	
	private IGameStateInputHandler stateInputHandler;
	private IControlScheme stateControlScheme;
	private IControlHandler stateControlHandler;

	
	
	/** Ctor with defaults.
	 * 
	 * @param context
	 */
	public BaseGamestate(IEngineContext context) {
		this(context, null, null, null);
	}
	
	/** Ctor with a base render order.
	 * 
	 * @param context
	 * @param baseOrder
	 */
	public BaseGamestate(IEngineContext context, IRenderOrder baseOrder) {
		this(context, RenderOrder.CreateFromBase(baseOrder), null, null);
	}
	
	
	/* Shortcut for not having to extend a class to pass platform dependent implementations.
	 * Pass all the things that are platform dependent
	 * 
	 * TODO: Document which things actually have to be set separately
	 */
	
	/** Ctor with platform dependencies. 
	 * 
	 * @param context
	 * @param stateInput
	 * @param stateRender
	 */
	public BaseGamestate(IEngineContext context, IGameStateInput stateInput, IStateRender stateRender) {
		this(context, null, stateInput, stateRender);
	}
	
	
	private BaseGamestate(IEngineContext context, IRenderOrder renderOrder, IGameStateInput stateInput, IStateRender stateRender) {
		if(context == null) {
			throw new NullPointerException();
		}
		
		this.engineContext = context;
		this.engine = context.getEngine();
		this.log = context.getLog();
		this.game = context.getGame();
	
		
		this.stateSceneControl = new GameSceneControl<IGameScene>();
		this.stateInputHandler = new EmptyStateInputHandler();
		this.stateControlScheme = new IControlScheme.EmptyControlScheme();
		this.stateControlHandler = new IControlHandler.EmptyControlHandler();
		
		
		if(renderOrder == null) {
			this.stateRenderOrder = new RenderOrder();
		}
		else {
			this.stateRenderOrder = renderOrder;
		}
		this.renderRunner = new RenderRunner(stateRenderOrder);

		
		if(stateRender == null) {
			this.stateRender = new EmptyStateRender(context.getEngine().getRenderService().getSurfaceHandler(), this);
		}
		else {
			this.stateRender = stateRender;
		}

		
		if(stateInput == null) {
			this.stateInput = new EmptyStateInput();
		}
		else {
			this.stateInput = stateInput;
		}
	}
	
	
	
	@Override
	public void onEngage() {
		IGameScene initialScene = new EmptyGamescene();
		initialScene.assign(this); // Assumed succeeded
		
		stateSceneControl.addScene(INITIAL_SCENE_NAME, initialScene);
		stateSceneControl.changeScene(INITIAL_SCENE_NAME);
	}
	
	@Override
	public void onEnter() {
		stateSceneControl.getActiveScene().onStart();
	}

	@Override
	public void onExit() {
		stateSceneControl.getActiveScene().onStop();
	}
	
	@Override
	public void onDisengage() {
		// TODO: Implement
		
		// Iterate over all scenes and remove them
		//getSceneControl().clear();
	}

	
	@Override
	public void update(long timeMillis) {
		getActiveScene().onUpdate(timeMillis);
	}

	@Override
	public void render() {
		renderRunner.run();
	}

	
	@Override
	public boolean assignScene(String name, IGameScene scene) {
		if(name == null || scene == null) {
			throw new IllegalArgumentException("name and scene must not be null");
		}
		
		boolean assigned = scene.assign(this);
		if(assigned) {
			stateSceneControl.addScene(name, scene);	
		}
		
		return assigned;
	}
	
	
	/** Does what {@link #assignScene(String, IGameScene)} does except with a given scene control and custom class.
	 * 
	 * @param sceneClass
	 * @param sceneControl
	 * @param name
	 * @param scene
	 * @return True if the given scene was assigned successful, false if not.
	 */
	protected <T extends IGameScene> boolean assignSceneGeneric(Class<T> sceneClass, IModifiableGameSceneControl<T> sceneControl, String sceneName, IGameScene scene) {
		if(sceneClass == null || sceneControl == null) {
			throw new NullPointerException();
		}
		
		if(sceneName == null || scene == null) {
			throw new IllegalArgumentException("name and scene must not be null");
		}

		// Check if correct type
		if(!sceneClass.isInstance(scene)) {
			TokTales.getLog().e(TAG, String.format("Scene \"%s\" is not assignable to gamestate \"%s\"", sceneName, this.getClass().getSimpleName()));
			return false;
		}
		
		T typedScene = sceneClass.cast(scene);
		
		boolean assigned = scene.assign(this);
		if(assigned) {
			sceneControl.addScene(sceneName, typedScene);
		}
		
		return assigned;
	}
	
	
	@Override
	public boolean changeScene(String name) {
		IGameScene scene = getSceneControl().getScene(name);
		if(scene == null) {
			return false;
		}
		
		getSceneControl().changeScene(name);
		getStateRender().updateCamera(scene.getCameraController().getCamera());
		return true;
	}
	
	
	@Override
	public IGameScene getActiveScene() {
		return getSceneControl().getActiveScene();
	}
	
	
	/* Getters */

	@Override
	public IEngineContext getEngineContext() {
		return engineContext;
	}

	@Override
	public IEngine getEngine() {
		return engine;
	}
	
	@Override
	public ILogger getLog() {
		return log;
	}
	
	@Override
	public IGame getGame() {
		return game;
	}
	
	@Override
	public IRenderOrder getRenderOrder() {
		return stateRenderOrder;
	}
	
	
	@Override
	public IStateRender getStateRender() {
		return stateRender;
	}
	
	@Override
	public IGameStateInput getStateInput() {
		return stateInput;
	}

	@Override
	public IGameStateInputHandler getStateInputHandler() {
		return stateInputHandler;
	}
	
	@Override
	public IControlScheme getStateControlScheme() {
		return stateControlScheme;
	}
	
	@Override
	public IControlHandler getStateControlHandler() {
		return stateControlHandler;
	}
	
	
	@Override
	public IModifiableGameSceneControl<? extends IGameScene> getSceneControl() {
		return stateSceneControl;
	}
	
	
	
	/** Sets the state render.
	 * <br><br>
	 * There is a default implementation that you can use.
	 * 
	 * @param stateRender
	 * @throws NullPointerException If stateRender is null.
	 */
	public void setStateRender(IStateRender stateRender) {
		if(stateRender == null) {
			throw new NullPointerException();
		}
		
		this.stateRender = stateRender;
	}
	
	/** Sets the state input.
	 * <br><br>
	 * The default is an empty state input.
	 * 
	 * @param input
	 * @throws NullPointerException If stateInput is null.
	 */
	public void setStateInput(IGameStateInput stateInput) {
		if(stateInput == null) {
			throw new NullPointerException();
		}
		
		this.stateInput = stateInput;
	}
	
	/** Sets the state input handler.
	 * <br><br>
	 * The default is an empty input handler.
	 * 
	 * @param inputHandler
	 * @throws NullPointerException If inputHandler is null.
	 */
	public void setStateInputHandler(IGameStateInputHandler inputHandler) {
		if(inputHandler == null) {
			throw new NullPointerException();
		}
		
		this.stateInputHandler = inputHandler;
	}
	
	/** Sets the state control scheme.
	 * <br><br>
	 * The default is an empty control scheme.
	 * 
	 * @param controlScheme
	 * @throws NullPointerException If controlScheme is null.
	 */
	public void setStateControlScheme(IControlScheme controlScheme) {
		if(controlScheme == null) {
			throw new NullPointerException();
		}
		
		this.stateControlScheme = controlScheme;
	}
	
	/** Sets the state control handler.
	 * <br><br>
	 * The default is an empty control handler.
	 * 
	 * @param controlHandler
	 * @throws NullPointerException If controlHandler is null.
	 */
	public void setStateControlHandler(IControlHandler controlHandler) {
		if(controlHandler == null) {
			throw new NullPointerException();
		}
		
		this.stateControlHandler = controlHandler;
	}
	
	
	
	// Should be protected
	public class EmptyGamescene extends BaseGamescene {
		
	}

	public class EmptyStateRender extends AbstractStateRender {

		public EmptyStateRender(ISurfaceHandler surfaceHandler, IGameState gamestate) {
			super(surfaceHandler, gamestate);
		}

		@Override
		public void renderCall(String layerName, double stackPosition) { }

		@Override
		public String getDescription() {
			return "EmptyStateRender";
		}

		@Override
		protected void onSurfaceCreated() {	}

		@Override
		protected void onSurfaceChanged() { }

		@Override
		protected void onSurfaceDestroyed() { }
		
	}
	
	private class EmptyStateInput implements IGameStateInput {

		@Override
		public boolean registerInputCallback(IInputCallback callback) {	return false; }

		@Override
		public <T extends IInputCallback> boolean registerInputCallback(T callback, Class<T> callbackType) { return false; }

		@Override
		public void registerInputCallbackOrFail(IInputCallback callback) throws InputCallbackException {
			throw new InputCallbackException("This implementation does not support any callbacks");
		}

		@Override
		public boolean unregisterInputCallback(IInputCallback callback) { return false; }

		@Override
		public <T extends IInputCallback> boolean unregisterInputCallback(T callback, Class<T> callbackType) { return false; }

		@Override
		public boolean hasInputCallback(IInputCallback callback) { return false; }

		@Override
		public <T extends IInputCallback> boolean hasInputCallback(T callback, Class<T> callbackType) {	return false; }
	}
	
	private class EmptyStateInputHandler implements IGameStateInputHandler {
		
	}
	
	
	// Could do this but when would this even be used?
	/*
	public static BaseGamestate newDefaultInstance() {
		return new BaseGamestate(TokTales.getEngine(), TokTales.getLog(), TokTales.getGame());
	}
	*/
	
}
