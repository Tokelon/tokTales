package com.tokelon.toktales.core.game.state.render;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joml.Matrix4f;

import com.tokelon.toktales.core.game.model.ICamera;
import com.tokelon.toktales.core.game.state.IGameState;
import com.tokelon.toktales.core.render.order.IRenderOrder;
import com.tokelon.toktales.core.render.renderer.IRenderer;
import com.tokelon.toktales.core.render.renderer.ISegmentRenderer;
import com.tokelon.toktales.core.render.texture.DefaultTextureCoordinator;
import com.tokelon.toktales.core.render.texture.ITextureCoordinator;
import com.tokelon.toktales.core.screen.surface.ISurface;
import com.tokelon.toktales.core.screen.view.AccurateViewport;
import com.tokelon.toktales.core.screen.view.DefaultViewTransformer;
import com.tokelon.toktales.core.screen.view.IScreenViewport;
import com.tokelon.toktales.core.screen.view.IViewTransformer;

public class ModularGameStateRenderer implements IModularGameStateRenderer {

	
	private static final double CALLBACK_RENDER = 0d;

	private final AccurateViewport contextViewport = new AccurateViewport();
	private final Matrix4f contextProjectionMatrix = new Matrix4f();
	private final IViewTransformer contextViewTransformer;

	private final Map<String, ISegmentRenderer> segmentRendererMap = new HashMap<>();
	private final Map<String, IRenderer> managedRendererMap = new HashMap<>();

	private final List<IViewTransformer> viewTransformerList = new ArrayList<>();
	
	
	private boolean hasSurface = false;
	private boolean hasView = false;
	
	private ISurface currentSurface;
	
	
	private final ITextureCoordinator textureCoordinator;
	private final IRenderingStrategy mStrategy;
	private final IGameState mGamestate;
	
	public ModularGameStateRenderer(IRenderingStrategy strategy, IGameState gamestate) {
		this.mStrategy = strategy;
		this.mGamestate = gamestate;

		this.contextViewTransformer = new DefaultViewTransformer();
		this.textureCoordinator = new DefaultTextureCoordinator(gamestate.getGame().getContentManager().getTextureManager());

		IRenderOrder renderOrder = gamestate.getRenderOrder();
		renderOrder.getStackForLayer(IRenderOrder.LAYER_BOTTOM).addCallbackAt(CALLBACK_RENDER, this);
	}
	
	
	
	@Override
	public void addSegmentRenderer(String name, ISegmentRenderer renderer) {
		segmentRendererMap.put(name, renderer);
		
		if(hasSurface) {
			renderer.contextCreated();
			
			if(hasView) {
				IViewTransformer rendererViewTransformer = mStrategy.createViewTransformerForRenderer(this, currentSurface.getViewport(), getCurrentCamera(), name);
				
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
		return mGamestate;
	}
	
	
	
	@Override
	public void render() {
		if(!hasView) {
			return;
		}
		
		//if(stackPosition == CALLBACK_RENDER)
		
		mStrategy.prepareFrame(this);
		mStrategy.renderFrame(this);
		//mStrategy.renderCall(this, layerName, stackPosition);
	}
	
	@Override
	public String getDescription() {
		return mStrategy.getDescription();
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
	public void addManagedRenderer(String name, IRenderer renderer) {
		managedRendererMap.put(name, renderer);
		
		if(hasSurface) {
			renderer.contextCreated();
			
			if(hasView) {
				renderer.contextChanged(contextViewTransformer, contextProjectionMatrix);
			}
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
	
	

	// synchronize when iterating over renderers?
	
	@Override
	public void surfaceCreated(ISurface surface) {
		currentSurface = surface;
		hasSurface = true;
		
		for(ISegmentRenderer segmentRenderer: segmentRendererMap.values()) {
			segmentRenderer.contextCreated();
		}
		
		for(IRenderer managedRenderer: managedRendererMap.values()) {
			managedRenderer.contextCreated();
		}
	}

	@Override
	public void surfaceChanged(ISurface surface) {
		IScreenViewport masterViewport = surface.getViewport();

		contextViewport.setSize(masterViewport.getWidth(), masterViewport.getHeight());
		contextViewport.setOffset(masterViewport.getHorizontalOffset(), masterViewport.getVerticalOffset());
		
		contextViewTransformer.updateViewport(contextViewport);

		contextProjectionMatrix.set(surface.getProjectionMatrix());

		// Really do this before renderer callbacks?
		hasView = true;

		
		viewTransformerList.clear();
		
		for(String rendererName: segmentRendererMap.keySet()) {
			
			ISegmentRenderer renderer = segmentRendererMap.get(rendererName);
			
			
			IViewTransformer rendererViewTransformer = mStrategy.createViewTransformerForRenderer(ModularGameStateRenderer.this, masterViewport, getCurrentCamera(), rendererName);
			viewTransformerList.add(rendererViewTransformer);
			
			renderer.contextChanged(rendererViewTransformer, contextProjectionMatrix);
		}
		
		for(IRenderer managedRenderer: managedRendererMap.values()) {
			managedRenderer.contextChanged(contextViewTransformer, contextProjectionMatrix);
		}
		

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
		currentSurface = null;
		hasSurface = false;
		hasView = false;
		
		for(ISegmentRenderer segmentRenderer: segmentRendererMap.values()) {
			segmentRenderer.contextDestroyed();
		}
		
		for(IRenderer managedRenderer: managedRendererMap.values()) {
			managedRenderer.contextDestroyed();
		}
	}
	
}
