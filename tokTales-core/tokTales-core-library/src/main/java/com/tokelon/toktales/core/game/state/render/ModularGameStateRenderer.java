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
import com.tokelon.toktales.core.render.renderer.ISingleRenderer;
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

	private final Map<String, ISingleRenderer> rendererMap = new HashMap<>();

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
	public void addRenderer(String name, ISingleRenderer renderer) {
		rendererMap.put(name, renderer);
		
		if(hasSurface) {
			renderer.contextCreated();
			
			if(surfaceIsValid) {
				IViewTransformer rendererViewTransformer = renderingStrategy.createViewTransformerForRenderer(this, currentSurface.getViewport(), getCurrentCamera(), name);
				
				renderer.contextChanged(rendererViewTransformer, currentSurface.getProjectionMatrix());
			}
		}
	}
	
	@Override
	public ISingleRenderer getRenderer(String name) {
		return rendererMap.get(name);
	}
	
	@Override
	public boolean hasRenderer(String name) {
		return rendererMap.containsKey(name);
	}
	
	@Override
	public void removeRenderer(String name) {
		rendererMap.remove(name);
	}
	
	
	@Override
	public Map<String, ISingleRenderer> getRenderers() { // Return immutable map ?
		return rendererMap;
	}
	
	
	@Override
	public IRenderCall getRenderCall(String renderName) {
		return () -> {
			ISingleRenderer renderer = rendererMap.get(renderName);
			if(renderer != null) {
				renderer.renderContents();
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


	@Override
	public void surfaceCreated(ISurface surface) {
		this.surfaceIsValid = false;
		this.currentSurface = surface;
		this.hasSurface = true;
		
		for(ISingleRenderer renderer: rendererMap.values()) {
			renderer.contextCreated();
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
		
		for(String rendererName: rendererMap.keySet()) {
			
			ISingleRenderer renderer = rendererMap.get(rendererName);
			
			
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
		
		for(ISingleRenderer renderer: rendererMap.values()) {
			renderer.contextDestroyed();
		}

		renderContextManager.contextDestroyed();
	}

	
	
	private class StrategyRenderCall implements IRenderCall {

		@Override
		public void render() {
			if(!surfaceIsValid) {
				return;
			}
			
			renderingStrategy.renderContents(ModularGameStateRenderer.this);
		}
		
		@Override
		public String getDescription() {
			return STRATEGY_RENDER_CALL_DESCRIPTION;
		}
	}
	
}
