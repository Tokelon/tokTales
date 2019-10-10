package com.tokelon.toktales.core.tiled;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.tokelon.toktales.core.config.StorageConfigLoader;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.engine.log.ILogging;
import com.tokelon.toktales.core.engine.storage.IStorageService;
import com.tokelon.toktales.core.engine.storage.StorageException;
import com.tokelon.toktales.core.game.logic.map.IMapLoader;
import com.tokelon.toktales.core.game.logic.map.MapLoaderException;
import com.tokelon.toktales.core.game.model.map.IBlockMap;
import com.tokelon.toktales.core.game.world.IWorld;
import com.tokelon.toktales.core.storage.IApplicationLocation;
import com.tokelon.toktales.core.storage.utils.DynamicApplicationLocation;
import com.tokelon.toktales.core.storage.utils.IMutablePath;
import com.tokelon.toktales.tools.core.config.ConfigDataException;
import com.tokelon.toktales.tools.core.config.ConfigFormatException;
import com.tokelon.toktales.tools.core.config.ICiniConfig;
import com.tokelon.toktales.tools.tiled.MapFormatException;
import com.tokelon.toktales.tools.tiled.TiledMapFormatException;
import com.tokelon.toktales.tools.tiled.TiledMapReader;
import com.tokelon.toktales.tools.tiled.TiledMapTilesetReader;
import com.tokelon.toktales.tools.tiled.model.ITiledMap;
import com.tokelon.toktales.tools.tiled.model.ITiledMapTileset;
import com.tokelon.toktales.tools.tiled.model.TiledMapTilesetImpl;

public class StorageTiledMapLoaderAuto implements IMapLoader {


	private InputStream mapIn;
	private IApplicationLocation mapfilelocation;
	private String mapfilename;
	
	private ITiledMapConfig mapConfig;
	private ITiledMap loadedMap;
	private IBlockMap blockMap;
	

	private boolean initialized = false;

	private final ILogger logger;
	private final IStorageService storageService;
	private final IWorld world;
	
	public StorageTiledMapLoaderAuto(ILogging logging, IStorageService storageService, IWorld world) {
		this.logger = logging.getLogger(getClass());
		this.storageService = storageService;
		this.world = world;
	}
	
	
	
	/** Opens an InputStream to the target file.
	 * 
	 * @param storageLocation
	 * @param mapFileName
	 * @throws StorageException If an error with opening the map file occurs.
	 * @throws NullPointerException If storageLocation or mapFileName is null.
	 * @throws IllegalArgumentException If mapFileName is invalid.
	 */
	public void setTarget(IApplicationLocation storageLocation, String mapFileName) throws StorageException {
		if(storageLocation == null || mapFileName == null) {
			throw new NullPointerException();
		}
		else if(mapFileName.trim().isEmpty()) {
			throw new IllegalArgumentException("Map file name cannot be empty");
		}
		
		this.mapIn = storageService.readAppFileOnExternal(storageLocation, mapFileName);
		this.mapfilename = mapFileName;
		this.mapfilelocation = storageLocation;
	}
	
		
	

	@Override
	public void runComplete() throws MapLoaderException, MapFormatException, IOException {
		initialize();
		
		load();
		
		process();
	}

	
	
	@Override
	public void initialize() throws MapFormatException, IOException {
		if(mapIn == null || mapfilename == null) {
			throw new NullPointerException("No target set");
		}
		
		
		
		// Check for config file
		
		int lioDot = mapfilename.lastIndexOf('.');
		String configFileNameGuess = lioDot == -1 ? mapfilename + "." : mapfilename.substring(0, lioDot+1);
		String configFileEnding = ".conf";

		File configFile = null;
		try {
			
			File[] filesInMapStorageLocation = storageService.listFilesAppDirOnExternal(mapfilelocation);
			for(File file: filesInMapStorageLocation) {
				
				if(file.isFile() && file.getName().startsWith(configFileNameGuess) && file.getName().endsWith(configFileEnding)) {
					configFile = file;
					break;
				}
			}
		} catch (StorageException e) {
			logger.warn("Failed to list files for location: {}", mapfilelocation, e);
		}

		
		
		ICiniConfig ciniConfig = null;
		
		// If a config file was found, load it
		if(configFile != null) {
			logger.info("Found map config file: {}", configFile.getName());
			
			
			// METHOD A
			StorageConfigLoader configLoader = new StorageConfigLoader(storageService);
			try {
				configLoader.setTarget(mapfilelocation, configFile.getName());
				
				logger.info("Loading map config...");
				ciniConfig = configLoader.loadConfig();
				
			} catch (StorageException stoex) {
				logger.warn("Failed to open config file:", stoex);
			} catch (ConfigFormatException conforex) {
				logger.warn("Bad config file:", conforex);
			}
			
			
			// METHOD B			
			/*
			InputStream configFileIn = null;
			try {
				configFileIn = storageFramework.readAppFileOnExternal(mapfilelocation, configFile.getName());
				
				CiniConfigStreamReader reader = new CiniConfigStreamReader();
				config = reader.readConfig(configFileIn);
				
			} catch (StorageException e) {
				Prog.getLog().w(TAG, "Failed to open config file. No config will be loaded.");
			} catch (ConfigFormatException e) {
				Prog.getLog().w(TAG, "Bad config file: " +e.getMessage() +". No config will be loaded.");
			} finally {
				if(configFileIn != null) configFileIn.close();
			}
			*/
			
		}
		
		
		TiledMapCiniConfig tiledMapConfig = null;
		
		// If a config was loaded, check it
		if(ciniConfig != null) {
			logger.info("Checking map config...");
			
			try {
				tiledMapConfig = new TiledMapCiniConfig(ciniConfig);
				
			} catch (ConfigDataException condatex) {
				logger.warn("Unsupported config:", condatex);
			}

		}
		
		
		if(tiledMapConfig == null) {
			logger.warn("No map config will be loaded.");
			mapConfig = TiledMapCiniConfig.defaultConfig();
		}
		else {
			logger.info("Map config was loaded: {}", configFile.getName());
			mapConfig = tiledMapConfig;
		}
		
		
		
		initialized = true;
	}

	@Override
	public void load() throws MapLoaderException, MapFormatException, IOException {
		if(!initialized) {
			throw new MapLoaderException("Loader not initialized");
		}
		
		
		TiledMapReader reader = new TiledMapReader();

		ITiledMap readMap = null;
		try {
			readMap = reader.readMap(mapIn, mapfilename);
		}
		finally {
			try {
				mapIn.close();
			} catch(IOException e) {
				logger.warn("close threw exception", e);
			}
		}
		
		loadedMap = readMap;
	}

	@Override
	public void process() throws MapLoaderException {
		if(loadedMap == null) {
			throw new MapLoaderException("Previous load failed");
		}
		
		
		/* Process tilesets */
		try {
			List<ITiledMapTileset> externalTilesets = processExternalTilesets(loadedMap.getTilesetList());
			
			loadedMap.getExternalTilesetList().addAll(externalTilesets);
		}
		catch(TiledMapFormatException tmfe) {
			throw new MapLoaderException(tmfe);
		}
		
		
		
		
		/* Create wrapper map */
		TiledWrapperMap wrapperMap = new TiledWrapperMap(loadedMap, mapConfig, world);
		
		/* Build the map */
		wrapperMap.build();
		
		
		

		blockMap = wrapperMap;
	}
	
	
	private List<ITiledMapTileset> processExternalTilesets(List<? extends ITiledMapTileset> mapTilesets) throws TiledMapFormatException {


		ArrayList<ITiledMapTileset> externalTilesetList = new ArrayList<ITiledMapTileset>();
		
		if(mapTilesets.isEmpty()) {
			return externalTilesetList;
		}
		

		
		TiledMapTilesetReader tilesetReader = new TiledMapTilesetReader();
		tilesetReader.setup();
		
		
		

		DynamicApplicationLocation tilesetLocation = new DynamicApplicationLocation();
		IMutablePath tilesetLocationPath = tilesetLocation.editLocationPath(); 
		
		Iterator<? extends ITiledMapTileset> mapTilesetsIt = mapTilesets.iterator();
		ITiledMapTileset tileset;
		while(mapTilesetsIt.hasNext()) {
			tileset = mapTilesetsIt.next();
			
			if(tileset.isReferenceToExternal()) {
				
				
				
				/* If the external tileset fails to load for whatever reason, we can
				 * 1. Remove the tileset from the map and accept that for all tiles in that tileset nothing will be rendered
				 * 2. Throw an Exception and refuse to load the map
				 * 3. Fill the tileset with error tiles which will be rendered as errors
				 *
				 * TODO: Change implementation ?
				 * Current implementation: 2
				 */
				
				
				String externalSource = tileset.getSource();	// Can this be null?
				if(externalSource.trim().isEmpty()) {
					logger.warn("WARNING: External tileset has no valid source. Tileset will be ignored: {}", externalSource);
					
					// Remove broken tileset from map
					logger.warn("Removing broken tileset: {}", externalSource);
					mapTilesetsIt.remove();
					continue;
				}
				
				
				File f = new File(externalSource);
				String tilesetPath = f.getParent();
				String tilesetFilename = f.getName();
				
				if(tilesetFilename.isEmpty()) {
					logger.warn("WARNING: External tileset source has no name. Tileset will be ignored: {}", externalSource);
					
					// Remove broken tileset from map
					logger.warn("Removing broken tileset: {}", externalSource);
					mapTilesetsIt.remove();
					continue;
				}
				
				
				tilesetLocationPath.setPath(mapfilelocation.getLocationPath());
				if(tilesetPath != null) {
					tilesetLocationPath.append(tilesetPath);
				}
				
				
				
				InputStream tilesetInput;
				try {
					tilesetInput = storageService.readAppFileOnExternal(tilesetLocation, tilesetFilename);
				} catch (StorageException e) {
					logger.warn("WARNING: External tileset source could not be read. Tileset will be ignored: {}", externalSource);
					
					// Remove broken tileset from map
					logger.warn("Removing broken tileset: {}", externalSource);
					mapTilesetsIt.remove();
					continue;
				}
				
				
				
				logger.info("Loading external tileset... : {}", externalSource);
				
				TiledMapTilesetImpl externalTileset = tilesetReader.readTileset(tilesetInput);
				externalTileset.setFirstGIDForExternal(tileset.getFirstGID());
				
				
				tileset.setExternalTileset(externalTileset);
				
				externalTilesetList.add(externalTileset);
			}
		}
	
		return externalTilesetList;
	}
	
	
	

	@Override
	public IBlockMap getLoadedMap() {
		return blockMap;
	}

}
