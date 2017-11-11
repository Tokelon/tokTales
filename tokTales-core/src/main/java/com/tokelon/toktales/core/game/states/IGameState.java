package com.tokelon.toktales.core.game.states;

import com.tokelon.toktales.core.engine.IEngine;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.game.IGame;
import com.tokelon.toktales.core.game.screen.IStateRender;
import com.tokelon.toktales.core.game.screen.order.IRenderOrder;

public interface IGameState {
	
	// TODO: Implement onPause() and onResume() and call the gamescene methods as well

	
	/** Called when the state is added to the state control.
	 */
	public void onEngage();

	
	/** Called when the state becomes the active state.
	 */
	public void onEnter();

	
	/** Called when the state stops being the active state.
	 * 
	 */
	public void onExit();
	
	
	/** Called when the state is removed from the state control.
	 */
	public void onDisengage();
	
	
	
	public void update(long timeMillis);
	
	public void render();
	
	
	/* The problem with these is that they would have to be called manually which is very error prone.
	public void onCreate();
	public void onDestroy();
	*/
	
	
	/** Assigns a scene with the given name to this gamestate.<br><br>
	 * The scene must be compatible with this state, in that it must match the type this state expects.
	 * 
	 * @param name
	 * @param scene
	 * @return True if the given scene was assigned successfully, false if not.
	 * @throws IllegalArgumentException If name or scene is null.
	 */
	public boolean assignScene(String name, IGameScene scene);
	
	
	/** Changes to active scene to the on with the given name.
	 * 
	 * @param name
	 * @return True if the scene was successfully changed, false if not.
	 * @throws IllegalArgumentException If name is null.
	 */
	public boolean changeScene(String name);
	
	
	/**
	 * 
	 * @return The currently active scene.
	 */
	public IGameScene getActiveScene();
	
	/**
	 * 
	 * @return The scene control for this state.
	 */
	public IGameSceneControl<? extends IGameScene> getSceneControl();
	
	
	
	// Have it just inherit from IEngineContext instead of redeclaring these here?
	public IGame getGame();
	public IEngine getEngine();
	public ILogger getLog();
	public IEngineContext getEngineContext();
	
	
	/**
	 * 
	 * @return The render order.
	 */
	public IRenderOrder getRenderOrder();
	
	
	/**
	 * 
	 * @return The state render.
	 */
	public IStateRender getStateRender();

	
	/**
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
	
	

	//public void onSurfaceChange(ISurface surface); // Add this?
	
	// Have additional controller manager in the state as well?
	
	
}
