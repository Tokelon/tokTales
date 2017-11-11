package com.tokelon.toktales.extens.def.core.game.states;

import java.io.File;

import com.tokelon.toktales.core.content.RGBAColorImpl;
import com.tokelon.toktales.core.content.text.ITextureFont;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.engine.TokTales;
import com.tokelon.toktales.core.engine.content.ContentException;
import com.tokelon.toktales.core.engine.storage.StorageException;
import com.tokelon.toktales.core.game.controller.IConsoleController;
import com.tokelon.toktales.core.game.model.ICamera;
import com.tokelon.toktales.core.game.model.IConsole;
import com.tokelon.toktales.core.game.model.entity.IGameEntity;
import com.tokelon.toktales.core.game.screen.DefaultModularStateRender;
import com.tokelon.toktales.core.game.screen.IRenderingStrategy;
import com.tokelon.toktales.core.game.states.BaseGamestate;
import com.tokelon.toktales.core.storage.IApplicationLocation;
import com.tokelon.toktales.core.storage.utils.LocationImpl;
import com.tokelon.toktales.extens.def.core.game.controller.DefaultConsoleController;
import com.tokelon.toktales.extens.def.core.game.controller.DefaultDialogController;
import com.tokelon.toktales.extens.def.core.game.controller.DefaultTextBoxController;
import com.tokelon.toktales.extens.def.core.game.logic.IConsoleInterpreter;
import com.tokelon.toktales.extens.def.core.game.model.Console;
import com.tokelon.toktales.extens.def.core.game.model.ScreenDialog;
import com.tokelon.toktales.extens.def.core.game.model.TextBox;
import com.tokelon.toktales.extens.def.core.game.screen.ConsoleRenderer;
import com.tokelon.toktales.extens.def.core.game.screen.DialogRenderer;
import com.tokelon.toktales.extens.def.core.game.screen.TextBoxRenderer;

public class ConsoleGamestate extends BaseGamestate {
	
	//TODO: Rename "predef" package to "builtin" or "buildin" or "buildn"
	
	public static final String TAG = "ConsoleGamestate";
	
	
	public static final String RENDERER_CONSOLE_NAME = "renderer_console";
	public static final String RENDERER_TEXT_NAME = "renderer_text_name";
	public static final String RENDERER_DIALOG_NAME = "renderer_dialog_name";
	
	public static final String CONTROLLER_CONSOLE_ID = "console_gamestate-controller_console";

	private static final String CONSOLE_PROMPT = ">"; //"> ";
	
	
	private DefaultConsoleController mConsoleController;
	
	private IRenderingStrategy mRenderingStrategy;
	
	
	private Console console;
	private ConsoleInterpreter interpreter;

	private IConsoleInterpreter customConsoleInterpreter;
	
	// TODO: Get the strategy from a protected method or like this through the constructor?
	public ConsoleGamestate(IEngineContext context, IRenderingStrategy renderingStrategy) {
		super(context);
		
		this.mRenderingStrategy = renderingStrategy;
	}

	
	@Override
	public void onEngage() {
		super.onEngage();

		ICamera camera = getActiveScene().getCameraController().getCamera();
		//getCamera().setSize(720, 1280);
		camera.setSize(1280, 720);
		//getCamera().setSize(1860, 1080);
		camera.setWorldCoordinates(camera.getOriginX(), camera.getOriginY());
		
		
		// Console
		interpreter = new ConsoleInterpreter();
		console = new Console(interpreter);
		console.setPrompt(CONSOLE_PROMPT);
		
		mConsoleController = new DefaultConsoleController(console);
		getActiveScene().getControllerManager().setController(CONTROLLER_CONSOLE_ID, mConsoleController);
		
		ConsoleRenderer consoleSegmentRenderer = new ConsoleRenderer(this, mConsoleController);
		
		
		// Dialog
		ScreenDialog dialog = new ScreenDialog(camera.getWidth() * 0.80f, camera.getHeight() * 0.20f, 48f);
		dialog.setText("Hello World!");
		DefaultDialogController dialogController = new DefaultDialogController(dialog);
		
		DialogRenderer dialogSegmentRenderer = new DialogRenderer(this);
		getActiveScene().getControllerManager().setController(dialogSegmentRenderer.getDialogControllerName(), dialogController);
		
		
		// Text renderer
		TextBox textBox = new TextBox(1280, 200, 64);
		IGameEntity textBoxEntity = textBox.getEntity();
		textBoxEntity.setWorldCoordinates(0, 720 - textBoxEntity.getHeight());
		
		//textBox.setText("Hello, I am a Text Box. This is my only hope. I am consumed by the nightsky.");
		textBox.setText("This was a triumph. \\\\ I'm making a note here: \\\\ HUGE SUCCESS. \\\\ It's hard to overstate my satisfaction.");
		//textBox.setText("I'm not even angry. \\\\ I'm being so sincere right now. \\\\ Even though you broke my heart and killed me. \\\\ And tore me to pieces. \\\\ And threw every piece into a fire. \\\\ As they burned it hurt because \\\\ I was so happy for you!");

		
		TextBoxRenderer textSegmentRenderer = new TextBoxRenderer(this);
		textSegmentRenderer.setColor(RGBAColorImpl.createFromCode("#FFE688"));
		getActiveScene().getControllerManager().setController(textSegmentRenderer.getTextBoxControllerName(), new DefaultTextBoxController(textBox));
		

		// State renderer
		DefaultModularStateRender baseStateRenderer = new DefaultModularStateRender(
				mRenderingStrategy, getEngine().getRenderService().getSurfaceHandler(), this);
		
		baseStateRenderer.addSegmentRenderer(RENDERER_CONSOLE_NAME, consoleSegmentRenderer);
		baseStateRenderer.addSegmentRenderer(RENDERER_DIALOG_NAME, dialogSegmentRenderer);
		
		baseStateRenderer.addSegmentRenderer(RENDERER_TEXT_NAME, textSegmentRenderer);
		
		
		setStateRender(baseStateRenderer);
		

		/*
		// TODO: Fix the path logic
		boolean isAndroid = getEngine().getEnvironment().getPlatformName().equals("Android");
		String tilesetLocation = isAndroid ? "assets/tilesets" : "assets\\tilesets";
		String spritesetsLocation = isAndroid ? "/assets/graphics/spritesets" : "\\assets\\graphics\\spritesets";
		
		TiledMapTilesetReader tilesetReader = new TiledMapTilesetReader();
		try {
			tilesetReader.setup();
			
			String spritesetFilename = "m5x7mod.png";
			String tilesetFilename = "m5x7mod.tsx";
			LocationImpl location = new LocationImpl(tilesetLocation);
			
			InputStream inputstream = getEngine().getStorageFramework().tryReadAppFileOnExternal(location, tilesetFilename);
			if(inputstream == null) {
				TokTales.getLog().e(TAG, String.format("Unable to open stream to file %s at %s", tilesetFilename, location));
			}
			else {
				
				TiledMapTilesetImpl tileset = tilesetReader.readTileset(inputstream);
				TiledSpriteset spriteset = new TiledSpriteset(spritesetFilename, tileset);
				
				
				SpriteFontImpl font = new SpriteFontImpl(spriteset);
				mConsoleController.setFont(font);
				
				textBox.setFont(font);
				
				
				StructuredLocation spritesetLocation = new StructuredLocation(LocationPrefix.EXTERNAL, spritesetsLocation);
				Resource spritesetsResource = new Resource(IResourceType.Type.SPRITE_SET, "-spritesets", spritesetLocation);
				
				try {
					getGame().getContentManager().getResourceManager().preloadResource(spritesetsResource, getGame().getContentManager().getResourceManager().getLooseResources());
				} catch (ContentException e1) {
					e1.printStackTrace();
				} catch (StorageException e1) {
					e1.printStackTrace();
				}
			}
			
		} catch (TiledMapFormatException e) {
			TokTales.getLog().e(TAG, "Unable to load tileset: " + e.getMessage());
		}
		*/
		
		boolean isAndroid = getEngine().getEnvironment().getPlatformName().equals("Android");
		String fontPath = isAndroid ? "assets/fonts" : "assets\\fonts";
		
		
		String fontFilename = "m5x7.ttf";
		IApplicationLocation fontLocation = new LocationImpl(fontPath);
		
		ITextureFont font;
		try {
			File fontFile = getEngine().getStorageService().getAppFileOnExternal(fontLocation, fontFilename);
			
			font = getEngine().getContentService().loadFontFromFile(fontFile);
			
			mConsoleController.setFont(font);
			textBox.setFont(font);
			dialog.setFont(font);
		} catch (ContentException e) {
			TokTales.getLog().e(TAG, "Unable to load font: " + e.getMessage());
		} catch (StorageException e) {
			TokTales.getLog().e(TAG, "Unable to read font file: " + e.getMessage());
		}

		
	}
	
	@Override
	public void onEnter() {
		super.onEnter();
		
		//interpreter.interpret(console, "load chunk_test");
	}
	
	

	/** Sets a custom console interpreter that will be used in addition.
	 * 
	 * @param interpreter
	 */
	public void setCustomConsoleInterpreter(IConsoleInterpreter interpreter) {
		this.customConsoleInterpreter = interpreter;
	}
	
	/**
	 * 
	 * @return The custom console interpreter, or null if none has been set.
	 */
	public IConsoleInterpreter getCustomConsoleInterpreter() {
		return customConsoleInterpreter;
	}
	
	
	/** The console controller will be available after {@link #onEngage()}.
	 * 
	 * @return The console controller.
	 */
	public IConsoleController getConsoleController() {
		return mConsoleController;
	}
	
	
	
	
	private class ConsoleInterpreter implements IConsoleInterpreter {

		@Override
		public boolean interpret(IConsole console, String input) {
			String response;
			
			if(input.contains("Hello")) {
				response = "Hello!";
			}
			else if(input.toLowerCase().contains("load module map")) {
				response = "Not found";
				//response = "Loading...";
				
				//getGame().getStateControl().changeStateTo("main_menu_state");
			}
			else if(input.toLowerCase().contains("load chunk_test")) {
				response = "Loading...";
				
				getGame().getStateControl().changeState("chunk_test_state");
			}
			else {
				if(getCustomConsoleInterpreter() == null) {
					response = "I did not understand that.";
				}
				else {
					boolean success = customConsoleInterpreter.interpret(console, input);
					response = success ? "" : "I did not understand that.";
				}
			}
			
			console.print(response);
			return true;
		}
	}
	
	
}
