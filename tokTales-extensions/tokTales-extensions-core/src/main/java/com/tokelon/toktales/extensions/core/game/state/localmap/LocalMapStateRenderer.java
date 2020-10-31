package com.tokelon.toktales.extensions.core.game.state.localmap;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.tokelon.toktales.core.content.graphics.RGBAColor;
import com.tokelon.toktales.core.game.model.ICamera;
import com.tokelon.toktales.core.game.model.map.IBlockMap;
import com.tokelon.toktales.core.game.model.map.ILevelReference;
import com.tokelon.toktales.core.game.model.map.IMapLayer;
import com.tokelon.toktales.core.render.DebugRenderingEnabled;
import com.tokelon.toktales.core.render.IMultiRenderCall;
import com.tokelon.toktales.core.render.IRenderContextManager;
import com.tokelon.toktales.core.render.IRenderToolkit;
import com.tokelon.toktales.core.render.RenderContextManager;
import com.tokelon.toktales.core.render.RenderException;
import com.tokelon.toktales.core.render.order.IRenderOrder;
import com.tokelon.toktales.core.render.texture.ITextureCoordinator;
import com.tokelon.toktales.core.screen.surface.ISurface;
import com.tokelon.toktales.core.screen.surface.ISurfaceManager;
import com.tokelon.toktales.core.screen.view.DefaultViewTransformer;
import com.tokelon.toktales.core.screen.view.IScreenViewport;
import com.tokelon.toktales.core.screen.view.IViewTransformer;
import com.tokelon.toktales.extensions.core.game.renderer.IDebugRenderer;
import com.tokelon.toktales.extensions.core.game.renderer.IDebugRenderer.IDebugRendererFactory;
import com.tokelon.toktales.extensions.core.game.renderer.IEntityRenderer;
import com.tokelon.toktales.extensions.core.game.renderer.IEntityRenderer.IEntityRendererFactory;
import com.tokelon.toktales.extensions.core.game.renderer.IMapRenderer;
import com.tokelon.toktales.extensions.core.game.renderer.IMapRenderer.IMapRendererFactory;
import com.tokelon.toktales.extensions.core.game.renderer.IObjectRenderer;
import com.tokelon.toktales.extensions.core.game.renderer.IObjectRenderer.IObjectRendererFactory;
import com.tokelon.toktales.extensions.core.game.renderer.IPlayerRenderer;
import com.tokelon.toktales.extensions.core.game.renderer.IPlayerRenderer.IPlayerRendererFactory;
import com.tokelon.toktales.extensions.core.game.renderer.PlayerRenderer;
import com.tokelon.toktales.tools.core.objects.options.NamedOptionsImpl;

import org.joml.Matrix4f;

public class LocalMapStateRenderer implements ILocalMapStateRenderer, IMultiRenderCall, ISurfaceManager.ISurfaceCallback {

	private static final String ASSERT_NOT_READY = "renderer not ready";
	
	
	private static final String DESCRIPTION = "Renders the local map";
	
	private static final double CALLBACK_POSITION_PREPARE = -100d;
	private static final double CALLBACK_POSITION_DEBUG = 5d;
	
	private static final RGBAColor CLEAR_COLOR = RGBAColor.createFromCode("#000");

	
	private final NamedOptionsImpl options = new NamedOptionsImpl();
	
	private IRenderToolkit renderToolkit;
	
	private IViewTransformer defaultViewTransformer;
	
	
	private boolean debugRenderingEnabled = false;
	
	private boolean hasSurface = false;
	private boolean surfaceIsValid = false;
	
	
	private final IRenderContextManager renderContextManager;

	private IViewTransformer currentViewTransformer;
	private Matrix4f currentProjectionMatrix;
	private ISurface currentSurface;

	private String layer;
	private double position;


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
		
		this.renderContextManager = new RenderContextManager();

		getContextManager().addManagedRenderer(this.mapRenderer = mapRendererFactory.createForTypedGamestate(gamestate));
		getContextManager().addManagedRenderer(this.playerRenderer = playerRendererFactory.createForTypedGamestate(gamestate));
		getContextManager().addManagedRenderer(this.entityRenderer = entityRendererFactory.createForTypedGamestate(gamestate));
		getContextManager().addManagedRenderer(this.debugRenderer = debugRendererFactory.createForTypedGamestate(gamestate));
		getContextManager().addManagedRenderer(this.objectRenderer = objecRendererFactory.createForTypedGamestate(gamestate));
		
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
	public void updatePosition(String layer, double position) {
		this.layer = layer;
		this.position = position;
	}

	@Override
	public void render() {
		if(!surfaceIsValid) {
			assert false : ASSERT_NOT_READY;
			return;
		}
		
		if(IRenderOrder.LAYER_BOTTOM.equals(layer) && position == CALLBACK_POSITION_PREPARE) {
			prepare();
			clearDraw();
		}
		else {
			renderLayerInternal(layer, position);
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
		if(!surfaceIsValid) {
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

	@Override
	public IRenderContextManager getContextManager() {
		return renderContextManager;
	}

	@Override
	public ISurfaceManager.ISurfaceCallback getSurfaceCallback() {
		return this;
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
		this.surfaceIsValid = false;

		renderToolkit = gamestate.getEngine().getRenderService().getRenderAccess().requestToolkit();
		if(renderToolkit == null) {
			throw new RenderException("No render toolkit found");
		}

		this.currentSurface = surface;
		this.hasSurface = true;

		renderContextManager.contextCreated();
	}

	@Override
	public void surfaceChanged(ISurface surface) {
		this.currentViewTransformer = onSurfaceChangeRefreshContextViewport(surface);
		this.currentProjectionMatrix = surface.getProjectionMatrix();
		this.surfaceIsValid = true;

		renderContextManager.contextChanged(currentViewTransformer, currentProjectionMatrix);
	}

	@Override
	public void surfaceDestroyed(ISurface surface) {
		this.surfaceIsValid = false;
		this.hasSurface = false;
		this.currentSurface = null;

		renderContextManager.contextDestroyed();

		this.currentViewTransformer = defaultViewTransformer;
		this.currentProjectionMatrix = null;
	}

}
