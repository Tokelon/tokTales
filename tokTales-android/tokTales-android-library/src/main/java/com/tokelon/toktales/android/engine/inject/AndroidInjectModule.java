package com.tokelon.toktales.android.engine.inject;

import com.tokelon.toktales.android.activity.integration.EngineIntegration;
import com.tokelon.toktales.android.activity.integration.IEngineIntegration;
import com.tokelon.toktales.android.activity.integration.IKeyboardActivityIntegration;
import com.tokelon.toktales.android.activity.integration.IKeyboardActivityIntegration.IKeyboardActivityIntegrationFactory;
import com.tokelon.toktales.android.activity.integration.ISimpleRequestPermissionsIntegration.ISimpleRequestPermissionsIntegrationFactory;
import com.tokelon.toktales.android.activity.integration.ISurfaceViewIntegration;
import com.tokelon.toktales.android.activity.integration.IUIServiceIntegration;
import com.tokelon.toktales.android.activity.integration.KeyboardActivityIntegration;
import com.tokelon.toktales.android.activity.integration.KeyboardActivityIntegration.KeyboardActivityIntegrationFactory;
import com.tokelon.toktales.android.activity.integration.SimpleRequestPermissionsIntegration.SimpleRequestPermissionsIntegrationFactory;
import com.tokelon.toktales.android.activity.integration.SurfaceViewIntegration;
import com.tokelon.toktales.android.activity.integration.UIServiceIntegration;
import com.tokelon.toktales.android.app.AndroidEnvironment;
import com.tokelon.toktales.android.data.AndroidContentService;
import com.tokelon.toktales.android.input.AndroidInputService;
import com.tokelon.toktales.android.input.IAndroidInputService;
import com.tokelon.toktales.android.input.dispatch.AndroidInputConsumer;
import com.tokelon.toktales.android.input.dispatch.AndroidInputDispatch;
import com.tokelon.toktales.android.input.dispatch.AndroidInputProducer;
import com.tokelon.toktales.android.input.dispatch.IAndroidInputConsumer.IAndroidInputConsumerFactory;
import com.tokelon.toktales.android.input.dispatch.IAndroidInputDispatch;
import com.tokelon.toktales.android.input.dispatch.IAndroidInputProducer.IAndroidInputProducerFactory;
import com.tokelon.toktales.android.render.AndroidRenderService;
import com.tokelon.toktales.android.render.DefaultRenderViewAdapter;
import com.tokelon.toktales.android.render.IRenderViewAdapter;
import com.tokelon.toktales.android.render.IViewRenderer;
import com.tokelon.toktales.android.render.opengl.DefaultGameViewRenderer;
import com.tokelon.toktales.android.render.tools.IUIControl.IUIControlFactory;
import com.tokelon.toktales.android.render.tools.UIControl;
import com.tokelon.toktales.android.states.AndroidGameStateInput;
import com.tokelon.toktales.android.states.AndroidInitialGamestateInputHandler;
import com.tokelon.toktales.android.states.IAndroidGameStateInput;
import com.tokelon.toktales.android.storage.AndroidStorageService;
import com.tokelon.toktales.android.ui.AndroidUIService;
import com.tokelon.toktales.android.ui.IAndroidUIService;
import com.tokelon.toktales.android.ui.IUserInterface;
import com.tokelon.toktales.android.ui.UserInterface;
import com.tokelon.toktales.core.engine.IEnvironment;
import com.tokelon.toktales.core.engine.content.IContentService;
import com.tokelon.toktales.core.engine.inject.AbstractInjectModule;
import com.tokelon.toktales.core.engine.input.IInputDispatch;
import com.tokelon.toktales.core.engine.input.IInputService;
import com.tokelon.toktales.core.engine.render.IRenderService;
import com.tokelon.toktales.core.engine.storage.IStorageService;
import com.tokelon.toktales.core.engine.ui.IUIService;
import com.tokelon.toktales.core.game.states.IGameStateInput;
import com.tokelon.toktales.core.game.states.IGameStateInputHandler;
import com.tokelon.toktales.core.game.states.InitialGamestate;
import com.tokelon.toktales.tools.core.sub.inject.scope.For;

public class AndroidInjectModule extends AbstractInjectModule {
	/* When using providers, always create or inject the object once in the constructor,
	 * this is to adhere to the scope of the provider.
	 * 
	 * When injected be aware that it might have it's own scope.
	 */
	
	
	@Override
	protected void configure() {
		/* Engine bindings */
		
		bindInEngineScope(IEnvironment.class, AndroidEnvironment.class);
		bind(IUIService.class).to(IAndroidUIService.class);
		 bindInEngineScope(IAndroidUIService.class, AndroidUIService.class);
		bindInEngineScope(IContentService.class, AndroidContentService.class);
		bindInEngineScope(IStorageService.class, AndroidStorageService.class);
		bind(IStorageService.IStorageServiceFactory.class).to(AndroidStorageService.AndroidStorageServiceFactory.class);
		bindInEngineScope(IRenderService.class, AndroidRenderService.class);
		bind(IInputService.class).to(IAndroidInputService.class);
		 bind(IAndroidInputService.class).to(AndroidInputService.class);
		 bindInEngineScope(AndroidInputService.class); // Bind to scope via the implementation!
		 bind(IInputDispatch.class).to(IAndroidInputDispatch.class);
		 bind(IAndroidInputDispatch.class).to(AndroidInputDispatch.class);
		  bind(IAndroidInputProducerFactory.class).to(AndroidInputProducer.AndroidInputProducerFactory.class);
		  bind(IAndroidInputConsumerFactory.class).to(AndroidInputConsumer.AndroidInputConsumerFactory.class);
		
		bindInGameScopeAndForNotScoped(IUserInterface.class, UserInterface.class);
		
		
		/* Other bindings */
		
		bind(IUIServiceIntegration.class).to(UIServiceIntegration.class);
		bind(IKeyboardActivityIntegration.class).to(KeyboardActivityIntegration.class);
		bind(IKeyboardActivityIntegrationFactory.class).to(KeyboardActivityIntegrationFactory.class);
		bind(ISimpleRequestPermissionsIntegrationFactory.class).to(SimpleRequestPermissionsIntegrationFactory.class);
		bind(ISurfaceViewIntegration.class).to(SurfaceViewIntegration.class);
		bind(IEngineIntegration.class).to(EngineIntegration.class);
		
		
		bind(IGameStateInput.class).to(IAndroidGameStateInput.class);
		bind(IAndroidGameStateInput.class).to(AndroidGameStateInput.class);
		
		bind(IGameStateInputHandler.class).annotatedWith(For.forClass(InitialGamestate.class)).to(AndroidInitialGamestateInputHandler.class);
		
		
		bind(IViewRenderer.class).to(DefaultGameViewRenderer.class);
		bind(IRenderViewAdapter.class).to(DefaultRenderViewAdapter.class);
		bind(IUIControlFactory.class).to(UIControl.UIControlFactory.class);
	}
	
}
