package com.tokelon.toktales.extensions.android.states.localmap;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.tokelon.toktales.core.config.IConfigManager;
import com.tokelon.toktales.core.config.IMainConfig;
import com.tokelon.toktales.core.engine.render.ISurface;
import com.tokelon.toktales.core.game.model.ICamera;
import com.tokelon.toktales.core.game.screen.view.AccurateViewport;
import com.tokelon.toktales.core.game.screen.view.DefaultViewTransformer;
import com.tokelon.toktales.core.game.screen.view.IScreenViewport;
import com.tokelon.toktales.core.game.screen.view.IViewTransformer;
import com.tokelon.toktales.core.render.DebugRenderingEnabled;
import com.tokelon.toktales.core.render.ITextureCoordinator;
import com.tokelon.toktales.extensions.core.game.screen.IDebugRenderer.IDebugRendererFactory;
import com.tokelon.toktales.extensions.core.game.screen.IEntityRenderer.IEntityRendererFactory;
import com.tokelon.toktales.extensions.core.game.screen.IMapRenderer.IMapRendererFactory;
import com.tokelon.toktales.extensions.core.game.screen.IObjectRenderer.IObjectRendererFactory;
import com.tokelon.toktales.extensions.core.game.screen.IPlayerRenderer.IPlayerRendererFactory;
import com.tokelon.toktales.extensions.core.game.states.localmap.ILocalMapGamestate;
import com.tokelon.toktales.extensions.core.game.states.localmap.LocalMapStateRenderer;

public class AndroidLocalMapStateRenderer extends LocalMapStateRenderer {
	// TODO: Merge with LocalMapStateRenderer if it makes sense


	private final ILocalMapGamestate gamestate;
	
	@Inject
	public AndroidLocalMapStateRenderer(
			IPlayerRendererFactory playerRendererFactory,
			IMapRendererFactory mapRendererFactory,
			IEntityRendererFactory entityRendererFactory,
			IObjectRendererFactory objecRendererFactory,
			IDebugRendererFactory debugRendererFactory,
			ITextureCoordinator textureCoordinator,
			@DebugRenderingEnabled boolean debugRenderingEnabled,
			@Assisted ILocalMapGamestate gamestate
	) {
		super(playerRendererFactory, mapRendererFactory, entityRendererFactory, objecRendererFactory, debugRendererFactory, textureCoordinator, debugRenderingEnabled, gamestate);
		
		this.gamestate = gamestate;
		

		/*
		// Get main config
		IConfigManager configManager = mGame.getConfigManager();
		IMainConfig mainConfig = (IMainConfig) configManager.getConfig(IConfigManager.MAIN_CONFIG);
		
		mainConfig.registerOnConfigChangeListener(new ConfigChangeListener());
		
		
		// Create context provider and set the context config according to the main config
		mContextProvider = new LocalMapContextProvider();
		
		LocalMapContextConfiguration contextConfig = mContextProvider.getContextConfiguration();
		contextConfig.configRenderGridCreationType = mainConfig.getConfigDisplayRenderGridCreationType();
		contextConfig.configRenderGridBlocksHorizontal = mainConfig.getConfigDisplayBlocksHorizontal();
		contextConfig.configRenderGridBlocksVertical = mainConfig.getConfigDisplayBlocksVertical();
		contextConfig.configRenderGridBlockPixelSize = mainConfig.getConfigDisplayBlockPixelSize();
		*/
	}

	
	@Override
	protected IViewTransformer onSurfaceChangeRefreshContextViewport(ISurface surface) {

		ICamera camera = getCurrentCamera();
		//IWorldGrid worldGrid = gamestate.getGame().getWorld().getGrid();
		IScreenViewport masterViewport = surface.getViewport();
		
		AccurateViewport contextViewport = new AccurateViewport();
		
		// Apply master viewport first (size and offset)
		contextViewport.applyMasterViewport(masterViewport);

		
		/* Add camera observer to context viewport ?
		 * So that when the size changed, the viewport can adapt ?
		 */

		
		
		IConfigManager configManager = gamestate.getGame().getConfigManager();
		IMainConfig mainConfig = (IMainConfig) configManager.getConfig(IConfigManager.MAIN_CONFIG);
		
		
		boolean adaptTheViewport = mainConfig.getConfigDisplayCameraCreationType() != IMainConfig.CAMERA_CREATION_TYPE_ADJUST_TO_VIEWPORT;
		boolean adaptTheCamera = true;
		
		
		/* Adapting the context viewport to the camera means
		 * 1. Applying the camera's aspect ratio and orientation to the viewport
		 * 2. Clamping the viewport to the master viewport if necessary (optional)
		 * 3. Centering the viewport in relation to the master viewport (optional)
		 * 
		 */
		if(adaptTheViewport) {
			adaptTheCamera = false;	// If we adapt the viewport we cannot adapt the camera

			
			// Change size according to camera aspect ratio
			float cameraAR = camera.getAspectRatio();
			contextViewport.setAspectRatio(cameraAR);

			if(camera.hasPortraitOrientation()) {
				contextViewport.setOrientation(IScreenViewport.ORIENTATION_PORTRAIT);
			}
			else {
				contextViewport.setOrientation(IScreenViewport.ORIENTATION_LANDSCAPE);
			}
			
			
			// Clamp while scaling to master viewport if neeeded
			contextViewport.clampscale((int) masterViewport.getWidth(), (int) masterViewport.getHeight(), false);
			// Center to master viewport if needed
			contextViewport.centerBy((int) masterViewport.getWidth(), (int) masterViewport.getHeight());
		}
		
		
		/* Adapting the camera to the context viewport means
		 * 1. Applying the viewport's orientation and aspect ration to the camera
		 * 
		 */
		if(adaptTheCamera) {
			
			float viewportAspectRatio;
			if(contextViewport.getOrientation() == IScreenViewport.ORIENTATION_LANDSCAPE) {
				viewportAspectRatio = (float) contextViewport.getWidth() / (float) contextViewport.getHeight();
				camera.setPortraitOrientation(false);
			}
			else {
				viewportAspectRatio = (float) contextViewport.getHeight() / (float) contextViewport.getWidth();
				camera.setPortraitOrientation(true);
			}
			
			camera.setAspectRatio(viewportAspectRatio);
		}
		
		
		// Debug output
		//float blockPixelSize = contextViewport.getScreenTileSize();
		//TokTales.getLog().d(TAG, "New blockPixelSize equals " +blockPixelSize);
		
		
		DefaultViewTransformer viewTransformer = new DefaultViewTransformer(camera, contextViewport);
		return viewTransformer;
	}
	

	
	/* IMapRenderer callback */
	
	/* TODO: This shouldn't be here or like in any way
	
	@Override
	public IMapPosition screenPositionForMapPosition(int screenx, int screeny) throws NoMapException {
		// Will fail if no map has been loaded or context has not been created
		
		
		// CHECK
		/*
		// Translate screen position to a render grid position
		IPoint gridPos = mContextProvider.getViewportMap().getGridPositionFromScreenPosition(screenx, screeny);
		
		
		// Translation from render grid position to map position (check if valid)
		if(gridPos != null && mContextProvider.getCameraWindow().isCameraPointInsideMap(gridPos)) {
			
			// If the screen position is on a valid map position, return the map position
			return mContextProvider.getCameraWindow().getMapPositionFromCameraPoint(gridPos);
		}
		else {
			// Else return null
			return null;
		}
		*//*
		
		return null;
	}
	*/
	
}
