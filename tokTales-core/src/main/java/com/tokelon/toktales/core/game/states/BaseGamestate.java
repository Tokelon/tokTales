package com.tokelon.toktales.core.game.states;

import javax.inject.Inject;
import javax.inject.Provider;

import com.tokelon.toktales.core.engine.IEngine;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.engine.inject.RequiresInjection;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.engine.render.ISurfaceHandler.ISurfaceCallback;
import com.tokelon.toktales.core.game.IGame;
import com.tokelon.toktales.core.game.screen.IStateRender;
import com.tokelon.toktales.core.game.screen.order.IRenderOrder;
import com.tokelon.toktales.core.game.screen.order.RenderRunner;
import com.tokelon.toktales.core.game.states.IGameSceneControl.IGameSceneControlFactory;
import com.tokelon.toktales.core.game.states.IGameSceneControl.IModifiableGameSceneControl;
import com.tokelon.toktales.tools.inject.IParameterInjector;
import com.tokelon.toktales.tools.inject.IParameterInjector.IParameterInjectorFactory;

/** Use as the base for game states.
 * <p>
 * Notes:<br>
 * <br>- Base dependencies will be injected, see {@link #injectBaseDependencies}.
 * <br>- State dependencies need to be set via setters or {@link #initStateDependencies}, or they will default to empty implementations.
 * <br>- State dependencies will also have the gamestate injected when set, if they use {@link InjectGameState}. To use this feature with other dependencies, see {@link #getGamestateInjector()}.
 * <br>- Inject your own dependencies by using {@link Inject}.
 * <br>- If needed there are hooks for {@link #afterInitBaseDependencies()} and {@link #afterInitStateDependencies()}.
 * <br>- Override {@link #getSceneControl()} to return your custom scene control and use the helper {@link #assignSceneGeneric}.
 * <br>- Override {@link #getTag()} and return your own tag to trace logs to the actual gamestate implementation.
 * <br>- The default {@link #render()} implementation will draw using {@link #getRenderOrder()}.
 * 
 */
@RequiresInjection
public class BaseGamestate<T extends IGameScene> implements ITypedGameState<T> {
	// TODO: Adjust documentations to new inject logic

	public static final String BASE_TAG = "BaseGamestate";

	public static final String INITIAL_SCENE_NAME = "base_gamestate_initial_scene";
	
	
	/* Base objects */
	
	private RenderRunner renderRunner;
	private IParameterInjector gamestateInjector;


	/* Injected base objects */
	private IEngineContext engineContext;
	private IEngine engine;
	private ILogger log;
	private IGame game;

	private IRenderOrder stateRenderOrder;

	private IGameStateInput stateInput;

	
	/* State objects */
	private Provider<? extends T> stateSceneProvider; // Can be used with subclasses of T
	private IModifiableGameSceneControl<T> stateSceneControl; // Must have type T to allow for adding objects
	private IStateRender stateRender;
	
	private IGameStateInputHandler stateInputHandler;
	private IControlScheme stateControlScheme;
	private IControlHandler stateControlHandler;
	

	/* The sceneType must be the class of T.
	 * 
	 * It's possible to use Class<? extends T> however then it would not be possible to assign any T,
	 * it would have to be an instance of sceneType. This would be confusing.
	 * There also is no real advantage in using it. You can still use it for the Provider.  
	 * 
	 */
	private final Class<T> sceneType;
	
	/** Default constructor.
	 * 
	 * @param sceneType The type of this state's scenes.
	 */
	public BaseGamestate(Class<T> sceneType) {
		if(sceneType == null) {
			throw new NullPointerException("sceneType must not be null");
		}
		
		this.sceneType = sceneType;
	}
	
	
	/** Constructor with optional state parameters.
	 * <p>
	 * You can pass any objects you'd like to assign. Null values are permitted for all values except the scene type.
	 * <p>
	 * <b>Note: Injected base objects like the context will not be available at this point.</b>
	 * <p>
	 * Useful for custom constructor injection, for manual creation see {@link #initStateDependencies}.<br>
	 * Alternatively you can use method injection, which will be called after base injection has happened.
	 * 
	 * @throws NullPointerException If sceneType is null.
	 */
	protected BaseGamestate(
			Class<T> sceneType,
			IStateRender defaultRender,
			IGameStateInputHandler defaultInputHandler,
			IControlScheme defaultControlScheme,
			IControlHandler defaultControlHandler
	) {
		this(sceneType, null, null, defaultRender, defaultInputHandler, defaultControlScheme, defaultControlHandler);
	}
	
	
	/** Constructor with optional scene type parameters.
	 * <p>
	 * You can pass any objects you'd like to assign. Null values are permitted for all values except the scene type.
	 * <p>
	 * <b>Note: Injected base objects like the context will not be available at this point.</b>
	 * 
	 * @throws NullPointerException If sceneType is null.
	 */
	protected BaseGamestate(
			Class<T> sceneType,
			Provider<? extends T> defaultSceneProvider,
			IModifiableGameSceneControl<T> defaultSceneControl
	) {
		this(sceneType, defaultSceneProvider, defaultSceneControl, null, null, null, null);
	}
	

	/** Constructor with optional state and scene type parameters.
	 * <p>
	 * You can pass any objects you'd like to assign. Null values are permitted for all values except the scene type.
	 * <p>
	 * <b>Note: Injected base objects like the context will not be available at this point.</b>
	 * 
	 * @throws NullPointerException If sceneType is null.
	 */
	protected BaseGamestate(
			Class<T> sceneType,
			Provider<? extends T> defaultSceneProvider,
			IModifiableGameSceneControl<T> defaultSceneControl,
			IStateRender defaultRender,
			IGameStateInputHandler defaultInputHandler,
			IControlScheme defaultControlScheme,
			IControlHandler defaultControlHandler
	) {
		this(sceneType);
		
		// Any of these can be null
		this.stateSceneProvider = defaultSceneProvider;
		this.stateSceneControl = defaultSceneControl;
		this.stateRender = defaultRender;
		this.stateInputHandler = defaultInputHandler;
		this.stateControlScheme = defaultControlScheme;
		this.stateControlHandler = defaultControlHandler;
	}
	

	
	/** Injects all dependencies for a state and object initialization.
	 * <p>
	 * Note that this method will be called before any <i>method</i> injections in subclasses.
	 * 
	 * @see #initBaseDependencies
	 * @see #initStateDependencies
	 */
	@Inject
	protected void injectDependencies(
			IParameterInjectorFactory parameterInjectorFactory,
			IGameSceneControlFactory sceneControlFactory,
			IEngineContext context,
			IRenderOrder renderOrder,
			IGameStateInput input,
			IStateRender render,
			IGameStateInputHandler inputHandler,
			IControlScheme controlScheme,
			IControlHandler controlHandler
	) {
		// Initialize internal dependencies
		this.gamestateInjector = parameterInjectorFactory.create(InjectGameState.class, this);
		
		
		Provider<? extends T> defaultSceneProvider = stateSceneProvider == null ? createDefaultSceneProvider(context) : stateSceneProvider;
		setSceneProvider(defaultSceneProvider);

		IModifiableGameSceneControl<T> defaultSceneControl = stateSceneControl == null ? createDefaultSceneControl(context, sceneControlFactory) : stateSceneControl;
		setSceneControl(defaultSceneControl);
		
		
		// Dependencies have been injected
		afterDependencyInjection();
		
		
		// Initialize base dependencies
		initBaseDependencies(context, renderOrder, input);
		afterInitBaseDependencies();
		
		
		// Determine the defaults state dependencies
		IStateRender defaultStateRender = stateRender == null ? render : stateRender;
		IGameStateInputHandler defaultStateInputHandler = stateInputHandler == null ? inputHandler : stateInputHandler;
		IControlScheme defaultStateControlScheme = stateControlScheme == null ? controlScheme : stateControlScheme;
		IControlHandler defaultStateControlHandler = stateControlHandler == null ? controlHandler : stateControlHandler;
		
		// Initialize state dependencies by passing the defaults
		initStateDependencies(defaultStateRender, defaultStateInputHandler, defaultStateControlScheme, defaultStateControlHandler);
		afterInitStateDependencies();
		
		
		// injectDependencies() has run (call this last)
		afterInjectDependencies();
	}
	
	/** Creates the default scene provider for this state.
	 * 
	 * @param context the engine context
	 * @return A scene provider for this state's scene type.
	 */
	protected Provider<? extends T> createDefaultSceneProvider(IEngineContext context) {
		return context.getInjector().getProvider(getSceneType());
	}
	
	/** Creates the default scene control for this state.
	 * 
	 * @param context the engine context
	 * @param defaultSceneControlFactory the default scene control factory
	 * @return A scene control for this state's scene type.
	 */
	protected IModifiableGameSceneControl<T> createDefaultSceneControl(IEngineContext context, IGameSceneControlFactory defaultSceneControlFactory) {
		return defaultSceneControlFactory.createModifiable();
	}
	
	/** Called after dependencies have been injected, during {@link #injectDependencies}.
	 * 
	 * @see #injectDependencies
	 */
	protected void afterDependencyInjection() { }
	
	
	/* Add dependencies here that are the same for all gamestate classes.
	 * 
	 * Only base dependencies are injected here,
	 * anything that is a state dependency must be set separately.
	 */
	/** Initializes the base dependencies.
	 * <p>
	 * Overriding this method is usually not necessary, however if you do you must call super().
	 * <p>
	 * This method will be called during {@link #injectDependencies}, after {@link #afterDependencyInjection()}.
	 * 
	 * @see #afterInitBaseDependencies()
	 */
	protected void initBaseDependencies(
			IEngineContext context,
			IRenderOrder renderOrder,
			IGameStateInput input
	) {
		this.engineContext = context;
		this.engine = context.getEngine();
		this.log = context.getLog();
		this.game = context.getGame();

		this.stateRenderOrder = renderOrder;
		this.stateInput = input;

		
		this.renderRunner = new RenderRunner(stateRenderOrder);
	}

	/** Called after base dependencies have been initialized.
	 * 
	 * @see #initBaseDependencies
	 */
	protected void afterInitBaseDependencies() { }

	
	/* Add dependencies here that differ between gamestate classes.
	 * 
	 * There is no point in injecting these here.
	 * 1. Usually the state needs these in their specific types anyways (or the other way around)
	 * 2. To inject the state we would have to pass them as factories, which would limit subclasses.
	 * 
	 * Subclasses can inject them better, with the proper types and factories when needed.
	 * 
	 * Therefore in the base, these fields will be set manually, either in this init or with the setters.
	 */
	/** Initializes the state dependencies.
	 * Override this method to pass custom state objects.
	 * <p>
	 * If any state objects have been set before this call (ex. in the constructor),
	 * those will be passed here as parameters.
	 * <p>
	 * This method will be called during {@link #injectDependencies}, after {@link #initBaseDependencies} and {@link #afterInitBaseDependencies()},
	 * so the context and any other injected objects will have their injected values.
	 * 
	 * @see #afterInitStateDependencies
	 */
	protected void initStateDependencies(
			IStateRender defaultRender,
			IGameStateInputHandler defaultInputHandler,
			IControlScheme defaultControlScheme,
			IControlHandler defaultControlHandler
	) {
		setStateRender(defaultRender);
		setStateInputHandler(defaultInputHandler);
		setStateControlScheme(defaultControlScheme);
		setStateControlHandler(defaultControlHandler);
	}
	
	/** Called after state dependencies have been initialized.
	 * <p>
	 * The default implementation for this will:<br>
	 * - Register the state input handler given by {@link #getStateInputHandler()}, to the state input given by {@link #getStateInput()}
	 * by calling {@link IGameStateInputHandler#register(IGameStateInput)}.<br>
	 * 
	 * @see #initStateDependencies
	 */
	protected void afterInitStateDependencies() {
		// TODO: Handle one of these changing later?
		
		// Register input handler
		IGameStateInput stateInput = getStateInput();
		IGameStateInputHandler stateInputHandler = getStateInputHandler();
		getLog().d(getTag(), String.format("Registering state input handler [%s] to state input [%s]", stateInputHandler.getClass(), stateInput.getClass()));
		stateInputHandler.register(stateInput);
	}
	

	/** Called after {@link #injectDependencies} has run (at the end of it).
	 * 
	 * The default implementation for this will:<br>
	 * - Setup the initial game scene using {@link #atSetupInitialGamescene()}.<br>
	 * 
	 * @see #injectDependencies
	 */
	protected void afterInjectDependencies() {
		
		// Create and assign initial scene
		atSetupInitialGamescene();
	}
	
	
	
	/* Lifecycle callbacks */

	@Override
	public void onEngage() {
		
		getEngine().getRenderService().getSurfaceHandler().addCallback(getStateRender());
	}
	
	
	/** Creates and assigns the initial gamescene to this state's scene control.
	 * <p>
	 * Uses {@link #createInitialScene()} to create the initial scene.
	 * 
	 * @see #createInitialScene()
	 */
	protected boolean atSetupInitialGamescene() {
		T initialGamescene = createInitialScene();
		
		boolean assigned = assignSceneTyped(INITIAL_SCENE_NAME, initialGamescene);
		if(assigned) {
			changeScene(INITIAL_SCENE_NAME);
		}
		
		return assigned;
	}
	
	/** Creates and returns the initial scene.
	 * <p>
	 * Can be overridden to return a custom initial scene.
	 * 
	 * @return An instance for the initial gamescene.
	 */
	protected T createInitialScene() {
		return stateSceneProvider.get();
	}

	
	@Override
	public void onEnter() {
		// Nothing yet
	}

	@Override
	public void onPause() {
		getSceneControl().pauseScene();
	}
	
	@Override
	public void onResume() {
		getSceneControl().resumeScene();
	}
	
	@Override
	public void onExit() {
		// Nothing yet
	}

	
	@Override
	public void onDisengage() {
		
		getEngine().getRenderService().getSurfaceHandler().removeCallback(getStateRender());
	}


	@Override
	public void update(long timeMillis) {
		
		getSceneControl().updateScene(timeMillis);
	}

	@Override
	public void render() {
		renderRunner.run();
	}
	


	/* Other methods */

	
	@Override
	public boolean assignScene(String name, IGameScene scene) {
		if(name == null || scene == null) {
			throw new NullPointerException("name and scene must not be null");
		}

		boolean assigned;
		if(getSceneType().isInstance(scene)) {
			// Cast and assign like normal
			@SuppressWarnings("unchecked") T typedScene = (T) scene;
			assigned = assignSceneTyped(name, typedScene);
		}
		else {
			getLog().e(getTag(), String.format("Failed to assign scene. Type [%s] is not an instance of scene type [%s]", scene.getClass(), getSceneType()));
			assigned = false;
		}
		
		return assigned;
	}
	
	
	@Override
	public boolean assignSceneTyped(String name, T scene) {
		if(name == null || scene == null) {
			throw new NullPointerException("name and scene must not be null");
		}
		
		boolean assigned = scene.assign(this);
		if(assigned) {
			getSceneControl().addScene(name, scene);
		}
		else {
			getLog().w(getTag(), String.format("Failed to assign scene. Scene [%s] assign() returned false", scene.getClass()));
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
	public boolean removeScene(String name) {
		IGameScene scene = getSceneControl().getScene(name);
		if(scene == null || scene.equals(getActiveScene())) {
			return false;
		}
		
		getSceneControl().removeScene(name);
		return true;
	}

	@Override
	public T getActiveScene() {
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
	public IModifiableGameSceneControl<T> getSceneControl() {
		return stateSceneControl;
	}


	/** Returns this state's scene type.
	 * <p>
	 * This type must be the generic type of this state's scene control.
	 * 
	 * @return The scene type of this state.
	 */
	protected Class<T> getSceneType() {
		return sceneType;
	}
	
	
	/** Returns an injector that can be used to inject this gamestate into dependencies via {@link InjectGameState}.
	 * 
	 * @return An injector for this gamestate.
	 */
	protected IParameterInjector getGamestateInjector() {
		return gamestateInjector;
	}

	
	/** Returns the scene provider for this state.
	 * 
	 * @return A provider for scenes of this state's scene type.
	 */
	protected Provider<? extends T> getSceneProvider() {
		return stateSceneProvider;
	}

	
	/** Override to return you custom tag.
	 * 
	 * @return The tag for this state.
	 */
	protected String getTag() {
		return BASE_TAG;
	}
	
	
	
	/* Setters */
	
	/** Sets the state scene provider.
	 * <p>
	 * If supported this gamestate will be injected via {@link InjectGameState}.
	 * 
	 * @param sceneProvider
	 * @throws NullPointerException If sceneProvider is null.
	 */
	private void setSceneProvider(Provider<? extends T> sceneProvider) {
		if(sceneProvider == null) {
			throw new NullPointerException();
		}
		
		gamestateInjector.injectInto(sceneProvider);
		this.stateSceneProvider = sceneProvider;
	}
	
	/** Sets the state scene control.
	 * <p>
	 * If supported this gamestate will be injected via {@link InjectGameState}.
	 * 
	 * @param sceneControl
	 * @throws NullPointerException If sceneControl is null.
	 */
	private void setSceneControl(IModifiableGameSceneControl<T> sceneControl) {
		if(sceneControl == null) {
			throw new NullPointerException();
		}

		gamestateInjector.injectInto(sceneControl);
		this.stateSceneControl = sceneControl;
	}

	/** Sets the state render.
	 * <p>
	 * If supported this gamestate will be injected via {@link InjectGameState}.
	 * <p>
	 * The given render will be registered as a {@link ISurfaceCallback} and the previous render will be unregistered.
	 * 
	 * @param render
	 * @throws NullPointerException If stateRender is null.
	 */
	protected void setStateRender(IStateRender render) {
		if(render == null) {
			throw new NullPointerException();
		}
		
		getEngine().getRenderService().getSurfaceHandler().removeCallback(this.stateRender);
		
		gamestateInjector.injectInto(render);
		this.stateRender = render;
		
		getEngine().getRenderService().getSurfaceHandler().addCallback(render);
	}

	/** Sets the state input handler.
	 * <p>
	 * If supported this gamestate will be injected via {@link InjectGameState}.
	 * 
	 * @param inputHandler
	 * @throws NullPointerException If inputHandler is null.
	 */
	protected void setStateInputHandler(IGameStateInputHandler inputHandler) {
		if(inputHandler == null) {
			throw new NullPointerException();
		}

		gamestateInjector.injectInto(inputHandler);
		this.stateInputHandler = inputHandler;
	}

	/** Sets the state control scheme.
	 * <p>
	 * If supported this gamestate will be injected via {@link InjectGameState}.
	 * 
	 * @param controlScheme
	 * @throws NullPointerException If controlScheme is null.
	 */
	protected void setStateControlScheme(IControlScheme controlScheme) {
		if(controlScheme == null) {
			throw new NullPointerException();
		}
		
		gamestateInjector.injectInto(controlScheme);
		this.stateControlScheme = controlScheme;
	}

	/** Sets the state control handler.
	 * <p>
	 * If supported this gamestate will be injected via {@link InjectGameState}.
	 * 
	 * @param controlHandler
	 * @throws NullPointerException If controlHandler is null.
	 */
	protected void setStateControlHandler(IControlHandler controlHandler) {
		if(controlHandler == null) {
			throw new NullPointerException();
		}

		gamestateInjector.injectInto(controlHandler);
		this.stateControlHandler = controlHandler;
	}

}
