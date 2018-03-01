package com.tokelon.toktales.extens.def.core.game.states;

import java.io.InputStream;

import com.tokelon.toktales.core.content.text.SpriteFontImpl;
import com.tokelon.toktales.core.engine.TokTales;
import com.tokelon.toktales.core.engine.content.ContentException;
import com.tokelon.toktales.core.engine.storage.StorageException;
import com.tokelon.toktales.core.game.model.IConsole;
import com.tokelon.toktales.core.game.model.entity.IGameEntity;
import com.tokelon.toktales.core.game.screen.DefaultModularStateRender;
import com.tokelon.toktales.core.game.screen.IRenderingStrategy;
import com.tokelon.toktales.core.game.states.BaseGamestate;
import com.tokelon.toktales.core.resources.IResourceType;
import com.tokelon.toktales.core.resources.Resource;
import com.tokelon.toktales.core.storage.LocationPrefix;
import com.tokelon.toktales.core.storage.utils.LocationImpl;
import com.tokelon.toktales.core.storage.utils.StructuredLocation;
import com.tokelon.toktales.extens.def.core.game.controller.ISprifoConsoleController;
import com.tokelon.toktales.extens.def.core.game.controller.SprifoConsoleController;
import com.tokelon.toktales.extens.def.core.game.logic.IConsoleInterpreter;
import com.tokelon.toktales.extens.def.core.game.model.Console;
import com.tokelon.toktales.extens.def.core.game.model.SprifoTextBox;
import com.tokelon.toktales.extens.def.core.game.screen.SprifoConsoleRenderer;
import com.tokelon.toktales.extens.def.core.game.screen.SprifoTextRenderer;
import com.tokelon.toktales.tools.tiledmap.TiledMapFormatException;
import com.tokelon.toktales.tools.tiledmap.TiledMapTilesetReader;
import com.tokelon.toktales.tools.tiledmap.TiledSpriteset;
import com.tokelon.toktales.tools.tiledmap.model.TiledMapTilesetImpl;

// Obsolete and broken
@Deprecated
public class SprifoConsoleGamestate extends BaseGamestate { // TODO: Delete
	
	public static final String TAG = "SprifoConsoleGamestate";
	
	
	public static final String RENDERER_CONSOLE_NAME = "renderer_console";
	public static final String RENDERER_TEXT_NAME = "renderer_text_name";
	
	public static final String CONTROLLER_CONSOLE_ID = "console_gamestate-controller_console";

	private static final String CONSOLE_PROMPT = ">"; //"> ";
	
	
	private SprifoConsoleController mConsoleController;
	
	private IRenderingStrategy mRenderingStrategy;
	

	// TODO: Get the strategy from a protected method or like this through the constructor?
	public SprifoConsoleGamestate(IRenderingStrategy renderingStrategy) {
		this.mRenderingStrategy = renderingStrategy;
	}

	
	@Override
	public void onEngage() {
		super.onEngage();
	
		Console console = new Console(new SFConsoleInterpreter());
		console.setPrompt(CONSOLE_PROMPT);
		
		mConsoleController = new SprifoConsoleController(console);
		//getControllerManager().setController(CONTROLLER_CONSOLE_ID, mConsoleController);
		
		SprifoConsoleRenderer consoleSegmentRenderer = new SprifoConsoleRenderer(this, mConsoleController);
		
		
		DefaultModularStateRender baseStateRenderer = new DefaultModularStateRender(
				mRenderingStrategy, getEngine().getRenderService().getSurfaceHandler(), this);
		
		baseStateRenderer.addSegmentRenderer(RENDERER_CONSOLE_NAME, consoleSegmentRenderer);

		
		
		SprifoTextBox textBox = new SprifoTextBox(1280, 200, 20);
		IGameEntity textBoxEntity = textBox.getEntity();
		textBoxEntity.setWorldCoordinates(0, 720 - textBoxEntity.getHeight());
		
		//textBox.setText("Hello, I am a Text Box. This is my only hope. I am consumed by the nightsky.");
		textBox.setText("This was a triumph. \\\\ I'm making a note here: \\\\ HUGE SUCCESS. \\\\ It's hard to overstate my satisfaction.");
		//textBox.setText("I'm not even angry. \\\\ I'm being so sincere right now. \\\\ Even though you broke my heart and killed me. \\\\ And tore me to pieces. \\\\ And threw every piece into a fire. \\\\ As they burned it hurt because \\\\ I was so happy for you!");

		
		SprifoTextRenderer textSegmentRenderer = new SprifoTextRenderer(TokTales.getContext(), textBox);
		
		baseStateRenderer.addSegmentRenderer(RENDERER_TEXT_NAME, textSegmentRenderer);
		
		
		
		setStateRender(baseStateRenderer);
		

		
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
			
			InputStream inputstream = getEngine().getStorageService().tryReadAppFileOnExternal(location, tilesetFilename);
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
		

		//ICamera camera = getCameraController().getCamera();
		//getCamera().setSize(720, 1280);
		//camera.setSize(1280, 720);
		//getCamera().setSize(1860, 1080);
		//camera.setStaticCoordinates(camera.getOriginX(), camera.getOriginY());
		
	}
	

	protected ISprifoConsoleController getConsoleController() {
		return mConsoleController;
	}
	
	
	private class SFConsoleInterpreter implements IConsoleInterpreter {

		@Override
		public boolean interpret(IConsole console, String input) {
			String response;
			
			if(input.contains("Hello")) {
				response = "Hello!";
			}
			else if(input.toLowerCase().contains("load module map")) {
				response = "Loading...";
				
				getGame().getStateControl().changeState("main_menu_state");
			}
			else {
				response = "I did not understand that.";
			}
			
			console.print(response);
			return true;
		}
	}
	
	
}
