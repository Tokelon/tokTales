package com.tokelon.toktales.desktop.main;

import java.io.File;

import com.google.inject.Injector;
import com.tokelon.toktales.core.engine.BaseSetup;
import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.engine.Engine.EngineFactory;
import com.tokelon.toktales.core.engine.IEngine;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.engine.log.MainLogger;
import com.tokelon.toktales.core.engine.render.IRenderAccess;
import com.tokelon.toktales.core.game.Game.GameFactory;
import com.tokelon.toktales.core.game.IGame;
import com.tokelon.toktales.core.game.IGameAdapter;
import com.tokelon.toktales.core.game.states.TokelonStates;
import com.tokelon.toktales.desktop.content.DesktopContentService;
import com.tokelon.toktales.desktop.game.states.DesktopGameStateManager;
import com.tokelon.toktales.desktop.game.states.DesktopInitialGamestate;
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
import com.tokelon.toktales.desktop.render.DesktopRenderService;
import com.tokelon.toktales.desktop.storage.DesktopStorageService;
import com.tokelon.toktales.desktop.ui.DesktopUIService;

public class DesktopSetup extends BaseSetup {

	public static final String TAG = "DesktopSetup";

	
	private static final String DATA_LOCATION_NAME = "Data";			// This is the DATA location
	
	private static final String STORAGE_LOCATION_NAME = "StorageData";  // This is the STORAGE location
	private static final String CONTENT_LOCATION_NAME = "ContentData";		// This is the CONTENT location
	
	public DesktopSetup(IGameAdapter gameAdapter) {
		super(gameAdapter);
	}
	
	
	@Override
	protected IEngine createEngine(Injector injector, EngineFactory defaultEngineFactory) throws EngineException {
		
		
		DesktopEnvironment desktopEnv = new DesktopEnvironment();
		defaultEngineFactory.setEnvironment(desktopEnv);
		
		
		DesktopLogService desktopLogService = new DesktopLogService();
		defaultEngineFactory.setLogService(desktopLogService);
		
		
		DesktopUIService desktopUIService = new DesktopUIService();
		defaultEngineFactory.setUIService(desktopUIService);
		
		
		File dataRootContent = new File(DATA_LOCATION_NAME);
		DesktopContentService desktopContentService = new DesktopContentService(dataRootContent, CONTENT_LOCATION_NAME);
		defaultEngineFactory.setContentService(desktopContentService);
		
		
		File dataRootStorage = new File(DATA_LOCATION_NAME);
		DesktopStorageService desktopStorageService = new DesktopStorageService(dataRootStorage, STORAGE_LOCATION_NAME);
		defaultEngineFactory.setStorageService(desktopStorageService);
		
		
		DesktopRenderService desktopRenderService = new DesktopRenderService();
		defaultEngineFactory.setRenderService(desktopRenderService);
		
		IRenderAccess renderAccess = desktopRenderService.getRenderAccess();
		renderAccess.registerKeyedTextureManager(new GLKeyedTextureManager.GLTextureManagerFactory());
		renderAccess.registerTextureManager(new GLBitmapTextureManager.GLBitmapTextureManagerFactory());
		renderAccess.registerToolkit(new DesktopRenderToolkit.DesktopRenderToolkitFactory());
		renderAccess.registerDriver(new GLSpriteDriver.GLSpriteDriverFactory());
		renderAccess.registerDriver(new GLSpriteFontDriver.GLSpriteFontDriverFactory());
		renderAccess.registerDriver(new GLBitmapFontDriver.GLBitmapFontDriverFactory());
		renderAccess.registerDriver(new GLShapeDriver.GLShapeDriverFactory());
		renderAccess.registerDriver(new GLBitmapDriver.GLBitmapDriverFactory());
		
		
		DesktopInputService desktopInputService = new DesktopInputService();
		defaultEngineFactory.setInputService(desktopInputService);
		
		
		return defaultEngineFactory.build();
	}

	
	@Override
	protected ILogger createLogger(Injector injector, IEngine engine, MainLogger defaultLogger) throws EngineException {
		return defaultLogger;
	}

	
	@Override
	protected IGame createGame(Injector injector, IEngine engine, ILogger logger, GameFactory defaultGameFactory) throws EngineException {
		
		if(engine.getInputService() instanceof IDesktopInputService) {
			DesktopGameStateManager gamestateControl = new DesktopGameStateManager((IDesktopInputService)engine.getInputService());
			defaultGameFactory.setStateControl(gamestateControl);
		}
		else {
			logger.e(TAG, "Failed to inject DesktopGameStateManager: IEngine.getInputService() returned incompatible service to IDesktopInputService");
		}
		
		
		return defaultGameFactory.build();
	}

	
	@Override
	protected void doRun(IEngineContext context) throws EngineException {
		super.doRun(context);
		
		
		DesktopInitialGamestate initialState = new DesktopInitialGamestate(context);
		
		context.getGame().getStateControl().addState(TokelonStates.STATE_INITIAL, initialState);
		context.getGame().getStateControl().changeState(TokelonStates.STATE_INITIAL);
		
	}
	
	
}
