package com.tokelon.toktales.android.engine.inject;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.google.inject.multibindings.Multibinder;
import com.tokelon.toktales.android.activity.integration.ActivityIntegrator;
import com.tokelon.toktales.android.activity.integration.IActivityIntegrator;
import com.tokelon.toktales.android.activity.integration.IActivityIntegrator.IActivityIntegratorFactory;
import com.tokelon.toktales.android.activity.integration.IKeyboardActivityIntegration;
import com.tokelon.toktales.android.activity.integration.IUIServiceIntegration;
import com.tokelon.toktales.android.activity.integration.KeyboardActivityIntegration;
import com.tokelon.toktales.android.app.AndroidEnvironment;
import com.tokelon.toktales.android.app.AndroidLogService;
import com.tokelon.toktales.android.data.AndroidContentService;
import com.tokelon.toktales.android.engine.ui.AndroidUIConsoleExtension;
import com.tokelon.toktales.android.input.AndroidInputService;
import com.tokelon.toktales.android.input.IAndroidInputService;
import com.tokelon.toktales.android.render.opengl.AndroidRenderService;
import com.tokelon.toktales.android.render.opengl.AndroidRenderToolkit;
import com.tokelon.toktales.android.render.opengl.GLBitmapDriver;
import com.tokelon.toktales.android.render.opengl.GLBitmapFontDriver;
import com.tokelon.toktales.android.render.opengl.GLBitmapTextureManager;
import com.tokelon.toktales.android.render.opengl.GLKeyedTextureManager;
import com.tokelon.toktales.android.render.opengl.GLShapeDriver;
import com.tokelon.toktales.android.render.opengl.GLSpriteDriver;
import com.tokelon.toktales.android.render.opengl.GLSpriteFontDriver;
import com.tokelon.toktales.android.storage.AndroidStorageService;
import com.tokelon.toktales.android.ui.AndroidUIService;
import com.tokelon.toktales.android.ui.IAndroidUIService;
import com.tokelon.toktales.android.ui.IUserInterface;
import com.tokelon.toktales.android.ui.UserInterface;
import com.tokelon.toktales.core.engine.IEnvironment;
import com.tokelon.toktales.core.engine.content.IContentService;
import com.tokelon.toktales.core.engine.inject.AbstractInjectModule;
import com.tokelon.toktales.core.engine.input.IInputService;
import com.tokelon.toktales.core.engine.log.ILogService;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.engine.render.IRenderAccess;
import com.tokelon.toktales.core.engine.render.IRenderService;
import com.tokelon.toktales.core.engine.storage.IStorageService;
import com.tokelon.toktales.core.engine.ui.IUIService;
import com.tokelon.toktales.core.render.IKeyedTextureManagerFactory;
import com.tokelon.toktales.core.render.IRenderDriverFactory;
import com.tokelon.toktales.core.render.ITextureManagerFactory;

import android.os.Environment;

public class AndroidInjectModule extends AbstractInjectModule {

	@Override
	protected void configure() {
		/* Engine bindings */
		
		bindInEngineScope(IEnvironment.class, AndroidEnvironment.class);
		bindInEngineScope(ILogService.class, AndroidLogService.class);
		bind(IUIService.class).to(IAndroidUIService.class);
		 bind(IAndroidUIService.class).toProvider(ProviderIUIService.class);
		 bindInEngineScope(ProviderIUIService.class); // Bind to scope via the provider!
		 //bindInEngineScope(AndroidUIService.class); // Do not bind implementation in this case, scoping will be done via the provider
		
		bindInEngineScope(IContentService.class, AndroidContentService.class);
		bindToProviderInEngineScope(IStorageService.class, ProviderIStorageService.class);
		 //bindInEngineScope(AndroidStorageService.class); // Does not affect IStorageService binding since provider assignment is via creation
		
		bindToProviderInEngineScope(IRenderService.class, ProviderIRenderService.class);
		bind(IInputService.class).to(IAndroidInputService.class);
		 bind(IAndroidInputService.class).to(AndroidInputService.class);
		 bindInEngineScope(AndroidInputService.class); // Bind to scope via the implementation!
		
		
		/* Other bindings */
		
		install(new FactoryModuleBuilder()
				.implement(IActivityIntegrator.class, ActivityIntegrator.class)
				.build(IActivityIntegratorFactory.class));
		bind(IUIServiceIntegration.class).to(IUIServiceIntegration.UIServiceIntegration.class);
		bind(IKeyboardActivityIntegration.class).to(KeyboardActivityIntegration.class);
		bind(IUserInterface.class).to(UserInterface.class);
		
		
		/* Unused so far - everything under here */
		
		Multibinder<IRenderDriverFactory> renderDriverFactoryBinder = Multibinder.newSetBinder(binder(), IRenderDriverFactory.class);
		renderDriverFactoryBinder.addBinding().to(GLSpriteDriver.GLSpriteDriverFactory.class);
		renderDriverFactoryBinder.addBinding().to(GLSpriteFontDriver.GLSpriteFontDriverFactory.class);
		renderDriverFactoryBinder.addBinding().to(GLBitmapFontDriver.GLBitmapFontDriverFactory.class);
		renderDriverFactoryBinder.addBinding().to(GLShapeDriver.GLShapeDriverFactory.class);
		renderDriverFactoryBinder.addBinding().to(GLBitmapDriver.GLBitmapDriverFactory.class);
		
		bind(IKeyedTextureManagerFactory.class).to(GLKeyedTextureManager.GLTextureManagerFactory.class);
		bind(ITextureManagerFactory.class).to(GLBitmapTextureManager.GLBitmapTextureManagerFactory.class);
	}



	/* When using providers, always create or inject the object once in the constructor,
	 * this is to adhere to the scope of the provider.
	 * 
	 * When injected be aware that it might have it's own scope.
	 */
	
	private static class ProviderIRenderService implements Provider<IRenderService> {
		private final IRenderService renderService;
		
		@Inject
		public ProviderIRenderService(AndroidRenderService androidRenderService) {
			this.renderService = androidRenderService;
			
			IRenderAccess renderAccess = androidRenderService.getRenderAccess();
			renderAccess.registerKeyedTextureManager(new GLKeyedTextureManager.GLTextureManagerFactory());
			renderAccess.registerTextureManager(new GLBitmapTextureManager.GLBitmapTextureManagerFactory());
			renderAccess.registerToolkit(new AndroidRenderToolkit.AndroidRenderToolkitFactory());
			renderAccess.registerDriver(new GLSpriteDriver.GLSpriteDriverFactory());
			renderAccess.registerDriver(new GLSpriteFontDriver.GLSpriteFontDriverFactory());
			renderAccess.registerDriver(new GLBitmapFontDriver.GLBitmapFontDriverFactory());
			renderAccess.registerDriver(new GLShapeDriver.GLShapeDriverFactory());
			renderAccess.registerDriver(new GLBitmapDriver.GLBitmapDriverFactory());
		}
		
		@Override
		public IRenderService get() {
			return renderService;
		}
	}

	private static class ProviderIUIService implements Provider<IAndroidUIService> {
		private final IAndroidUIService uiService;
		@Inject
		public ProviderIUIService(AndroidUIService androidUIService) {
			// Object assignment via injection; scope of AndroidUIService will apply!
			this.uiService = androidUIService;
			
			uiService.addExtension("console", new AndroidUIConsoleExtension()); // Possibly refactor
		}
		
		@Override
		public IAndroidUIService get() {
			return uiService;
		}
	}

	private static class ProviderIStorageService implements Provider<IStorageService> {
		private final IStorageService storageService;
		@Inject
		public ProviderIStorageService(ILogger logger) {
			// Object assignment via creation; scope of AndroidStorageService will not apply!
			storageService = new AndroidStorageService(logger, Environment.getExternalStorageDirectory());
		}

		@Override
		public IStorageService get() {
			return storageService;
		}
	}


}
