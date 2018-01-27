package com.tokelon.toktales.android.engine.inject;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.multibindings.Multibinder;
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

import android.content.Context;
import android.os.Environment;

public class AndroidInjectModule extends AbstractInjectModule {

	@Override
	protected void configure() {
		/* Engine bindings */
		
		bindInEngineScope(IEnvironment.class, AndroidEnvironment.class);
		bindInEngineScope(ILogService.class, AndroidLogService.class);
		bindToProviderInEngineScope(IUIService.class, ProviderIUIService.class);
		bindToProviderInEngineScope(IContentService.class, ProviderIContentService.class);
		bindToProviderInEngineScope(IStorageService.class, ProviderIStorageService.class);
		bindToProviderInEngineScope(IRenderService.class, ProviderIRenderService.class);
		bind(IInputService.class).to(IAndroidInputService.class);
		 bind(IAndroidInputService.class).to(AndroidInputService.class);
		 bindInEngineScope(AndroidInputService.class);


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


	private static class ProviderIRenderService implements Provider<IRenderService> {
		
		@Override
		public IRenderService get() {
			AndroidRenderService androidRenderService = new AndroidRenderService();

			IRenderAccess renderAccess = androidRenderService.getRenderAccess();
			renderAccess.registerKeyedTextureManager(new GLKeyedTextureManager.GLTextureManagerFactory());
			renderAccess.registerTextureManager(new GLBitmapTextureManager.GLBitmapTextureManagerFactory());
			renderAccess.registerToolkit(new AndroidRenderToolkit.AndroidRenderToolkitFactory());
			renderAccess.registerDriver(new GLSpriteDriver.GLSpriteDriverFactory());
			renderAccess.registerDriver(new GLSpriteFontDriver.GLSpriteFontDriverFactory());
			renderAccess.registerDriver(new GLBitmapFontDriver.GLBitmapFontDriverFactory());
			renderAccess.registerDriver(new GLShapeDriver.GLShapeDriverFactory());
			renderAccess.registerDriver(new GLBitmapDriver.GLBitmapDriverFactory());

			return androidRenderService;
		}
	}

	
	private static class ProviderIUIService implements Provider<IUIService> {
		private final ILogger logger;
		
		@Inject
		public ProviderIUIService(ILogger logger) {
			this.logger = logger;
		}
		
		@Override
		public IUIService get() {
			AndroidUIService androidUIService = new AndroidUIService(logger);
			androidUIService.addExtension("console", new AndroidUIConsoleExtension());
			return androidUIService;
		}
	}

	private static class ProviderIContentService implements Provider<IContentService> {
		private final ILogger logger;
		private final Context appContext;

		@Inject
		public ProviderIContentService(ILogger logger, Context applicationContext) {
			this.logger = logger;
			this.appContext = applicationContext;
		}

		@Override
		public IContentService get() {
			AndroidContentService androidContentService = new AndroidContentService(logger, appContext);
			return androidContentService;
		}
	}

	private static class ProviderIStorageService implements Provider<IStorageService> {
		private final ILogger logger;

		@Inject
		public ProviderIStorageService(ILogger logger) {
			this.logger = logger;
		}

		@Override
		public IStorageService get() {
			AndroidStorageService androidStorageService = new AndroidStorageService(logger, Environment.getExternalStorageDirectory());
			return androidStorageService;
		}
	}


}
