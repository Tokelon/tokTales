package com.tokelon.toktales.extens.def.desktop.game.states;

import java.io.IOException;

import com.tokelon.toktales.core.engine.IEngine;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.engine.log.ILogging;
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


	private final IEngine engine;
	private final ILogger logger;
	private final IGame game;
	private final IGameState gamestate;
	
	public TestingProcess(IEngine engine, ILogging logging, IGame game, IGameState gamestate) {
		this.engine = engine;
		this.logger = logging.getLogger(getClass());
		this.game = game;
		this.gamestate = gamestate;
	}
	
	
	private IGame getGame() {
		return game;
	}
	

	
	public void loadTiledMap(String mapFileName) {
		StorageTiledMapLoaderAuto loader = new StorageTiledMapLoaderAuto(gamestate.getLogging(), engine.getStorageService(), game.getWorld());
		
		
		try {
			IApplicationLocation mapLocation = LocationsAndPlaces.LOCATION_EXTERNAL_MAPS;
			loader.setTarget(mapLocation, mapFileName);
			
		} catch (StorageException se) {
			logger.error("StorageException while configuring loader:", se);
			return;
		}
		
		
		
		logger.debug("Loading Tiled map: Started");

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

			
			game.getContentManager().getSpriteAssetManager().getStore().clear();

			
			//game.getStateControl().changeStateTo(TokelonGameStates.STATE_LOCAL_MAP);

		} catch (MapFormatException mfe) {
			logger.error("MapFormatException at loading map:", mfe);
		} catch (IOException ioe) {
			logger.error("IOException at loading map:", ioe);
		} catch (MapLoaderException mle) {
			logger.error("MapLoaderException at loading map:", mle);
		} catch (MapException e) {
			logger.error("Loading map cancelled:", e);
		}
		finally {
			logger.debug("Loading map: Finished");
		}
	}
	
}
