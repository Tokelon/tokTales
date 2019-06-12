package com.tokelon.toktales.android.engine.inject;

import java.io.File;

import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.tokelon.toktales.android.activity.integration.ActivityIntegrator;
import com.tokelon.toktales.android.activity.integration.IActivityIntegrator;
import com.tokelon.toktales.android.activity.integration.IActivityIntegrator.IActivityIntegratorFactory;
import com.tokelon.toktales.android.activity.integration.IKeyboardActivityIntegration;
import com.tokelon.toktales.android.activity.integration.IUIServiceIntegration;
import com.tokelon.toktales.android.activity.integration.KeyboardActivityIntegration;
import com.tokelon.toktales.android.app.AndroidEnvironment;
import com.tokelon.toktales.android.app.AndroidLogService;
import com.tokelon.toktales.android.data.AndroidContentService;
import com.tokelon.toktales.android.input.AndroidInputService;
import com.tokelon.toktales.android.input.IAndroidInputService;
import com.tokelon.toktales.android.input.dispatch.AndroidInputConsumer;
import com.tokelon.toktales.android.input.dispatch.AndroidInputDispatch;
import com.tokelon.toktales.android.input.dispatch.AndroidInputProducer;
import com.tokelon.toktales.android.input.dispatch.IAndroidInputConsumer.IAndroidInputConsumerFactory;
import com.tokelon.toktales.android.input.dispatch.IAndroidInputDispatch;
import com.tokelon.toktales.android.input.dispatch.IAndroidInputProducer.IAndroidInputProducerFactory;
import com.tokelon.toktales.android.render.opengl.AndroidRenderService;
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
import com.tokelon.toktales.core.engine.inject.For;
import com.tokelon.toktales.core.engine.inject.annotation.StorageRoot;
import com.tokelon.toktales.core.engine.input.IInputDispatch;
import com.tokelon.toktales.core.engine.input.IInputService;
import com.tokelon.toktales.core.engine.log.ILogService;
import com.tokelon.toktales.core.engine.render.IRenderService;
import com.tokelon.toktales.core.engine.storage.IStorageService;
import com.tokelon.toktales.core.engine.ui.IUIService;
import com.tokelon.toktales.core.game.states.IGameStateInput;
import com.tokelon.toktales.core.game.states.IGameStateInputHandler;
import com.tokelon.toktales.core.game.states.InitialGamestate;

import android.os.Environment;

public class AndroidInjectModule extends AbstractInjectModule {
	/* When using providers, always create or inject the object once in the constructor,
	 * this is to adhere to the scope of the provider.
	 * 
	 * When injected be aware that it might have it's own scope.
	 */
	
	public static final String DEFAULT_STORAGE_DIR_NAME = "Tokelon";
	
	
	@Override
	protected void configure() {
		/* Engine bindings */
		bind(String.class).annotatedWith(StorageRoot.class).toProvider(() -> new File(Environment.getExternalStorageDirectory(), DEFAULT_STORAGE_DIR_NAME).getPath());
		// bind a path for ContentRootPath as well?
		
		
		bindInEngineScope(IEnvironment.class, AndroidEnvironment.class);
		bindInEngineScope(ILogService.class, AndroidLogService.class);
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
		
		
		/* Other bindings */
		install(new FactoryModuleBuilder()
				.implement(IActivityIntegrator.class, ActivityIntegrator.class)
				.build(IActivityIntegratorFactory.class));
		bind(IUIServiceIntegration.class).to(IUIServiceIntegration.UIServiceIntegration.class);
		bind(IKeyboardActivityIntegration.class).to(KeyboardActivityIntegration.class);
		bind(IUserInterface.class).to(UserInterface.class); // Maybe bind to Game scope
		
		bind(IGameStateInput.class).to(IAndroidGameStateInput.class);
		bind(IAndroidGameStateInput.class).to(AndroidGameStateInput.class);
		
		bind(IGameStateInputHandler.class).annotatedWith(For.forClass(InitialGamestate.class)).to(AndroidInitialGamestateInputHandler.class);
	}
	
}
