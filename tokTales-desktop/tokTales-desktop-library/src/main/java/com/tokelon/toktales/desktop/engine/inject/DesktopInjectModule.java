package com.tokelon.toktales.desktop.engine.inject;

import com.tokelon.toktales.core.engine.IEnvironment;
import com.tokelon.toktales.core.engine.content.IContentService;
import com.tokelon.toktales.core.engine.inject.AbstractInjectModule;
import com.tokelon.toktales.core.engine.input.IInputDispatch;
import com.tokelon.toktales.core.engine.input.IInputService;
import com.tokelon.toktales.core.engine.render.IRenderService;
import com.tokelon.toktales.core.engine.storage.IStorageService;
import com.tokelon.toktales.core.engine.ui.IUIService;
import com.tokelon.toktales.core.game.state.IGameStateInput;
import com.tokelon.toktales.core.game.state.IGameStateInputHandler;
import com.tokelon.toktales.core.game.state.InitialGamestate;
import com.tokelon.toktales.desktop.content.DesktopContentService;
import com.tokelon.toktales.desktop.engine.DesktopEnvironment;
import com.tokelon.toktales.desktop.game.state.DesktopGameStateInput;
import com.tokelon.toktales.desktop.game.state.IDesktopGameStateInput;
import com.tokelon.toktales.desktop.input.DesktopInputService;
import com.tokelon.toktales.desktop.input.IDesktopInputService;
import com.tokelon.toktales.desktop.input.dispatch.DesktopInputConsumer;
import com.tokelon.toktales.desktop.input.dispatch.DesktopInputDispatch;
import com.tokelon.toktales.desktop.input.dispatch.DesktopInputProducer;
import com.tokelon.toktales.desktop.input.dispatch.IDesktopInputConsumer.IDesktopInputConsumerFactory;
import com.tokelon.toktales.desktop.input.dispatch.IDesktopInputDispatch;
import com.tokelon.toktales.desktop.input.dispatch.IDesktopInputProducer.IDesktopInputProducerFactory;
import com.tokelon.toktales.desktop.render.DesktopRenderService;
import com.tokelon.toktales.desktop.storage.DesktopStorageService;
import com.tokelon.toktales.desktop.ui.DesktopUIService;
import com.tokelon.toktales.tools.core.sub.inject.scope.For;

public class DesktopInjectModule extends AbstractInjectModule {
	
	/* It is possible to use assisted inject bindings together with provider bindings
	 * so DesktopContentService could get assisted inject as an alternative to the provider,
	 * however it will be differentiated by the type injected so IContentService or IContentServiceFactory
	 */
	
	/* Overriding bindings does not work with Multibinder or MapBinder, because the final value is always arbitrary
	 */
	
	
	@Override
	protected void configure() {
		/* Engine bindings */
		
		bindInEngineScope(IEnvironment.class, DesktopEnvironment.class);
		bindInEngineScope(IUIService.class, DesktopUIService.class);
		bindInEngineScope(IContentService.class, DesktopContentService.class);
		bindInEngineScope(IStorageService.class, DesktopStorageService.class);
		bind(IStorageService.IStorageServiceFactory.class).to(DesktopStorageService.DesktopStorageServiceFactory.class);
		bindInEngineScope(IRenderService.class, DesktopRenderService.class);
		bind(IInputService.class).to(IDesktopInputService.class);
		 bind(IDesktopInputService.class).to(DesktopInputService.class);
		 bindInEngineScope(DesktopInputService.class);
		 bind(IInputDispatch.class).to(IDesktopInputDispatch.class);
		 bind(IDesktopInputDispatch.class).to(DesktopInputDispatch.class);
		  bind(IDesktopInputProducerFactory.class).to(DesktopInputProducer.DesktopInputProducerFactory.class);
		  bind(IDesktopInputConsumerFactory.class).to(DesktopInputConsumer.DesktopInputConsumerFactory.class);
		  
		
		/* Other bindings*/
		
		bind(IGameStateInput.class).to(IDesktopGameStateInput.class);
		bind(IDesktopGameStateInput.class).to(DesktopGameStateInput.class);
		
		// Use inexact annotation binding? Just ForClass.class
		bind(IGameStateInputHandler.class).annotatedWith(For.forClass(InitialGamestate.class)).to(IGameStateInputHandler.EmptyGameStateInputHandler.class);
	}
	
	
	/* Apparently this also works? What about scope though?
	@ForClass(InitialGamestate.class)
	@Provides
	protected IGameStateInputHandler providesIGameStateInputHandler() {
		return new BaseGamestate.EmptyStateInputHandler();
	}*/
	
}
