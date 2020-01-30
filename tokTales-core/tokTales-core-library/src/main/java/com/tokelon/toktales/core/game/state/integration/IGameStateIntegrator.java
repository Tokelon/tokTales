package com.tokelon.toktales.core.game.state.integration;

import java.util.Map;

import com.tokelon.toktales.core.game.state.IGameState;
import com.tokelon.toktales.core.game.state.scene.IGameScene;

public interface IGameStateIntegrator {
	// TODO: Document and possibly add more event callbacks
	
	
	// TODO: Adjust return types?
	public void addIntegration(String name, IGameStateIntegration integration);
	public void removeIntegration(String name);
	
	public boolean hasIntegration(String name);
	public boolean hasIntegrationAs(String name, Class<?> type);
	
	public IGameStateIntegration getIntegration(String name);
	public <T> T getIntegrationAs(String name, Class<?> type);
	public <T extends IGameStateIntegration> T findIntegrationByType(Class<T> type);
	
	
	// Really have these?
	/**
	 * @return An unmodifiable map of names to integrations.
	 */
	public Map<String, IGameStateIntegration> getIntegrationMap();
	
	/**
	 * @return An unmodifiable list of integrations.
	 */
	public Iterable<IGameStateIntegration> getIntegrationList();
	


	public void onEngage();

	public void onEnter();

	public void onPause();

	public void onResume();
	
	public void onExit();
	
	public void onDisengage();

	public void onUpdate(long timeMillis);
	
	public void onRender();
	

	public void onSceneAssign(String sceneName, IGameScene assignedScene);
	public void onSceneChange(String sceneName);
	public void onSceneRemove(String sceneName, IGameScene removedScene);
	
	
	public interface IGameStateIntegratorFactory {
		
		public IGameStateIntegrator create(IGameState gamestate);
	}
	
}
