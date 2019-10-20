package com.tokelon.toktales.core.game.states.integration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.tokelon.toktales.core.game.states.IGameScene;
import com.tokelon.toktales.core.game.states.IGameState;
import com.tokelon.toktales.tools.core.annotations.compatibility.CompatFunctionalInterface;

public class GamestateIntegrator implements IGameStateIntegrator {
	// TODO: Implement automatic gamestate injection for integrations via parameter injector?

	private final Map<String, IGameStateIntegration> integrationsMap = new HashMap<>();
	private final List<IGameStateIntegration> integrationsOrderList = new ArrayList<>();
	
	private final Map<String, IGameStateIntegration> integrationsMapUnmodifiable = Collections.unmodifiableMap(integrationsMap);
	private final List<IGameStateIntegration> integrationsOrderListUnmodifiable = Collections.unmodifiableList(integrationsOrderList);
	

	private final IGameState gamestate;
	
	@Inject
	public GamestateIntegrator(@Assisted IGameState gamestate) {
		this.gamestate = gamestate;
	}
	
	
	@Override
	public void addIntegration(String name, IGameStateIntegration integration) {
		integrationsMap.put(name, integration);
		integrationsOrderList.add(integration);
	}

	@Override
	public void removeIntegration(String name) {
		IGameStateIntegration removedIntegration = integrationsMap.remove(name);
		integrationsOrderList.remove(removedIntegration);
	}

	@Override
	public boolean hasIntegration(String name) {
		return integrationsMap.containsKey(name);
	}

	@Override
	public boolean hasIntegrationAs(String name, Class<?> type) {
		return type.isInstance(integrationsMap.get(name));
	}

	@Override
	public IGameStateIntegration getIntegration(String name) {
		return integrationsMap.get(name);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getIntegrationAs(String name, Class<?> type) {
		IGameStateIntegration integration = integrationsMap.get(name);
		if(type.isInstance(integration)) {
			return (T) integration;
		}
		else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends IGameStateIntegration> T findIntegrationByType(Class<T> type) {
		T result = null;
		
		for(IGameStateIntegration integration: integrationsMap.values()) {
			if(type.isInstance(integration)) {
				result = (T) integration;
				break;
			}
		}
		
		return result;
	}

	@Override
	public Map<String, IGameStateIntegration> getIntegrationMap() {
		return integrationsMapUnmodifiable;
	}

	@Override
	public Iterable<IGameStateIntegration> getIntegrationList() {
		return integrationsOrderListUnmodifiable;
	}

	
	
	private void callGamestateIntegrations(IntegrationMethodRunner runner) {
		// Run in order of integrations list
		for(IGameStateIntegration integration: integrationsOrderList) {
			runner.run(integration);
		}
	}
	
	
	@Override
	public void onEngage() {
		callGamestateIntegrations(integration -> integration.onStateEngage(gamestate));
	}

	@Override
	public void onEnter() {
		callGamestateIntegrations(integration -> integration.onStateEnter(gamestate));
	}

	@Override
	public void onPause() {
		callGamestateIntegrations(integration -> integration.onStatePause(gamestate));
	}

	@Override
	public void onResume() {
		callGamestateIntegrations(integration -> integration.onStateResume(gamestate));
	}

	@Override
	public void onExit() {
		callGamestateIntegrations(integration -> integration.onStateExit(gamestate));
	}

	@Override
	public void onDisengage() {
		callGamestateIntegrations(integration -> integration.onStateDisengage(gamestate));
	}

	@Override
	public void onUpdate(long timeMillis) {
		callGamestateIntegrations(integration -> integration.onStateUpdate(gamestate, timeMillis));
	}

	@Override
	public void onRender() {
		callGamestateIntegrations(integration -> integration.onStateRender(gamestate));
	}

	@Override
	public void onSceneAssign(String sceneName, IGameScene assignedScene) {
		callGamestateIntegrations(integration -> integration.onSceneAssign(gamestate, sceneName, assignedScene));
	}

	@Override
	public void onSceneChange(String sceneName) {
		callGamestateIntegrations(integration -> integration.onSceneChange(gamestate, sceneName));
	}

	@Override
	public void onSceneRemove(String sceneName, IGameScene removedScene) {
		callGamestateIntegrations(integration -> integration.onSceneRemove(gamestate, sceneName, removedScene));
	}

	
	
	public static class GamestateIntegratorFactory implements IGameStateIntegratorFactory {
		
		@Override
		public IGameStateIntegrator create(IGameState gamestate) {
			return new GamestateIntegrator(gamestate);
		}
	}
	
	
	@CompatFunctionalInterface
	private interface IntegrationMethodRunner {
		public void run(IGameStateIntegration integration);
	}

}
