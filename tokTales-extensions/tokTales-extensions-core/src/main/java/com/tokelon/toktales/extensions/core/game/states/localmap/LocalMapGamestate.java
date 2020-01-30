package com.tokelon.toktales.extensions.core.game.states.localmap;

import java.util.List;

import javax.inject.Inject;

import com.tokelon.toktales.core.config.IFileConfig;
import com.tokelon.toktales.core.config.IFileConfig.OnConfigChangeListener;
import com.tokelon.toktales.core.content.manage.font.IFontAsset;
import com.tokelon.toktales.core.content.manage.font.IFontAssetKey;
import com.tokelon.toktales.core.content.text.IFont;
import com.tokelon.toktales.core.game.controller.IConsoleController;
import com.tokelon.toktales.core.game.controller.map.IMapController;
import com.tokelon.toktales.core.game.logic.ActionTakerImpl;
import com.tokelon.toktales.core.game.model.IConsole;
import com.tokelon.toktales.core.game.states.BaseGamestate;
import com.tokelon.toktales.core.game.states.ExtendedGamescene;
import com.tokelon.toktales.core.game.states.IControlHandler;
import com.tokelon.toktales.core.game.states.IControlScheme;
import com.tokelon.toktales.core.game.states.IGameStateInputHandler;
import com.tokelon.toktales.core.game.states.render.IStateRender;
import com.tokelon.toktales.core.util.FrameTool;
import com.tokelon.toktales.extensions.core.game.controller.DefaultConsoleController;
import com.tokelon.toktales.extensions.core.game.logic.IConsoleInterpreter;
import com.tokelon.toktales.extensions.core.game.model.Console;
import com.tokelon.toktales.extensions.core.game.states.integration.ConsoleIntegration;
import com.tokelon.toktales.extensions.core.game.states.integration.IConsoleIntegration;
import com.tokelon.toktales.extensions.core.game.states.integration.IConsoleIntegrationControlHandler.IConsoleIntegrationControlHandlerFactory;
import com.tokelon.toktales.extensions.core.game.states.localmap.ILocalMapControlHandler.EmptyLocalMapControlHandler;
import com.tokelon.toktales.extensions.core.game.states.localmap.ILocalMapControlHandler.ILocalMapControlHandlerFactory;
import com.tokelon.toktales.extensions.core.game.states.localmap.ILocalMapInputHandler.ILocalMapInputHandlerFactory;
import com.tokelon.toktales.extensions.core.game.states.localmap.ILocalMapStateRenderer.ILocalMapStateRendererFactory;

public class LocalMapGamestate extends BaseGamestate<ILocalMapGamescene> implements ILocalMapGamestate {


	public static final String ASSET_KEY_ID_FONT_MAIN = "LOCAL_MAP_GAMESTATE-ASSET_KEY_ID_FONT_MAIN";

	
	private static final String CONSOLE_PROMPT = ">"; //"> ";

	public static final String CONTROLLER_CONSOLE_ID = "localmap_gamestate-controller_console";
	public static final String GAMESTATE_INTEGRATION_CONSOLE = "localmap_gamestate-integration_console";

	
	private final ActionTakerImpl actionTaker = new ActionTakerImpl();

	private final FrameTool frameTool = new FrameTool();
	private boolean fpsModeEnabled = false;

	private boolean logDrawTime = false;
	private boolean logRenderTime = false;

	
	
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
		
		
		IConsole console = new Console(consoleInterpreter);
		console.setPrompt(CONSOLE_PROMPT);
		
		IConsoleController consoleController = new DefaultConsoleController(console);
		consoleIntegration = new ConsoleIntegration(getGame().getContentManager().getCodepointAssetManager(), this, consoleController, consoleOverlayControlHandlerFactory);
		getIntegrator().addIntegration(GAMESTATE_INTEGRATION_CONSOLE, consoleIntegration);
		consoleIntegration.onStateEngage(this); // Any way to create it earlier and avoid this?
	}
	
	@Override
	public void onUpdate(long timeMillis) {
		super.onUpdate(timeMillis);

		consoleIntegration.getConsoleController().setFont(getFont());
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
			if(logDrawTime) getLogger().debug("Draw Time MS = {}", drawDT);
		}
		finally {
			mapController.getActionScheduler().finishAction(actionTaker);
		}
		

		if(fpsModeEnabled) {
			int fps = frameTool.countFps();
			if(fps != -1) {
				getLogger().debug("FPS: {}", fps);
			}
		}
		
		int renderDT = (int) (System.currentTimeMillis() - renderStart);
		if(logRenderTime) getLogger().debug("Render Time MS = {}", renderDT);
	}


	private IFont getFont() {
		// TODO: Enable injecting your own registry?
		IFontAssetKey fontAssetKey = getGame().getRegistryManager().getAssetKeyRegistry().resolveAs(ASSET_KEY_ID_FONT_MAIN, IFontAssetKey.class);
		IFontAsset asset = getGame().getContentManager().getFontAssetManager().getAssetIfKeyValid(fontAssetKey, ASSET_KEY_ID_FONT_MAIN);
		return getGame().getContentManager().getFontAssetManager().isAssetValid(asset) ? asset.getFont() : null;
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
