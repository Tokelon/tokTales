package com.tokelon.toktales.core.engine.inject;

import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.tokelon.toktales.core.config.ConfigManager;
import com.tokelon.toktales.core.config.IConfigManager;
import com.tokelon.toktales.core.content.ContentManager;
import com.tokelon.toktales.core.content.IContentManager;
import com.tokelon.toktales.core.content.IResourceManager;
import com.tokelon.toktales.core.content.ResourceManager;
import com.tokelon.toktales.core.content.sprite.ISpriteManager;
import com.tokelon.toktales.core.content.sprite.ISpriteManager.ISpriteManagerFactory;
import com.tokelon.toktales.core.content.sprite.SpriteManager;
import com.tokelon.toktales.core.editor.EditorManager;
import com.tokelon.toktales.core.editor.IEditorManager;
import com.tokelon.toktales.core.engine.Engine;
import com.tokelon.toktales.core.engine.EngineContext;
import com.tokelon.toktales.core.engine.IEngine;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.engine.log.MainLogger;
import com.tokelon.toktales.core.game.Game;
import com.tokelon.toktales.core.game.GameLogicManager;
import com.tokelon.toktales.core.game.IGame;
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
		// e.g. bind(Engine.class)
		
		// you can use optional bindings
		// e.g. OptionalBinder.newOptionalBinder(binder(), IEngine.class).setDefault(Engine.class).setBinding(...);
		
		// you can bind TypeListener, InjectListener and MembersInjector
		// e.g. bindListener(new ClassMatcherAdapter(Matchers.subclassesOf(ILogger.class)), new LoggerTypeListener());
		
		
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
		   bind(IResourceManager.class).to(ResourceManager.class);
		   install(new FactoryModuleBuilder()
				   .implement(ISpriteManager.class, SpriteManager.class)
				   .build(ISpriteManagerFactory.class));
		  bindToProviderInGameScopeAndForNotScoped(IWorld.class, () -> new World(32.0f));  // Maybe do param with @Named

		// Other
		bind(InitialGamestate.class);
		bind(IRenderOrder.class).to(RenderOrder.class);

	}

	
}
