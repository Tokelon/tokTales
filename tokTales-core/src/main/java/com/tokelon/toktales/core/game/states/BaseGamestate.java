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
public class BaseGamestate implements IGameState {

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
	private Provider<? extends IGameScene> stateSceneProvider;
	private IModifiableGameSceneControl<? extends IGameScene> stateSceneControl;
	private IStateRender stateRender;
	
	private IGameStateInputHandler stateInputHandler;
	private IControlScheme stateControlScheme;
	private IControlHandler stateControlHandler;
	


	/** Default constructor.
	 */
	@Inject
	public BaseGamestate() {
		// Used for injection
	}
	
	
	/** Constructor with custom scene type paramters.
	 */
	protected BaseGamestate(
			Provider<? extends IGameScene> defaultSceneProvider,
			IModifiableGameSceneControl<? extends IGameScene> defaultSceneControl
	) {
		this.stateSceneProvider = defaultSceneProvider;
		this.stateSceneControl = defaultSceneControl;
	}

	
	/** Constructor with optional state objects.
	 * <p>
	 * You can pass any objects you'd like to assign. Null values are permitted.
	 * <p>
	 * <b>Note: Injected base objects like the context will not be available here.</b>
	 * <p>
	 * Useful for custom constructor injection, for manual creation see {@link #initStateDependencies}.<br>
	 * Alternatively you can use method injection, which will be called after base injection has happened.
	 * 
	 */
	protected BaseGamestate(
			Provider<? extends IGameScene> defaultSceneProvider,
			IModifiableGameSceneControl<? extends IGameScene> defaultSceneControl,
			IStateRender defaultRender,
			IGameStateInputHandler defaultInputHandler,
			IControlScheme defaultControlScheme,
			IControlHandler defaultControlHandler
	) {
		this();
		
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
	 * Note that this method will be called before any <i>method</i> injections of subtypes.
	 * 
	 * @see #initBaseDependencies
	 * @see #initStateDependencies
	 */
	@Inject
	protected void injectDependencies(
			IParameterInjectorFactory parameterInjectorFactory,
			Provider<IGameScene> sceneProvider, // Cannot use extends because of injection, use the ctor instead
			IModifiableGameSceneControl<? extends IGameScene> sceneControl,
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

		
		Provider<? extends IGameScene> defaultSceneProvider = stateSceneProvider == null ? sceneProvider : stateSceneProvider;
		setSceneProvider(defaultSceneProvider);

		IModifiableGameSceneControl<? extends IGameScene> defaultSceneControl = stateSceneControl == null ? sceneControl : stateSceneControl;
		setSceneControl(defaultSceneControl);
		
		
		// Dependencies have been injected
		afterInjectDependencies();
		
		
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
		
	}

	/** Called after dependencies have been injected, during {@link #injectDependencies}.
	 * 
	 * @see #injectDependencies
	 */
	protected void afterInjectDependencies() { }
	
	
	/* Add dependencies here that are the same for all gamestate classes.
	 * 
	 * Only base dependencies are injected here,
	 * anything that is a state dependency must be set separately.
	 */
	/** Initializes the base dependencies.
	 * <p>
	 * Overriding this method is usually not necessary, however if you do you must call super().
	 * <p>
	 * This method will be called after {@link #injectDependencies}} and {@link #afterInjectDependencies()}.
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
	 * This method will be called after {@link #initBaseDependencies} and {@link #afterInitBaseDependencies()},
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
	 * by calling {@link IGameStateInputHandler#register(IGameStateInput)}.
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
	

	
	/* Lifecycle callbacks */

	@Override
	public void onEngage() {
		// Create and assign initial scene
		atSetupInitialGamescene();
		
		getEngine().getRenderService().getSurfaceHandler().addCallback(getStateRender());
	}
	
	/** Creates and assigns the initial gamescene to this state's scene control.
	 * <p>
	 * Uses {@link #atCreateInitialScene()} to create the initial scene.
	 * 
	 * @see #atCreateInitialScene()
	 */
	protected void atSetupInitialGamescene() {
		IGameScene initialGamescene = atCreateInitialScene();
		Class<? extends IGameScene> stateSceneType = getStateSceneType();
		
		if(stateSceneType.isInstance(initialGamescene)) {
			if(assignScene(INITIAL_SCENE_NAME, initialGamescene)) {
				changeScene(INITIAL_SCENE_NAME);
			}
			else {
				getLog().e(getTag(), String.format("Failed to assign initial scene of type [%s] to scene control of type [%s]", initialGamescene.getClass(), stateSceneType));
			}
		}
		else {
			getLog().w(getTag(), String.format("Initial scene of type [%s] is not compatible with scene control type [%s]", initialGamescene.getClass(), stateSceneType));
		}
	}
	
	/** Creates and returns the initial scene.
	 * <p>
	 * Can be overridden to return a custom initial scene.
	 * 
	 * @return An instance of the initial gamescene.
	 */
	protected IGameScene atCreateInitialScene() { // TODO: Keep this?
		return stateSceneProvider.get();
	}

	
	@Override
	public void onEnter() {
		getActiveScene().onStart();
	}

	
	@Override
	public void onExit() {
		getActiveScene().onStop();
	}

	
	@Override
	public void onDisengage() {
		// TODO: Implement
		
		// Remove all scenes?
		//getSceneControl().clear();
		
		
		getEngine().getRenderService().getSurfaceHandler().removeCallback(getStateRender());
	}


	@Override
	public void update(long timeMillis) {
		getActiveScene().onUpdate(timeMillis);
	}

	@Override
	public void render() {
		renderRunner.run();
	}
	


	/* Other methods */

	/** Returns this state's scene type.
	 * <p>
	 * This type must be the generic type of this state's scene control.
	 * 
	 * @return The scene type of this state.
	 * @see #getSceneControlTyped(Class)
	 */
	protected Class<? extends IGameScene> getStateSceneType() {
		return IGameScene.class;
	}
	
	
	/** Returns the state scene provider with compatibility to this state's scene type.
	 * 
	 * @param sceneType
	 * @return This state's scene provider, in compatibility with this state's scene type.
	 */
	@SuppressWarnings("unchecked")
	protected <T extends IGameScene> Provider<T> getSceneProviderTyped(Class<T> sceneType) {
		checkIfSceneTypeCompatible(sceneType);
		
		Provider<T> typedSceneProvider = (Provider<T>) stateSceneProvider;
		return typedSceneProvider;
	}
	
	/** Returns the state scene control with compatibility to this state's scene type.
	 * <p>
	 * <b>Important:</b> The given type must be equal or a supertype of this state's scene type (returned by {@link #getStateSceneType()}).
	 * This means that to use this method with a custom type, you first need to override {@link #getStateSceneType()} to return your custom type.
	 * 
	 * @param sceneType A type compatible with this state's scene type.
	 * @return This state's scene control, in compatibility with this state's scene type.
	 * @throws RuntimeException If the given type is not equal or a supertype of this state's scene type.
	 */
	@SuppressWarnings("unchecked")
	protected <T extends IGameScene> IModifiableGameSceneControl<T> getSceneControlTyped(Class<T> sceneType) {
		// Do a type check first
		checkIfSceneTypeCompatible(sceneType);
		
		IModifiableGameSceneControl<T> typedSceneControl = (IModifiableGameSceneControl<T>) stateSceneControl;
		return typedSceneControl;
	}
	
	private void checkIfSceneTypeCompatible(Class<? extends IGameScene> sceneType) {
		Class<? extends IGameScene> stateSceneType = getStateSceneType();
		if(!sceneType.isAssignableFrom(stateSceneType)) { // stateSceneType must be same or a superclass of sceneType
			throw new RuntimeException(String.format("Invalid scene type! This state's scene type [%s] is not assignable from the given type [%s]. Make sure you override getStateSceneType()", stateSceneType, sceneType));
		}
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public boolean assignScene(String name, IGameScene scene) {
		if(name == null || scene == null) {
			throw new NullPointerException("name and scene must not be null");
		}
		
		Class<? extends IGameScene> stateSceneType = getStateSceneType();
		
		boolean assigned;
		if(stateSceneType.isInstance(scene)) {
			
			assigned = scene.assign(this);
			if(assigned) {
				// Using raw type here - should be safe because of type check
				IModifiableGameSceneControl sceneControl = getSceneControl();
				sceneControl.addScene(name, scene);
			}
			else {
				getLog().w(getTag(), String.format("Failed to assign scene. Scene [%s] assign() returned false", scene.getClass()));
			}
		}
		else {
			getLog().e(getTag(), String.format("Failed to assign scene. Type [%s] is not an instance of scene control type [%s]", scene.getClass(), stateSceneType));
			assigned = false;
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
	protected <T extends IGameScene> boolean assignSceneGeneric(Class<T> sceneClass, IModifiableGameSceneControl<T> sceneControl, String sceneName, IGameScene scene) { // Why is sceneClass needed?
		if(sceneClass == null || sceneControl == null || sceneName == null || scene == null) {
			throw new NullPointerException("sceneClass, sceneControl, name, scene must not be null");
		}

		// Check if correct type
		if(!sceneClass.isInstance(scene)) {
			getLog().e(getTag(), String.format("Scene \"%s\" is not assignable to gamestate \"%s\"", sceneName, this.getClass().getSimpleName()));
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
	public boolean removeScene(String name) {
		IGameScene scene = getSceneControl().getScene(name);
		if(scene == null || scene.equals(getActiveScene())) {
			return false;
		}
		
		getSceneControl().removeScene(name);
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


	/** Override to return you custom tag.
	 * 
	 * @return The tag for this state.
	 */
	protected String getTag() {
		return BASE_TAG;
	}
	
	
	/** Returns an injector that can be used to inject this gamestate into dependencies via {@link InjectGameState}.
	 * 
	 * @return An injector for this gamestate.
	 */
	protected IParameterInjector getGamestateInjector() {
		return gamestateInjector;
	}
	
	
	
	
	/* Setters */
	
	/** Sets the state scene provider.
	 * <p>
	 * If supported this gamestate will be injected via {@link InjectGameState}.
	 * 
	 * @param sceneProvider
	 * @throws NullPointerException If sceneProvider is null.
	 */
	private void setSceneProvider(Provider<? extends IGameScene> sceneProvider) {
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
	private void setSceneControl(IModifiableGameSceneControl<? extends IGameScene> sceneControl) {
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
