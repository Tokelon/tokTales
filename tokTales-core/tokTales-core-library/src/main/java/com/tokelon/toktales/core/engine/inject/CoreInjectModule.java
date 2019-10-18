package com.tokelon.toktales.core.engine.inject;

import java.lang.reflect.Type;

import com.google.common.reflect.TypeToken;
import com.google.inject.TypeLiteral;
import com.google.inject.util.Types;
import com.tokelon.toktales.core.config.ConfigManager;
import com.tokelon.toktales.core.config.IConfigManager;
import com.tokelon.toktales.core.content.ContentManager;
import com.tokelon.toktales.core.content.ContentUtils;
import com.tokelon.toktales.core.content.IContentManager;
import com.tokelon.toktales.core.content.IContentUtils;
import com.tokelon.toktales.core.content.IResourceManager;
import com.tokelon.toktales.core.content.ResourceManager;
import com.tokelon.toktales.core.editor.EditorManager;
import com.tokelon.toktales.core.editor.IEditorManager;
import com.tokelon.toktales.core.engine.Engine;
import com.tokelon.toktales.core.engine.EngineContext;
import com.tokelon.toktales.core.engine.IEngine;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.engine.render.DefaultRenderAccess;
import com.tokelon.toktales.core.engine.render.DefaultSurfaceHandler;
import com.tokelon.toktales.core.engine.render.IRenderAccess;
import com.tokelon.toktales.core.engine.render.ISurfaceHandler;
import com.tokelon.toktales.core.game.Game;
import com.tokelon.toktales.core.game.GameLogicManager;
import com.tokelon.toktales.core.game.IGame;
import com.tokelon.toktales.core.game.IGameLogicManager;
import com.tokelon.toktales.core.game.IGameScriptManager;
import com.tokelon.toktales.core.game.IRegistryManager;
import com.tokelon.toktales.core.game.LuaGameScriptManager;
import com.tokelon.toktales.core.game.RegistryManager;
import com.tokelon.toktales.core.game.control.GameControl;
import com.tokelon.toktales.core.game.control.IGameControl;
import com.tokelon.toktales.core.game.control.ITimeManager;
import com.tokelon.toktales.core.game.control.TimeManager;
import com.tokelon.toktales.core.game.controller.CameraController;
import com.tokelon.toktales.core.game.controller.ControllerManager;
import com.tokelon.toktales.core.game.controller.ICameraController;
import com.tokelon.toktales.core.game.controller.ICameraController.ICameraControllerFactory;
import com.tokelon.toktales.core.game.controller.IControllerManager;
import com.tokelon.toktales.core.game.controller.IPlayerController;
import com.tokelon.toktales.core.game.controller.IPlayerController.IPlayerControllerFactory;
import com.tokelon.toktales.core.game.controller.PlayerController;
import com.tokelon.toktales.core.game.controller.map.IMapController;
import com.tokelon.toktales.core.game.controller.map.IMapController.IMapControllerFactory;
import com.tokelon.toktales.core.game.controller.map.MapController;
import com.tokelon.toktales.core.game.model.Actor;
import com.tokelon.toktales.core.game.model.Camera;
import com.tokelon.toktales.core.game.model.CameraModel;
import com.tokelon.toktales.core.game.model.IActor;
import com.tokelon.toktales.core.game.model.ICamera;
import com.tokelon.toktales.core.game.model.ICameraModel;
import com.tokelon.toktales.core.game.model.IPlayer;
import com.tokelon.toktales.core.game.model.Player;
import com.tokelon.toktales.core.game.model.entity.GameEntity;
import com.tokelon.toktales.core.game.model.entity.GameEntityModel;
import com.tokelon.toktales.core.game.model.entity.IGameEntity;
import com.tokelon.toktales.core.game.model.entity.IGameEntityModel;
import com.tokelon.toktales.core.game.model.map.EmptyBlockMap;
import com.tokelon.toktales.core.game.model.map.IBlockMap;
import com.tokelon.toktales.core.game.screen.EmptyStateRender;
import com.tokelon.toktales.core.game.screen.IStateRender;
import com.tokelon.toktales.core.game.screen.order.IRenderOrder;
import com.tokelon.toktales.core.game.screen.order.RenderOrder;
import com.tokelon.toktales.core.game.states.BaseGamescene;
import com.tokelon.toktales.core.game.states.BaseGamestate;
import com.tokelon.toktales.core.game.states.BaseGamestateProvider;
import com.tokelon.toktales.core.game.states.DefaultGamestate;
import com.tokelon.toktales.core.game.states.GameSceneControl;
import com.tokelon.toktales.core.game.states.GameSceneControl.GameSceneControlFactory;
import com.tokelon.toktales.core.game.states.GameStateControl;
import com.tokelon.toktales.core.game.states.IBaseGamestateFactory;
import com.tokelon.toktales.core.game.states.IControlHandler;
import com.tokelon.toktales.core.game.states.IControlScheme;
import com.tokelon.toktales.core.game.states.IGameScene;
import com.tokelon.toktales.core.game.states.IGameSceneControl;
import com.tokelon.toktales.core.game.states.IGameSceneControl.IGameSceneControlFactory;
import com.tokelon.toktales.core.game.states.IGameSceneControl.IModifiableGameSceneControl;
import com.tokelon.toktales.core.game.states.IGameState;
import com.tokelon.toktales.core.game.states.IGameStateControl;
import com.tokelon.toktales.core.game.states.IGameStateInputHandler;
import com.tokelon.toktales.core.game.states.ITypedGameState;
import com.tokelon.toktales.core.game.states.InitialGamestate;
import com.tokelon.toktales.core.game.states.integration.GamestateIntegrator;
import com.tokelon.toktales.core.game.states.integration.IGameStateIntegrator.IGameStateIntegratorFactory;
import com.tokelon.toktales.core.game.world.DefaultCollisionStrategy;
import com.tokelon.toktales.core.game.world.ICollisionStrategy;
import com.tokelon.toktales.core.game.world.IWorld;
import com.tokelon.toktales.core.game.world.IWorldspace;
import com.tokelon.toktales.core.game.world.World;
import com.tokelon.toktales.core.game.world.Worldspace;
import com.tokelon.toktales.core.render.DefaultTextureCoordinator;
import com.tokelon.toktales.core.render.DefaultTextureManager;
import com.tokelon.toktales.core.render.ITextureCoordinator;
import com.tokelon.toktales.core.render.ITextureCoordinator.ITextureCoordinatorFactory;
import com.tokelon.toktales.core.render.ITextureManager;
import com.tokelon.toktales.tools.core.inject.IInjector;
import com.tokelon.toktales.tools.core.inject.parameter.IParameterInjector.IParameterInjectorFactory;
import com.tokelon.toktales.tools.core.inject.parameter.ParameterInjectorFactory;
import com.tokelon.toktales.tools.core.objects.pools.DefaultObjectPool;
import com.tokelon.toktales.tools.core.objects.pools.IObjectPool.IObjectPoolFactory;
import com.tokelon.toktales.tools.core.sub.inject.GuiceInjector;

public class CoreInjectModule extends AbstractInjectModule {


	@SuppressWarnings("serial")
	@Override
	protected void configure() {
		// you can use untargeted bindings so that the injector may prepare dependencies eagerly
		// e.g. bind(Engine.class)
		
		// you can use optional bindings
		// e.g. OptionalBinder.newOptionalBinder(binder(), IEngine.class).setDefault(Engine.class).setBinding(...);
		
		// you can bind TypeListener, InjectListener and MembersInjector
		// e.g. bindListener(new ClassMatcherAdapter(Matchers.subclassesOf(ILogger.class)), new LoggerTypeListener());
		
		// Important: Only if feature is not disabled -> binder().requireExactBindingAnnotations();
		// you can make 'inexact' annotated bindings - fallback for missing annotated bindings with parameter
		// e.g. bind(IControlScheme.class).annotatedWith(ForClass.class).to(IControlScheme.EmptyControlScheme.class);
		// -> This causes a dependency to (@ForClass(Example.class) IControlScheme controScheme) to succeed even if no For.forClass(Example.class) bound
		
		
		// Tools injector
		bind(IInjector.class).to(GuiceInjector.class);
		
		
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
		  // Engine bindings
		  bindInEngineScopeAndForNotScoped(ISurfaceHandler.class, DefaultSurfaceHandler.class);
		  bindInEngineScopeAndForNotScoped(IRenderAccess.class, DefaultRenderAccess.class);
		 
		 
		 bindInGameScope(IGame.class, Game.class);
		  // Game bindings
		  bindInGameScopeAndForNotScoped(IGameControl.class, GameControl.class);
		   // GameControl bindings
		   bindInGameScopeAndForNotScoped(IGameLogicManager.class, GameLogicManager.class);
		  bindInGameScopeAndForNotScoped(IGameStateControl.class, GameStateControl.class);
		  bindInGameScopeAndForNotScoped(ITimeManager.class, TimeManager.class);
		  bindInGameScopeAndForNotScoped(IGameScriptManager.class, LuaGameScriptManager.class);
		  bindInGameScopeAndForNotScoped(IConfigManager.class, ConfigManager.class);
		  bindInGameScopeAndForNotScoped(IEditorManager.class, EditorManager.class);
		  bindInGameScopeAndForNotScoped(IContentManager.class, ContentManager.class);
		   bindInGameScopeAndForNotScoped(IResourceManager.class, ResourceManager.class);
		   bindInGameScopeAndForNotScoped(ITextureManager.class, DefaultTextureManager.class);
		   bindInGameScope(IRegistryManager.class, RegistryManager.class);
		  bindInGameScopeAndForNotScoped(IWorld.class, World.class);
		  
		bind(IContentUtils.class).to(ContentUtils.class);
		
		// Other
		bind(IGameState.class).to(DefaultGamestate.class);
		bind(IGameScene.class).to(BaseGamescene.class);
		bind(new TypeLiteral<ITypedGameState<IGameScene>>() {}).to(new TypeLiteral<BaseGamestate<IGameScene>>() {});
		bind(new TypeLiteral<BaseGamestate<IGameScene>>() {}).toProvider(new BaseGamestateProvider<IGameScene>(new TypeToken<IGameScene>() {}));
		bind(IBaseGamestateFactory.class).to(IBaseGamestateFactory.BaseGamestateFactory.class);
		bind(BaseGamestate.class);
		bind(InitialGamestate.class);
		
		bindGameSceneControlTypes();
		
		bind(IGameStateIntegratorFactory.class).to(GamestateIntegrator.GamestateIntegratorFactory.class);
		bind(IRenderOrder.class).to(RenderOrder.class);
		bind(IStateRender.class).to(EmptyStateRender.class);
		 bind(ITextureCoordinator.class).to(DefaultTextureCoordinator.class);
		 bind(ITextureCoordinatorFactory.class).to(DefaultTextureCoordinator.TextureCoordinatorFactory.class);
		bind(IGameStateInputHandler.class).to(IGameStateInputHandler.EmptyGameStateInputHandler.class);
		bind(IControlScheme.class).to(IControlScheme.EmptyControlScheme.class);
		bind(IControlHandler.class).to(IControlHandler.EmptyControlHandler.class);
		
		bind(IControllerManager.class).to(ControllerManager.class);
		bind(ICamera.class).to(Camera.class);
		bind(ICameraController.class).to(CameraController.class);
		bind(ICameraControllerFactory.class).to(CameraController.CameraControllerFactory.class);
		bind(IPlayer.class).to(Player.class);
		bind(IPlayerController.class).to(PlayerController.class);
		bind(IPlayerControllerFactory.class).to(PlayerController.PlayerControllerFactory.class);
		bind(IBlockMap.class).to(EmptyBlockMap.class);
		bind(IMapController.class).to(MapController.class);
		bind(IMapControllerFactory.class).to(MapController.MapControllerFactory.class);
		bind(IWorldspace.class).to(Worldspace.class);
		bind(ICollisionStrategy.class).to(DefaultCollisionStrategy.class);
		
		bind(IActor.class).to(Actor.class);
		bind(IGameEntity.class).to(GameEntity.class);
		 bind(IGameEntityModel.class).to(GameEntityModel.class);
		bind(ICamera.class).to(Camera.class);
		 bind(ICameraModel.class).to(CameraModel.class);
		
		
		// Tools
		bind(IParameterInjectorFactory.class).to(ParameterInjectorFactory.class);
		bind(IObjectPoolFactory.class).to(DefaultObjectPool.DefaultObjectPoolFactory.class);
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void bindGameSceneControlTypes() {
		/* So... Binding generic types.
		 * 
		 * Short version: Don't do it.
		 * Instead bind and inject the type without parameter information and at the injection point cast it into the parameterized type you need.
		 * Ex. instead of injecting IGameSceneControl<? extends IGameScene>, inject IGameSceneControl and then cast it to IGameSceneControl<? extends IGameScene>.
		 * See (1).
		 * 
		 * Alternatively (and the better solution), use a factory with a typed create() method.
		 * See (2).
		 * 
		 * Long version: You can bind generic types for specific parameter but you have to do it once for each parameter.
		 * You can use a simple @Provides method in the module.
		 * See (3).
		 * Or you can create the types programmatically (fun).
		 * See (4).
		 * 
		 */
		
		// (1) Injecting without parameter information
		bind(IGameSceneControl.class).to(GameSceneControl.class);
		// The compiler cannot understand this for some reason, therefore it needs a cast (which works just fine)
		bind(IModifiableGameSceneControl.class).to((Class<? extends IModifiableGameSceneControl>) GameSceneControl.class);
		
		
		// (2). Using a factory with a typed create method
		bind(IGameSceneControlFactory.class).to(GameSceneControlFactory.class);
		

		// (4). Creating the types programmatically and using Type and TypeLiteral to bind them. Only works for parameter <? extends IGameScene>.
		Type gamesceneSubtype = Types.subtypeOf(IGameScene.class);

		// Here we bind the inner (notice WithOwner) type IModifiableGameSceneControl to GameSceneControl but only for parameter <? extends IGameScene>
		Type modifiableGamesSceneControlOfGameSceneSubtype = Types.newParameterizedTypeWithOwner(IGameSceneControl.class, IModifiableGameSceneControl.class, gamesceneSubtype);
		TypeLiteral<IModifiableGameSceneControl<? extends IGameScene>> typeLiteralModifiableGameSceneControl = (TypeLiteral) TypeLiteral.get(modifiableGamesSceneControlOfGameSceneSubtype);
		bind(typeLiteralModifiableGameSceneControl).to(new TypeLiteral<GameSceneControl<? extends IGameScene>>() {});
		
		// Here we bind the supertype IGameSceneControl to it's subtype IModifiableGameSceneControl but only for parameter <? extends IGameScene>
		Type gameSceneControlOfGamesceneSubtype = Types.newParameterizedType(IGameSceneControl.class, gamesceneSubtype);
		TypeLiteral<IGameSceneControl<? extends IGameScene>> typeLiteralGameSceneControl = (TypeLiteral) TypeLiteral.get(gameSceneControlOfGamesceneSubtype);
		bind(typeLiteralGameSceneControl).to(typeLiteralModifiableGameSceneControl);
	}
	
	/* (3). Using a @Provides method in the module.
	@Provides
	protected IModifiableGameSceneControl<? extends IGameScene> provideModifiableGameSceneControl(ILogger logger) {
		return new GameSceneControl<IGameScene>(logger);
	}*/
	
}
