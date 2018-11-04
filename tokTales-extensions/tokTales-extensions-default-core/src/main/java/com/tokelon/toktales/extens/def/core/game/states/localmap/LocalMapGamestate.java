package com.tokelon.toktales.extens.def.core.game.states.localmap;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import com.tokelon.toktales.core.config.IFileConfig;
import com.tokelon.toktales.core.config.IFileConfig.OnConfigChangeListener;
import com.tokelon.toktales.core.content.text.ITextureFont;
import com.tokelon.toktales.core.engine.TokTales;
import com.tokelon.toktales.core.engine.content.ContentException;
import com.tokelon.toktales.core.engine.storage.StorageException;
import com.tokelon.toktales.core.game.controller.IConsoleController;
import com.tokelon.toktales.core.game.controller.map.IMapController;
import com.tokelon.toktales.core.game.logic.ActionTakerImpl;
import com.tokelon.toktales.core.game.model.IConsole;
import com.tokelon.toktales.core.game.screen.IStateRender;
import com.tokelon.toktales.core.game.states.BaseGamestate;
import com.tokelon.toktales.core.game.states.ExtendedGamescene;
import com.tokelon.toktales.core.game.states.IControlHandler;
import com.tokelon.toktales.core.game.states.IControlScheme;
import com.tokelon.toktales.core.game.states.IGameStateInputHandler;
import com.tokelon.toktales.core.storage.IApplicationLocation;
import com.tokelon.toktales.core.storage.utils.LocationImpl;
import com.tokelon.toktales.core.util.FrameTool;
import com.tokelon.toktales.extens.def.core.game.controller.DefaultConsoleController;
import com.tokelon.toktales.extens.def.core.game.logic.IConsoleInterpreter;
import com.tokelon.toktales.extens.def.core.game.model.Console;
import com.tokelon.toktales.extens.def.core.game.states.integration.ConsoleIntegration;
import com.tokelon.toktales.extens.def.core.game.states.integration.IConsoleIntegration;
import com.tokelon.toktales.extens.def.core.game.states.integration.IConsoleIntegrationControlHandler.IConsoleIntegrationControlHandlerFactory;
import com.tokelon.toktales.extens.def.core.game.states.localmap.ILocalMapControlHandler.EmptyLocalMapControlHandler;
import com.tokelon.toktales.extens.def.core.game.states.localmap.ILocalMapControlHandler.ILocalMapControlHandlerFactory;
import com.tokelon.toktales.extens.def.core.game.states.localmap.ILocalMapInputHandler.ILocalMapInputHandlerFactory;
import com.tokelon.toktales.extens.def.core.game.states.localmap.ILocalMapStateRenderer.ILocalMapStateRendererFactory;

public class LocalMapGamestate extends BaseGamestate<ILocalMapGamescene> implements ILocalMapGamestate {

	public static final String SUB_TAG = "LocalMapGamestate";
	
	
	private static final String CONSOLE_PROMPT = ">"; //"> ";

	public static final String CONTROLLER_CONSOLE_ID = "localmap_gamestate-controller_console";
	public static final String GAMESTATE_INTEGRATION_CONSOLE = "localmap_gamestate-integration_console";

	
	private final ActionTakerImpl actionTaker = new ActionTakerImpl();

	private final FrameTool frameTool = new FrameTool();
	private boolean fpsModeEnabled = false;

	private boolean logDrawTime = false;
	private boolean logRenderTime = false;

	
	
	private ITextureFont font;
	
	private IConsoleIntegration consoleIntegration;

	private ILocalMapStateRenderer customRenderer;
	private ILocalMapControlHandler customControlHandler;
	
	
	private IConsoleIntegrationControlHandlerFactory consoleOverlayControlHandlerFactory;
	
	private final ILocalMapStateRendererFactory stateRendererFactory;
	private final ILocalMapInputHandlerFactory inputHandlerFactory;
	private final IControlScheme controlScheme;
	private final ILocalMapControlHandlerFactory controlHandlerFactory;
	private final ILocalMapConsoleIntepreter consoleInterpreter;
	
	@Inject
	public LocalMapGamestate(
			ILocalMapStateRendererFactory stateRendererFactory,
			ILocalMapInputHandlerFactory inputHandlerFactory,
			ILocalMapControlScheme controlScheme,
			ILocalMapControlHandlerFactory controlHandlerFactory,
			ILocalMapConsoleIntepreter consoleInterpreter
	) {
		this.stateRendererFactory = stateRendererFactory;
		this.inputHandlerFactory = inputHandlerFactory;
		this.controlScheme = controlScheme;
		this.controlHandlerFactory = controlHandlerFactory;
		this.consoleInterpreter = consoleInterpreter;
	}

	
	@Inject
	protected void injectLocalMapStateDependencies(
			IConsoleIntegrationControlHandlerFactory consoleOverlayControlHandlerFactory
	) {
		this.consoleOverlayControlHandlerFactory = consoleOverlayControlHandlerFactory;
	}
	
	
	@Override
	protected void initStateDependencies(
			IStateRender defaultRender,
			IGameStateInputHandler defaultInputHandler,
			IControlScheme defaultControlScheme,
			IControlHandler defaultControlHandler
	) {
		// TODO: Enable using the given params? If they are compatible?

		ILocalMapStateRenderer stateRenderer = stateRendererFactory.create(this);
		ILocalMapInputHandler stateInputHandler = inputHandlerFactory.create(this);
		ILocalMapControlHandler stateControlHandler = controlHandlerFactory.create(this);
		
		// Set the custom values
		this.customRenderer = stateRenderer;
		this.customControlHandler = stateControlHandler;
		
		super.initStateDependencies(stateRenderer, stateInputHandler, controlScheme, stateControlHandler);
	}
	
	
	@Override
	protected void afterInjectDependencies() {
		super.afterInjectDependencies();
		
		getGamestateInjector().injectInto(consoleInterpreter);
	}
	
	
	/** Use instead of {@link #setStateRender(IStateRender)}.
	 * 
	 * @param renderer
	 */
	protected void setStateRenderCustom(ILocalMapStateRenderer renderer) {
		this.customRenderer = renderer;
		
		setStateRender(renderer); // Important	
	}
	
	@Override
	public ILocalMapStateRenderer getStateRenderCustom() {
		return customRenderer;
	}
	
	
	/** Use instead of {@link #setStateControlHandler(IControlHandler)}.
	 * 
	 * @param controlHandler
	 */
	protected void setStateControlHandlerCustom(ILocalMapControlHandler controlHandler) {
		this.customControlHandler = controlHandler;
		
		setStateControlHandler(controlHandler);
	}
	
	@Override
	public ILocalMapControlHandler getStateControlHandler() {
		return customControlHandler;
	}
	
	
	@Override
	public IConsoleIntegration getIntegrationConsole() {
		return consoleIntegration;
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
			TokTales.getLog().e(getTag(), "Failed to load font");
		}
		
		
		IConsole console = new Console(consoleInterpreter);
		console.setPrompt(CONSOLE_PROMPT);
		
		IConsoleController consoleController = new DefaultConsoleController(console);
		if(font != null) {
			consoleController.setFont(font);
		}
		

		consoleIntegration = new ConsoleIntegration(getGame().getContentManager().getCodepointManager(), this, consoleController, consoleOverlayControlHandlerFactory);
		getIntegrator().addIntegration(GAMESTATE_INTEGRATION_CONSOLE, consoleIntegration);
		consoleIntegration.onStateEngage(this); // Any way to create it earlier and avoid this?
	}
	
	
	
	@Override
	public void onRender() {
		long renderStart = System.currentTimeMillis();

		
		IMapController mapController = getActiveScene().getMapController();
		
		// TODO: Use rendering action scheduler ?
		mapController.getActionScheduler().requestActionOrError(actionTaker);
		try {
			long drawStart = System.currentTimeMillis();

			// Actual rendering
			super.onRender();

			
			int drawDT = (int) (System.currentTimeMillis() - drawStart);
			if(logDrawTime) TokTales.getLog().d(getTag(), "Draw Time MS = " + drawDT);
		}
		finally {
			mapController.getActionScheduler().finishAction(actionTaker);
		}
		

		if(fpsModeEnabled) {
			int fps = frameTool.countFps();
			if(fps != -1) {
				TokTales.getLog().d(getTag(), "FPS: " +fps);
			}
		}
		
		int renderDT = (int) (System.currentTimeMillis() - renderStart);
		if(logRenderTime) TokTales.getLog().d(getTag(), "Render Time MS = " + renderDT);
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
			TokTales.getLog().e(getTag(), "Unable to load font: " + e.getMessage());
		} catch (StorageException e) {
			TokTales.getLog().e(getTag(), "Unable to read font file: " + e.getMessage());
		}
		
		return font;
	}
	
	
	@Override
	protected String getTag() {
		return SUB_TAG + "_" + BASE_TAG;
	}

	
	// TODO: Implement or remove
	@SuppressWarnings("unused")
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
	

	public static class EmptyLocalMapGamescene extends ExtendedGamescene implements ILocalMapGamescene {
		private final EmptyLocalMapControlHandler emptyControlHandler = new EmptyLocalMapControlHandler();
		
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
