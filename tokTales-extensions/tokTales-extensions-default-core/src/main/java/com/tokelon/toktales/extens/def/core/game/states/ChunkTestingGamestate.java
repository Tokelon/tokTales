package com.tokelon.toktales.extens.def.core.game.states;

import java.io.File;

import javax.inject.Inject;

import com.tokelon.toktales.core.content.text.ITextureFont;
import com.tokelon.toktales.core.engine.TokTales;
import com.tokelon.toktales.core.engine.content.ContentException;
import com.tokelon.toktales.core.engine.inject.ForClass;
import com.tokelon.toktales.core.engine.storage.StorageException;
import com.tokelon.toktales.core.game.states.BaseGamestate;
import com.tokelon.toktales.core.game.states.IGameStateInputHandler;
import com.tokelon.toktales.core.storage.IApplicationLocation;
import com.tokelon.toktales.core.storage.utils.LocationImpl;
import com.tokelon.toktales.extens.def.core.game.screen.ChunkTestingStateRenderer;

public class ChunkTestingGamestate extends BaseGamestate {
	// TODO: Remove from this project
	
	public static final String TAG = "ChunkTestingGamestate";
	

	@Inject
	public ChunkTestingGamestate(@ForClass(ChunkTestingGamestate.class) IGameStateInputHandler inputHandler) {
		super(null, inputHandler, null, null);
	}
	

	@Override
	public void onEngage() {
		super.onEngage();
		
		ChunkTestingStateRenderer chunkRenderer = new ChunkTestingStateRenderer(getEngine().getRenderService().getSurfaceHandler(), this);
		setStateRender(chunkRenderer);
		

		ITextureFont font = loadFont();
		chunkRenderer.setCharFont(font);

		
		//getCameraController().getCamera().setSize(2000, 560);
		//getCameraController().getCamera().setSize(560, 2000);
		
		// Android
		//getCameraController().getCamera().setSize(1080, 1920);
		//getCameraController().getCamera().setSize(540, 960);
		//getCameraController().getCamera().setSize(360, 640);
		
		//getCameraController().getCamera().setSize(2560, 1440);
		//getCameraController().getCamera().setSize(1920, 1080);
		//getCameraController().getCamera().setSize(640, 360);
		//getCameraController().getCamera().setSize(960, 540);
		//getCameraController().getCamera().setSize(1080, 900);
		
		
		getActiveScene().getCameraController().getCamera().setSize(1280, 720);
		//getCamera().setSize(640, 360);
		
		//getCamera().setSize(1800, 1000);
		//getCamera().setSize(1300, 800);
		//getCamera().setSize(1100, 700);
		
		//getCamera().setSize(2560, 720);
		//getCamera().setSize(1280, 1440);
	}
	
	
	private ITextureFont loadFont() {
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
	
	
}
