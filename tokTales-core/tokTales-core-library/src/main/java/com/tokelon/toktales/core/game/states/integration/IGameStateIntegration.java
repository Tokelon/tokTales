package com.tokelon.toktales.core.game.states.integration;

import com.tokelon.toktales.core.game.states.IGameScene;
import com.tokelon.toktales.core.game.states.IGameState;

public interface IGameStateIntegration {


	public default void onStateEngage(IGameState gamestate) { }
	
	public default void onStateEnter(IGameState gamestate) { }

	public default void onStatePause(IGameState gamestate) { }

	public default void onStateResume(IGameState gamestate) { }
	
	public default void onStateExit(IGameState gamestate) { }
	
	public default void onStateDisengage(IGameState gamestate) { }

	public default void onStateUpdate(IGameState gamestate, long timeMillis) { }
	
	public default void onStateRender(IGameState gamestate) { }
	
	public default void onSceneAssign(IGameState gamestate, String sceneName, IGameScene assignedScene) { }
	public default void onSceneChange(IGameState gamestate, String sceneName) { }
	public default void onSceneRemove(IGameState gamestate, String sceneName, IGameScene removedScene) { }
	
}
