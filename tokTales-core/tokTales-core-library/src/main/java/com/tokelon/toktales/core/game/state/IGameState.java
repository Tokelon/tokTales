package com.tokelon.toktales.core.game.state;

import com.tokelon.toktales.core.engine.IEngine;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.engine.log.ILogging;
import com.tokelon.toktales.core.game.IGame;
import com.tokelon.toktales.core.game.state.render.IGameStateRenderer;
import com.tokelon.toktales.core.game.state.scene.IGameScene;
import com.tokelon.toktales.core.game.state.scene.IGameSceneAssignment;
import com.tokelon.toktales.core.game.state.scene.IGameSceneControl;
import com.tokelon.toktales.core.render.order.IRenderOrder;

public interface IGameState {


	/** Called when the state is added to the state control.
	 */
	public void onEngage();

	
	/** Called when the state becomes the active state.
	 */
	public void onEnter();

	/** Called when the state is paused.
	 */
	public void onPause();

	/** Called when the state is resumed.
	 */
	public void onResume();
	
	/** Called when the state stops being the active state.
	 */
	public void onExit();
	
	
	/** Called when the state is removed from the state control.
	 */
	public void onDisengage();


	/** Called to update the state.
	 * 
	 * @param timeMillis
	 */
	public void onUpdate(long timeMillis);
	
	/** Called to render the state.
	 */
	public void onRender();

	
	/* The problem with these is that they would have to be called manually which is very error prone.
	public void onCreate();
	public void onDestroy();
	*/
	
	
	/** Assigns a scene to this state with the given name.
	 * <p>
	 * The scene must be compatible with this state, in that it must match the type this state expects.
	 * 
	 * @param name
	 * @param scene
	 * @return True if the given scene was assigned successfully, false if not.
	 * @throws NullPointerException If name or scene is null.
	 */
	public boolean assignScene(String name, IGameScene scene);
	
	/** Assigns a scene using a scene assignment parameter.
	 * <p>
	 * Returns whether the scene was actually assigned or not.
	 * 
	 * @param name
	 * @param sceneAssignment
	 * @return True if the given scene was successfully assigned, false if not.
	 * @throws NullPointerException If name or sceneAssignment is null.
	 */
	public boolean assignSceneCustom(String name, IGameSceneAssignment sceneAssignment);
	
	
	/** Changes to active scene to the on with the given name.
	 * 
	 * @param name
	 * @return True if the scene was successfully changed, false if not.
	 * @throws NullPointerException If name is null.
	 */
	public boolean changeScene(String name);
	
	/** Removes the scene for the given name if it exists.
	 * <p>
	 * If the scene is the currently active scene, it cannot be removed.
	 * 
	 * @param name
	 * @return True if the scene was successfully removed, false if not.
	 * @throws NullPointerException If name is null.
	 */
	public boolean removeScene(String name);

	
	/** You can override this method to return a custom scene type.
	 * 
	 * @return The currently active scene.
	 */
	public IGameScene getActiveScene();
	
	/** You can override this method to return a custom scene control type.
	 * 
	 * @return The scene control for this state.
	 */
	public IGameSceneControl<? extends IGameScene> getSceneControl();
	
	
	
	// Redeclare here to avoid being bound to the engine context (and have to include the injector)

	/**
	 * @return The context game.
	 */
	public IGame getGame();
	
	/**
	 * @return The context engine.
	 */
	public IEngine getEngine();
	
	/**
	 * @return The logging.
	 */
	public ILogging getLogging();

	/**
	 * @return The logger.
	 */
	public ILogger getLogger();
	
	/**
	 * @return The engine context.
	 */
	public IEngineContext getEngineContext(); // Maybe remove and use the methods above?
	
	
	// TODO: To expose this we need to implement belated callback support
	//public IGameStateIntegrator getIntegrator();
	
	
	/** Returns the render order for this state.
	 * 
	 * @return A render order.
	 */
	public IRenderOrder getRenderOrder();
	
	/** Returns the state renderer for this state.
	 * 
	 * @return A state render.
	 */
	public IGameStateRenderer getStateRenderer();

	
	/** The state input is a manager that collects input handlers,
	 *  and notifies them when new events happen.
	 *  <p>
	 *  The implementation is platform specific and provides extended functionality to register callbacks.
	 * 
	 * @return The state input.
	 */
	public IGameStateInput getStateInput();
	
	
	/** The state input handler is the default way of processing state related events,
	 * and transforming them into actions on the gamestate or gamescene.
	 * <br><br>
	 * The following details the expected workflow:<br>
	 * 1. A state and platform dependent input handler is implemented and set on this state.<br>
	 * 2. The input handler is registered at the state input.<br>
	 * 3. The input handler receives inputs and uses the control scheme to process them to actions.<br>
	 * 4. The input handler passes the actions to the control handler.<br>
	 * <br><br>
	 * Notes:<br>
	 * 1. Sometimes you will want to implement state specific input handler, control scheme and control handler.
	 * In those cases you can override these methods to return your state specific types.<br>
	 * 
	 * @return The state input handler.
	 */
	public IGameStateInputHandler getStateInputHandler();
	
	/** The control scheme provides a mapping from inputs to actions,
	 * and is used by the state input handler for this reason.
	 * <br><br>
	 * It is state and platform dependent.
	 * 
	 * @return The state control scheme.
	 */
	public IControlScheme getStateControlScheme();
	
	/** The control handler handles all actions it is given,
	 * and is normally used by the state input handler for this reason.
	 * <br><br>
	 * Note that state handler should first call the scene handler to make scene specific handling possible.
	 * 
	 * @return The state control handler.
	 */
	public IControlHandler getStateControlHandler();
	
}
