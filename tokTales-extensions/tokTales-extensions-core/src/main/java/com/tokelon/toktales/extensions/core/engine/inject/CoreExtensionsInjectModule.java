package com.tokelon.toktales.extensions.core.engine.inject;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.tokelon.toktales.core.engine.inject.AbstractInjectModule;
import com.tokelon.toktales.core.game.states.render.IRenderingStrategy;
import com.tokelon.toktales.extensions.core.game.logic.DefaultConsoleInterpreterManager;
import com.tokelon.toktales.extensions.core.game.logic.IConsoleInterpreterManager;
import com.tokelon.toktales.extensions.core.game.logic.TaleConsoleInterpreter;
import com.tokelon.toktales.extensions.core.game.renderer.ConsoleOverlayRenderer;
import com.tokelon.toktales.extensions.core.game.renderer.DebugRenderer;
import com.tokelon.toktales.extensions.core.game.renderer.EntityRenderer;
import com.tokelon.toktales.extensions.core.game.renderer.MapRenderer;
import com.tokelon.toktales.extensions.core.game.renderer.ObjectRenderer;
import com.tokelon.toktales.extensions.core.game.renderer.PlayerRenderer;
import com.tokelon.toktales.extensions.core.game.renderer.IConsoleOverlayRenderer.IConsoleOverlayRendererFactory;
import com.tokelon.toktales.extensions.core.game.renderer.IDebugRenderer.IDebugRendererFactory;
import com.tokelon.toktales.extensions.core.game.renderer.IEntityRenderer.IEntityRendererFactory;
import com.tokelon.toktales.extensions.core.game.renderer.IMapRenderer.IMapRendererFactory;
import com.tokelon.toktales.extensions.core.game.renderer.IObjectRenderer.IObjectRendererFactory;
import com.tokelon.toktales.extensions.core.game.renderer.IPlayerRenderer.IPlayerRendererFactory;
import com.tokelon.toktales.extensions.core.game.states.console.ConsoleGamestate;
import com.tokelon.toktales.extensions.core.game.states.console.ConsoleGamestateInterpreter;
import com.tokelon.toktales.extensions.core.game.states.console.ConsoleRenderingStrategy;
import com.tokelon.toktales.extensions.core.game.states.console.IConsoleGamestate;
import com.tokelon.toktales.extensions.core.game.states.integration.ConsoleIntegrationControlHandler;
import com.tokelon.toktales.extensions.core.game.states.integration.IConsoleIntegrationControlHandler;
import com.tokelon.toktales.extensions.core.game.states.integration.IConsoleIntegrationControlHandler.IConsoleIntegrationControlHandlerFactory;
import com.tokelon.toktales.extensions.core.game.states.localmap.ILocalMapConsoleIntepreter;
import com.tokelon.toktales.extensions.core.game.states.localmap.ILocalMapControlHandler;
import com.tokelon.toktales.extensions.core.game.states.localmap.ILocalMapGamescene;
import com.tokelon.toktales.extensions.core.game.states.localmap.ILocalMapGamestate;
import com.tokelon.toktales.extensions.core.game.states.localmap.ILocalMapStateRenderer;
import com.tokelon.toktales.extensions.core.game.states.localmap.LocalMapConsoleInterpreter;
import com.tokelon.toktales.extensions.core.game.states.localmap.LocalMapControlHandler;
import com.tokelon.toktales.extensions.core.game.states.localmap.LocalMapGamestate;
import com.tokelon.toktales.extensions.core.game.states.localmap.LocalMapStateRenderer;
import com.tokelon.toktales.extensions.core.game.states.localmap.ILocalMapControlHandler.ILocalMapControlHandlerFactory;
import com.tokelon.toktales.extensions.core.game.states.localmap.ILocalMapStateRenderer.ILocalMapStateRendererFactory;
import com.tokelon.toktales.extensions.core.tale.ITaleLoader;
import com.tokelon.toktales.extensions.core.tale.TaleLoader;
import com.tokelon.toktales.extensions.core.tale.procedure.ILoadTaleProcedure;
import com.tokelon.toktales.extensions.core.tale.procedure.ISetMapTaleProcedure;
import com.tokelon.toktales.extensions.core.tale.procedure.LoadTaleProcedure;
import com.tokelon.toktales.extensions.core.tale.procedure.SetMapTaleProcedure;
import com.tokelon.toktales.extensions.core.tale.states.ITaleGamescene;
import com.tokelon.toktales.extensions.core.tale.states.ITaleGamestate;
import com.tokelon.toktales.extensions.core.tale.states.TaleGamescene;
import com.tokelon.toktales.extensions.core.tale.states.TaleGamestate;
import com.tokelon.toktales.tools.core.sub.inject.scope.For;

public class CoreExtensionsInjectModule extends AbstractInjectModule {

	
    @Override
    protected void configure() {
    	bind(ITaleGamestate.class).to(TaleGamestate.class);
		bind(ITaleGamescene.class).to(TaleGamescene.class);
		
		bind(IConsoleInterpreterManager.class).to(DefaultConsoleInterpreterManager.class);
		
    	// LocalMapGamestate
    	bind(ILocalMapGamestate.class).to(LocalMapGamestate.class);
		 install(new FactoryModuleBuilder()
				 .implement(ILocalMapStateRenderer.class, LocalMapStateRenderer.class)
				 .build(ILocalMapStateRendererFactory.class));
		 install(new FactoryModuleBuilder()
				 .implement(ILocalMapControlHandler.class, LocalMapControlHandler.class)
				 .build(ILocalMapControlHandlerFactory.class));
    	bind(ILocalMapGamescene.class).to(LocalMapGamestate.EmptyLocalMapGamescene.class);
    	bind(ILocalMapConsoleIntepreter.class).to(LocalMapConsoleInterpreter.class);
		
	    // ConsoleGamestate
		bind(IConsoleGamestate.class).to(ConsoleGamestate.class);
	    bind(IRenderingStrategy.class).annotatedWith(For.forClass(ConsoleGamestate.class)).to(ConsoleRenderingStrategy.class);
	    bind(IConsoleInterpreterManager.class).annotatedWith(For.forClass(ConsoleGamestate.class)).toProvider(ConsoleInterpreterManagerProvider.class);
		 install(new FactoryModuleBuilder()
				.implement(IConsoleIntegrationControlHandler.class, ConsoleIntegrationControlHandler.class)
				.build(IConsoleIntegrationControlHandlerFactory.class));
	    
		 
	    bind(IPlayerRendererFactory.class).to(PlayerRenderer.PlayerRendererFactory.class);
	    bind(IMapRendererFactory.class).to(MapRenderer.MapRendererFactory.class);
	    bind(IEntityRendererFactory.class).to(EntityRenderer.EntityRendererFactory.class);
	    bind(IObjectRendererFactory.class).to(ObjectRenderer.ObjectRendererFactory.class);
	    bind(IConsoleOverlayRendererFactory.class).to(ConsoleOverlayRenderer.ConsoleOverlayRendererFactory.class);
	    bind(IDebugRendererFactory.class).to(DebugRenderer.DebugRendererFactory.class);
	    
	    
	    bind(ITaleLoader.class).to(TaleLoader.class);
	    bind(ILoadTaleProcedure.class).to(LoadTaleProcedure.class);
	    bind(ISetMapTaleProcedure.class).to(SetMapTaleProcedure.class);
    }
    
    
	protected static class ConsoleInterpreterManagerProvider implements Provider<IConsoleInterpreterManager> {

		private final IConsoleInterpreterManager consoleInterpreterManager;
		private final ConsoleGamestateInterpreter consoleGamestateInterpreter;
		private final TaleConsoleInterpreter taleConsoleInterpreter;

		@Inject
		public ConsoleInterpreterManagerProvider(
				IConsoleInterpreterManager consoleInterpreterManager,
				ConsoleGamestateInterpreter consoleGamestateInterpreter,
				TaleConsoleInterpreter taleConsoleInterpreter
		) {
			this.consoleInterpreterManager = consoleInterpreterManager;
			this.consoleGamestateInterpreter = consoleGamestateInterpreter;
			this.taleConsoleInterpreter = taleConsoleInterpreter;
		}
		
		@Override
		public IConsoleInterpreterManager get() {
			consoleInterpreterManager.add(consoleGamestateInterpreter);
			consoleInterpreterManager.add(taleConsoleInterpreter);
			return consoleInterpreterManager;
		}
	}

}
