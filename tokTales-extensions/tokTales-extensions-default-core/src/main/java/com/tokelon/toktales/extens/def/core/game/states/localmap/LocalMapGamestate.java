package com.tokelon.toktales.extens.def.core.game.states.localmap;

import java.io.File;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;

import com.tokelon.toktales.core.config.IConfigManager;
import com.tokelon.toktales.core.config.IFileConfig;
import com.tokelon.toktales.core.config.IFileConfig.OnConfigChangeListener;
import com.tokelon.toktales.core.config.IMainConfig;
import com.tokelon.toktales.core.content.text.ITextureFont;
import com.tokelon.toktales.core.engine.TokTales;
import com.tokelon.toktales.core.engine.content.ContentException;
import com.tokelon.toktales.core.engine.inject.ForClass;
import com.tokelon.toktales.core.engine.storage.StorageException;
import com.tokelon.toktales.core.game.controller.IConsoleController;
import com.tokelon.toktales.core.game.controller.map.IMapController;
import com.tokelon.toktales.core.game.logic.ActionTakerImpl;
import com.tokelon.toktales.core.game.model.ICamera;
import com.tokelon.toktales.core.game.model.IConsole;
import com.tokelon.toktales.core.game.screen.IStateRender;
import com.tokelon.toktales.core.game.screen.order.IRenderCallback;
import com.tokelon.toktales.core.game.screen.order.IRenderOrder;
import com.tokelon.toktales.core.game.states.BaseGamescene;
import com.tokelon.toktales.core.game.states.BaseGamestate;
import com.tokelon.toktales.core.game.states.IControlHandler;
import com.tokelon.toktales.core.game.states.IControlScheme;
import com.tokelon.toktales.core.game.states.IGameScene;
import com.tokelon.toktales.core.game.states.IGameSceneControl.IGameSceneControlFactory;
import com.tokelon.toktales.core.game.states.IGameSceneControl.IModifiableGameSceneControl;
import com.tokelon.toktales.core.game.states.IGameStateInputHandler;
import com.tokelon.toktales.core.storage.IApplicationLocation;
import com.tokelon.toktales.core.storage.utils.LocationImpl;
import com.tokelon.toktales.core.util.FrameTool;
import com.tokelon.toktales.extens.def.core.game.controller.DefaultConsoleController;
import com.tokelon.toktales.extens.def.core.game.logic.IConsoleInterpreter;
import com.tokelon.toktales.extens.def.core.game.model.Console;
import com.tokelon.toktales.extens.def.core.game.screen.IConsoleOverlayRenderer;
import com.tokelon.toktales.extens.def.core.game.states.localmap.ILocalMapControlHandler.EmptyLocalMapControlHandler;
import com.tokelon.toktales.extens.def.core.game.states.localmap.ILocalMapControlHandler.ILocalMapControlHandlerFactory;
import com.tokelon.toktales.extens.def.core.game.states.localmap.ILocalMapInputHandler.ILocalMapInputHandlerFactory;
import com.tokelon.toktales.extens.def.core.game.states.localmap.ILocalMapStateRenderer.ILocalMapStateRendererFactory;

public class LocalMapGamestate extends BaseGamestate implements ILocalMapGamestate {

	public static final String TAG = "LocalMapGamestate";
	
	
	private static final double CALLBACK_POSITION_CONSOLE = 10d;
	
	private static final String CONSOLE_PROMPT = ">"; //"> ";

	public static final String CONTROLLER_CONSOLE_ID = "localmap_gamestate-controller_console";

	
	private final ActionTakerImpl actionTaker = new ActionTakerImpl();

	private final FrameTool frameTool = new FrameTool();
	private boolean fpsModeEnabled = false;

	private boolean logDrawTime = false;
	private boolean logRenderTime = false;

	
	private ILocalMapStateRenderer customRenderer;

	private ILocalMapControlHandler customControlHandler;
	
	private IConsoleController consoleController;

	
	private ConsoleRenderCallback consoleRenderCallback;
	
	private ITextureFont font;
	
	
	private final ILocalMapStateRendererFactory stateRendererFactory;
	private final ILocalMapInputHandlerFactory inputHandlerFactory;
	private final IControlScheme controlScheme;
	private final ILocalMapControlHandlerFactory controlHandlerFactory;
	
	@Inject
	public LocalMapGamestate(
			Provider<ILocalMapGamescene> localMapSceneProvider,
			IGameSceneControlFactory sceneControlFactory,
			ILocalMapStateRendererFactory stateRendererFactory,
			ILocalMapInputHandlerFactory inputHandlerFactory,
			@ForClass(LocalMapGamestate.class) IControlScheme controlScheme,
			ILocalMapControlHandlerFactory controlHandlerFactory
	) {
		super(localMapSceneProvider, createSceneControl(sceneControlFactory));
		
		this.stateRendererFactory = stateRendererFactory;
		this.inputHandlerFactory = inputHandlerFactory;
		this.controlScheme = controlScheme;
		this.controlHandlerFactory = controlHandlerFactory;
	}
	
	private static IModifiableGameSceneControl<ILocalMapGamescene> createSceneControl(IGameSceneControlFactory sceneControlFactory) {
		return sceneControlFactory.createModifiable();
	}

	
	@Override
	protected void initStateDependencies(
			IStateRender defaultRender,
			IGameStateInputHandler defaultInputHandler,
			IControlScheme defaultControlScheme,
			IControlHandler defaultControlHandler
	) {

		ILocalMapStateRenderer stateRenderer = stateRendererFactory.create(this);
		ILocalMapInputHandler stateInputHandler = inputHandlerFactory.create(this);
		ILocalMapControlHandler stateControlHandler = controlHandlerFactory.create(this);
		
		// Set the custom values
		this.customRenderer = stateRenderer;
		this.customControlHandler = stateControlHandler;
		
		super.initStateDependencies(stateRenderer, stateInputHandler, controlScheme, stateControlHandler);
	}
	
	
	
	protected void setStateRenderCustom(ILocalMapStateRenderer renderer) {
		this.customRenderer = renderer;
		
		setStateRender(renderer); // Important	
	}
	
	@Override
	public ILocalMapStateRenderer getStateRenderCustom() {
		return customRenderer;
	}
	
	
	protected void setStateControlHandlerCustom(ILocalMapControlHandler controlHandler) {
		this.customControlHandler = controlHandler;
		
		setStateControlHandler(controlHandler);
	}
	
	@Override
	public ILocalMapControlHandler getStateControlHandler() {
		return customControlHandler;
	}
	
	
	
	
	@Override
	public void onEngage() {
		super.onEngage();
		
		/* Uncomment if needed
		IConfigManager configManager = getGame().getConfigManager();
		IMainConfig mainConfig = (IMainConfig) configManager.getConfig(IConfigManager.MAIN_CONFIG);
		
		mainConfig.registerOnConfigChangeListener(new MainConfigChangeListener());
		*/
		
		
		font = loadFont();
		if(font == null) {
			TokTales.getLog().e(TAG, "Failed to load font");
		}
		
		
		IConsole console = new Console(new ConsoleInterpreter());
		console.setPrompt(CONSOLE_PROMPT);
		
		consoleController = new DefaultConsoleController(console);
		if(font != null) {
			consoleController.setFont(font);
		}
		
		
		consoleRenderCallback = new ConsoleRenderCallback(getStateRenderCustom(), consoleController, getRenderOrder());
	}
	
	
	@Override
	public IConsoleController getConsoleController() {
		return consoleController;
	}
	
	
	@Override
	protected Class<? extends IGameScene> getStateSceneType() {
		return ILocalMapGamescene.class;
	}
	
	@Override
	public IModifiableGameSceneControl<ILocalMapGamescene> getSceneControl() {
		return getSceneControlTyped(ILocalMapGamescene.class);
	}
	
	@Override
	public ILocalMapGamescene getActiveScene() {
		return getSceneControlTyped(ILocalMapGamescene.class).getActiveScene();
	}
	
	
	
	@Override
	public void render() {
		long renderStart = System.currentTimeMillis();

		
		IMapController mapController = getActiveScene().getMapController();
		if(mapController != null) {
			// TODO: Use rendering action scheduler ?
			mapController.getActionScheduler().requestActionOrError(actionTaker);
		}
		
		try {
			long drawStart = System.currentTimeMillis();

			// Actual rendering
			super.render();

			
			int drawDT = (int) (System.currentTimeMillis() - drawStart);
			if(logDrawTime) TokTales.getLog().d(TAG, "Draw Time MS = " + drawDT);
		}
		finally {
			if(mapController != null) {	// TODO: MapController should never be null
				mapController.getActionScheduler().finishAction(actionTaker);
			}
		}
		

		if(fpsModeEnabled) {
			int fps = frameTool.countFps();
			if(fps != -1) {
				TokTales.getLog().d(TAG, "FPS: " +fps);
			}
		}
		
		int renderDT = (int) (System.currentTimeMillis() - renderStart);
		if(logRenderTime) TokTales.getLog().d(TAG, "Render Time MS = " + renderDT);
	}
	
	
	
	
	private ITextureFont loadFont() {
		// TODO: Is this okay?? Should be a default font somewhere that is just used here
		
		boolean isAndroid = getEngine().getEnvironment().getPlatformName().equals("Android");
		String fontPath = isAndroid ? "assets/fonts" : "assets\\fonts";
		
		
		String fontFilename = "m5x7.ttf";
		IApplicationLocation fontLocation = new LocationImpl(fontPath);
		
		ITextureFont font = null;
		try {
			File fontFile = getEngine().getStorageService().getAppFileOnExternal(fontLocation, fontFilename);
			
			font = getEngine().getContentService().loadFontFromFile(fontFile);
		} catch (ContentException e) {
			TokTales.getLog().e(TAG, "Unable to load font: " + e.getMessage());
		} catch (StorageException e) {
			TokTales.getLog().e(TAG, "Unable to read font file: " + e.getMessage());
		}

		return font;
	}
	
	
	@Override
	protected String getTag() {
		return TAG + "_" + BASE_TAG;
	}

	
	private class MainConfigChangeListener implements OnConfigChangeListener {

		@Override
		public void onConfigChanged(IFileConfig config, String category, String key) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onConfigBatchChanged(IFileConfig config, List<String> entryCategory, List<String> entryKey) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	
	private class ConsoleRenderCallback implements IRenderCallback {
		
		private final IConsoleOverlayRenderer coRenderer;
		private final IConsoleController coController;
		
		public ConsoleRenderCallback(ILocalMapStateRenderer stateRenderer, IConsoleController consoleController, IRenderOrder renderOrder) {
			coRenderer = stateRenderer.getConsoleOverlayRenderer();
			coController = consoleController;
			
			renderOrder.getStackForLayer(IRenderOrder.LAYER_TOP).addCallbackAt(CALLBACK_POSITION_CONSOLE, this);
		}
		
		@Override
		public void renderCall(String layerName, double stackPosition) {
			coRenderer.drawConsoleOverlay(coController);
		}

		@Override
		public String getDescription() {
			return "renders the console overlay";
		}
	}


	
	private class ConsoleInterpreter implements IConsoleInterpreter {

		@Override
		public boolean interpret(IConsole console, String input) {
			
			IConsoleInterpreter sceneConsInter = getActiveScene().getSceneConsoleInterpreter();
			if(sceneConsInter != null && sceneConsInter.interpret(console, input)) {
				return true;
			}
			
			
			String response = "";
			
			if(input.contains("Hello")) {
				response = "Hello!";
			}
			else if(input.equals("td") || input.equals("debug toggle")) {
				//Game.getEventHub().getGlobalBus().send("debug.toggle");	// Event bus?
				
				getStateRenderCustom().setDebugRendering(!getStateRenderCustom().isDebugRenderingEnabled());
			}
			else if(input.startsWith("camera size")) {
				String[] inputWords = input.split(" ");
				if(inputWords.length >= 4) {
					try {
						int xval = Integer.parseInt(inputWords[2]);
						int yval = Integer.parseInt(inputWords[3]);
						
						getActiveScene().getCameraController().getCamera().setSize(xval, yval);
					}
					catch(NumberFormatException nfe) {
						response = "Bad x y values";
					}
				}
				else {
					response = "Bad syntax. Use: camera size x y";
				}
			}
			else if(input.startsWith("camera zoom")) {
				String[] inputWords = input.split(" ");
				if(inputWords.length >= 3) {
					try {
						float zoom = Float.parseFloat(inputWords[2]);
						
						getActiveScene().getCameraController().getCamera().setZoom(zoom, false);
					}
					catch(NumberFormatException nfe) {
						response = "Bad zoom value";
					}
				}
				else {
					response = "Bad syntax. Use: camera zoom z";
				}
			}
			else {
				response = "I did not understand that.";
				return false;
			}
			
			console.print(response);
			return true;
		}
	}

	
	public static class EmptyLocalMapGamescene extends BaseGamescene implements ILocalMapGamescene {
		private final EmptyLocalMapControlHandler emptyControlHandler = new EmptyLocalMapControlHandler();
		
		@Override
		public void onAssign() {
			super.onAssign();
			
			// This is old stuff, only used as fallback
			IConfigManager configManager = getGamestate().getGame().getConfigManager();
			IMainConfig mainConfig = (IMainConfig) configManager.getConfig(IConfigManager.MAIN_CONFIG);

			ICamera camera = getCameraController().getCamera();
			camera.setSize(mainConfig.getConfigCameraSizeUnitsX(), mainConfig.getConfigCameraSizeUnitsY());
			//getCamera().setSize(IWorld.GRID_TILE_SIZE * 8, IWorld.GRID_TILE_SIZE * 8);
			
			// TODO: Important - This should probably not be done here
			getCameraController().enableCameraFollow(getPlayerController().getPlayer());
		}
		
		@Override
		public ILocalMapControlHandler getSceneControlHandler() {
			return emptyControlHandler;
		}
		
		@Override
		public IConsoleInterpreter getSceneConsoleInterpreter() {
			return null;
		}
	}
	
}
