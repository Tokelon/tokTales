package com.tokelon.toktales.extens.def.core.game.screen;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.joml.Matrix4f;

import com.tokelon.toktales.core.content.RGBAColorImpl;
import com.tokelon.toktales.core.engine.render.ISurface;
import com.tokelon.toktales.core.engine.render.ISurfaceHandler.ISurfaceCallback;
import com.tokelon.toktales.core.game.model.ICamera;
import com.tokelon.toktales.core.game.model.map.IBlockMap;
import com.tokelon.toktales.core.game.model.map.ILevelReference;
import com.tokelon.toktales.core.game.model.map.IMapLayer;
import com.tokelon.toktales.core.game.screen.order.IRenderOrder;
import com.tokelon.toktales.core.game.screen.view.DefaultViewTransformer;
import com.tokelon.toktales.core.game.screen.view.IScreenViewport;
import com.tokelon.toktales.core.game.screen.view.IViewTransformer;
import com.tokelon.toktales.core.game.states.IGameState;
import com.tokelon.toktales.core.render.IRenderToolkit;
import com.tokelon.toktales.core.render.IRenderer;
import com.tokelon.toktales.core.render.RenderException;
import com.tokelon.toktales.core.util.NamedOptionsImpl;
import com.tokelon.toktales.extens.def.core.game.states.localmap.ILocalMapStateRenderer;

public class LocalMapStateRenderer implements ILocalMapStateRenderer {

	private static final String ASSERT_NOT_READY = "renderer not ready";
	
	
	private static final String DESCRIPTION = "Renders the local map";
	
	private static final double CALLBACK_POSITION_PREPARE = -100d;
	private static final double CALLBACK_POSITION_DEBUG = 5d;
	private static final double CALLBACK_POSITION_CONSOLE = 10d;
	
	private static final RGBAColorImpl CLEAR_COLOR = RGBAColorImpl.createFromCode("#000");

	
	private final Map<String, IRenderer> managedRendererMap;

	private final MapRenderer mapRenderer;
	private final PlayerRenderer playerRenderer;
	private final EntityRenderer entityRenderer;
	private final DebugRenderer debugRenderer;
	private final ObjectRenderer objectRenderer;
	private final ConsoleOverlayRenderer consoleOverlayRenderer;
	
	private final NamedOptionsImpl options = new NamedOptionsImpl();
	
	private IRenderToolkit renderToolkit;
	
	private ICamera camera;
	private IViewTransformer defaultViewTransformer;
	
	
	private boolean debugRenderingEnabled = false;
	
	private boolean hasSurface = false;
	private boolean hasView = false;
	
	private IViewTransformer currentViewTransformer;
	private Matrix4f currentProjectionMatrix;
	private ISurface currentSurface;
	
	private final IGameState gamestate;
	
	
	public LocalMapStateRenderer(IGameState gamestate) {
		this.gamestate = gamestate;
		
		this.managedRendererMap = Collections.synchronizedMap(new HashMap<String, IRenderer>());

		this.mapRenderer = new MapRenderer(gamestate);
		this.playerRenderer = new PlayerRenderer(gamestate);
		this.entityRenderer = new EntityRenderer(gamestate);
		this.debugRenderer = new DebugRenderer(gamestate);
		this.objectRenderer = new ObjectRenderer(gamestate);
		this.consoleOverlayRenderer = new ConsoleOverlayRenderer(gamestate);
		
		this.defaultViewTransformer = new DefaultViewTransformer();
		this.currentViewTransformer = defaultViewTransformer;
		
		IRenderOrder renderOrder = gamestate.getRenderOrder();
		renderOrder.getStackForLayer(IRenderOrder.LAYER_BOTTOM).addCallbackAt(CALLBACK_POSITION_PREPARE, this);
		renderOrder.getStackForLayer(IRenderOrder.LAYER_TOP).addCallbackAt(CALLBACK_POSITION_DEBUG, this);
		renderOrder.getStackForLayer(IRenderOrder.LAYER_TOP).addCallbackAt(CALLBACK_POSITION_CONSOLE, this);
		
		gamestate.getEngine().getRenderService().getSurfaceHandler().addCallback(new SurfaceCallback());
	}
	
	
	
	@Override
	public void setDebugRendering(boolean enabled) {
		this.debugRenderingEnabled = enabled;
	}
	
	@Override
	public boolean isDebugRenderingEnabled() {
		return debugRenderingEnabled;
	}
	
	
	@Override
	public void renderCall(String layerName, double stackPosition) {
		if(!hasView) {
			assert false : ASSERT_NOT_READY;
			return;
		}
		
		if(IRenderOrder.LAYER_BOTTOM.equals(layerName) && stackPosition == CALLBACK_POSITION_PREPARE) {
			prepare();
			clearDraw();
		}
		else {
			renderLayerInternal(layerName, stackPosition);
		}
	}

	
	@Override
	public String getDescription() {
		return DESCRIPTION;
	}

	
	private void prepare() {
		long gameTime = gamestate.getGame().getTimeManager().getGameTimeMillis();
		
		mapRenderer.prepare(gameTime);
		playerRenderer.prepare(gameTime);
		entityRenderer.prepare(gameTime);
		debugRenderer.prepare(gameTime);
		objectRenderer.prepare(gameTime);
		consoleOverlayRenderer.prepare(gameTime);
	}

	private void clearDraw() {
		renderToolkit.clearSurface(CLEAR_COLOR);
	}

	
	private void renderFrame() {
		if(!hasView) {
			assert false : ASSERT_NOT_READY;
			return;
		}
		
		clearDraw();
		
		IBlockMap map = gamestate.getActiveScene().getMapController().getMap();
		ILevelReference levelRef = map.getLevelReference();
		
		for(int level = levelRef.getLowestLevel(); level <= levelRef.getHighestLevel(); level++) {
			IMapLayer layer = map.getLayerOnLevel(level);
			String layerName = layer.getName();
			
			options.set(PlayerRenderer.OPTION_MAP_LAYER, layer);
			
			mapRenderer.drawLayer(options, layerName);
			playerRenderer.drawLayer(options, layerName);
			entityRenderer.drawLayer(options, layerName);
			objectRenderer.drawLayer(options, layerName);
		}
		
		// TODO: What to do with this?
		// Just call debug and console here or?
	}
	
	
	private void renderLayerInternal(String layerName, double position) {
		if(IRenderOrder.LAYER_TOP.equals(layerName)) {
			if(debugRenderingEnabled && position == CALLBACK_POSITION_DEBUG) {
				debugRenderer.drawFull(options);
			}
			else if(position == CALLBACK_POSITION_CONSOLE) {
				consoleOverlayRenderer.drawFull(options);
			}
		}
		else {
			IBlockMap map = gamestate.getActiveScene().getMapController().getMap();
			IMapLayer layer = map.getLayerForName(layerName);
			
			if(layer != null) {
				options.set(PlayerRenderer.OPTION_MAP_LAYER, layer);
				
				mapRenderer.drawLayer(options, layerName);
				playerRenderer.drawLayer(options, layerName);
				entityRenderer.drawLayer(options, layerName);
				objectRenderer.drawLayer(options, layerName);
			}
		}
	}
	
	
	@Override
	public IConsoleOverlayRenderer getConsoleOverlayRenderer() {
		return consoleOverlayRenderer;
	}
	
	@Override
	public IDebugRenderer getDebugRenderer() {
		return debugRenderer;
	}
	
	@Override
	public IEntityRenderer getEntityRenderer() {
		return entityRenderer;
	}
	
	@Override
	public IMapRenderer getMapRenderer() {
		return mapRenderer;
	}
	
	@Override
	public IObjectRenderer getObjectRenderer() {
		return objectRenderer;
	}
	
	@Override
	public IPlayerRenderer getPlayerRenderer() {
		return playerRenderer;
	}
	
	

	@Override
	public void addManagedRenderer(String name, IRenderer renderer) {
		// what if the name is taken?
		managedRendererMap.put(name, renderer);
		
		if(hasSurface) {
			renderer.contextCreated();
		}
		
		if(hasView) {
			renderer.contextChanged(currentViewTransformer, currentProjectionMatrix);
		}
	}
	
	@Override
	public IRenderer getManagedRenderer(String name) {
		return managedRendererMap.get(name);
	}
	
	@Override
	public IRenderer removeManagedRenderer(String name) {
		IRenderer renderer = managedRendererMap.get(name);
		if(renderer != null && hasSurface) {
			renderer.contextDestroyed();
		}
		
		return managedRendererMap.remove(name);
	}
	
	@Override
	public boolean hasManagedRenderer(String name) {
		return managedRendererMap.containsKey(name);
	}
	
	
	
	@Override
	public ICamera getCamera() {
		return camera;
	}
	
	@Override
	public void updateCamera(ICamera camera) {
		// TODO: Normally we should synchronize this with rendering
		this.camera = camera;
		this.currentViewTransformer.setCamera(camera);
	}
	
	
	@Override
	public boolean hasSurface() {
		return hasSurface;
	}
	
	@Override
	public ISurface getSurface() {
		return currentSurface;
	}
	
	@Override
	public IViewTransformer getViewTransformer() {
		return currentViewTransformer;
	}
	
	
	
	/* Assumes that there is one view transformer for all renderers.
	 * Possibly refactor this.
	 * 
	 */
	protected IViewTransformer onSurfaceChangeRefreshContextViewport(ISurface surface) {
		IScreenViewport surfaceViewport = surface.getViewport();
		
		defaultViewTransformer.setViewport(surfaceViewport);

		return defaultViewTransformer;
	}
	
	
	protected class SurfaceCallback implements ISurfaceCallback {

		@Override
		public void surfaceCreated(ISurface surface) {
			hasView = false;

			mapRenderer.contextCreated();
			playerRenderer.contextCreated();
			entityRenderer.contextCreated();
			debugRenderer.contextCreated();
			objectRenderer.contextCreated();
			consoleOverlayRenderer.contextCreated();
			
			renderToolkit = gamestate.getEngine().getRenderService().getRenderAccess().requestToolkit();
			if(renderToolkit == null) {
				throw new RenderException("No render toolkit found");
			}

			synchronized (managedRendererMap) {
				for(IRenderer renderer: managedRendererMap.values()) {
					renderer.contextCreated();
				}
			}

			currentSurface = surface;
			hasSurface = true;
		}

		@Override
		public void surfaceChanged(ISurface surface) {
			currentViewTransformer = onSurfaceChangeRefreshContextViewport(surface);
			currentProjectionMatrix = surface.getProjectionMatrix();
			hasView = true;
			
			mapRenderer.contextChanged(currentViewTransformer, currentProjectionMatrix);
			playerRenderer.contextChanged(currentViewTransformer, currentProjectionMatrix);
			entityRenderer.contextChanged(currentViewTransformer, currentProjectionMatrix);
			debugRenderer.contextChanged(currentViewTransformer, currentProjectionMatrix);
			objectRenderer.contextChanged(currentViewTransformer, currentProjectionMatrix);
			consoleOverlayRenderer.contextChanged(currentViewTransformer, currentProjectionMatrix);

			// Iterate over the managed renderers
			synchronized (managedRendererMap) {
				for(IRenderer renderer: managedRendererMap.values()) {
					renderer.contextChanged(currentViewTransformer, currentProjectionMatrix);
				}
			}
		}

		@Override
		public void surfaceDestroyed(ISurface surface) {
			hasView = false;
			hasSurface = false;
			currentSurface = null;
			
			mapRenderer.contextDestroyed();
			playerRenderer.contextDestroyed();
			entityRenderer.contextDestroyed();
			debugRenderer.contextDestroyed();
			objectRenderer.contextDestroyed();
			consoleOverlayRenderer.contextDestroyed();
			
			synchronized (managedRendererMap) {
				for(IRenderer renderer: managedRendererMap.values()) {
					renderer.contextDestroyed();
				}
			}

			currentViewTransformer = defaultViewTransformer;
			currentProjectionMatrix = null;
		}
		
	}

	
}
