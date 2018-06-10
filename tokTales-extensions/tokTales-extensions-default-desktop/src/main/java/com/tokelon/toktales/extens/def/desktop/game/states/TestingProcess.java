package com.tokelon.toktales.extens.def.desktop.game.states;

import java.io.IOException;

import com.tokelon.toktales.core.engine.IEngine;
import com.tokelon.toktales.core.engine.TokTales;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.engine.storage.StorageException;
import com.tokelon.toktales.core.game.IGame;
import com.tokelon.toktales.core.game.logic.map.MapException;
import com.tokelon.toktales.core.game.logic.map.MapLoaderException;
import com.tokelon.toktales.core.game.model.map.IBlockMap;
import com.tokelon.toktales.core.game.states.IGameState;
import com.tokelon.toktales.core.resources.IResourceType;
import com.tokelon.toktales.core.resources.Resource;
import com.tokelon.toktales.core.storage.IApplicationLocation;
import com.tokelon.toktales.core.storage.LocationPrefix;
import com.tokelon.toktales.core.storage.utils.StructuredLocation;
import com.tokelon.toktales.core.values.LocationsAndPlaces;
import com.tokelon.toktales.extens.def.core.tale.DefaultSceneMapReceiver;
import com.tokelon.toktales.extens.def.core.tale.states.ITaleGamescene;
import com.tokelon.toktales.tools.tiledmap.MapFormatException;
import com.tokelon.toktales.tools.tiledmap.StorageTiledMapLoaderAuto;

public class TestingProcess {
	// TODO: Remove/Rename and move
	
	public static final String TAG = "TestingProcess";

	
	private final IEngine engine;
	private final ILogger log;
	private final IGame game;
	private final IGameState gamestate;
	
	public TestingProcess(IEngine engine, ILogger log, IGame game, IGameState gamestate) {
		this.engine = engine;
		this.log = log;
		this.game = game;
		this.gamestate = gamestate;
	}
	
	private IGame getGame() {
		return game;
	}
	

	
	public void loadTiledMap(String mapFileName) {
		StorageTiledMapLoaderAuto loader = new StorageTiledMapLoaderAuto(engine.getStorageService(), game.getWorld());
		
		
		try {
			IApplicationLocation mapLocation = LocationsAndPlaces.LOCATION_EXTERNAL_MAPS;
			loader.setTarget(mapLocation, mapFileName);
			
		} catch (StorageException se) {
			TokTales.getLog().e(TAG, "StorageException while configuring loader: " +se.getMessage());
			return;
		}
		
		
		
		TokTales.getLog().d(TAG, "Loading Tiled map: Started");

		try {
			loader.runComplete();
			
			
			IBlockMap loadedMap = loader.getLoadedMap();
			
			DefaultSceneMapReceiver receiver = new DefaultSceneMapReceiver((ITaleGamescene) gamestate.getActiveScene());
			receiver.receiveMap(loadedMap);
			

			// TODO: Somehow else
			// TMP Solution
			// Adds some default resources

			Resource defaultTilesetResource = new Resource(IResourceType.Type.SPRITE_SET, "default-asset-tilesets", new StructuredLocation(LocationPrefix.EXTERNAL, "assets\\graphics\\tilesets\\"));
			loadedMap.getResources().addResource(defaultTilesetResource);

			Resource mapTilesetResource = new Resource(IResourceType.Type.SPRITE_SET, loadedMap.getName()+"-tilesets", new StructuredLocation(LocationPrefix.EXTERNAL, "maps\\" +loadedMap.getName() + "\\assets\\graphics\\tilesets"));
			loadedMap.getResources().addResource(mapTilesetResource);
			
			Resource mapSpritesResource = new Resource(IResourceType.Type.SPRITE_SET, loadedMap.getName()+"-sprites", new StructuredLocation(LocationPrefix.EXTERNAL, "maps\\" +loadedMap.getName() + "\\assets\\graphics\\sprites"));
			loadedMap.getResources().addResource(mapSpritesResource);

			Resource mapPlayerResource = new Resource(IResourceType.Type.SPRITE_SET, loadedMap.getName()+"-player", new StructuredLocation(LocationPrefix.EXTERNAL, "maps\\" +loadedMap.getName() + "\\assets\\graphics\\player"));
			loadedMap.getResources().addResource(mapPlayerResource);
			
			
			
			
			
			game.getContentManager().getResourceManager().resetListings();
			game.getContentManager().getResourceManager().resetResources();
			
			
			game.getContentManager().getResourceManager().preloadResourceSet("blkmap_editor_loaded_map_resources", loadedMap.getResources());

			
			game.getContentManager().getSpriteManager().runClearAll();

			
			//game.getStateControl().changeStateTo(TokelonGameStates.STATE_LOCAL_MAP);

		} catch (MapFormatException mfe) {
			TokTales.getLog().e(TAG, "MapFormatException at loading map: " +mfe.getMessage());
		} catch (IOException ioe) {
			TokTales.getLog().e(TAG, "IOException at loading map: " +ioe.getMessage());
		} catch (MapLoaderException mle) {
			TokTales.getLog().e(TAG, "MapLoaderException at loading map: " +mle.getMessage());
		} catch (MapException e) {
			TokTales.getLog().e(TAG, "Loading map cancelled");
		}
		finally {

			TokTales.getLog().d(TAG, "Loading map: Finished");
		}
		
	}
	
}
