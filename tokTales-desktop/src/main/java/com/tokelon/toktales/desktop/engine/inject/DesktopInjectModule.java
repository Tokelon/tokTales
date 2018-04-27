package com.tokelon.toktales.desktop.engine.inject;

import java.io.File;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Scopes;
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
import com.tokelon.toktales.core.render.IRenderToolkit;
import com.tokelon.toktales.core.render.IRenderToolkit.IRenderToolkitFactory;
import com.tokelon.toktales.core.render.ITextureManagerFactory;
import com.tokelon.toktales.core.render.opengl.gl20.IGL11;
import com.tokelon.toktales.core.render.opengl.gl20.IGL13;
import com.tokelon.toktales.core.render.opengl.gl20.IGL14;
import com.tokelon.toktales.core.render.opengl.gl20.IGL15;
import com.tokelon.toktales.core.render.opengl.gl20.IGL20;
import com.tokelon.toktales.desktop.content.DesktopContentService;
import com.tokelon.toktales.desktop.game.states.DesktopGameStateInput;
import com.tokelon.toktales.desktop.game.states.IDesktopGameStateInput;
import com.tokelon.toktales.desktop.input.DesktopInputService;
import com.tokelon.toktales.desktop.input.IDesktopInputService;
import com.tokelon.toktales.desktop.lwjgl.render.DesktopRenderToolkit;
import com.tokelon.toktales.desktop.lwjgl.render.DesktopRenderToolkit.DesktopRenderToolkitFactory;
import com.tokelon.toktales.desktop.lwjgl.render.GLBitmapDriver;
import com.tokelon.toktales.desktop.lwjgl.render.GLBitmapFontDriver;
import com.tokelon.toktales.desktop.lwjgl.render.GLKeyedTextureManager;
import com.tokelon.toktales.desktop.lwjgl.render.GLShapeDriver;
import com.tokelon.toktales.desktop.lwjgl.render.GLSpriteDriver;
import com.tokelon.toktales.desktop.lwjgl.render.GLSpriteFontDriver;
import com.tokelon.toktales.desktop.lwjgl.render.GLTextureManager;
import com.tokelon.toktales.desktop.main.DesktopEnvironment;
import com.tokelon.toktales.desktop.main.DesktopLogService;
import com.tokelon.toktales.desktop.render.DesktopRenderService;
import com.tokelon.toktales.desktop.render.opengl.gl20.DesktopGL11;
import com.tokelon.toktales.desktop.render.opengl.gl20.DesktopGL13;
import com.tokelon.toktales.desktop.render.opengl.gl20.DesktopGL14;
import com.tokelon.toktales.desktop.render.opengl.gl20.DesktopGL15;
import com.tokelon.toktales.desktop.render.opengl.gl20.DesktopGL20;
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
		
		bind(IGL11.class).to(DesktopGL11.class).in(Scopes.SINGLETON);
		bind(IGL13.class).to(DesktopGL13.class).in(Scopes.SINGLETON);
		bind(IGL14.class).to(DesktopGL14.class).in(Scopes.SINGLETON);
		bind(IGL15.class).to(DesktopGL15.class).in(Scopes.SINGLETON);
		bind(IGL20.class).to(DesktopGL20.class).in(Scopes.SINGLETON);
		
		bind(GLSpriteDriver.class);
		bind(GLSpriteFontDriver.class);
		bind(GLBitmapFontDriver.class);
		bind(GLShapeDriver.class);
		bind(GLBitmapDriver.class);
		
		bind(IKeyedTextureManagerFactory.class).to(GLKeyedTextureManager.GLKeyedTextureManagerFactory.class);
		bind(ITextureManagerFactory.class).to(GLTextureManager.GLTextureManagerFactory.class);
		bind(IRenderToolkitFactory.class).to(DesktopRenderToolkitFactory.class);
		bind(IRenderToolkit.class).to(DesktopRenderToolkit.class);
		
		
		/* Unused so far - everything under here */

		/* Overriding bindings does not work with Multibinder or MapBinder, because the final value is always arbitrary
		 */
		Multibinder<IRenderDriverFactory> renderDriverFactoryBinder = Multibinder.newSetBinder(binder(), IRenderDriverFactory.class);
		renderDriverFactoryBinder.addBinding().to(GLSpriteDriver.GLSpriteDriverFactory.class);
		renderDriverFactoryBinder.addBinding().to(GLSpriteFontDriver.GLSpriteFontDriverFactory.class);
		renderDriverFactoryBinder.addBinding().to(GLBitmapFontDriver.GLBitmapFontDriverFactory.class);
		renderDriverFactoryBinder.addBinding().to(GLShapeDriver.GLShapeDriverFactory.class);
		renderDriverFactoryBinder.addBinding().to(GLBitmapDriver.GLBitmapDriverFactory.class);

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
		public ProviderIRenderService(
				DesktopRenderService desktopRenderService,
				IKeyedTextureManagerFactory keyedTextureManagerFactory,
				ITextureManagerFactory textureManagerFactory,
				IRenderToolkitFactory renderToolkitFactory,
				GLSpriteDriver.GLSpriteDriverFactory glSpriteDriverFactory,
				GLSpriteFontDriver.GLSpriteFontDriverFactory glSpriteFontDriverFactory,
				GLBitmapFontDriver.GLBitmapFontDriverFactory glBitmapFontDriverFactory,
				GLShapeDriver.GLShapeDriverFactory glShapeDriverFactory,
				GLBitmapDriver.GLBitmapDriverFactory glBitmapDriverFactory
		) {

			this.renderService = desktopRenderService;

			IRenderAccess renderAccess = desktopRenderService.getRenderAccess();
			renderAccess.registerKeyedTextureManager(keyedTextureManagerFactory);
			renderAccess.registerTextureManager(textureManagerFactory);
			renderAccess.registerToolkit(renderToolkitFactory);
			renderAccess.registerDriver(glSpriteDriverFactory);
			renderAccess.registerDriver(glSpriteFontDriverFactory);
			renderAccess.registerDriver(glBitmapFontDriverFactory);
			renderAccess.registerDriver(glShapeDriverFactory);
			renderAccess.registerDriver(glBitmapDriverFactory);
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
