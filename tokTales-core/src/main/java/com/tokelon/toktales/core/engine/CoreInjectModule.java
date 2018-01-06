package com.tokelon.toktales.core.engine;

import com.tokelon.toktales.core.config.ConfigManager;
import com.tokelon.toktales.core.config.IConfigManager;
import com.tokelon.toktales.core.content.ContentManager;
import com.tokelon.toktales.core.content.IContentManager;
import com.tokelon.toktales.core.editor.EditorManager;
import com.tokelon.toktales.core.editor.IEditorManager;
import com.tokelon.toktales.core.engine.inject.AbstractInjectModule;
import com.tokelon.toktales.core.engine.inject.EngineScope;
import com.tokelon.toktales.core.engine.inject.EngineScoped;
import com.tokelon.toktales.core.engine.inject.GameScope;
import com.tokelon.toktales.core.engine.inject.GameScoped;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.engine.log.MainLogger;
import com.tokelon.toktales.core.game.Game;
import com.tokelon.toktales.core.game.GameLogicManager;
import com.tokelon.toktales.core.game.IGame;
import com.tokelon.toktales.core.game.IGameAdapter;
import com.tokelon.toktales.core.game.IGameAdapter.EmptyGameAdapter;
import com.tokelon.toktales.core.game.IGameLogicManager;
import com.tokelon.toktales.core.game.IGameScriptManager;
import com.tokelon.toktales.core.game.LuaGameScriptManager;
import com.tokelon.toktales.core.game.control.GameControl;
import com.tokelon.toktales.core.game.control.IGameControl;
import com.tokelon.toktales.core.game.control.ITimeManager;
import com.tokelon.toktales.core.game.control.TimeManager;
import com.tokelon.toktales.core.game.screen.order.IRenderOrder;
import com.tokelon.toktales.core.game.screen.order.RenderOrder;
import com.tokelon.toktales.core.game.states.GameStateControl;
import com.tokelon.toktales.core.game.states.IGameStateControl;
import com.tokelon.toktales.core.game.states.InitialGamestate;
import com.tokelon.toktales.core.game.world.IWorld;
import com.tokelon.toktales.core.game.world.World;

public class CoreInjectModule extends AbstractInjectModule {

	@Override
	protected void configure() {
		// you can use untargeted bindings so that the injector may prepare dependencies eagerly

		
		// Engine Scope
		EngineScope engineScope = new EngineScope();
		bindScope(EngineScoped.class, engineScope);
		bind(EngineScope.class).toInstance(engineScope);
		
		// Game Scope
		GameScope gameScope = new GameScope();
		bindScope(GameScoped.class, gameScope);
		bind(GameScope.class).toInstance(gameScope); //.annotatedWith(Names.named("gameScope"))

		
		
		// Engine
		bindInEngineScope(IEngineContext.class, EngineContext.class);

		 // EngineContext bindings
		 bindInEngineScope(IEngine.class, Engine.class); // bindings in platform module
		 bindInEngineScopeAndForNotScoped(ILogger.class, MainLogger.class); // bindings in platform module

		 bindInGameScope(IGame.class, Game.class);
		  // Game bindings
		  bindInGameScopeAndForNotScoped(IGameControl.class, GameControl.class);
		   // GameControl bindings
		   bindInGameScopeAndForNotScoped(IGameLogicManager.class, GameLogicManager.class);
		  bindInGameScopeAndForNotScoped(IGameStateControl.class, GameStateControl.class);
		  bindInGameScopeAndForNotScoped(ITimeManager.class, TimeManager.class);
		  bindInGameScopeAndForNotScoped(IGameScriptManager.class, LuaGameScriptManager.class);
		  //bind(IGameLogicManager.class)
		  bindInGameScopeAndForNotScoped(IConfigManager.class, ConfigManager.class);
		  bindInGameScopeAndForNotScoped(IEditorManager.class, EditorManager.class);
		  bindInGameScopeAndForNotScoped(IContentManager.class, ContentManager.class);
		  bindToProviderInGameScopeAndForNotScoped(IWorld.class, () -> new World(32.0f));  // Maybe do param with @Named
		  bindInGameScopeAndForNotScoped(IGameAdapter.class, EmptyGameAdapter.class);

		// Other
		bind(InitialGamestate.class);
		bind(IRenderOrder.class).to(RenderOrder.class);

	}

	
}
