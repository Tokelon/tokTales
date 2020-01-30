package com.tokelon.toktales.core.game.state.integration;

import com.tokelon.toktales.core.game.state.IGameState;
import com.tokelon.toktales.core.game.state.scene.IGameScene;

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
