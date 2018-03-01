package com.tokelon.toktales.core.engine.setup.manual;

import com.google.inject.Injector;
import com.tokelon.toktales.core.config.ConfigManager;
import com.tokelon.toktales.core.config.IConfigManager;
import com.tokelon.toktales.core.content.ContentManager;
import com.tokelon.toktales.core.content.IContentManager;
import com.tokelon.toktales.core.content.IResourceManager;
import com.tokelon.toktales.core.content.ResourceManager;
import com.tokelon.toktales.core.content.sprite.ISpriteManager.ISpriteManagerFactory;
import com.tokelon.toktales.core.content.sprite.SpriteManager;
import com.tokelon.toktales.core.editor.EditorManager;
import com.tokelon.toktales.core.editor.IEditorManager;
import com.tokelon.toktales.core.engine.IEngine;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.game.Game;
import com.tokelon.toktales.core.game.GameLogicManager;
import com.tokelon.toktales.core.game.IGame;
import com.tokelon.toktales.core.game.IGameAdapter;
import com.tokelon.toktales.core.game.IGameLogicManager;
import com.tokelon.toktales.core.game.IGameScriptManager;
import com.tokelon.toktales.core.game.LuaGameScriptManager;
import com.tokelon.toktales.core.game.control.GameControl;
import com.tokelon.toktales.core.game.control.IGameControl;
import com.tokelon.toktales.core.game.control.ITimeManager;
import com.tokelon.toktales.core.game.control.TimeManager;
import com.tokelon.toktales.core.game.states.GameStateControl;
import com.tokelon.toktales.core.game.states.IGameStateControl;
import com.tokelon.toktales.core.game.world.IWorld;
import com.tokelon.toktales.core.game.world.World;

@Deprecated
public class GameFactory {

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
	
	private final ILogger logger;
	private final IEngine engine;
	private Game game;
	
	public GameFactory(ILogger logger, IEngine engine, IGameAdapter adapter) {
		world = new World(32.0f);
		
		this.logger = logger;
		this.engine = engine;
		this.gameAdapter = adapter;
		
		
		configManager = new ConfigManager();
		editorManager = new EditorManager();
		
		IResourceManager resourceManager = new ResourceManager(logger, engine.getContentService(), engine.getStorageService());
		ISpriteManagerFactory spriteManagerFactory = (IResourceManager rscMgr) -> new SpriteManager(logger, engine.getContentService(), engine.getStorageService(), rscMgr);
		contentManager = new ContentManager(resourceManager, spriteManagerFactory);
		
		stateControl = new GameStateControl(logger);
		timeManager = new TimeManager();
		scriptManager = new LuaGameScriptManager(logger);

		defaultLogicManager = new GameLogicManager(() -> new CustomEngineContext(), () -> game);
		logicManager = defaultLogicManager;
		
		gameControl = new GameControl(logicManager);
	}
	
	
	public Game build() {
		game = new Game(
				gameControl,
				stateControl,
				timeManager,
				scriptManager,
				logicManager,
				configManager,
				editorManager,
				contentManager,
				world,
				() -> gameAdapter);
		
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
	
	
	private class CustomEngineContext implements IEngineContext {

		@Override
		public Injector getInjector() {
			return null;
		}

		@Override
		public IEngine getEngine() {
			return engine;
		}

		@Override
		public ILogger getLog() {
			return logger;
		}

		@Override
		public IGame getGame() {
			return game;
		}
	}
	
	
}
