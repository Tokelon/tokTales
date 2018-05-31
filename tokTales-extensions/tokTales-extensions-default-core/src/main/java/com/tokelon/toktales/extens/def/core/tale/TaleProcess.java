package com.tokelon.toktales.extens.def.core.tale;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.tokelon.toktales.core.config.CiniMainConfig;
import com.tokelon.toktales.core.config.IConfigManager;
import com.tokelon.toktales.core.config.IMainConfig;
import com.tokelon.toktales.core.content.sprite.SpriteImpl;
import com.tokelon.toktales.core.engine.IEngine;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.engine.storage.IStorageService;
import com.tokelon.toktales.core.engine.storage.StorageException;
import com.tokelon.toktales.core.game.IGame;
import com.tokelon.toktales.core.game.controller.IPlayerController;
import com.tokelon.toktales.core.game.graphic.SpriteGraphicImpl;
import com.tokelon.toktales.core.game.graphic.animation.GameAnimation;
import com.tokelon.toktales.core.game.graphic.animation.IGameAnimation;
import com.tokelon.toktales.core.game.logic.map.MapException;
import com.tokelon.toktales.core.game.logic.map.MapLoaderException;
import com.tokelon.toktales.core.game.model.IPlayer;
import com.tokelon.toktales.core.game.model.map.IBlockMap;
import com.tokelon.toktales.core.game.world.ICrossDirection;
import com.tokelon.toktales.core.resources.IResourceType;
import com.tokelon.toktales.core.resources.Resource;
import com.tokelon.toktales.core.script.StorageLocationResourceFinder;
import com.tokelon.toktales.core.storage.LocationPrefix;
import com.tokelon.toktales.core.storage.utils.LocationImpl;
import com.tokelon.toktales.core.storage.utils.StructuredLocation;
import com.tokelon.toktales.extens.def.core.tale.config.CiniAnimConfig;
import com.tokelon.toktales.extens.def.core.tale.config.CiniTaleConfig;
import com.tokelon.toktales.extens.def.core.tale.config.ITaleConfig;
import com.tokelon.toktales.extens.def.core.tale.states.ITaleGamescene;
import com.tokelon.toktales.tools.config.CiniConfigStreamReader;
import com.tokelon.toktales.tools.config.ConfigDataException;
import com.tokelon.toktales.tools.config.ConfigFormatException;
import com.tokelon.toktales.tools.config.MutableCiniConfig;
import com.tokelon.toktales.tools.script.IScriptModule;
import com.tokelon.toktales.tools.script.ScriptErrorException;
import com.tokelon.toktales.tools.tiledmap.MapFormatException;
import com.tokelon.toktales.tools.tiledmap.StorageTiledMapLoaderAuto;

class TaleProcess {
	// TODO: Refactor internal structure and error handling
	// Implement as a procedure?
	
	
	public static final String TAG = "TaleProcess";

	private static final String TALE_MAIN_FILE_ENDING = ".tok";
	private static final String TALE_CONFIG_FILE_ENDING = ".conf";
	private static final String TALE_SCRIPT_FILE_ENDING = ".lua";
	private static final String TALE_SCRIPTS_DIRECTORY = "scripts";
	
	private static final String TALE_SCENE_CLASS_POSTFIX = "TaleGamescene";
	
	
	
	private ITaleGamescene gamesceneResult;
	
	private final IEngineContext engineContext;
	private final ILogger log;
	private final IEngine engine;
	private final IGame game;

	private String taleAppPath;
	
	public TaleProcess(IEngineContext engineContext, String taleApplicationPath) {
		this.engineContext = engineContext;
		this.log = engineContext.getLog();
		this.engine = engineContext.getEngine();
		this.game = engineContext.getGame();
		
		this.taleAppPath = taleApplicationPath;
	}

	
	public ITaleGamescene getResult() {
		return gamesceneResult;
	}
	
	
	public void run() {
		
		IStorageService storageService = engine.getStorageService();

		// Root directory of the Tale
		LocationImpl taleLocation = new LocationImpl(taleAppPath);

		
		
		// List the files in the Tale directory
		String[] fileNames;
		try {
			fileNames = storageService.listAppDirOnExternal(taleLocation);
			
		} catch (StorageException e) {
			log.e(TAG, "List Tale directory failed: " +e.getMessage());
			return;
		}
		
		
		// Search for main Tale file (ends with .tok)
		String mainFileName = null;
		for(String fName: fileNames) {
			
			if(fName.endsWith(TALE_MAIN_FILE_ENDING)) {
				mainFileName = fName;
			}
		}
		
		
		if(mainFileName == null) {
			log.e(TAG, "No main Tale file found");
			return;
		}
		

		
		
		// Name for tale config file that might exist
		String configFileName = mainFileName.substring(0, mainFileName.length() - TALE_MAIN_FILE_ENDING.length()).concat(TALE_CONFIG_FILE_ENDING);

		// Check if there is a config file
		InputStream configFileIn = storageService.tryReadAppFileOnExternal(taleLocation, configFileName);
		
		if(configFileIn != null) {
			log.i(TAG, "Tale main config file found: " +configFileName);
			
			
			CiniConfigStreamReader reader = new CiniConfigStreamReader();
			
			IMainConfig mainConfig = null;
			try {
				MutableCiniConfig ciniConfig = reader.readConfig(configFileIn);
				
				
				log.i(TAG, "Checking config...");

				mainConfig = new CiniMainConfig(ciniConfig);
				
				log.i(TAG, "Config was loaded.");
			} catch (ConfigFormatException cfe) {
				log.w(TAG, "Bad config file: " +cfe.getMessage());
			} catch (ConfigDataException cde) {
				log.w(TAG, "Unsupported config: " +cde.getMessage());
			}
			finally {
				try {
					configFileIn.close();
				} catch (IOException e) { /* Nothing to see here... */ }
			}
			
			if(mainConfig == null) {
				log.i(TAG, "No main config was loaded.");
			}
			else {
				// TODO: 096 Load a tale specific config and not the main config
				
				game.getConfigManager().loadConfig(IConfigManager.MAIN_CONFIG, mainConfig);		// We have to load the main config first so it is available TODO: Fix (If tale loading fails, main config will be wrong)
			}
		}
		
		
		
		// Load tale
		ITaleGamescene taleScene = null; // TODO: If taleScene is null, the rest should probably not execute
		try {
			taleScene = loadTale(storageService, taleLocation, mainFileName);
			gamesceneResult = taleScene;
		} catch (TaleException te) {
			log.e(TAG, "Loading Tale failed. Tale error: " +te);
		} catch (StorageException se) {
			log.e(TAG, "Loading Tale failed. IO error: " +se);
		} catch (ConfigFormatException cfe) {
			log.e(TAG, "Loading Tale failed. Config format error: " +cfe);
		} catch (ConfigDataException cde) {
			log.e(TAG, "Loading Tale failed. Config data error: " +cde);
		}
		
		
		// Add resource finder for the tale script directory
		LocationImpl taleScriptLocation = new LocationImpl(taleLocation.getLocationPath().getPathAppendedBy(TALE_SCRIPTS_DIRECTORY));
		StorageLocationResourceFinder taleScriptFinder = new StorageLocationResourceFinder(storageService, taleScriptLocation);
		game.getScriptManager().getResourceFinder().addResourceFinder(taleScriptFinder);
		
		
		
		// Name for tale script file that might exist
		String scriptFileName = mainFileName.substring(0, mainFileName.length() - TALE_MAIN_FILE_ENDING.length()).concat(TALE_SCRIPT_FILE_ENDING);

		// Check if there is a script file
		InputStream scriptFileIn = storageService.tryReadAppFileOnExternal(taleLocation, scriptFileName);
		if(scriptFileIn != null) {
			log.i(TAG, "Tale main script file found: " +configFileName);
			
			IScriptModule taleModule;
			try {
				taleModule = game.getScriptManager().loadModule(scriptFileIn, scriptFileName);
				taleModule.callFunction(ITaleScriptModule.FUNCTION_onTaleCreate, taleScene);
				
				
				/*
				Object result = taleModule.callFunction("createActor", "Actor_Jack");
				if(!(result instanceof IActor)) {
					log.w(TAG, "Return type for module function createActor does not match the expected type (IActor)");
				}
				else {

					IActor jack = (IActor) result;
					game.getGamestate().getWorldspace().putEntity("actor_jack", jack);
					game.getGamestate().getControllerManager().setPlayerController(new PlayerController(jack));
					game.getGamestate().getControllerManager().getCameraController().enableCameraFollow(jack);
					
					
					String gravityFileName = "Gravity.lua";
					InputStream gravityFileIn = storageFramework.tryReadAppFileOnExternal(taleLocation, gravityFileName);
					if(gravityFileIn != null) {
						log.i(TAG, "Gravity module file found");
						IScriptModule gravityModule = game.getScriptManager().loadModule(gravityFileIn, gravityFileName);
						
						Object participantResult = gravityModule.callFunction("createGravityParticipant", null);
						if(participantResult instanceof IGameEntityParticipant) {
							IGameEntityParticipant participant = (IGameEntityParticipant) participantResult;
							jack.getParticipation().addParticipant(participant);
						}
						else {
							log.w(TAG, "Return type for module function createGravityParticipant does not match the expected type (IGameEntityParticipant)");
						}
					}
					
				}
				*/
				
			} catch (ScriptErrorException e) {
				log.w(TAG, "Script failed to load: " +e.getMessage());
			}
			
			
		}
		
		
		log.d(TAG, "Reading Tale finished");
	}

	
	
	private ITaleGamescene loadTale(IStorageService storageService, LocationImpl taleLocation, String mainFileName) throws TaleException, StorageException, ConfigFormatException, ConfigDataException {
		log.d(TAG, "Reading Tale: Started");
		
		
		// Load main Tale file

		CiniTaleConfig taleConfig = null;
		try(InputStream mainFileIn = storageService.readAppFileOnExternal(taleLocation, mainFileName)) {
			CiniConfigStreamReader ccReader = new CiniConfigStreamReader();
			MutableCiniConfig cConfig = ccReader.readConfig(mainFileIn);
			
			taleConfig = new CiniTaleConfig(cConfig);
		} catch (IOException e3) { /* Nothing */ }
		

		ITaleGamescene sceneResult = null;
		String initialSceneCodename = taleConfig.getConfigTaleInitialSceneCodename().trim();
		if(initialSceneCodename.isEmpty()) {
			sceneResult = engineContext.getInjector().getInstance(ITaleGamescene.class);
			log.i(TAG, "Loaded default scene implementation");
		}
		else {
		    String sceneClassName = initialSceneCodename + TALE_SCENE_CLASS_POSTFIX;

		    try {
		        Class<?> sceneClass = Class.forName(sceneClassName);
		        log.i(TAG, "Loading initial scene with class: " +sceneClass.getName());
		        
		        if(ITaleGamescene.class.isAssignableFrom(sceneClass)) {
		        	try {
		        		sceneResult = (ITaleGamescene) engineContext.getInjector().getInstance(sceneClass);
		        		log.i(TAG, "Loaded scene with implementation: " + sceneResult.getClass().getName());
		        	}
		        	catch (Exception e) {
		        		throw new TaleException(e);
					}
		        }
		        else {
		            //log.e(TAG, "Loading scene failed | Scene class is not an ITaleGamescene: " + sceneClassName);
		            throw new TaleException("Loading scene failed | Scene class is not an ITaleGamescene: " + sceneClassName);
		        }
		    } catch (ClassNotFoundException e) {
		        //log.e(TAG, "Loading scene failed | Scene class not found: " + sceneClassName);
	            throw new TaleException("Loading scene failed | Scene class not found: " + sceneClassName);
		    }
		}

		
		// Setup the initial map location
		LocationImpl mapsLocation = new LocationImpl(taleLocation.getLocationPath().getPathAppendedBy(taleConfig.getConfigResourcesMapDirectory()));
		String initialMapfileName = taleConfig.getConfigTaleInitialMapfileName();
		if(initialMapfileName.trim().isEmpty()) {
			log.e(TAG, "Loading Tale failed: No initial map");
			return null;
		}
		

		log.d(TAG, "Loading initial map...");
		// Read the initial map
		IBlockMap initialMap = readTiledMap(mapsLocation, initialMapfileName);
		
		if(initialMap == null) {
			log.e(TAG, "Loading Tale failed: Could not read initial map");
			return null;
		}
		else {
			// Map was read fine
			
			// Load the map into our map manager
			boolean res = loadMapIntoGame(initialMap, taleConfig, taleLocation, sceneResult);
			if(!res) {
				log.e(TAG, "Loading Tale failed: Could not load map into game");
				return null;
			}
		}

		
		// Load player sprites and animations
		setupPlayerEntity(storageService, taleLocation, taleConfig, sceneResult);

		return sceneResult;
	}
	
	
	private void setupPlayerEntity(IStorageService storageService, LocationImpl taleLocation, ITaleConfig taleConfig, ITaleGamescene taleScene) {
		
		IPlayerController playerController = taleScene.getPlayerController();
		IPlayer player = playerController.getPlayer();
		
		
		// Values
		
		//float tileSize = IWorld.GRID_TILE_SIZE;
		//float speed = tileSize * (playerMoveDuration / 1000.0f);
		
		
		float playerWalkSpeedUnits = taleConfig.getConfigPlayerWalkSpeedUnits();
	
		player.setSpeedX(playerWalkSpeedUnits);
		player.setSpeedY(playerWalkSpeedUnits);

		
		
		// Animations
		LocationImpl animationsLocation = new LocationImpl(taleLocation.getLocationPath().getPathAppendedBy(taleConfig.getConfigResourcesAnimationDirectory()));
		
		CiniConfigStreamReader ciniReader = new CiniConfigStreamReader();

		String animFilename;
		
		
	
		// Walk left
		animFilename = taleConfig.getConfigPlayerAnimationWalkLeft();
		loadAnimationIntoPlayer(storageService, animationsLocation, animFilename, ciniReader, IPlayer.ANIMATION_WALK_LEFT, player);
		
		// Walk Up
		animFilename = taleConfig.getConfigPlayerAnimationWalkUp();
		loadAnimationIntoPlayer(storageService, animationsLocation, animFilename, ciniReader, IPlayer.ANIMATION_WALK_UP, player);

		// Walk right
		animFilename = taleConfig.getConfigPlayerAnimationWalkRight();
		loadAnimationIntoPlayer(storageService, animationsLocation, animFilename, ciniReader, IPlayer.ANIMATION_WALK_RIGHT, player);

		// Walk down
		animFilename = taleConfig.getConfigPlayerAnimationWalkDown();
		loadAnimationIntoPlayer(storageService, animationsLocation, animFilename, ciniReader, IPlayer.ANIMATION_WALK_DOWN, player);

		

		
		// Idle graphics
		String graphicName;
		
		
		// Idle left
		graphicName = taleConfig.getConfigPlayerGraphicIdleLeft();
		player.getGraphicsImage().assignGraphic(IPlayer.GRAPHIC_IDLE_LEFT, new SpriteGraphicImpl(new SpriteImpl(graphicName)));

		// Idle up
		graphicName = taleConfig.getConfigPlayerGraphicIdleUp();
		player.getGraphicsImage().assignGraphic(IPlayer.GRAPHIC_IDLE_UP, new SpriteGraphicImpl(new SpriteImpl(graphicName)));

		// Idle right
		graphicName = taleConfig.getConfigPlayerGraphicIdleRight();
		player.getGraphicsImage().assignGraphic(IPlayer.GRAPHIC_IDLE_RIGHT, new SpriteGraphicImpl(new SpriteImpl(graphicName)));

		// Idle down
		graphicName = taleConfig.getConfigPlayerGraphicIdleDown();
		player.getGraphicsImage().assignGraphic(IPlayer.GRAPHIC_IDLE_DOWN, new SpriteGraphicImpl(new SpriteImpl(graphicName)));
		
		
		
		playerController.playerLook(ICrossDirection.DOWN);
	}
	
	private void loadAnimationIntoPlayer(IStorageService storageService, LocationImpl animLocation, String animFilename, CiniConfigStreamReader ciniReader, String animCode, IPlayer player) {
		
		
		/* Default for if walk animations do not define their time
		 * Do either this or set default animation time to 1
		 */
		//int aniTime = playerEntity.getWalkOneBlockDuration();
		//int aniTime = taleConfig.getConfigPlayerMoveOneBlockDuration();
		// TODO: What to do here?
		//int aniTime = 1;
		float tileSize = game.getWorld().getGridTileSize();
		int aniTime = (int) (1000.0f * tileSize / player.getSpeedX());

		
		
		
		if(animFilename.trim().isEmpty()) {
			log.w(TAG, "No animation file for code: " +animCode);
			
			player.getGraphicsImage().assignAnimation(animCode, null);
			return;
		}
		
		
		InputStream animIn = storageService.tryReadAppFileOnExternal(animLocation, animFilename);
		if(animIn == null) {
			log.w(TAG, String.format("Failed to read animation file: %s at (%s)", animFilename, animLocation.getLocationPath().getPath()));
			
			player.getGraphicsImage().assignAnimation(animCode, null);
			return;
		}
		
		
		IGameAnimation animation = loadAnimation(animIn, ciniReader, aniTime);
		if(animation == null) {
			log.w(TAG, String.format("Failed to load animation file: %s at (%s)", animFilename, animLocation.getLocationPath().getPath()));

			player.getGraphicsImage().assignAnimation(animCode, null);
			return;
		}

		player.getGraphicsImage().assignAnimation(animCode, animation);
	}
	
	
	private IGameAnimation loadAnimation(InputStream animIn, CiniConfigStreamReader reader, int defaultAnimTime) {
		try {
			// Read in the config
			MutableCiniConfig cConfig = reader.readConfig(animIn);

			// Parse it to an animation config
			CiniAnimConfig animConfig = new CiniAnimConfig(cConfig);


			
			// The resulting animation object
			GameAnimation animation = new GameAnimation();
			


			// Setup the sequence as a graphics array
			List<String> sequenceList = animConfig.getConfigAnimationSequence();		// The sequence list
			SpriteGraphicImpl[] graphics = new SpriteGraphicImpl[sequenceList.size()];	// The sequence array

			for(int i=0; i < graphics.length; i++) {
				graphics[i] = new SpriteGraphicImpl(new SpriteImpl(sequenceList.get(i)));
			}

			// Pass the map graphics array
			animation.setupGraphics(graphics);



			// Setup the timings
			List<Integer> sequenceTimingsList = animConfig.getConfigAnimationSequenceTiming();			// The sequence timings list (can be empty if there is a single animation time)

			if(sequenceTimingsList.isEmpty() || sequenceTimingsList.size() < sequenceList.size()) {		// If the timings list is smaller than the sequence list we cant use it

				// Pass the single animation time
				int animTime = animConfig.getConfigAnimationAnimTime();
				if(animTime <= 0) {
					animTime = defaultAnimTime;
				}
				
				int frameDuration = (int)Math.ceil(animTime / (double)graphics.length);
				animation.setupFramesWithOneDuration(graphics.length, frameDuration);
			}
			else {

				// Setup the timings as an array of ints
				int[] timings = new int[sequenceTimingsList.size()];	// The timings array
				for(int i=0; i < timings.length; i++) {
					timings[i] = sequenceTimingsList.get(i);
				}

				// Pass the timings array
				animation.setupFramesWithDurations(timings);
			}


			return animation;

		} catch (ConfigFormatException cfe) {
			log.w(TAG, "Invalid format: " +cfe.getMessage());
		} catch (ConfigDataException cde) {
			log.w(TAG, "Invalid data: " +cde.getMessage());
		}


		// Error
		return null;
	}
	
	
	private IBlockMap readTiledMap(LocationImpl location, String fileName) {
		
		// Tiled Map loader
		StorageTiledMapLoaderAuto loader = new StorageTiledMapLoaderAuto(engine.getStorageService(), game.getWorld());
		
		
		try {
			loader.setTarget(location, fileName);
			
		} catch (StorageException se) {
			log.e(TAG, "StorageException while configuring loader: " +se.getMessage());
			return null;
		}
		
		
		
		log.d(TAG, "Reading Tiled map: Started");

		try {
			loader.runComplete();
			
			// If the map loads fine, return it
			return loader.getLoadedMap();
			
		} catch (MapFormatException mfe) {
			log.e(TAG, "MapFormatException at loading map: " +mfe.getMessage());
		} catch (IOException ioe) {
			log.e(TAG, "IOException at loading map: " +ioe.getMessage());
		} catch (MapLoaderException mle) {
			log.e(TAG, "MapLoaderException at loading map: " +mle.getMessage());
		}
		
		return null;
	}
	
	

	private boolean loadMapIntoGame(IBlockMap map, ITaleConfig taleConfig, LocationImpl taleLocation, ITaleGamescene taleScene) {
		
		// TODO: Refactor?
		DefaultSceneMapReceiver receiver = new DefaultSceneMapReceiver(taleScene);
		
		try {
			receiver.receiveMap(map);
		} catch (MapException e) {
			// pass exception?
			return false;
		}
		
		
		
		StructuredLocation tilesetLocation = new StructuredLocation(LocationPrefix.EXTERNAL, taleLocation.getLocationPath().getPathAppendedBy(taleConfig.getConfigResourcesGTilesetDirectory()));
		
		Resource mapTilesetResource = new Resource(IResourceType.Type.SPRITE_SET, map.getFileName()+"-tilesets", tilesetLocation);
		map.getResources().addResource(mapTilesetResource);
		
		
		StructuredLocation spriteLocation = new StructuredLocation(LocationPrefix.EXTERNAL, taleLocation.getLocationPath().getPathAppendedBy(taleConfig.getConfigResourcesGSpriteDirectory()));
		
		Resource mapSpritesResource = new Resource(IResourceType.Type.SPRITE, map.getFileName()+"-sprites", spriteLocation);
		map.getResources().addResource(mapSpritesResource);

		
		StructuredLocation playerLocation = new StructuredLocation(LocationPrefix.EXTERNAL, taleLocation.getLocationPath().getPathAppendedBy(taleConfig.getConfigResourcesGPlayerDirectory()));
		
		Resource mapPlayerResource = new Resource(IResourceType.Type.SPRITE_SET, map.getFileName()+"-player", playerLocation);
		map.getResources().addResource(mapPlayerResource);
		
		
		
		
		game.getContentManager().getResourceManager().resetListings();
		game.getContentManager().getResourceManager().resetResources();
		
		
		game.getContentManager().getResourceManager().preloadResourceSet("tile_map_" +map.getFileName() +"_resources", map.getResources());

		
		
		game.getContentManager().notifyResourcesChanged();
		game.getContentManager().getSpriteManager().runClearAll();

		
		return true;
	}
	
}
