package com.tokelon.toktales.core.game;

import javax.inject.Inject;
import javax.inject.Provider;

import com.tokelon.toktales.core.config.IConfigManager;
import com.tokelon.toktales.core.content.IContentManager;
import com.tokelon.toktales.core.editor.IEditorManager;
import com.tokelon.toktales.core.game.control.IGameControl;
import com.tokelon.toktales.core.game.control.ITimeManager;
import com.tokelon.toktales.core.game.states.IGameState;
import com.tokelon.toktales.core.game.states.IGameStateControl;
import com.tokelon.toktales.core.game.world.IWorld;

public class Game implements IGame {

	
	private final Object gameAdapterLock = new Object();

	
	private IGameAdapter gameAdapter;

	
	private final IGameControl gameControl;
	private final IGameStateControl stateControl;
	private final ITimeManager timeManager;
	private final IGameScriptManager scriptManager;

	private final IGameLogicManager logicManager;

	private final IConfigManager configManager;
	private final IEditorManager editorManager;
	private final IContentManager contentManager;
	
	private final IRegistryManager registryManager;
	
	private final IWorld world;

	private final Provider<IGameAdapter> gameAdapterProvider;
	
	@Inject
	public Game(
			IGameControl gameControl,
			IGameStateControl stateControl,
			ITimeManager timeManager,
			IGameScriptManager scriptManager,
			IGameLogicManager logicManager,
			IConfigManager configManager,
			IEditorManager editorManager,
			IContentManager contentManager,
			IRegistryManager registryManager,
			IWorld world,
			Provider<IGameAdapter> gameAdapterProvider // Pass provider to avoid generation here
	) {
		
		this.gameControl = gameControl;
		this.stateControl = stateControl;
		this.timeManager = timeManager;
		this.scriptManager = scriptManager;
		this.logicManager = logicManager;
		this.configManager = configManager;
		this.editorManager = editorManager;
		this.contentManager = contentManager;
		this.registryManager = registryManager;
		this.world = world;
		this.gameAdapterProvider = gameAdapterProvider;
	}


	@Override
	public IGameState getActiveState() {
		return stateControl.getActiveState();
	}
	
	@Override
	public IGameAdapter getGameAdapter() {
		synchronized (gameAdapterLock) {
			if(gameAdapter == null) {
				gameAdapter = gameAdapterProvider.get();
			}
		}
		
		return gameAdapter;
	}

	@Override
	public IWorld getWorld() {
		return world;
	}
	
	
	@Override
	public IGameControl getGameControl() {
		return gameControl;
	}
	
	@Override
	public IGameStateControl getStateControl() {
		return stateControl;
	}

	@Override
	public ITimeManager getTimeManager() {
		return timeManager;
	}
	
	@Override
	public IGameScriptManager getScriptManager() {
		return scriptManager;
	}
	
	@Override
	public IGameLogicManager getLogicManager() {
		return logicManager;
	}


	@Override
	public IConfigManager getConfigManager() {
		return configManager;
	}

	@Override
	public IEditorManager getEditorManager() {
		return editorManager;
	}

	@Override
	public IContentManager getContentManager() {
		return contentManager;
	}

	
	@Override
	public IRegistryManager getRegistryManager() {
		return registryManager;
	}
	
}