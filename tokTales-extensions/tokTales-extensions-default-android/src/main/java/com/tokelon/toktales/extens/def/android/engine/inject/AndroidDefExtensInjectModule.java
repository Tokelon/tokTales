package com.tokelon.toktales.extens.def.android.engine.inject;

import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.tokelon.toktales.core.engine.inject.AbstractInjectModule;
import com.tokelon.toktales.core.engine.inject.For;
import com.tokelon.toktales.core.game.states.IControlScheme;
import com.tokelon.toktales.core.game.states.IGameStateInputHandler;
import com.tokelon.toktales.extens.def.android.states.AndroidConsoleInputHandler;
import com.tokelon.toktales.extens.def.android.states.localmap.AndroidLocalMapControlScheme;
import com.tokelon.toktales.extens.def.android.states.localmap.AndroidLocalMapInputHandler;
import com.tokelon.toktales.extens.def.core.game.states.ConsoleGamestate;
import com.tokelon.toktales.extens.def.core.game.states.localmap.ILocalMapInputHandler;
import com.tokelon.toktales.extens.def.core.game.states.localmap.ILocalMapInputHandler.ILocalMapInputHandlerFactory;
import com.tokelon.toktales.extens.def.core.game.states.localmap.LocalMapGamestate;

public class AndroidDefExtensInjectModule extends AbstractInjectModule {

	
    @Override
    protected void configure() {
    	// LocalMapGamestate
    	install(new FactoryModuleBuilder()
    			.implement(ILocalMapInputHandler.class, AndroidLocalMapInputHandler.class)
    			.build(ILocalMapInputHandlerFactory.class));
    	bind(IControlScheme.class).annotatedWith(For.forClass(LocalMapGamestate.class)).to(AndroidLocalMapControlScheme.class);
    	
    	
    	// ConsoleGamestate
    	bind(IGameStateInputHandler.class).annotatedWith(For.forClass(ConsoleGamestate.class)).to(AndroidConsoleInputHandler.class);
    	
    }

}
