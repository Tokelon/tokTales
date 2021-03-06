package com.tokelon.toktales.core.game.state;

import java.lang.reflect.TypeVariable;

import javax.inject.Inject;
import javax.inject.Provider;

import com.google.common.reflect.TypeToken;
import com.google.inject.Key;
import com.tokelon.toktales.core.engine.IEngine;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.engine.log.ILogging;
import com.tokelon.toktales.core.game.IGame;
import com.tokelon.toktales.core.game.state.integration.IGameStateIntegrator;
import com.tokelon.toktales.core.game.state.integration.IGameStateIntegrator.IGameStateIntegratorFactory;
import com.tokelon.toktales.core.game.state.render.IGameStateRenderer;
import com.tokelon.toktales.core.game.state.scene.GenericGamesceneAssignment;
import com.tokelon.toktales.core.game.state.scene.IGameScene;
import com.tokelon.toktales.core.game.state.scene.IGameSceneAssignment;
import com.tokelon.toktales.core.game.state.scene.IGameSceneControl.IGameSceneControlFactory;
import com.tokelon.toktales.core.game.state.scene.IGameSceneControl.IModifiableGameSceneControl;
import com.tokelon.toktales.core.screen.surface.ISurface;
import com.tokelon.toktales.core.screen.surface.ISurfaceManager.ISurfaceCallback;
import com.tokelon.toktales.tools.core.inject.parameter.IParameterInjector;
import com.tokelon.toktales.tools.core.inject.parameter.IParameterInjector.IParameterInjectorFactory;
import com.tokelon.toktales.tools.core.sub.inject.annotations.RequiresInjection;

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
 * <br>- The default {@link #onRender()} implementation will draw using {@link #getRenderOrder()}.
 * 
 */
@RequiresInjection
public class BaseGamestate<T extends IGameScene> implements ITypedGameState<T> {
	// TODO: Adjust documentations to new inject logic

	public static final String INITIAL_SCENE_NAME = "base_gamestate_initial_scene";
	
	
	
	/* Base objects */
	private StateSurfaceCallback currentSurfaceCallback;
	private ISurface currentSurface;


	/* Injected base objects */
	private IEngineContext engineContext;
	private IEngine engine;
	private ILogging logging;
	private ILogger logger;
	private IGame game;
	
	private IParameterInjector gamestateInjector;
	private IGameStateIntegrator stateIntegrator;

	private IGameStateInput stateInput;

	
	/* State objects */
	private Provider<? extends T> stateSceneProvider; // Can be used with subclasses of T
	private IModifiableGameSceneControl<T> stateSceneControl; // Must have type T to allow for adding objects
	private IGameStateRenderer gameStateRenderer;
	
	private IGameStateInputHandler stateInputHandler;
	private IControlScheme stateControlScheme;
	private IControlHandler stateControlHandler;
	
	
	/* The sceneType must be of T.
	 * 
	 * It's possible to use TypeToken<? extends T> however then it would not be possible to assign any T,
	 * it would have to be an instance of sceneType. This would be confusing.
	 * There also is no real advantage in using it. You can still use it for the Provider.
	 */
	private final TypeToken<T> sceneType;
	
	/** Default constructor.
	 * <p>
	 * The scene type will be resolved from this state's type.
	 * <p>
	 * For example the state below will have the scene type <code>ICustomGamescene</code>.<br>
	 * {@code public CustomGamestate extends BaseGamestate<ICustomGamescene>}
	 * <p>
	 * You can also create instances of generic states with the use of anonymous subclasses (note the curly braces a the end).<br>
	 * <code>BaseGamestate{@literal<}ICustomGamescene{@literal>} gamestate = new BaseGamestate{@literal<}ICustomGamescene{@literal>}() { };</code>
	 * <p>
	 * Be aware that the scene type must be fully resolved for this to work.<br>
	 * The following will not work because the actual type of T (T is a type variable) will not be known at runtime.<br>
	 * <code>BaseGamestate{@literal<}T{@literal>} gamestate = new BaseGamestate{@literal<}T{@literal>}() { };</code>
	 * 
	 * @throws IllegalArgumentException If the scene type cannot be fully resolved.
	 */
	@Inject
	protected BaseGamestate() {
		sceneType = resolveSceneTypeFromStateContext();
		checkSceneTypeValidity(sceneType, "The scene type that was resolved from this state's type is not valid. You must use a subclass or provide a valid scene type. Invalid type variable: " + sceneType.getType());
	}
	
	/** Public constructor with a scene type token.
	 * <p>
	 * Use this to create instances of generic states dynamically and without the use of an anonymous subclass for the state.<br>
	 * Instead of the scene type being resolved from the context, it will be stored in the TypeToken that was given.
	 * <p>
	 * For example below we instantiate a state directly by passing it a new TypeToken. We don't have to create the TypeToken right there, it could have come from somewhere else too.<br>
	 * <code>BaseGamestate{@literal<}ICustomGamescene{@literal>} gamestate = new BaseGamestate{@literal<}ICustomGamescene{@literal>}(new TypeToken{@literal<}ICustomGamescene{@literal>}() { });</code> 
	 * <p>
	 * Be aware that the scene type must be fully resolved for this to work.<br>
	 * The following will not work because the actual type of T (T is a type variable) will not be known at runtime.<br>
	 * <code>BaseGamestate{@literal<}T{@literal>} gamestate = new BaseGamestate{@literal<}T{@literal>}(new TypeToken{@literal<}T{@literal>}() { });</code>
	 * 
	 * @param sceneTypeToken The type token that should be used for the scene type.
	 * @throws IllegalArgumentException If the scene type cannot be fully resolved.
	 */
	public BaseGamestate(TypeToken<T> sceneTypeToken) {
		if(sceneTypeToken == null) {
			throw new NullPointerException("sceneTypeToken must not be null");
		}
		
		sceneType = sceneTypeToken;
		checkSceneTypeValidity(sceneType, "The scene type token you provided is not valid. Invalid type variable: " + sceneTypeToken.getType());
	}
	
	/** Public constructor with a scene type class.
	 * <p>
	 * Use this to create instances of generic gamestate types.
	 * 
	 * @param sceneTypeClass The class that should be used for the scene type.
	 * @throws IllegalArgumentException If the scene type cannot be fully resolved.
	 */
	public BaseGamestate(Class<T> sceneTypeClass) {
		if(sceneTypeClass == null) {
			throw new NullPointerException("sceneTypeClass must not be null");
		}
		
		sceneType = TypeToken.of(sceneTypeClass);
		checkSceneTypeValidity(sceneType, "The scene type class you provided is not valid. Invalid type variable: " + sceneTypeClass);
	}
	
	
	/**
	 * @return A type token for the type of this state's scenes.
	 */
	@SuppressWarnings("serial")
	protected TypeToken<T> resolveSceneTypeFromStateContext() {
		return new TypeToken<T>(getClass()) {};
	}
	
	/** Checks if the type of the given scene type token is valid as a scene type.
	 * <br>
	 * Throws an exception with the given error message if the scene type is not valid.
	 * 
	 * @param sceneTypeToken
	 * @param errorMessage
	 * @throws IllegalArgumentException If the type is not valid.
	 */
	protected void checkSceneTypeValidity(TypeToken<T> sceneTypeToken, String errorMessage) {
		if(!isTypeValidForInjection(sceneTypeToken)) {
			throw new IllegalArgumentException(errorMessage);
		}
	}
	
	/** Returns whether the type of the given type token can be injected. 
	 * 
	 * @param token The type token for the type.
	 * @return True if the type can be injected, false if not.
	 */
	protected boolean isTypeValidForInjection(TypeToken<T> token) {
		// TODO: Are Wildcard types valid?
		return !(token.getType() instanceof TypeVariable);
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
			IGameStateRenderer defaultRender,
			IGameStateInputHandler defaultInputHandler,
			IControlScheme defaultControlScheme,
			IControlHandler defaultControlHandler
	) {
		this(null, null, defaultRender, defaultInputHandler, defaultControlScheme, defaultControlHandler);
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
			Provider<? extends T> defaultSceneProvider,
			IModifiableGameSceneControl<T> defaultSceneControl
	) {
		this(defaultSceneProvider, defaultSceneControl, null, null, null, null);
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
			Provider<? extends T> defaultSceneProvider,
			IModifiableGameSceneControl<T> defaultSceneControl,
			IGameStateRenderer defaultRender,
			IGameStateInputHandler defaultInputHandler,
			IControlScheme defaultControlScheme,
			IControlHandler defaultControlHandler
	) {
		this();
		
		// Any of these can be null
		this.stateSceneProvider = defaultSceneProvider;
		this.stateSceneControl = defaultSceneControl;
		this.gameStateRenderer = defaultRender;
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
			IGameStateIntegratorFactory gamestateIntegratorFactory,
			IEngineContext engineContext,
			IGameStateInput gamestateInput,
			IGameSceneControlFactory gamesceneControlFactory,
			IGameStateRenderer render,
			IGameStateInputHandler inputHandler,
			IControlScheme controlScheme,
			IControlHandler controlHandler
	) {
		// Initialize internal dependencies
		this.gamestateInjector = parameterInjectorFactory.create(InjectGameState.class, this);
		this.stateIntegrator = gamestateIntegratorFactory.create(this);

		// Dependencies have been injected
		afterDependencyInjection();
		

		// Determine the default base dependencies
		Provider<? extends T> defaultSceneProvider = stateSceneProvider == null ? createDefaultSceneProvider(engineContext) : stateSceneProvider;
		IModifiableGameSceneControl<T> defaultSceneControl = stateSceneControl == null ? createDefaultSceneControl(engineContext, gamesceneControlFactory) : stateSceneControl;
		
		// Initialize base dependencies
		initBaseDependencies(engineContext, gamestateInput, defaultSceneProvider, defaultSceneControl);
		afterInitBaseDependencies();
		
		
		// Determine the default state dependencies
		IGameStateRenderer defaultStateRender = gameStateRenderer == null ? render : gameStateRenderer;
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
	@SuppressWarnings("unchecked")
	protected Provider<? extends T> createDefaultSceneProvider(IEngineContext context) {
		TypeToken<T> sceneTypeToken = getSceneTypeToken();
		checkSceneTypeValidity(sceneTypeToken, "The scene type is not valid. Invalid type variable: " + sceneTypeToken.getType());
		
		Key<T> sceneKey = (Key<T>) Key.get(sceneTypeToken.getType());
		return context.getInjector().getProvider(sceneKey);
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
			IEngineContext defaultEngineContext,
			IGameStateInput defaultGamestateInput,
			Provider<? extends T> defaultGamesceneProvider,
			IModifiableGameSceneControl<T> defaultGamesceneControl
	) {
		this.engineContext = defaultEngineContext;
		this.engine = defaultEngineContext.getEngine();
		this.logging = defaultEngineContext.getLogging();
		this.logger = defaultEngineContext.getLogging().getLogger(getClass()); // Add scene type to logger name?
		this.game = defaultEngineContext.getGame();

		setGamestateInput(defaultGamestateInput);
		setGamesceneProvider(defaultGamesceneProvider);
		setGamesceneControl(defaultGamesceneControl);
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
			IGameStateRenderer defaultRender,
			IGameStateInputHandler defaultInputHandler,
			IControlScheme defaultControlScheme,
			IControlHandler defaultControlHandler
	) {
		setStateRenderer(defaultRender);
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
		getLogger().debug("Registering state input handler [{}] to state input [{}]", stateInputHandler, stateInput);
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
	
	
	
	/* Lifecycle callbacks */

	@Override
	public void onEngage() {
		
		getIntegrator().onEngage();
	}

	
	@Override
	public void onEnter() {
		getEngine().getRenderService().getSurfaceManager().addCallback(currentSurfaceCallback = new StateSurfaceCallback());
		
		getIntegrator().onEnter();
	}

	@Override
	public void onPause() {
		getSceneControl().pauseScene();
		
		
		getIntegrator().onPause();
	}
	
	@Override
	public void onResume() {
		getSceneControl().resumeScene();
		
		
		getIntegrator().onResume();
	}
	
	@Override
	public void onExit() {
		getEngine().getRenderService().getSurfaceManager().removeCallback(currentSurfaceCallback);
		
		getIntegrator().onExit();
	}

	
	@Override
	public void onDisengage() {
		
		getIntegrator().onDisengage();
	}


	@Override
	public void onUpdate(long timeMillis) {
		getSceneControl().updateScene(timeMillis);
		
		
		getIntegrator().onUpdate(timeMillis);
	}

	@Override
	public void onRender() {
		getStateRenderer().renderState();
		
		
		getIntegrator().onRender();
	}
	
	
	/** Called when a surface is created.
	 * <p>
	 * The default implementation saves the first surface that is created and sets it as current.
	 * The surface will remain the current as long as it is not destroyed.
	 * 
	 * @param surface
	 * @see {@link ISurfaceCallback#surfaceCreated(ISurface)}
	 */
	protected void onSurfaceCreated(ISurface surface) {
		if(currentSurface == null) {
			currentSurface = surface;
		}
	}
	
	/** Called when a surface is changed.
	 * <p>
	 * The implementation should test whether the given surface is the current one with {@link #getCurrentSurface()}.
	 * <p>
	 * The default implementation does nothing.
	 * 
	 * @param surface
	 * @see {@link ISurfaceCallback#surfaceChanged(ISurface)}
	 */
	protected void onSurfaceChanged(ISurface surface) {
	}
	
	/** Called when a surface is destroyed.
	 * <p>
	 * The implementation should test whether the given surface is the current one with {@link #getCurrentSurface()}.
	 * <p>
	 * The default implementation sets the surface returned by {@link #getCurrentSurface()} to null if it's equal the given one. 
	 * 
	 * @param surface
	 * @see {@link ISurfaceCallback#surfaceDestroyed(ISurface)}
	 */
	protected void onSurfaceDestroyed(ISurface surface) {
		if(surface.equals(currentSurface)) {
			currentSurface = null;
		}
	}
	
	

	/* Other methods */
	
	/** {@inheritDoc}
	 * <p>
	 * The default implementation checks if the scene is an instance of this state's scene type,
	 * and if yes calls {@link #assignSceneTyped(String, IGameScene)}.<br>
	 * Note that the type check will not consider any generic parts, if you need that use {@link #assignSceneCustom(String, IGameSceneAssignment)}. 
	 */
	@Override
	public boolean assignScene(String name, IGameScene scene) {
		if(name == null || scene == null) {
			throw new NullPointerException("name and scene must not be null");
		}

		boolean assigned;
		if(getSceneTypeToken().getRawType().isInstance(scene)) {
			// Cast and assign like normal
			@SuppressWarnings("unchecked") T typedScene = (T) scene;
			assigned = assignSceneTyped(name, typedScene);
		}
		else {
			getLogger().error("Failed to assign scene with name '{}'. Type [{}] is not a subtype of scene type [{}]", name, scene.getClass(), getSceneTypeToken());
			assigned = false;
		}
		
		return assigned;
	}
	

	/** {@inheritDoc}
	 * <p>
	 * The default implementation checks if the assignment scene is of type of this state's scene type,
	 * and if yes calls {@link #assignSceneTyped(String, IGameScene)}.<br>
	 * Note that if the scene assignment returns true for the type check, it will try to cast the scene to this state's scene type.
	 * If the cast fails it will throw an {@link IllegalArgumentException}.
	 * <p>
	 * You can use this method to make type checks that respect generic types, using something like {@link GenericGamesceneAssignment}.
	 */
	@Override
	public boolean assignSceneCustom(String name, IGameSceneAssignment sceneAssignment) {
		if(name == null || sceneAssignment == null) {
			throw new NullPointerException("name and scene must not be null");
		}
		
		boolean assigned;
		if(sceneAssignment.isSceneOfType(getSceneTypeToken().getType())) {
			T typedScene;
			try {
				@SuppressWarnings("unchecked") T sceneCast = (T) sceneAssignment.getScene();
				typedScene = sceneCast;
			}
			catch(ClassCastException cce) {
				throw new IllegalArgumentException("Scene assignment failed due to invalid scene type", cce);
			}
			
			if(typedScene == null) {
				assigned = false;
			}
			else {
				assigned = assignSceneTyped(name, typedScene);
			}
		}
		else {
			getLogger().error("Failed to assign scene with name '{}'. Type [{}] is not a subtype of scene type [{}]", name, sceneAssignment.getSceneType(), getSceneTypeToken());
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
			scene.onAssign();
			
			
			getIntegrator().onSceneAssign(name, scene);
		}
		else {
			getLogger().warn("Failed to assign scene. Scene [{}] assign() returned false", scene.getClass());
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
		getStateRenderer().updateCamera(scene.getSceneCamera());
		
		
		getIntegrator().onSceneChange(name);
		return true;
	}

	@Override
	public boolean removeScene(String name) {
		IGameScene scene = getSceneControl().getScene(name);
		if(scene == null || scene.equals(getActiveScene())) {
			return false;
		}
		
		getSceneControl().removeScene(name);
		
		
		getIntegrator().onSceneRemove(name, scene);
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
	public ILogging getLogging() {
		return logging;
	}
	
	@Override
	public ILogger getLogger() {
		return logger;
	}

	@Override
	public IGame getGame() {
		return game;
	}


	@Override
	public IGameStateRenderer getStateRenderer() {
		return gameStateRenderer;
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


	/** Returns the integrator for this state.
	 * 
	 * @return A gamestate integrator.
	 */
	protected IGameStateIntegrator getIntegrator() {
		return stateIntegrator;
	}
	

	/** Returns the type token for this state's scene type.
	 * 
	 * @return The TypeToken used for the scene type.
	 */
	protected TypeToken<T> getSceneTypeToken() {
		return sceneType;
	}
	
	/** Returns a class representing the raw type of this state's scenes.
	 * <p>
	 * Use this if this state does not have a generic scene type or you don't care about the generic part.
	 * 
	 * @return The raw type of this state's scenes.
	 */
	protected Class<? super T> getSceneTypeClass() {
		return sceneType.getRawType();
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


	/**
	 * @return The current surface, or null if there is none.
	 */
	protected ISurface getCurrentSurface() { // TODO: Keep this?
		return currentSurface;
	}

	
	
	
	/* Setters */
	
	/** Sets the state input.
	 * <p>
	 * If supported this gamestate will be injected via {@link InjectGameState}.
	 * 
	 * @param gamestateInput
	 * @throws NullPointerException If gamestateInput is null.
	 */
	private void setGamestateInput(IGameStateInput gamestateInput) {
		if(gamestateInput == null) {
			throw new NullPointerException();
		}
		
		gamestateInjector.injectInto(gamestateInput);
		this.stateInput = gamestateInput;
	}
	
	/** Sets the state scene provider.
	 * <p>
	 * If supported this gamestate will be injected via {@link InjectGameState}.
	 * 
	 * @param gamesceneProvider
	 * @throws NullPointerException If gamesceneProvider is null.
	 */
	private void setGamesceneProvider(Provider<? extends T> gamesceneProvider) {
		if(gamesceneProvider == null) {
			throw new NullPointerException();
		}
		
		gamestateInjector.injectInto(gamesceneProvider);
		this.stateSceneProvider = gamesceneProvider;
	}
	
	/** Sets the state scene control.
	 * <p>
	 * If supported this gamestate will be injected via {@link InjectGameState}.
	 * 
	 * @param gamesceneControl
	 * @throws NullPointerException If gamesceneControl is null.
	 */
	private void setGamesceneControl(IModifiableGameSceneControl<T> gamesceneControl) {
		if(gamesceneControl == null) {
			throw new NullPointerException();
		}

		gamestateInjector.injectInto(gamesceneControl);
		this.stateSceneControl = gamesceneControl;
	}

	/** Sets the state render.
	 * <p>
	 * If supported this gamestate will be injected via {@link InjectGameState}.
	 * <p>
	 * The given render will be registered as a {@link ISurfaceCallback} and the previous render will be unregistered.
	 * 
	 * @param renderer
	 * @throws NullPointerException If renderer is null.
	 */
	protected void setStateRenderer(IGameStateRenderer renderer) {
		if(renderer == null) {
			throw new NullPointerException();
		}

		if(gameStateRenderer != null) {
			getEngine().getRenderService().getSurfaceManager().removeCallback(gameStateRenderer.getSurfaceCallback());
		}

		gamestateInjector.injectInto(renderer);
		this.gameStateRenderer = renderer;

		ISurfaceCallback surfaceCallback = renderer.getSurfaceCallback();
		if(surfaceCallback == null) {
			logger.debug("No surface callback was registered for state renderer: {} [{}]", renderer.getClass().getName(), renderer);
		}
		else {
			getEngine().getRenderService().getSurfaceManager().addCallback(surfaceCallback);
		}
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
	
	
	/** Callback wrapper for surface events. 
	 */
	protected class StateSurfaceCallback implements ISurfaceCallback {
		@Override
		public void surfaceCreated(ISurface surface) {
			onSurfaceCreated(surface);
		}

		@Override
		public void surfaceChanged(ISurface surface) {
			onSurfaceChanged(surface);
		}

		@Override
		public void surfaceDestroyed(ISurface surface) {
			onSurfaceDestroyed(surface);
		}
	}

}
