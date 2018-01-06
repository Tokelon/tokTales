package com.tokelon.toktales.android.engine;

import com.google.inject.Inject;
import com.google.inject.Provider;
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
import com.tokelon.toktales.android.states.AndroidGameStateManager;
import com.tokelon.toktales.android.storage.AndroidStorageService;
import com.tokelon.toktales.android.ui.AndroidUIService;
import com.tokelon.toktales.core.engine.IEnvironment;
import com.tokelon.toktales.core.engine.content.IContentService;
import com.tokelon.toktales.core.engine.inject.AbstractInjectModule;
import com.tokelon.toktales.core.engine.inject.GameScoped;
import com.tokelon.toktales.core.engine.input.IInputService;
import com.tokelon.toktales.core.engine.log.ILogService;
import com.tokelon.toktales.core.engine.render.IRenderAccess;
import com.tokelon.toktales.core.engine.render.IRenderService;
import com.tokelon.toktales.core.engine.storage.IStorageService;
import com.tokelon.toktales.core.engine.ui.IUIService;
import com.tokelon.toktales.core.game.states.IGameStateControl;

import android.content.Context;

public class AndroidInjectModule extends AbstractInjectModule {

	@Override
	protected void configure() {

		// Engine bindings
		bindInEngineScope(IEnvironment.class, AndroidEnvironment.class);
		bindInEngineScope(ILogService.class, AndroidLogService.class);
		bindToProviderInEngineScope(IUIService.class, () -> {
			AndroidUIService androidUIService = new AndroidUIService();
			androidUIService.addExtension("console", new AndroidUIConsoleExtension());
			return androidUIService;
		});
		bindToProviderInEngineScope(IContentService.class, ProviderIContentService.class);
		bindToProviderInEngineScope(IStorageService.class, ProviderIStorageService.class);
		bindToProviderInEngineScope(IRenderService.class, ProviderIRenderService.class);
		bind(IInputService.class).to(IAndroidInputService.class);
		 bind(IAndroidInputService.class).to(AndroidInputService.class);
		 bindInEngineScope(AndroidInputService.class);

		// Game bindings
		bindInScopeAndForNotScoped(IGameStateControl.class, AndroidGameStateManager.class, GameScoped.class);

	}


	private static class ProviderIRenderService implements Provider<IRenderService> {
		// TODO: Try multibindings ?
		
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


	private static class ProviderIContentService implements Provider<IContentService> {
		private final Context appContext;

		@Inject
		public ProviderIContentService(Context applicationContext) {
			this.appContext = applicationContext;
		}

		@Override
		public IContentService get() {
			AndroidContentService androidContentService = new AndroidContentService();
			androidContentService.setGlobalContext(appContext);
			return androidContentService;
		}
	}

	private static class ProviderIStorageService implements Provider<IStorageService> {
		private final Context appContext;

		@Inject
		public ProviderIStorageService(Context applicationContext) {
			this.appContext = applicationContext;
		}

		@Override
		public IStorageService get() {
			AndroidStorageService androidStorageService = new AndroidStorageService();
			androidStorageService.setGlobalContext(appContext);
			return androidStorageService;
		}
	}


}
