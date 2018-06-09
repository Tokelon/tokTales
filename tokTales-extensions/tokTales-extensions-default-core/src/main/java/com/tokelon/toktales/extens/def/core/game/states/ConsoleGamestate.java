package com.tokelon.toktales.extens.def.core.game.states;

import java.io.File;

import javax.inject.Inject;

import com.tokelon.toktales.core.content.RGBAColorImpl;
import com.tokelon.toktales.core.content.text.ITextureFont;
import com.tokelon.toktales.core.engine.TokTales;
import com.tokelon.toktales.core.engine.content.ContentException;
import com.tokelon.toktales.core.engine.inject.ForClass;
import com.tokelon.toktales.core.engine.storage.StorageException;
import com.tokelon.toktales.core.game.controller.IConsoleController;
import com.tokelon.toktales.core.game.model.ICamera;
import com.tokelon.toktales.core.game.model.entity.IGameEntity;
import com.tokelon.toktales.core.game.screen.DefaultModularStateRender;
import com.tokelon.toktales.core.game.screen.IRenderingStrategy;
import com.tokelon.toktales.core.game.states.BaseGamestate;
import com.tokelon.toktales.core.game.states.IGameScene;
import com.tokelon.toktales.core.game.states.IGameStateInputHandler;
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
import com.tokelon.toktales.extens.def.core.game.screen.ConsoleRenderer.ConsoleRendererFactory;
import com.tokelon.toktales.extens.def.core.game.screen.DialogRenderer.DialogRendererFactory;
import com.tokelon.toktales.extens.def.core.game.screen.IDialogRenderer;
import com.tokelon.toktales.extens.def.core.game.screen.TextBoxRenderer;
import com.tokelon.toktales.extens.def.core.game.screen.TextBoxRenderer.TextBoxRendererFactory;
import com.tokelon.toktales.extens.def.core.values.ControllerExtensionsValues;

public class ConsoleGamestate extends BaseGamestate<IGameScene> implements IConsoleGamestate {
	
	public static final String TAG = "ConsoleGamestate";
	
	public static final String RENDERER_CONSOLE_NAME = "renderer_console";
	public static final String RENDERER_TEXT_NAME = "renderer_text_name";
	public static final String RENDERER_DIALOG_NAME = "renderer_dialog_name";
	

	private static final String CONSOLE_PROMPT = ">"; //"> ";
	
	
	private DefaultConsoleController mConsoleController;
	private Console console;
	
	private final IRenderingStrategy mRenderingStrategy;
	private final IConsoleInterpreter consoleInterpreter;
	
	@Inject
	public ConsoleGamestate(
			@ForClass(ConsoleGamestate.class) IGameStateInputHandler inputHandler,
			@ForClass(ConsoleGamestate.class) IRenderingStrategy renderingStrategy,
			@ForClass(ConsoleGamestate.class) IConsoleInterpreter consoleInterpreter
	) {
		super(IGameScene.class, null, inputHandler, null, null);
		
		this.mRenderingStrategy = renderingStrategy;
		this.consoleInterpreter = consoleInterpreter;
	}
	
	@Override
	protected void afterInitStateDependencies() {
		super.afterInitStateDependencies();
		
		getGamestateInjector().injectInto(consoleInterpreter);
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
		console = new Console(consoleInterpreter);
		console.setPrompt(CONSOLE_PROMPT);
		
		mConsoleController = new DefaultConsoleController(console);
		getActiveScene().getControllerManager().setController(ControllerExtensionsValues.CONTROLLER_CONSOLE, mConsoleController);
		
		ConsoleRenderer consoleSegmentRenderer = new ConsoleRendererFactory().createForGamestate(this);
		
		
		// Dialog
		ScreenDialog dialog = new ScreenDialog(camera.getWidth() * 0.80f, camera.getHeight() * 0.20f, 48f);
		dialog.setText("Hello World!");
		DefaultDialogController dialogController = new DefaultDialogController(dialog);
		
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
		getActiveScene().getControllerManager().setController(ControllerExtensionsValues.CONTROLLER_TEXTBOX, new DefaultTextBoxController(textBox));
		

		// State renderer
		DefaultModularStateRender baseStateRenderer = new DefaultModularStateRender(mRenderingStrategy, this);
		
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
	
	
	/** The console controller will be available after {@link #onEngage()}.
	 * 
	 * @return The console controller.
	 */
	@Override
	public IConsoleController getConsoleController() {
		return mConsoleController;
	}
	
}
