package com.tokelon.toktales.core.game.states;

import javax.inject.Inject;

import com.tokelon.toktales.core.engine.IEngine;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.engine.inject.RequiresInjection;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.engine.render.ISurfaceHandler.ISurfaceCallback;
import com.tokelon.toktales.core.game.IGame;
import com.tokelon.toktales.core.game.screen.EmptyStateRender;
import com.tokelon.toktales.core.game.screen.IStateRender;
import com.tokelon.toktales.core.game.screen.order.IRenderOrder;
import com.tokelon.toktales.core.game.screen.order.RenderRunner;
import com.tokelon.toktales.core.game.states.IGameSceneControl.IModifiableGameSceneControl;
import com.tokelon.toktales.core.render.DefaultTextureCoordinator;
import com.tokelon.toktales.tools.inject.IParameterInjector;
import com.tokelon.toktales.tools.inject.ParameterInjector;

/** Use as a base for gamestates.
 * <p>
 * Notes:<br>
 * <br>- Base dependencies will be injected, see {@link #injectBaseDependencies(IEngineContext, IRenderOrder, IGameStateInput)}.
 * <br>- State dependencies need to be set via setters or {@link #initStateDependencies(IStateRender, IGameStateInputHandler, IControlScheme, IControlHandler)}, or they will default to empty implementations.
 * <br>- State dependencies will also have the gamestate injected when set, if they use {@link InjectGameState}. To use this feature with other dependencies, see {@link #getGamestateInjector()}.
 * <br>- Inject your own dependencies by using {@link Inject}.
 * <br>- If needed there are hooks for {@link #afterInjectBaseDependencies()} and {@link #afterInitStateDependencies()}.
 * <br>- Override {@link #getSceneControl()} to return your custom scene control and use the helper {@link #assignSceneGeneric(Class, IModifiableGameSceneControl, String, IGameScene)}.
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

	private IModifiableGameSceneControl<? extends IGameScene> stateSceneControl;
	private IStateRender stateRender;
	
	private IGameStateInputHandler stateInputHandler;
	private IControlScheme stateControlScheme;
	private IControlHandler stateControlHandler;
	


	/** Default constructor.
	 */
	@Inject
	public BaseGamestate() {
		this.gamestateInjector = new ParameterInjector(InjectGameState.class, this);
	}

	
	/** Constructor with optional state objects.
	 * <p>
	 * You can pass any objects you'd like to assign. Null values are permitted.
	 * <p>
	 * <b>Note: Injected base objects like the context will not be available here.</b>
	 * <p>
	 * Useful with custom ctor injection, for manual creation see {@link #initStateDependencies(IStateRender, IGameStateInputHandler, IControlScheme, IControlHandler)}.<br>
	 * Alternatively you can use method injection, which will be called after base injection has happened.
	 * 
	 * @param defaultSceneControl
	 * @param defaultRender
	 * @param defaultInputHandler
	 * @param defaultControlScheme
	 * @param defaultControlHandler
	 */
	public BaseGamestate(
			IModifiableGameSceneControl<? extends IGameScene> defaultSceneControl,
			IStateRender defaultRender,
			IGameStateInputHandler defaultInputHandler,
			IControlScheme defaultControlScheme,
			IControlHandler defaultControlHandler
	) {
		this();
		
		// Any of these can be null
		this.stateSceneControl = defaultSceneControl;
		this.stateRender = defaultRender;
		this.stateInputHandler = defaultInputHandler;
		this.stateControlScheme = defaultControlScheme;
		this.stateControlHandler = defaultControlHandler;
	}

	
	/* Add dependencies here that are the same for all gamestate classes.
	 * 
	 * Only base dependencies are injected here,
	 * anything that is a state dependency must be set separately.
	 */
	/** Injects the base objects and starts state object creation.
	 * <p>
	 * Overriding this method will usually not be necessary, however if you do you must call super().
	 * <p>
	 * Note that this method will be called before any method injections of subtypes.
	 * 
	 * @param context
	 * @param renderOrder
	 * @param input
	 * 
	 * @see #afterInjectBaseDependencies()
	 */
	@Inject
	protected void injectBaseDependencies(
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

		// After inject callback
		afterInjectBaseDependencies();

		
		// Create defaults if not already set
		IModifiableGameSceneControl<? extends IGameScene> defaultSceneControl;
		if(stateSceneControl == null) {
			defaultSceneControl = new GameSceneControl<IGameScene>(log);
		}
		else {
			defaultSceneControl = stateSceneControl;
		}
		
		IStateRender defaultStateRender;
		if(stateRender == null) {
			defaultStateRender = new EmptyStateRender(new DefaultTextureCoordinator(context.getGame().getContentManager().getTextureManager())); 
		}
		else {
			defaultStateRender = stateRender;
		}
		
		IGameStateInputHandler defaultStateInputHandler;
		if(stateInputHandler == null) {
			defaultStateInputHandler = new IGameStateInputHandler.EmptyGameStateInputHandler();
		}
		else {
			defaultStateInputHandler = stateInputHandler;
		}
		
		IControlScheme defaultStateControlScheme;
		if(stateControlScheme == null) {
			defaultStateControlScheme = new IControlScheme.EmptyControlScheme();
		}
		else {
			defaultStateControlScheme = stateControlScheme;
		}
		
		IControlHandler defaultStateControlHandler;
		if(stateControlHandler == null) {
			defaultStateControlHandler = new IControlHandler.EmptyControlHandler();
		}
		else {
			defaultStateControlHandler = stateControlHandler;
		}
		
		initStateDependencies(defaultSceneControl, defaultStateRender, defaultStateInputHandler, defaultStateControlScheme, defaultStateControlHandler);
	}

	/** Callback for after base injection has happened.
	 * 
	 */
	protected void afterInjectBaseDependencies() { }


	
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
	/** Sets the state objects to the defaults.
	 * Can be overridden to pass custom state objects.
	 * <p>
	 * If any state objects have been set before this call (ex. in the constructor),
	 * those will be passed here as parameters.
	 * <p>
	 * This method will be called after {@link #injectBaseDependencies(IEngineContext, IRenderOrder, IGameStateInput)} and {@link #afterInjectBaseDependencies()},
	 * so the context and any other injected objects will have their injected values.
	 * 
	 * @param defaultRender
	 * @param defaultInputHandler
	 * @param defaultControlScheme
	 * @param defaultControlHandler
	 * 
	 * @see #afterInitStateDependencies()
	 */
	protected void initStateDependencies(
			IModifiableGameSceneControl<? extends IGameScene> defaultSceneControl,
			IStateRender defaultRender,
			IGameStateInputHandler defaultInputHandler,
			IControlScheme defaultControlScheme,
			IControlHandler defaultControlHandler
	) {
		setSceneControl(defaultSceneControl);
		setStateRender(defaultRender);
		setStateInputHandler(defaultInputHandler);
		setStateControlScheme(defaultControlScheme);
		setStateControlHandler(defaultControlHandler);
		
		
		afterInitStateDependencies();
	}
	
	/** Callback for after state dependencies have been initialized.
	 * <p>
	 * The default implementation for this will:<br>
	 * - Register the state input handler given by {@link #getStateInputHandler()}, to the state input given by {@link #getStateInput()}
	 * by calling {@link IGameStateInputHandler#register(IGameStateInput)}.
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
	protected IGameScene atCreateInitialScene() {
		return new BaseGamescene();
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
	
	/** Returns the state scene control but in compatibility to this state's scene type.
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
		Class<? extends IGameScene> stateSceneType = getStateSceneType();
		if(!sceneType.isAssignableFrom(stateSceneType)) { // stateSceneType must be same or a superclass of sceneType
			throw new RuntimeException(String.format("Invalid scene type! This state's scene type [%s] is not assignable from the given type [%s]. Make sure you override getStateSceneType()", stateSceneType, sceneType));
		}
		
		
		IModifiableGameSceneControl<T> typeSceneControl = (IModifiableGameSceneControl<T>) stateSceneControl;
		return typeSceneControl;
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
	
	
	/** The injector can be used to inject this gamestate into dependencies via {@link InjectGameState}.
	 * 
	 * @return An injector for this gamestate.
	 */
	protected IParameterInjector getGamestateInjector() {
		return gamestateInjector;
	}
	
	
	
	
	/* Setters */
	
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
