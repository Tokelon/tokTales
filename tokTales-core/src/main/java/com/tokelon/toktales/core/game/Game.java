package com.tokelon.toktales.core.game;

import javax.inject.Inject;

import com.tokelon.toktales.core.config.IConfigManager;
import com.tokelon.toktales.core.content.IContentManager;
import com.tokelon.toktales.core.editor.IEditorManager;
import com.tokelon.toktales.core.game.control.IGameControl;
import com.tokelon.toktales.core.game.control.ITimeManager;
import com.tokelon.toktales.core.game.states.IGameState;
import com.tokelon.toktales.core.game.states.IGameStateControl;
import com.tokelon.toktales.core.game.world.IWorld;

public class Game implements IGame, IGameScriptManagerProvider {


	private final IGameControl mGameControl;
	private final IGameStateControl mStateControl;
	private final ITimeManager mTimeManager;
	private final IGameScriptManager mScriptManager;

	private final IGameLogicManager mLogicManager;

	private final IConfigManager mConfigManager;
	private final IEditorManager mEditorManager;
	private final IContentManager mContentManager;
	
	private final IWorld mWorld;

	private final IGameAdapter mGameAdapter;
	
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
			IGameAdapter gameAdapter
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
		this.mGameAdapter = gameAdapter;
	}

	
	
	@Override
	public IGameAdapter getGameAdapter() {
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