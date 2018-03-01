package com.tokelon.toktales.extens.def.android.engine;

import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.tokelon.toktales.core.engine.inject.AbstractInjectModule;
import com.tokelon.toktales.core.engine.inject.For;
import com.tokelon.toktales.core.game.states.IControlScheme;
import com.tokelon.toktales.extens.def.android.states.AndroidConsoleInputHandler;
import com.tokelon.toktales.extens.def.android.states.localmap.AndroidLocalMapControlScheme;
import com.tokelon.toktales.extens.def.android.states.localmap.AndroidLocalMapInputHandler;
import com.tokelon.toktales.extens.def.core.game.states.IConsoleGamestateInputHandler;
import com.tokelon.toktales.extens.def.core.game.states.IConsoleGamestateInputHandler.IConsoleGamestateInputHandlerFactory;
import com.tokelon.toktales.extens.def.core.game.states.localmap.ILocalMapInputHandler;
import com.tokelon.toktales.extens.def.core.game.states.localmap.ILocalMapInputHandler.ILocalMapInputHandlerFactory;
import com.tokelon.toktales.extens.def.core.game.states.localmap.LocalMapGamestate;

public class AndroidDefaultExtensionsInjectModule extends AbstractInjectModule {

	
    @Override
    protected void configure() {
    	// LocalMapGamestate
    	install(new FactoryModuleBuilder()
    			.implement(ILocalMapInputHandler.class, AndroidLocalMapInputHandler.class)
    			.build(ILocalMapInputHandlerFactory.class));
    	bind(IControlScheme.class).annotatedWith(For.forClass(LocalMapGamestate.class)).to(AndroidLocalMapControlScheme.class);
    	
    	
    	// ConsoleGamestate
    	install(new FactoryModuleBuilder()
    			.implement(IConsoleGamestateInputHandler.class, AndroidConsoleInputHandler.class)
    			.build(IConsoleGamestateInputHandlerFactory.class));
	    
    }

}
