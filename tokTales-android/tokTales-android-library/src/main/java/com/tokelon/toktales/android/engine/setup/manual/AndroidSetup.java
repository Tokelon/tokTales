package com.tokelon.toktales.android.engine.setup.manual;

import com.google.inject.Injector;
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
import com.tokelon.toktales.android.states.AndroidInitialGamestate;
import com.tokelon.toktales.android.storage.AndroidStorageService;
import com.tokelon.toktales.android.ui.AndroidUIService;
import com.tokelon.toktales.android.ui.UserInterface;
import com.tokelon.toktales.core.engine.Engine;
import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.engine.IEngine;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.engine.log.MainLogger;
import com.tokelon.toktales.core.engine.render.IRenderAccess;
import com.tokelon.toktales.core.engine.setup.manual.BaseSetup;
import com.tokelon.toktales.core.engine.setup.manual.EngineFactory;
import com.tokelon.toktales.core.engine.setup.manual.GameFactory;
import com.tokelon.toktales.core.game.IGame;
import com.tokelon.toktales.core.game.IGameAdapter;
import com.tokelon.toktales.core.game.states.TokelonStates;

import android.content.Context;
import android.os.Environment;

@Deprecated
public class AndroidSetup extends BaseSetup {

	public static final String TAG = "AndroidSetup";

	
	
	protected final Context mApplicationContext;
	
	public AndroidSetup(IGameAdapter gameAdapter, Context applicationContext) {
		super(gameAdapter);
		mApplicationContext = applicationContext;
	}

	
	@Override
	protected Engine createEngine(Injector injector, EngineFactory defaultEngineFactory) throws EngineException {
		
		AndroidEnvironment androidEnv = new AndroidEnvironment();
		defaultEngineFactory.setEnvironment(androidEnv);

		
		AndroidLogService androidLogService = new AndroidLogService();
		defaultEngineFactory.setLogService(androidLogService);
		ILogger mainLogger = new MainLogger(androidLogService); // TODO: Should be the created logger and not the default
		
		AndroidUIService androidUIService = new AndroidUIService(mainLogger, new UserInterface());
		defaultEngineFactory.setUIService(androidUIService);
		
		androidUIService.addExtension("console", new AndroidUIConsoleExtension());
		
		
		AndroidContentService androidContentService = new AndroidContentService(mainLogger, mApplicationContext);
		defaultEngineFactory.setContentService(androidContentService);
		
		
		AndroidStorageService androidStorageService = new AndroidStorageService(mainLogger, Environment.getExternalStorageDirectory());
		defaultEngineFactory.setStorageService(androidStorageService);
		
		
		AndroidRenderService androidRenderService = new AndroidRenderService();
		defaultEngineFactory.setRenderService(androidRenderService);
		
		IRenderAccess renderAccess = androidRenderService.getRenderAccess();
		renderAccess.registerKeyedTextureManager(new GLKeyedTextureManager.GLTextureManagerFactory());
		renderAccess.registerTextureManager(new GLBitmapTextureManager.GLBitmapTextureManagerFactory());
		renderAccess.registerToolkit(new AndroidRenderToolkit.AndroidRenderToolkitFactory());
		renderAccess.registerDriver(new GLSpriteDriver.GLSpriteDriverFactory());
		renderAccess.registerDriver(new GLSpriteFontDriver.GLSpriteFontDriverFactory());
		renderAccess.registerDriver(new GLBitmapFontDriver.GLBitmapFontDriverFactory());
		renderAccess.registerDriver(new GLShapeDriver.GLShapeDriverFactory());
		renderAccess.registerDriver(new GLBitmapDriver.GLBitmapDriverFactory());
		
		
		AndroidInputService androidInputService = new AndroidInputService();
		defaultEngineFactory.setInputService(androidInputService);
		
		return defaultEngineFactory.build();
	}
	
	
	
	@Override
	protected ILogger createLogger(Injector injector, IEngine engine, MainLogger defaultLogger) throws EngineException {
		/*
		ILogFramework logFramework = (ILogFramework) mInterfaceProvider.getProgramInterface(IPIP.IID_FRAMEWORK_LOG);
		MainLogger mainLogger = new MainLogger(logFramework);
		mInterfaceProvider.loadProgramInterface(IPIP.IID_LOGGER, mainLogger);
		*/
		
		return defaultLogger;
	}
	
	
	
	@Override
	protected IGame createGame(Injector injector, IEngine engine, ILogger logger, GameFactory defaultGameFactory) throws EngineException {
		
		if(engine.getInputService() instanceof IAndroidInputService) {
			AndroidGameStateManager gamestateControl = new AndroidGameStateManager(logger, (IAndroidInputService) engine.getInputService());
			//gamestateControl.setInputMaster(inputMaster);	// Maybe this should be done after the game has been created
			defaultGameFactory.setStateControl(gamestateControl);
		}
		else {
			logger.e(TAG, "Failed to inject AndroidGameStateManager: IEngine.getInputService() returned incompatible service to IAndroidInputService");
		}
		
		
		return defaultGameFactory.build();
	}
	
	
	@Override
	protected void doRun(IEngineContext context) throws EngineException {
		super.doRun(context);
		
		AndroidInitialGamestate initialState = new AndroidInitialGamestate(context);
		//InitialGamestate initialState = BaseGamestate.createGamestate(InitialGamestate.class, getEngine(), getLogger(), getGame(), new ParamsImpl());
		
		context.getGame().getStateControl().addState(TokelonStates.STATE_INITIAL, initialState);
		context.getGame().getStateControl().changeState(TokelonStates.STATE_INITIAL);
		
	}
	
	
}
