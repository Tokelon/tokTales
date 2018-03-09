package com.tokelon.toktales.extens.def.android.engine.inject;

import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.tokelon.toktales.core.engine.inject.AbstractInjectModule;
import com.tokelon.toktales.core.engine.inject.For;
import com.tokelon.toktales.core.game.screen.IRenderingStrategy;
import com.tokelon.toktales.extens.def.android.game.screen.AndroidConsoleRenderingStrategy;
import com.tokelon.toktales.extens.def.android.game.screen.AndroidLocalMapStateRenderer;
import com.tokelon.toktales.extens.def.android.states.localmap.AndroidLocalMapControlHandler;
import com.tokelon.toktales.extens.def.core.game.states.ConsoleGamestate;
import com.tokelon.toktales.extens.def.core.game.states.localmap.ILocalMapControlHandler;
import com.tokelon.toktales.extens.def.core.game.states.localmap.ILocalMapControlHandler.ILocalMapControlHandlerFactory;
import com.tokelon.toktales.extens.def.core.game.states.localmap.ILocalMapStateRenderer;
import com.tokelon.toktales.extens.def.core.game.states.localmap.ILocalMapStateRenderer.ILocalMapStateRendererFactory;

public class AndroidDefaultExtensionsOverrideInjectModule extends AbstractInjectModule {


	@Override
	protected void configure() {
		// LocalMapGamestate
		install(new FactoryModuleBuilder()
				.implement(ILocalMapControlHandler.class, AndroidLocalMapControlHandler.class)
				.build(ILocalMapControlHandlerFactory.class));
		install(new FactoryModuleBuilder()
				.implement(ILocalMapStateRenderer.class, AndroidLocalMapStateRenderer.class)
				.build(ILocalMapStateRendererFactory.class));
		
		
		// ConsoleGamestate
	    bind(IRenderingStrategy.class).annotatedWith(For.forClass(ConsoleGamestate.class)).to(AndroidConsoleRenderingStrategy.class);
	    
	}

}
