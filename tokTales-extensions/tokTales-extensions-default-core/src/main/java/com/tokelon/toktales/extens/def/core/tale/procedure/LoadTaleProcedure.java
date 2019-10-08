package com.tokelon.toktales.extens.def.core.tale.procedure;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.inject.Inject;

import com.google.inject.Injector;
import com.tokelon.toktales.core.config.CiniMainConfig;
import com.tokelon.toktales.core.config.IConfigManager;
import com.tokelon.toktales.core.config.IMainConfig;
import com.tokelon.toktales.core.content.sprite.SpriteImpl;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.engine.log.ILogging;
import com.tokelon.toktales.core.engine.storage.IStorageService;
import com.tokelon.toktales.core.engine.storage.StorageException;
import com.tokelon.toktales.core.game.IGame;
import com.tokelon.toktales.core.game.controller.IPlayerController;
import com.tokelon.toktales.core.game.graphic.SpriteGraphicImpl;
import com.tokelon.toktales.core.game.graphic.animation.GameAnimation;
import com.tokelon.toktales.core.game.graphic.animation.IGameAnimation;
import com.tokelon.toktales.core.game.logic.map.MapLoaderException;
import com.tokelon.toktales.core.game.model.IActor;
import com.tokelon.toktales.core.game.model.IPlayer;
import com.tokelon.toktales.core.game.model.map.IBlockMap;
import com.tokelon.toktales.core.game.world.ICrossDirection;
import com.tokelon.toktales.core.game.world.IWorld;
import com.tokelon.toktales.core.resources.IResourceType;
import com.tokelon.toktales.core.resources.Resource;
import com.tokelon.toktales.core.script.StorageLocationResourceFinder;
import com.tokelon.toktales.core.storage.LocationPrefix;
import com.tokelon.toktales.core.storage.utils.LocationImpl;
import com.tokelon.toktales.core.storage.utils.StructuredLocation;
import com.tokelon.toktales.extens.def.core.tale.ITaleScriptModule;
import com.tokelon.toktales.extens.def.core.tale.TaleException;
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

public class LoadTaleProcedure implements ILoadTaleProcedure {
	// TODO: Refactor internal structure and error handling
	// TODO: Divide further into procedures


	private static final String TALE_MAIN_FILE_ENDING = ".tok";
	private static final String TALE_CONFIG_FILE_ENDING = ".conf";
	private static final String TALE_SCRIPT_FILE_ENDING = ".lua";
	private static final String TALE_SCRIPTS_DIRECTORY = "scripts";
	
	private static final String TALE_SCENE_CLASS_POSTFIX = "TaleGamescene";
	
	
	
	private final ILogger logger;
	private final ILogging logging;
	private final IStorageService storageService;
	private final Injector injector;
	private final ISetMapTaleProcedure setMapProcedure;
	
	@Inject
	public LoadTaleProcedure(ILogging logging, IStorageService storageService, Injector injector, ISetMapTaleProcedure setMapProcedure) {
		this.logger = logging.getLogger(getClass());
		this.logging = logging;
		this.storageService = storageService;
		this.injector = injector;
		this.setMapProcedure = setMapProcedure;
	}

	
	@Override
	public ITaleGamescene run(IGame game, String taleAppPath) throws TaleException {
		// Root directory of the Tale
		LocationImpl taleLocation = new LocationImpl(taleAppPath);

		
		
		// List the files in the Tale directory
		String[] fileNames;
		try {
			fileNames = storageService.listAppDirOnExternal(taleLocation);
		} catch (StorageException e) {
			logger.error("List Tale directory failed:", e);
			throw new TaleException(e);
		}
		
		
		// Search for main Tale file (ends with .tok)
		String mainFileName = null;
		for(String fName: fileNames) {
			if(fName.endsWith(TALE_MAIN_FILE_ENDING)) {
				mainFileName = fName;
			}
		}
		
		
		if(mainFileName == null) {
			logger.error("No main Tale file found with ending: {}", TALE_MAIN_FILE_ENDING);
			throw new TaleException("No main Tale file found with ending: " + TALE_MAIN_FILE_ENDING);
		}
		

		
		
		// Name for tale config file that might exist
		String configFileName = mainFileName.substring(0, mainFileName.length() - TALE_MAIN_FILE_ENDING.length()).concat(TALE_CONFIG_FILE_ENDING);

		// Check if there is a config file
		InputStream configFileIn = storageService.tryReadAppFileOnExternal(taleLocation, configFileName);
		
		if(configFileIn != null) {
			logger.info("Tale main config file found: {}", configFileName);
			
			
			CiniConfigStreamReader reader = new CiniConfigStreamReader();
			
			IMainConfig mainConfig = null;
			try {
				MutableCiniConfig ciniConfig = reader.readConfig(configFileIn);
				
				
				logger.info("Checking config...");

				mainConfig = new CiniMainConfig(ciniConfig);
				
				logger.info("Config was loaded.");
			} catch (ConfigFormatException cfe) {
				logger.warn("Bad config file:", cfe);
			} catch (ConfigDataException cde) {
				logger.warn("Unsupported config:", cde);
			}
			finally {
				try {
					configFileIn.close();
				} catch (IOException e) { /* Nothing to see here... */ }
			}
			
			if(mainConfig == null) {
				logger.info("No main config was loaded.");
			}
			else {
				// TODO: 096 Load a tale specific config and not the main config
				
				game.getConfigManager().loadConfig(IConfigManager.MAIN_CONFIG, mainConfig); // We have to load the main config first so it is available TODO: Fix (If tale loading fails, main config will be wrong)
			}
		}
		
		
		
		// Load tale
		ITaleGamescene taleScene = null; // TODO: If taleScene is null, the rest should probably not execute
		try {
			taleScene = loadTale(game, taleLocation, mainFileName);
		} catch (TaleException te) {
			logger.error("Loading Tale failed. Tale error:", te);
		} catch (StorageException se) {
			logger.error("Loading Tale failed. IO error:", se);
		} catch (ConfigFormatException cfe) {
			logger.error("Loading Tale failed. Config format error:", cfe);
		} catch (ConfigDataException cde) {
			logger.error("Loading Tale failed. Config data error:", cde);
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
			logger.info("Tale main script file found: {}", configFileName);
			
			IScriptModule taleModule;
			try {
				taleModule = game.getScriptManager().loadModule(scriptFileIn, scriptFileName);
				taleModule.callFunction(ITaleScriptModule.FUNCTION_onTaleCreate, taleScene);
				
				//createScriptActor(); //testing
			} catch (ScriptErrorException e) {
				logger.warn("Script failed to load:", e);
			}
		}
		
		
		logger.debug("Reading Tale finished");
		return taleScene;
	}

	
	
	private ITaleGamescene loadTale(IGame game, LocationImpl taleLocation, String mainFileName) throws TaleException, StorageException, ConfigFormatException, ConfigDataException {
		logger.debug("Reading Tale: Started");
		
		
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
			sceneResult = injector.getInstance(ITaleGamescene.class);
			logger.info("Loaded default scene implementation");
		}
		else {
		    String sceneClassName = initialSceneCodename + TALE_SCENE_CLASS_POSTFIX;

		    try {
		        Class<?> sceneClass = Class.forName(sceneClassName);
		        logger.info("Loading initial scene with class: {}", sceneClass.getName());
		        
		        if(ITaleGamescene.class.isAssignableFrom(sceneClass)) {
		        	try {
		        		sceneResult = (ITaleGamescene) injector.getInstance(sceneClass);
		        		logger.info("Loaded scene with implementation: {}", sceneResult.getClass().getName());
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
			logger.error("Loading Tale failed: No initial map");
			return null;
		}
		

		logger.debug("Loading initial map...");
		// Read the initial map
		IBlockMap initialMap = readTiledMap(game.getWorld(), mapsLocation, initialMapfileName);
		
		if(initialMap == null) {
			logger.error("Loading Tale failed: Could not read initial map");
			return null;
		}
		else {
			// Map was read fine
			
			// Load the map into our map manager
			boolean res = loadMapIntoGame(game, initialMap, taleConfig, taleLocation, sceneResult);
			if(!res) {
				logger.error("Loading Tale failed: Could not load map into game");
				return null;
			}
		}

		
		// Load player sprites and animations
		setupPlayerEntity(game.getWorld(), taleLocation, taleConfig, sceneResult);

		return sceneResult;
	}
	
	
	private void setupPlayerEntity(IWorld world, LocationImpl taleLocation, ITaleConfig taleConfig, ITaleGamescene taleScene) {
		IPlayerController playerController = taleScene.getPlayerController();
		IPlayer player = playerController.getPlayer();
		IActor playerActor = player.getActor();
		
		
		// Values
		
		//float tileSize = IWorld.GRID_TILE_SIZE;
		//float speed = tileSize * (playerMoveDuration / 1000.0f);
		
		
		float playerWalkSpeedUnits = taleConfig.getConfigPlayerWalkSpeedUnits();
	
		playerActor.setSpeedX(playerWalkSpeedUnits);
		playerActor.setSpeedY(playerWalkSpeedUnits);

		
		
		// Animations
		LocationImpl animationsLocation = new LocationImpl(taleLocation.getLocationPath().getPathAppendedBy(taleConfig.getConfigResourcesAnimationDirectory()));
		
		CiniConfigStreamReader ciniReader = new CiniConfigStreamReader();

		String animFilename;
		
		
	
		// Walk left
		animFilename = taleConfig.getConfigPlayerAnimationWalkLeft();
		loadAnimationIntoActor(world, animationsLocation, animFilename, ciniReader, IPlayer.ANIMATION_WALK_LEFT, playerActor);
		
		// Walk Up
		animFilename = taleConfig.getConfigPlayerAnimationWalkUp();
		loadAnimationIntoActor(world, animationsLocation, animFilename, ciniReader, IPlayer.ANIMATION_WALK_UP, playerActor);

		// Walk right
		animFilename = taleConfig.getConfigPlayerAnimationWalkRight();
		loadAnimationIntoActor(world, animationsLocation, animFilename, ciniReader, IPlayer.ANIMATION_WALK_RIGHT, playerActor);

		// Walk down
		animFilename = taleConfig.getConfigPlayerAnimationWalkDown();
		loadAnimationIntoActor(world, animationsLocation, animFilename, ciniReader, IPlayer.ANIMATION_WALK_DOWN, playerActor);

		

		
		// Idle graphics
		String graphicName;
		
		
		// Idle left
		graphicName = taleConfig.getConfigPlayerGraphicIdleLeft();
		playerActor.getGraphicsImage().assignGraphic(IPlayer.GRAPHIC_IDLE_LEFT, new SpriteGraphicImpl(new SpriteImpl(graphicName)));

		// Idle up
		graphicName = taleConfig.getConfigPlayerGraphicIdleUp();
		playerActor.getGraphicsImage().assignGraphic(IPlayer.GRAPHIC_IDLE_UP, new SpriteGraphicImpl(new SpriteImpl(graphicName)));

		// Idle right
		graphicName = taleConfig.getConfigPlayerGraphicIdleRight();
		playerActor.getGraphicsImage().assignGraphic(IPlayer.GRAPHIC_IDLE_RIGHT, new SpriteGraphicImpl(new SpriteImpl(graphicName)));

		// Idle down
		graphicName = taleConfig.getConfigPlayerGraphicIdleDown();
		playerActor.getGraphicsImage().assignGraphic(IPlayer.GRAPHIC_IDLE_DOWN, new SpriteGraphicImpl(new SpriteImpl(graphicName)));
		
		
		
		playerController.playerLook(ICrossDirection.DOWN);
	}
	
	private void loadAnimationIntoActor(IWorld world, LocationImpl animLocation, String animFilename, CiniConfigStreamReader ciniReader, String animCode, IActor actor) {
		/* Default for if walk animations do not define their time
		 * Do either this or set default animation time to 1
		 */
		//int aniTime = playerEntity.getWalkOneBlockDuration();
		//int aniTime = taleConfig.getConfigPlayerMoveOneBlockDuration();
		// TODO: What to do here?
		//int aniTime = 1;
		float tileSize = world.getGridTileSize();
		int aniTime = (int) (1000.0f * tileSize / actor.getSpeedX());

		
		
		
		if(animFilename.trim().isEmpty()) {
			logger.warn("No animation file for code: {}", animCode);
			
			actor.getGraphicsImage().assignAnimation(animCode, null);
			return;
		}
		
		
		InputStream animIn = storageService.tryReadAppFileOnExternal(animLocation, animFilename);
		if(animIn == null) {
			logger.warn("Failed to read animation file: {} at ({})", animFilename, animLocation.getLocationPath().getPath());
			
			actor.getGraphicsImage().assignAnimation(animCode, null);
			return;
		}
		
		
		IGameAnimation animation = loadAnimation(animIn, ciniReader, aniTime);
		if(animation == null) {
			logger.warn("Failed to load animation file: {} at ({})", animFilename, animLocation.getLocationPath().getPath());

			actor.getGraphicsImage().assignAnimation(animCode, null);
			return;
		}

		actor.getGraphicsImage().assignAnimation(animCode, animation);
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
			logger.warn("Invalid format:", cfe);
		} catch (ConfigDataException cde) {
			logger.warn("Invalid data:", cde);
		}


		// Error
		return null;
	}
	
	
	private IBlockMap readTiledMap(IWorld world, LocationImpl location, String fileName) {
		// Tiled Map loader
		StorageTiledMapLoaderAuto loader = new StorageTiledMapLoaderAuto(logging, storageService, world);
		
		
		try {
			loader.setTarget(location, fileName);
			
		} catch (StorageException se) {
			logger.error("StorageException while configuring loader:", se);
			return null;
		}
		
		
		
		logger.debug("Reading Tiled map: Started");

		try {
			loader.runComplete();
			
			// If the map loads fine, return it
			return loader.getLoadedMap();
			
		} catch (MapFormatException mfe) {
			logger.error("MapFormatException at loading map:", mfe);
		} catch (IOException ioe) {
			logger.error("IOException at loading map:", ioe);
		} catch (MapLoaderException mle) {
			logger.error("MapLoaderException at loading map:", mle);
		}
		
		return null;
	}
	
	

	private boolean loadMapIntoGame(IGame game, IBlockMap map, ITaleConfig taleConfig, LocationImpl taleLocation, ITaleGamescene taleScene) {
		try {
			taleScene.runSetMap(setMapProcedure.toSupplier(game, map));
		} catch (TaleException te) {
			logger.error("Invalid map configuration while loading into tale:", te);
			return false;
		} catch (Exception re) {
			// pass exception?
			logger.error("Loading map into tale failed:", re);
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

		
		game.getContentManager().getSpriteAssetManager().getStore().clear();

		
		return true;
	}
	
	
	/*
	private void createScriptActor(ITaleGamescene taleScene, IScriptModule taleModule, IApplicationLocation taleLocation) {
		Object result = taleModule.callFunction("createActor", "Actor_Jack");
		if(!(result instanceof IActor)) {
			logger.warn("Return type for module function createActor does not match the expected type (IActor)");
		}
		else {
			IActor jack = (IActor) result;
			taleScene.getWorldspace().putEntity("actor_jack", jack);
			taleScene.setPlayerController(new PlayerController(jack));
			taleScene.getCameraController().enableCameraFollow(jack);
			
			
			String gravityFileName = "Gravity.lua";
			InputStream gravityFileIn = engine.getStorageService().tryReadAppFileOnExternal(taleLocation, gravityFileName);
			if(gravityFileIn != null) {
				logger.info("Gravity module file found");
				IScriptModule gravityModule = game.getScriptManager().loadModule(gravityFileIn, gravityFileName);
				
				Object participantResult = gravityModule.callFunction("createGravityParticipant", null);
				if(participantResult instanceof IGameEntityParticipant) {
					IGameEntityParticipant participant = (IGameEntityParticipant) participantResult;
					jack.getParticipation().addParticipant(participant);
				}
				else {
					logger.warn("Return type for module function createGravityParticipant does not match the expected type (IGameEntityParticipant)");
				}
			}
		}
	}
	*/
	
}
