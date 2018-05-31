package com.tokelon.toktales.extens.def.core.engine.inject;

import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.tokelon.toktales.core.engine.inject.AbstractInjectModule;
import com.tokelon.toktales.core.engine.inject.For;
import com.tokelon.toktales.core.game.screen.IRenderingStrategy;
import com.tokelon.toktales.extens.def.core.game.logic.IConsoleInterpreter;
import com.tokelon.toktales.extens.def.core.game.screen.ConsoleRenderingStrategy;
import com.tokelon.toktales.extens.def.core.game.screen.LocalMapStateRenderer;
import com.tokelon.toktales.extens.def.core.game.states.ConsoleGamestate;
import com.tokelon.toktales.extens.def.core.game.states.ConsoleGamestateInterpreter;
import com.tokelon.toktales.extens.def.core.game.states.IConsoleGamestate;
import com.tokelon.toktales.extens.def.core.game.states.localmap.ILocalMapControlHandler;
import com.tokelon.toktales.extens.def.core.game.states.localmap.ILocalMapControlHandler.ILocalMapControlHandlerFactory;
import com.tokelon.toktales.extens.def.core.game.states.localmap.ILocalMapGamescene;
import com.tokelon.toktales.extens.def.core.game.states.localmap.ILocalMapGamestate;
import com.tokelon.toktales.extens.def.core.game.states.localmap.ILocalMapStateRenderer;
import com.tokelon.toktales.extens.def.core.game.states.localmap.ILocalMapStateRenderer.ILocalMapStateRendererFactory;
import com.tokelon.toktales.extens.def.core.game.states.localmap.LocalMapControlHandler;
import com.tokelon.toktales.extens.def.core.game.states.localmap.LocalMapGamestate;
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
	    
    }

}
