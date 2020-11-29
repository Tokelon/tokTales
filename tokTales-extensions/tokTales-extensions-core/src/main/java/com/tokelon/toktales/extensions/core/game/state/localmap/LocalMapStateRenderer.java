package com.tokelon.toktales.extensions.core.game.state.localmap;

import org.joml.Matrix4f;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.tokelon.toktales.core.content.graphics.RGBAColor;
import com.tokelon.toktales.core.game.model.ICamera;
import com.tokelon.toktales.core.game.model.map.IBlockMap;
import com.tokelon.toktales.core.game.model.map.ILevelReference;
import com.tokelon.toktales.core.game.model.map.IMapLayer;
import com.tokelon.toktales.core.render.DebugRenderingEnabled;
import com.tokelon.toktales.core.render.IMultiRenderCall;
import com.tokelon.toktales.core.render.IRenderCall;
import com.tokelon.toktales.core.render.IRenderContextManager;
import com.tokelon.toktales.core.render.IRenderToolkit;
import com.tokelon.toktales.core.render.RenderContextManager;
import com.tokelon.toktales.core.render.RenderException;
import com.tokelon.toktales.core.render.order.IRenderOrder;
import com.tokelon.toktales.core.render.order.RenderOrder;
import com.tokelon.toktales.core.render.order.RenderRunner;
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

public class LocalMapStateRenderer implements ILocalMapStateRenderer, ISurfaceManager.ISurfaceCallback {

	
	private static final String ASSERT_NOT_READY = "renderer not ready";
	
	private static final String MAP_RENDER_CALL_DESCRIPTION = "Render the local map";

	private static final double MAP_RENDER_CALL_POSITION_MAP = 0d;
	private static final double MAP_RENDER_CALL_POSITION_DEBUG = 5d;
	
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


	private final IRenderOrder renderOrder;
	private final RenderRunner renderRunner;
	
	private final MapRenderCall mapRenderCall;

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

		getContextManager().addContext(this.mapRenderer = mapRendererFactory.createForTypedGamestate(gamestate));
		getContextManager().addContext(this.playerRenderer = playerRendererFactory.createForTypedGamestate(gamestate));
		getContextManager().addContext(this.entityRenderer = entityRendererFactory.createForTypedGamestate(gamestate));
		getContextManager().addContext(this.debugRenderer = debugRendererFactory.createForTypedGamestate(gamestate));
		getContextManager().addContext(this.objectRenderer = objecRendererFactory.createForTypedGamestate(gamestate));
		
		this.defaultViewTransformer = new DefaultViewTransformer();
		this.currentViewTransformer = defaultViewTransformer;
		
		this.renderOrder = new RenderOrder();
		this.renderRunner = new RenderRunner(renderOrder);
		
		this.mapRenderCall = new MapRenderCall();
		renderOrder.getStackForLayer(IRenderOrder.LAYER_BOTTOM).addCallbackAt(MAP_RENDER_CALL_POSITION_MAP, mapRenderCall);
		renderOrder.getStackForLayer(IRenderOrder.LAYER_TOP).addCallbackAt(MAP_RENDER_CALL_POSITION_DEBUG, mapRenderCall);
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
	public void renderState() {
		if(!surfaceIsValid) {
			assert false : ASSERT_NOT_READY;
			return;
		}
		
		prepare();
		clearDraw();
		
		renderRunner.run();
	}

	
	private void prepare() {
		long gameTime = gamestate.getGame().getTimeManager().getGameTimeMillis();
		
		mapRenderer.prepareFrame(gameTime);
		playerRenderer.prepareFrame(gameTime);
		entityRenderer.prepareFrame(gameTime);
		objectRenderer.prepareFrame(gameTime);
	}

	private void clearDraw() {
		renderToolkit.clearSurface(CLEAR_COLOR);
	}

	@Override
	public IRenderCall getRenderCall(String renderName) {
		return () -> renderLayerInternal(renderName, 0d);
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
			
			mapRenderer.renderContent(layerName, options);
			playerRenderer.renderContent(layerName, options);
			entityRenderer.renderContent(layerName, options);
			objectRenderer.renderContent(layerName, options);
		}
		
		// TODO: What to do with this?
		// Just call debug and console here or?
	}
	
	
	private void renderLayerInternal(String layerName, double position) {
		if(IRenderOrder.LAYER_TOP.equals(layerName)) {
			if(debugRenderingEnabled && position == MAP_RENDER_CALL_POSITION_DEBUG) {
				debugRenderer.renderContents();
			}
		}
		else {
			IBlockMap map = gamestate.getActiveScene().getMapController().getMap();
			IMapLayer layer = map.getLayerForName(layerName);
			
			if(layer != null) {
				options.set(PlayerRenderer.OPTION_MAP_LAYER, layer);
				
				mapRenderer.renderContent(layerName, options);
				playerRenderer.renderContent(layerName, options);
				entityRenderer.renderContent(layerName, options);
				objectRenderer.renderContent(layerName, options);
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
	public IRenderOrder getRenderOrder() {
		return renderOrder;
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
	
	
	
	private class MapRenderCall implements IMultiRenderCall {
		private String layer;
		private double position;
		
		@Override
		public void render() {
			renderLayerInternal(layer, position);
		}
		
		@Override
		public String getDescription() {
			return MAP_RENDER_CALL_DESCRIPTION;
		}

		@Override
		public void updatePosition(String layer, double position) {
			this.layer = layer;
			this.position = position;
		}
	}

}
