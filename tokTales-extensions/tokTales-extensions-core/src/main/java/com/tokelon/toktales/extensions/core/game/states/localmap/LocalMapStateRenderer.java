package com.tokelon.toktales.extensions.core.game.states.localmap;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.joml.Matrix4f;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.tokelon.toktales.core.content.graphics.RGBAColorImpl;
import com.tokelon.toktales.core.engine.render.ISurface;
import com.tokelon.toktales.core.game.model.ICamera;
import com.tokelon.toktales.core.game.model.map.IBlockMap;
import com.tokelon.toktales.core.game.model.map.ILevelReference;
import com.tokelon.toktales.core.game.model.map.IMapLayer;
import com.tokelon.toktales.core.game.screen.order.IRenderOrder;
import com.tokelon.toktales.core.game.screen.view.DefaultViewTransformer;
import com.tokelon.toktales.core.game.screen.view.IScreenViewport;
import com.tokelon.toktales.core.game.screen.view.IViewTransformer;
import com.tokelon.toktales.core.render.DebugRenderingEnabled;
import com.tokelon.toktales.core.render.IRenderToolkit;
import com.tokelon.toktales.core.render.ITextureCoordinator;
import com.tokelon.toktales.core.render.RenderException;
import com.tokelon.toktales.core.render.renderer.IRenderer;
import com.tokelon.toktales.extensions.core.game.screen.IDebugRenderer;
import com.tokelon.toktales.extensions.core.game.screen.IEntityRenderer;
import com.tokelon.toktales.extensions.core.game.screen.IMapRenderer;
import com.tokelon.toktales.extensions.core.game.screen.IObjectRenderer;
import com.tokelon.toktales.extensions.core.game.screen.IPlayerRenderer;
import com.tokelon.toktales.extensions.core.game.screen.PlayerRenderer;
import com.tokelon.toktales.extensions.core.game.screen.IDebugRenderer.IDebugRendererFactory;
import com.tokelon.toktales.extensions.core.game.screen.IEntityRenderer.IEntityRendererFactory;
import com.tokelon.toktales.extensions.core.game.screen.IMapRenderer.IMapRendererFactory;
import com.tokelon.toktales.extensions.core.game.screen.IObjectRenderer.IObjectRendererFactory;
import com.tokelon.toktales.extensions.core.game.screen.IPlayerRenderer.IPlayerRendererFactory;
import com.tokelon.toktales.tools.core.objects.options.NamedOptionsImpl;

public class LocalMapStateRenderer implements ILocalMapStateRenderer {

	private static final String ASSERT_NOT_READY = "renderer not ready";
	
	
	private static final String DESCRIPTION = "Renders the local map";
	
	private static final double CALLBACK_POSITION_PREPARE = -100d;
	private static final double CALLBACK_POSITION_DEBUG = 5d;
	
	private static final RGBAColorImpl CLEAR_COLOR = RGBAColorImpl.createFromCode("#000");

	
	private final NamedOptionsImpl options = new NamedOptionsImpl();
	
	private IRenderToolkit renderToolkit;
	
	private IViewTransformer defaultViewTransformer;
	
	
	private boolean debugRenderingEnabled = false;
	
	private boolean hasSurface = false;
	private boolean hasView = false;
	
	
	private final Map<String, IRenderer> managedRendererMap;

	private IViewTransformer currentViewTransformer;
	private Matrix4f currentProjectionMatrix;
	private ISurface currentSurface;
	

	private final IMapRenderer mapRenderer;
	private final IPlayerRenderer playerRenderer;
	private final IEntityRenderer entityRenderer;
	private final IDebugRenderer debugRenderer;
	private final IObjectRenderer objectRenderer;
	
	private final ITextureCoordinator textureCoordinator;
	private final ILocalMapGamestate gamestate;
	
	@Inject
	public LocalMapStateRenderer(
			IPlayerRendererFactory playerRendererFactory,
			IMapRendererFactory mapRendererFactory,
			IEntityRendererFactory entityRendererFactory,
			IObjectRendererFactory objecRendererFactory,
			IDebugRendererFactory debugRendererFactory,
			ITextureCoordinator textureCoordinator,
			@DebugRenderingEnabled boolean debugRenderingEnabled,
			@Assisted ILocalMapGamestate gamestate
	) {
		this.textureCoordinator = textureCoordinator;
		this.debugRenderingEnabled = debugRenderingEnabled;
		this.gamestate = gamestate;
		
		this.managedRendererMap = Collections.synchronizedMap(new HashMap<String, IRenderer>());

		this.mapRenderer = mapRendererFactory.createForTypedGamestate(gamestate);
		this.playerRenderer = playerRendererFactory.createForTypedGamestate(gamestate);
		this.entityRenderer = entityRendererFactory.createForTypedGamestate(gamestate);
		this.debugRenderer = debugRendererFactory.createForTypedGamestate(gamestate);
		this.objectRenderer = objecRendererFactory.createForTypedGamestate(gamestate);
		
		this.defaultViewTransformer = new DefaultViewTransformer();
		this.currentViewTransformer = defaultViewTransformer;
		
		IRenderOrder renderOrder = gamestate.getRenderOrder();
		renderOrder.getStackForLayer(IRenderOrder.LAYER_BOTTOM).addCallbackAt(CALLBACK_POSITION_PREPARE, this);
		renderOrder.getStackForLayer(IRenderOrder.LAYER_TOP).addCallbackAt(CALLBACK_POSITION_DEBUG, this);
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
	}

	private void clearDraw() {
		renderToolkit.clearSurface(CLEAR_COLOR);
	}

	
	@SuppressWarnings("unused") // TODO: Old - Remove
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
	public ICamera getCurrentCamera() {
		return getViewTransformer().getCurrentCamera();
	}
	
	@Override
	public void updateCamera(ICamera camera) {
		getViewTransformer().updateCamera(camera);
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
	
	@Override
	public ITextureCoordinator getTextureCoordinator() {
		return textureCoordinator;
	}
	
	
	/* Assumes that there is one view transformer for all renderers.
	 * Possibly refactor this.
	 * 
	 */
	protected IViewTransformer onSurfaceChangeRefreshContextViewport(ISurface surface) {
		IScreenViewport surfaceViewport = surface.getViewport();
		
		defaultViewTransformer.updateViewport(surfaceViewport);

		return defaultViewTransformer;
	}
	

	@Override
	public void surfaceCreated(ISurface surface) {
		hasView = false;

		mapRenderer.contextCreated();
		playerRenderer.contextCreated();
		entityRenderer.contextCreated();
		debugRenderer.contextCreated();
		objectRenderer.contextCreated();
		
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
		
		synchronized (managedRendererMap) {
			for(IRenderer renderer: managedRendererMap.values()) {
				renderer.contextDestroyed();
			}
		}

		currentViewTransformer = defaultViewTransformer;
		currentProjectionMatrix = null;
	}
	
}
