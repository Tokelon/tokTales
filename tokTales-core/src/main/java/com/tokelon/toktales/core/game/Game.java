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

public class Game implements IGame, IGameScriptManagerProvider {

	
	private final Object gameAdapterLock = new Object();

	private final IGameControl mGameControl;
	private final IGameStateControl mStateControl;
	private final ITimeManager mTimeManager;
	private final IGameScriptManager mScriptManager;

	private final IGameLogicManager mLogicManager;

	private final IConfigManager mConfigManager;
	private final IEditorManager mEditorManager;
	private final IContentManager mContentManager;
	
	private final IWorld mWorld;

	private final Provider<IGameAdapter> mGameAdapterProvider;
	private IGameAdapter mGameAdapter;
	
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
			IWorld world,
			Provider<IGameAdapter> gameAdapterProvider // Pass provider to avoid generation here
			) {
		
		this.mGameControl = gameControl;
		this.mStateControl = stateControl;
		this.mTimeManager = timeManager;
		this.mScriptManager = scriptManager;
		this.mLogicManager = logicManager;
		this.mConfigManager = configManager;
		this.mEditorManager = editorManager;
		this.mContentManager = contentManager;
		this.mWorld = world;
		this.mGameAdapterProvider = gameAdapterProvider;
	}

	
	
	@Override
	public IGameAdapter getGameAdapter() {
		if(mGameAdapter == null) { // Check before locking to avoid overhead
			
			synchronized (gameAdapterLock) {
				if(mGameAdapter == null) {
					mGameAdapter = mGameAdapterProvider.get();
				}
			}
		}
		
		return mGameAdapter;
	}

	@Override
	public IGameControl getGameControl() {
		return mGameControl;
	}
	
	@Override
	public IGameStateControl getStateControl() {
		return mStateControl;
	}

	@Override
	public ITimeManager getTimeManager() {
		return mTimeManager;
	}
	
	@Override
	public IGameScriptManager getScriptManager() {
		return mScriptManager;
	}
	
	@Override
	public IGameLogicManager getLogicManager() {
		return mLogicManager;
	}



	@Override
	public IConfigManager getConfigManager() {
		return mConfigManager;
	}

	@Override
	public IEditorManager getEditorManager() {
		return mEditorManager;
	}

	@Override
	public IContentManager getContentManager() {
		return mContentManager;
	}



	@Override
	public IGameState getActiveState() {
		return mStateControl.getActiveState();
	}
	
	@Override
	public IWorld getWorld() {
		return mWorld;
	}
	
	
}