package com.tokelon.toktales.desktop.engine;

import java.io.File;

import com.google.inject.Provider;
import com.tokelon.toktales.core.engine.IEnvironment;
import com.tokelon.toktales.core.engine.content.IContentService;
import com.tokelon.toktales.core.engine.inject.AbstractInjectModule;
import com.tokelon.toktales.core.engine.input.IInputService;
import com.tokelon.toktales.core.engine.log.ILogService;
import com.tokelon.toktales.core.engine.render.IRenderAccess;
import com.tokelon.toktales.core.engine.render.IRenderService;
import com.tokelon.toktales.core.engine.storage.IStorageService;
import com.tokelon.toktales.core.engine.ui.IUIService;
import com.tokelon.toktales.core.game.states.IGameStateControl;
import com.tokelon.toktales.desktop.content.DesktopContentService;
import com.tokelon.toktales.desktop.game.states.DesktopGameStateManager;
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


	@Override
	protected void configure() {

		// Engine bindings
		bindInEngineScope(IEnvironment.class, DesktopEnvironment.class);
		bindInEngineScope(ILogService.class, DesktopLogService.class);
		bindInEngineScope(IUIService.class, DesktopUIService.class);
		bindToProviderInEngineScope(IContentService.class, () -> new DesktopContentService(new File(DATA_LOCATION_NAME), CONTENT_LOCATION_NAME));
		bindToProviderInEngineScope(IStorageService.class, () -> new DesktopStorageService(new File(DATA_LOCATION_NAME), STORAGE_LOCATION_NAME));
		bindToProviderInEngineScope(IRenderService.class, ProviderIRenderService.class);
		bind(IInputService.class).to(IDesktopInputService.class);
		 bind(IDesktopInputService.class).to(DesktopInputService.class);
		 bindInEngineScope(DesktopInputService.class);

		// Game bindings
		bindInGameScopeAndForNotScoped(IGameStateControl.class, DesktopGameStateManager.class);

	}


	public static class ProviderIRenderService implements Provider<IRenderService> {
		// TODO: Try multibindings ?
		
		@Override
		public IRenderService get() {
			DesktopRenderService desktopRenderService = new DesktopRenderService();

			IRenderAccess renderAccess = desktopRenderService.getRenderAccess();
			renderAccess.registerKeyedTextureManager(new GLKeyedTextureManager.GLTextureManagerFactory());
			renderAccess.registerTextureManager(new GLBitmapTextureManager.GLBitmapTextureManagerFactory());
			renderAccess.registerToolkit(new DesktopRenderToolkit.DesktopRenderToolkitFactory());
			renderAccess.registerDriver(new GLSpriteDriver.GLSpriteDriverFactory());
			renderAccess.registerDriver(new GLSpriteFontDriver.GLSpriteFontDriverFactory());
			renderAccess.registerDriver(new GLBitmapFontDriver.GLBitmapFontDriverFactory());
			renderAccess.registerDriver(new GLShapeDriver.GLShapeDriverFactory());
			renderAccess.registerDriver(new GLBitmapDriver.GLBitmapDriverFactory());

			return desktopRenderService;
		}
	}


}
