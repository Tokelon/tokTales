package com.tokelon.toktales.extensions.core.game.state.console;

import javax.inject.Inject;

import com.tokelon.toktales.core.content.graphics.RGBAColorImpl;
import com.tokelon.toktales.core.content.manage.font.IFontAsset;
import com.tokelon.toktales.core.content.manage.font.IFontAssetKey;
import com.tokelon.toktales.core.content.text.IFont;
import com.tokelon.toktales.core.game.controller.IConsoleController;
import com.tokelon.toktales.core.game.model.ICamera;
import com.tokelon.toktales.core.game.model.entity.IGameEntity;
import com.tokelon.toktales.core.game.state.BaseGamestate;
import com.tokelon.toktales.core.game.state.IGameScene;
import com.tokelon.toktales.core.game.state.IGameStateInputHandler;
import com.tokelon.toktales.core.game.state.render.DefaultModularStateRender;
import com.tokelon.toktales.core.game.state.render.IRenderingStrategy;
import com.tokelon.toktales.extensions.core.game.controller.DefaultConsoleController;
import com.tokelon.toktales.extensions.core.game.controller.DefaultDialogController;
import com.tokelon.toktales.extensions.core.game.controller.DefaultTextBoxController;
import com.tokelon.toktales.extensions.core.game.logic.IConsoleInterpreter;
import com.tokelon.toktales.extensions.core.game.logic.IConsoleInterpreterManager;
import com.tokelon.toktales.extensions.core.game.model.Console;
import com.tokelon.toktales.extensions.core.game.model.ScreenDialog;
import com.tokelon.toktales.extensions.core.game.model.TextBox;
import com.tokelon.toktales.extensions.core.game.renderer.ConsoleRenderer;
import com.tokelon.toktales.extensions.core.game.renderer.IDialogRenderer;
import com.tokelon.toktales.extensions.core.game.renderer.TextBoxRenderer;
import com.tokelon.toktales.extensions.core.game.renderer.ConsoleRenderer.ConsoleRendererFactory;
import com.tokelon.toktales.extensions.core.game.renderer.DialogRenderer.DialogRendererFactory;
import com.tokelon.toktales.extensions.core.game.renderer.TextBoxRenderer.TextBoxRendererFactory;
import com.tokelon.toktales.extensions.core.values.ControllerExtensionsValues;
import com.tokelon.toktales.tools.core.sub.inject.scope.ForClass;

public class ConsoleGamestate extends BaseGamestate<IGameScene> implements IConsoleGamestate {


	public static final String ASSET_KEY_ID_FONT_MAIN = "CONSOLE_GAMESTATE-ASSET_KEY_ID_FONT_MAIN";

	
	public static final String RENDERER_CONSOLE_NAME = "renderer_console";
	public static final String RENDERER_TEXT_NAME = "renderer_text_name";
	public static final String RENDERER_DIALOG_NAME = "renderer_dialog_name";
	

	private static final String CONSOLE_PROMPT = ">"; //"> ";
	
	
	private DefaultConsoleController consoleController;
	private DefaultDialogController dialogController;
	private DefaultTextBoxController textBoxController;
	
	private final IRenderingStrategy renderingStrategy;
	private final IConsoleInterpreterManager consoleInterpreterManager;
	
	@Inject
	public ConsoleGamestate(
			@ForClass(ConsoleGamestate.class) IGameStateInputHandler inputHandler,
			@ForClass(ConsoleGamestate.class) IRenderingStrategy renderingStrategy,
			@ForClass(ConsoleGamestate.class) IConsoleInterpreterManager consoleInterpreterManager
	) {
		super(null, inputHandler, null, null);
		
		this.renderingStrategy = renderingStrategy;
		this.consoleInterpreterManager = consoleInterpreterManager;
	}
	
	
	@Override
	protected void afterInjectDependencies() {
		super.afterInjectDependencies();
		
		getGamestateInjector().injectInto(renderingStrategy);

		getGamestateInjector().injectInto(consoleInterpreterManager);
		for(IConsoleInterpreter interpreter: consoleInterpreterManager.getInterpreterList()) {
			getGamestateInjector().injectInto(interpreter);
		}
	}

	
	@Override
	public void onEngage() {
		super.onEngage();

		ICamera camera = getActiveScene().getSceneCamera();
		//getCamera().setSize(720, 1280);
		camera.setSize(1280, 720);
		//getCamera().setSize(1860, 1080);
		camera.setWorldCoordinates(camera.getOriginX(), camera.getOriginY());
		
		
		// Console
		Console console = new Console(consoleInterpreterManager);
		console.setPrompt(CONSOLE_PROMPT);
		
		consoleController = new DefaultConsoleController(console);
		getActiveScene().getControllerManager().setController(ControllerExtensionsValues.CONTROLLER_CONSOLE, consoleController);
		
		ConsoleRenderer consoleSegmentRenderer = new ConsoleRendererFactory().createForGamestate(this);
		
		
		// Dialog
		ScreenDialog dialog = new ScreenDialog(camera.getWidth() * 0.80f, camera.getHeight() * 0.20f, 48f);
		dialog.setText("Hello World!");
		dialogController = new DefaultDialogController(dialog);
		
		IDialogRenderer dialogSegmentRenderer = new DialogRendererFactory().createForGamestate(this);
		getActiveScene().getControllerManager().setController(ControllerExtensionsValues.CONTROLLER_DIALOG, dialogController);
		
		
		// Text renderer
		TextBox textBox = new TextBox(1280, 200, 64);
		IGameEntity textBoxEntity = textBox.getEntity();
		textBoxEntity.setWorldCoordinates(0, 720 - textBoxEntity.getHeight());
		
		//textBox.setText("Hello, I am a Text Box. This is my only hope. I am consumed by the nightsky.");
		textBox.setText("This was a triumph. \\\\ I'm making a note here: \\\\ HUGE SUCCESS. \\\\ It's hard to overstate my satisfaction.");
		//textBox.setText("I'm not even angry. \\\\ I'm being so sincere right now. \\\\ Even though you broke my heart and killed me. \\\\ And tore me to pieces. \\\\ And threw every piece into a fire. \\\\ As they burned it hurt because \\\\ I was so happy for you!");

		
		TextBoxRenderer textSegmentRenderer = new TextBoxRendererFactory().createForGamestate(this);
		textSegmentRenderer.setColor(RGBAColorImpl.createFromCode("#FFE688"));
		textBoxController = new DefaultTextBoxController(textBox);
		getActiveScene().getControllerManager().setController(ControllerExtensionsValues.CONTROLLER_TEXTBOX, textBoxController);
		

		// State renderer
		DefaultModularStateRender baseStateRenderer = new DefaultModularStateRender(renderingStrategy, this);
		
		baseStateRenderer.addSegmentRenderer(RENDERER_CONSOLE_NAME, consoleSegmentRenderer);
		baseStateRenderer.addSegmentRenderer(RENDERER_DIALOG_NAME, dialogSegmentRenderer);
		
		baseStateRenderer.addSegmentRenderer(RENDERER_TEXT_NAME, textSegmentRenderer);
		
		
		baseStateRenderer.updateCamera(getActiveScene().getSceneCamera());
		setStateRender(baseStateRenderer);
	}
	
	
	@Override
	public void onUpdate(long timeMillis) {
		super.onUpdate(timeMillis);

		updateFont();
	}
	

	private void updateFont() {
		IFontAssetKey fontAssetKey = getGame().getRegistryManager().getAssetKeyRegistry().resolveAs(ASSET_KEY_ID_FONT_MAIN, IFontAssetKey.class);
		IFontAsset asset = getGame().getContentManager().getFontAssetManager().getAssetIfKeyValid(fontAssetKey, ASSET_KEY_ID_FONT_MAIN);
		IFont font = getGame().getContentManager().getFontAssetManager().isAssetValid(asset) ? asset.getFont() : null;

		consoleController.setFont(font);
		textBoxController.getModel().setFont(font);
		dialogController.getDialog().setFont(font);
	}
	

	@Override
	public IConsoleInterpreterManager getInterpreterManager() {
		return consoleInterpreterManager;
	}

	@Override
	public void addInterpreterAndInjectState(IConsoleInterpreter interpreter) {
		consoleInterpreterManager.add(interpreter);
		getGamestateInjector().injectInto(interpreter);
	}

	@Override
	public void addInterpreterAndInjectState(IConsoleInterpreter interpreter, int index) {
		consoleInterpreterManager.addAtIndex(interpreter, index);
		getGamestateInjector().injectInto(interpreter);
	}
	
	
	/** The console controller will be available after {@link #onEngage()}.
	 * 
	 * @return The console controller.
	 */
	@Override
	public IConsoleController getConsoleController() {
		return consoleController;
	}
	
}
