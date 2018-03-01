package com.tokelon.toktales.desktop.engine.inject;

import java.io.File;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.multibindings.Multibinder;
import com.tokelon.toktales.core.engine.IEnvironment;
import com.tokelon.toktales.core.engine.content.IContentService;
import com.tokelon.toktales.core.engine.inject.AbstractInjectModule;
import com.tokelon.toktales.core.engine.inject.For;
import com.tokelon.toktales.core.engine.input.IInputService;
import com.tokelon.toktales.core.engine.log.ILogService;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.engine.render.IRenderAccess;
import com.tokelon.toktales.core.engine.render.IRenderService;
import com.tokelon.toktales.core.engine.storage.IStorageService;
import com.tokelon.toktales.core.engine.ui.IUIService;
import com.tokelon.toktales.core.game.states.IGameStateInput;
import com.tokelon.toktales.core.game.states.IGameStateInputHandler;
import com.tokelon.toktales.core.game.states.InitialGamestate;
import com.tokelon.toktales.core.render.IKeyedTextureManagerFactory;
import com.tokelon.toktales.core.render.IRenderDriverFactory;
import com.tokelon.toktales.core.render.ITextureManagerFactory;
import com.tokelon.toktales.desktop.content.DesktopContentService;
import com.tokelon.toktales.desktop.game.states.DesktopGameStateInput;
import com.tokelon.toktales.desktop.game.states.IDesktopGameStateInput;
import com.tokelon.toktales.desktop.input.DesktopInputService;
import com.tokelon.toktales.desktop.input.IDesktopInputService;
import com.tokelon.toktales.desktop.lwjgl.render.DesktopRenderToolkit;
import com.tokelon.toktales.desktop.lwjgl.render.GLBitmapDriver;
import com.tokelon.toktales.desktop.lwjgl.render.GLBitmapFontDriver;
import com.tokelon.toktales.desktop.lwjgl.render.GLBitmapTextureManager;
import com.tokelon.toktales.desktop.lwjgl.render.GLKeyedTextureManager;
import com.tokelon.toktales.desktop.lwjgl.render.GLShapeDriver;
import com.tokelon.toktales.desktop.lwjgl.render.GLSpriteDriver;
import com.tokelon.toktales.desktop.lwjgl.render.GLSpriteFontDriver;
import com.tokelon.toktales.desktop.main.DesktopEnvironment;
import com.tokelon.toktales.desktop.main.DesktopLogService;
import com.tokelon.toktales.desktop.render.DesktopRenderService;
import com.tokelon.toktales.desktop.storage.DesktopStorageService;
import com.tokelon.toktales.desktop.ui.DesktopUIService;

public class DesktopInjectModule extends AbstractInjectModule {

	private static final String DATA_LOCATION_NAME = "Data";            // This is the DATA location

	private static final String STORAGE_LOCATION_NAME = "StorageData";  // This is the STORAGE location
	private static final String CONTENT_LOCATION_NAME = "ContentData";      // This is the CONTENT location


	/* It is possible to use assisted inject bindings together with provider bindings
	 * so DesktopContentService could get assisted inject as an alternative to the provider,
	 * however it will be differentiated by the type injected so IContentService or IContentServiceFactory
	 */
	
	@Override
	protected void configure() {
		/* Engine bindings */
		
		bindInEngineScope(IEnvironment.class, DesktopEnvironment.class);
		bindInEngineScope(ILogService.class, DesktopLogService.class);
		bindInEngineScope(IUIService.class, DesktopUIService.class);
		bindToProviderInEngineScope(IContentService.class, ProviderIContentService.class);
		bindToProviderInEngineScope(IStorageService.class, ProviderIStorageService.class);
		bindToProviderInEngineScope(IRenderService.class, ProviderIRenderService.class);
		bind(IInputService.class).to(IDesktopInputService.class);
		 bind(IDesktopInputService.class).to(DesktopInputService.class);
		 bindInEngineScope(DesktopInputService.class);
		
		
		/* Other bindings*/
		
		bind(IGameStateInput.class).to(IDesktopGameStateInput.class);
		bind(IDesktopGameStateInput.class).to(DesktopGameStateInput.class);
		
		// Use inexact annotation binding? Just ForClass.class
		bind(IGameStateInputHandler.class).annotatedWith(For.forClass(InitialGamestate.class)).to(IGameStateInputHandler.EmptyGameStateInputHandler.class);
		
		
		/* Unused so far - everything under here */

		/* Overriding bindings does not work with Multibinder or MapBinder, because the final value is always arbitrary
		 */
		Multibinder<IRenderDriverFactory> renderDriverFactoryBinder = Multibinder.newSetBinder(binder(), IRenderDriverFactory.class);
		renderDriverFactoryBinder.addBinding().to(GLSpriteDriver.GLSpriteDriverFactory.class);
		renderDriverFactoryBinder.addBinding().to(GLSpriteFontDriver.GLSpriteFontDriverFactory.class);
		renderDriverFactoryBinder.addBinding().to(GLBitmapFontDriver.GLBitmapFontDriverFactory.class);
		renderDriverFactoryBinder.addBinding().to(GLShapeDriver.GLShapeDriverFactory.class);
		renderDriverFactoryBinder.addBinding().to(GLBitmapDriver.GLBitmapDriverFactory.class);
		
		bind(IKeyedTextureManagerFactory.class).to(GLKeyedTextureManager.GLTextureManagerFactory.class);
		bind(ITextureManagerFactory.class).to(GLBitmapTextureManager.GLBitmapTextureManagerFactory.class);
	}

	
	/* Apparently this also works? What about scope though?
	@ForClass(InitialGamestate.class)
	@Provides
	protected IGameStateInputHandler providesIGameStateInputHandler() {
		return new BaseGamestate.EmptyStateInputHandler();
	}*/

	
	public static class ProviderIRenderService implements Provider<IRenderService> {
		private final IRenderService renderService;
		@Inject
		public ProviderIRenderService(DesktopRenderService desktopRenderService) {
			this.renderService = desktopRenderService;

			IRenderAccess renderAccess = desktopRenderService.getRenderAccess();
			renderAccess.registerKeyedTextureManager(new GLKeyedTextureManager.GLTextureManagerFactory());
			renderAccess.registerTextureManager(new GLBitmapTextureManager.GLBitmapTextureManagerFactory());
			renderAccess.registerToolkit(new DesktopRenderToolkit.DesktopRenderToolkitFactory());
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

	public static class ProviderIContentService implements Provider<IContentService> {
		private final IContentService contentService;
		@Inject
		public ProviderIContentService(ILogger logger) {
			this.contentService = new DesktopContentService(logger, new File(DATA_LOCATION_NAME), CONTENT_LOCATION_NAME);
		}
		
		@Override
		public IContentService get() {
			return contentService;
		}
	}
	
	public static class ProviderIStorageService implements Provider<IStorageService> {
		private final IStorageService storageService;
		@Inject
		public ProviderIStorageService(ILogger logger) {
			this.storageService = new DesktopStorageService(logger, new File(DATA_LOCATION_NAME), STORAGE_LOCATION_NAME);
		}

		@Override
		public IStorageService get() {
			return storageService;
		}
	}

	
}
