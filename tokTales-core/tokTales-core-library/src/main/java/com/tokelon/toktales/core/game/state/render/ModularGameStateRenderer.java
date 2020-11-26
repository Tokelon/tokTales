package com.tokelon.toktales.core.game.state.render;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joml.Matrix4f;

import com.tokelon.toktales.core.game.model.ICamera;
import com.tokelon.toktales.core.game.state.IGameState;
import com.tokelon.toktales.core.render.IRenderCall;
import com.tokelon.toktales.core.render.IRenderContextManager;
import com.tokelon.toktales.core.render.RenderContextManager;
import com.tokelon.toktales.core.render.order.IRenderOrder;
import com.tokelon.toktales.core.render.order.RenderOrder;
import com.tokelon.toktales.core.render.order.RenderRunner;
import com.tokelon.toktales.core.render.renderer.ISegmentRenderer;
import com.tokelon.toktales.core.render.texture.DefaultTextureCoordinator;
import com.tokelon.toktales.core.render.texture.ITextureCoordinator;
import com.tokelon.toktales.core.screen.surface.ISurface;
import com.tokelon.toktales.core.screen.surface.ISurfaceManager;
import com.tokelon.toktales.core.screen.view.AccurateViewport;
import com.tokelon.toktales.core.screen.view.DefaultViewTransformer;
import com.tokelon.toktales.core.screen.view.IScreenViewport;
import com.tokelon.toktales.core.screen.view.IViewTransformer;

public class ModularGameStateRenderer implements IModularGameStateRenderer, ISurfaceManager.ISurfaceCallback {

	
	private static final String STRATEGY_RENDER_CALL_DESCRIPTION = "Renders the strategy";
	private static final double STRATEGY_RENDER_CALL_POSITION = 0d;

	private final AccurateViewport contextViewport = new AccurateViewport();
	private final Matrix4f contextProjectionMatrix = new Matrix4f();
	private final IViewTransformer contextViewTransformer;

	private final Map<String, ISegmentRenderer> segmentRendererMap = new HashMap<>();

	private final List<IViewTransformer> viewTransformerList = new ArrayList<>();
	
	
	private boolean hasSurface = false;
	private boolean surfaceIsValid = false;
	
	private ISurface currentSurface;


	private final IRenderOrder renderOrder;
	private final RenderRunner renderRunner;

	private final StrategyRenderCall strategyRenderCall;

	private final IRenderContextManager renderContextManager;
	private final ITextureCoordinator textureCoordinator;
	private final IRenderingStrategy renderingStrategy;
	private final IGameState gamestate;
	
	public ModularGameStateRenderer(IRenderingStrategy strategy, IGameState gamestate) {
		this.renderingStrategy = strategy;
		this.gamestate = gamestate;

		this.renderContextManager = new RenderContextManager();
		this.contextViewTransformer = new DefaultViewTransformer();
		this.textureCoordinator = new DefaultTextureCoordinator(gamestate.getGame().getContentManager().getTextureManager());

		this.renderOrder = new RenderOrder();
		this.renderRunner = new RenderRunner(renderOrder);
		
		this.strategyRenderCall = new StrategyRenderCall();
		renderOrder.getStackForLayer(IRenderOrder.LAYER_BOTTOM).addCallbackAt(STRATEGY_RENDER_CALL_POSITION, strategyRenderCall);
	}
	
	
	@Override
	public void addSegmentRenderer(String name, ISegmentRenderer renderer) {
		segmentRendererMap.put(name, renderer);
		
		if(hasSurface) {
			renderer.contextCreated();
			
			if(surfaceIsValid) {
				IViewTransformer rendererViewTransformer = renderingStrategy.createViewTransformerForRenderer(this, currentSurface.getViewport(), getCurrentCamera(), name);
				
				renderer.contextChanged(rendererViewTransformer, currentSurface.getProjectionMatrix());
			}
		}
	}
	
	@Override
	public ISegmentRenderer getSegmentRenderer(String name) {
		return segmentRendererMap.get(name);
	}
	
	@Override
	public boolean hasSegmentRenderer(String name) {
		return segmentRendererMap.containsKey(name);
	}
	
	@Override
	public void removeSegmentRenderer(String name) {
		segmentRendererMap.remove(name);
	}
	
	
	@Override
	public Map<String, ISegmentRenderer> getRenderers() {	// Return immutable map ?
		return segmentRendererMap;
	}
	
	
	@Override
	public IGameState getGamestate() {
		return gamestate;
	}
	
	
	@Override
	public IRenderCall getRenderCall(String renderName) {
		return () -> {
			ISegmentRenderer renderer = segmentRendererMap.get(renderName);
			if(renderer != null) {
				renderer.prepare(gamestate.getGame().getTimeManager().getGameTimeMillis());
				renderer.drawFull(null);
			}
		};
	}
	
	@Override
	public void renderState() {
		renderRunner.run();
	}
	
	
	@Override
	public void updateCamera(ICamera camera) {
		getViewTransformer().updateCamera(camera);
		
		for(IViewTransformer viewTransformer: viewTransformerList) {
			viewTransformer.updateCamera(camera);
		}
	}
	
	@Override
	public ICamera getCurrentCamera() {
		return getViewTransformer().getCurrentCamera();
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
		return contextViewTransformer;
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


	// synchronize when iterating over renderers?
	
	@Override
	public void surfaceCreated(ISurface surface) {
		this.surfaceIsValid = false;
		this.currentSurface = surface;
		this.hasSurface = true;
		
		for(ISegmentRenderer segmentRenderer: segmentRendererMap.values()) {
			segmentRenderer.contextCreated();
		}

		renderContextManager.contextCreated();
	}

	@Override
	public void surfaceChanged(ISurface surface) {
		IScreenViewport masterViewport = surface.getViewport();

		contextViewport.setSize(masterViewport.getWidth(), masterViewport.getHeight());
		contextViewport.setOffset(masterViewport.getHorizontalOffset(), masterViewport.getVerticalOffset());
		
		contextViewTransformer.updateViewport(contextViewport);

		contextProjectionMatrix.set(surface.getProjectionMatrix());


		this.surfaceIsValid = true;

		viewTransformerList.clear();
		
		for(String rendererName: segmentRendererMap.keySet()) {
			
			ISegmentRenderer renderer = segmentRendererMap.get(rendererName);
			
			
			IViewTransformer rendererViewTransformer = renderingStrategy.createViewTransformerForRenderer(ModularGameStateRenderer.this, masterViewport, getCurrentCamera(), rendererName);
			viewTransformerList.add(rendererViewTransformer);
			
			renderer.contextChanged(rendererViewTransformer, contextProjectionMatrix);
		}

		renderContextManager.contextChanged(contextViewTransformer, contextProjectionMatrix);


		/* Implement camera adjust ?
		 * 
		ICamera gamestateCamera = mGamestate.getCamera();
		
		int orientation = masterViewport.getOrientation();
		if(orientation == IViewport.ORIENTATION_LANDSCAPE) {
			gamestateCamera.setPortraitOrientation(false);
		}
		else if(orientation == IViewport.ORIENTATION_PORTRAIT) {
			gamestateCamera.setPortraitOrientation(true);
		}
		*/
	}

	@Override
	public void surfaceDestroyed(ISurface surface) {
		this.currentSurface = null;
		this.hasSurface = false;
		this.surfaceIsValid = false;
		
		for(ISegmentRenderer segmentRenderer: segmentRendererMap.values()) {
			segmentRenderer.contextDestroyed();
		}

		renderContextManager.contextDestroyed();
	}

	
	
	private class StrategyRenderCall implements IRenderCall {

		@Override
		public void render() {
			if(!surfaceIsValid) {
				return;
			}
			
			//if(stackPosition == CALLBACK_RENDER)
			
			renderingStrategy.prepareFrame(ModularGameStateRenderer.this);
			renderingStrategy.renderFrame(ModularGameStateRenderer.this);
			//mStrategy.renderCall(this, layerName, stackPosition);			
		}
		
		@Override
		public String getDescription() {
			return STRATEGY_RENDER_CALL_DESCRIPTION;
		}
	}
	
}
