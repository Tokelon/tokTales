package com.tokelon.toktales.core.game;

import javax.inject.Inject;

import com.tokelon.toktales.core.config.ConfigManager;
import com.tokelon.toktales.core.config.IConfigManager;
import com.tokelon.toktales.core.content.ContentManager;
import com.tokelon.toktales.core.content.IContentManager;
import com.tokelon.toktales.core.editor.EditorManager;
import com.tokelon.toktales.core.editor.IEditorManager;
import com.tokelon.toktales.core.engine.IEngine;
import com.tokelon.toktales.core.game.control.GameControl;
import com.tokelon.toktales.core.game.control.IGameControl;
import com.tokelon.toktales.core.game.control.ITimeManager;
import com.tokelon.toktales.core.game.control.TimeManager;
import com.tokelon.toktales.core.game.states.GameStateControl;
import com.tokelon.toktales.core.game.states.IGameState;
import com.tokelon.toktales.core.game.states.IGameStateControl;
import com.tokelon.toktales.core.game.world.IWorld;
import com.tokelon.toktales.core.game.world.World;

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
	
	
	
	
	public static class GameFactory {
		
		private final GameLogicManager defaultLogicManager;
		
		private IGameControl gameControl;
		private IGameStateControl stateControl;
		private ITimeManager timeManager;
		private IGameScriptManager scriptManager;
		private IGameLogicManager logicManager;
		private IConfigManager configManager;
		private IEditorManager editorManager;
		private IContentManager contentManager;
		private IWorld world;
		private IGameAdapter gameAdapter;
		
		public GameFactory(IEngine engine, IGameAdapter adapter) {
			world = new World(32.0f);
			
			gameAdapter = adapter;
			
			
			configManager = new ConfigManager();
			editorManager = new EditorManager();
			contentManager = new ContentManager(engine);
			
			stateControl = new GameStateControl();
			timeManager = new TimeManager();
			scriptManager = new LuaGameScriptManager();

			defaultLogicManager = new GameLogicManager();
			logicManager = defaultLogicManager;
			
			gameControl = new GameControl(logicManager);
		}
		
		
		public Game build() {
			Game game = new Game(
					gameControl,
					stateControl,
					timeManager,
					scriptManager,
					logicManager,
					configManager,
					editorManager,
					contentManager,
					world,
					gameAdapter);
			
			defaultLogicManager.setupGame(game);
			
			return game;
		}


		public void setGameControl(IGameControl gameControl) {
			this.gameControl = gameControl;
		}

		public void setStateControl(IGameStateControl stateControl) {
			this.stateControl = stateControl;
		}

		public void setTimeManager(ITimeManager timeManager) {
			this.timeManager = timeManager;
		}

		public void setScriptManager(IGameScriptManager scriptManager) {
			this.scriptManager = scriptManager;
		}

		public void setLogicManager(IGameLogicManager logicManager) {
			this.logicManager = logicManager;
		}

		public void setConfigManager(IConfigManager configManager) {
			this.configManager = configManager;
		}

		public void setEditorManager(IEditorManager editorManager) {
			this.editorManager = editorManager;
		}

		public void setContentManager(IContentManager contentManager) {
			this.contentManager = contentManager;
		}

		public void setWorld(IWorld world) {
			this.world = world;
		}
		
		public void setGameAdapter(IGameAdapter adapter) {
			this.gameAdapter = adapter;
		}
		
	}
	
	
}