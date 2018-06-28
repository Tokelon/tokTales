package com.tokelon.toktales.extens.def.core.engine.inject;

import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.tokelon.toktales.core.engine.inject.AbstractInjectModule;
import com.tokelon.toktales.core.engine.inject.For;
import com.tokelon.toktales.core.game.screen.IRenderingStrategy;
import com.tokelon.toktales.extens.def.core.game.logic.IConsoleInterpreter;
import com.tokelon.toktales.extens.def.core.game.screen.ConsoleOverlayRenderer;
import com.tokelon.toktales.extens.def.core.game.screen.DebugRenderer;
import com.tokelon.toktales.extens.def.core.game.screen.EntityRenderer;
import com.tokelon.toktales.extens.def.core.game.screen.IConsoleOverlayRenderer.IConsoleOverlayRendererFactory;
import com.tokelon.toktales.extens.def.core.game.screen.IDebugRenderer.IDebugRendererFactory;
import com.tokelon.toktales.extens.def.core.game.screen.IEntityRenderer.IEntityRendererFactory;
import com.tokelon.toktales.extens.def.core.game.screen.IMapRenderer.IMapRendererFactory;
import com.tokelon.toktales.extens.def.core.game.screen.IObjectRenderer.IObjectRendererFactory;
import com.tokelon.toktales.extens.def.core.game.screen.IPlayerRenderer.IPlayerRendererFactory;
import com.tokelon.toktales.extens.def.core.game.screen.LocalMapStateRenderer;
import com.tokelon.toktales.extens.def.core.game.screen.MapRenderer;
import com.tokelon.toktales.extens.def.core.game.screen.ObjectRenderer;
import com.tokelon.toktales.extens.def.core.game.screen.PlayerRenderer;
import com.tokelon.toktales.extens.def.core.game.states.console.ConsoleGamestate;
import com.tokelon.toktales.extens.def.core.game.states.console.ConsoleGamestateInterpreter;
import com.tokelon.toktales.extens.def.core.game.states.console.ConsoleRenderingStrategy;
import com.tokelon.toktales.extens.def.core.game.states.console.IConsoleGamestate;
import com.tokelon.toktales.extens.def.core.game.states.integration.ConsoleIntegrationControlHandler;
import com.tokelon.toktales.extens.def.core.game.states.integration.IConsoleIntegrationControlHandler;
import com.tokelon.toktales.extens.def.core.game.states.integration.IConsoleIntegrationControlHandler.IConsoleIntegrationControlHandlerFactory;
import com.tokelon.toktales.extens.def.core.game.states.localmap.ILocalMapControlHandler;
import com.tokelon.toktales.extens.def.core.game.states.localmap.ILocalMapControlHandler.ILocalMapControlHandlerFactory;
import com.tokelon.toktales.extens.def.core.game.states.localmap.ILocalMapGamescene;
import com.tokelon.toktales.extens.def.core.game.states.localmap.ILocalMapGamestate;
import com.tokelon.toktales.extens.def.core.game.states.localmap.ILocalMapStateRenderer;
import com.tokelon.toktales.extens.def.core.game.states.localmap.ILocalMapStateRenderer.ILocalMapStateRendererFactory;
import com.tokelon.toktales.extens.def.core.game.states.localmap.LocalMapControlHandler;
import com.tokelon.toktales.extens.def.core.game.states.localmap.LocalMapGamestate;
import com.tokelon.toktales.extens.def.core.tale.ITaleLoader;
import com.tokelon.toktales.extens.def.core.tale.TaleLoader;
import com.tokelon.toktales.extens.def.core.tale.states.ITaleGamescene;
import com.tokelon.toktales.extens.def.core.tale.states.ITaleGamestate;
import com.tokelon.toktales.extens.def.core.tale.states.TaleGamescene;
import com.tokelon.toktales.extens.def.core.tale.states.TaleGamestate;

public class CoreDefExtensInjectModule extends AbstractInjectModule {

	
    @Override
    protected void configure() {
    	bind(ITaleGamestate.class).to(TaleGamestate.class);
		bind(ITaleGamescene.class).to(TaleGamescene.class);
		
    	// LocalMapGamestate
    	bind(ILocalMapGamestate.class).to(LocalMapGamestate.class);
		 install(new FactoryModuleBuilder()
				 .implement(ILocalMapStateRenderer.class, LocalMapStateRenderer.class)
				 .build(ILocalMapStateRendererFactory.class));
		 install(new FactoryModuleBuilder()
				 .implement(ILocalMapControlHandler.class, LocalMapControlHandler.class)
				 .build(ILocalMapControlHandlerFactory.class));
    	bind(ILocalMapGamescene.class).to(LocalMapGamestate.EmptyLocalMapGamescene.class);
		 
	    // ConsoleGamestate
		bind(IConsoleGamestate.class).to(ConsoleGamestate.class);
	    bind(IRenderingStrategy.class).annotatedWith(For.forClass(ConsoleGamestate.class)).to(ConsoleRenderingStrategy.class);
	    bind(IConsoleInterpreter.class).annotatedWith(For.forClass(ConsoleGamestate.class)).to(ConsoleGamestateInterpreter.class);
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
    }

}
