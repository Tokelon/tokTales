package com.tokelon.toktales.core.game.screen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joml.Matrix4f;

import com.tokelon.toktales.core.engine.render.ISurface;
import com.tokelon.toktales.core.game.model.ICamera;
import com.tokelon.toktales.core.game.screen.order.IRenderOrder;
import com.tokelon.toktales.core.game.screen.view.AccurateViewport;
import com.tokelon.toktales.core.game.screen.view.DefaultViewTransformer;
import com.tokelon.toktales.core.game.screen.view.IScreenViewport;
import com.tokelon.toktales.core.game.screen.view.IViewTransformer;
import com.tokelon.toktales.core.game.states.IGameState;
import com.tokelon.toktales.core.render.DefaultTextureCoordinator;
import com.tokelon.toktales.core.render.IRenderer;
import com.tokelon.toktales.core.render.ITextureCoordinator;

public class DefaultModularStateRender implements IModularStateRender {

	
	private static final double CALLBACK_RENDER = 0d;

	private final AccurateViewport contextViewport = new AccurateViewport();
	private final Matrix4f contextProjectionMatrix = new Matrix4f();
	private final IViewTransformer contextViewTransformer;

	private final Map<String, ISegmentRenderer> segmentRendererMap = new HashMap<String, ISegmentRenderer>();
	
	private final List<IViewTransformer> viewTransformerList = new ArrayList<IViewTransformer>();
	
	
	private boolean mIsReady = false;
	
	private boolean hasSurface = false;
	private boolean hasSurfaceValues = false;
	
	private ISurface currentSurface;
	
	private ICamera camera;
	
	private final ITextureCoordinator textureCoordinator;
	private final IRenderingStrategy mStrategy;
	private final IGameState mGamestate;
	
	public DefaultModularStateRender(IRenderingStrategy strategy, IGameState gamestate) {
		this.mStrategy = strategy;
		this.mGamestate = gamestate;
		this.camera = gamestate.getActiveScene().getCameraController().getCamera();

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
			
			if(hasSurfaceValues) {
				
				IViewTransformer rendererViewTransformer = mStrategy.createViewTransformerForRenderer(this, currentSurface.getViewport(), camera, name);
				
				renderer.contextChanged(rendererViewTransformer, currentSurface.getProjectionMatrix());
			}
		}
	}
	
	@Override
	public ISegmentRenderer getSegmentRenderer(String name) {
		return segmentRendererMap.get(name);
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
	public void renderCall(String layerName, double stackPosition) {
		if(!mIsReady) {
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
		this.camera = camera;
		
		contextViewTransformer.setCamera(camera);
		for(IViewTransformer viewTransformer: viewTransformerList) {
			viewTransformer.setCamera(camera);
		}
	}
	
	@Override
	public ICamera getCamera() {
		return camera;
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


	// TODO: How to handle these? Replace the other methods?

	@Override
	public void addManagedRenderer(String name, IRenderer renderer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IRenderer getManagedRenderer(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IRenderer removeManagedRenderer(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasManagedRenderer(String name) {
		// TODO Auto-generated method stub
		return false;
	}
	
	

	// synchronize when iterating over renderers?
	
	@Override
	public void surfaceCreated(ISurface surface) {
		currentSurface = surface;
		hasSurface = true;
		
		for(ISegmentRenderer renderer: segmentRendererMap.values()) {
			renderer.contextCreated();
		}
	
		
		mIsReady = true;
	}

	@Override
	public void surfaceChanged(ISurface surface) {
		hasSurfaceValues = true;

		IScreenViewport masterViewport = surface.getViewport();

		contextViewport.setSize(masterViewport.getWidth(), masterViewport.getHeight());
		contextViewport.setOffset(masterViewport.getHorizontalOffset(), masterViewport.getVerticalOffset());
		
		contextViewTransformer.setViewport(contextViewport);

		contextProjectionMatrix.set(surface.getProjectionMatrix());

		
		viewTransformerList.clear();
		
		for(String rendererName: segmentRendererMap.keySet()) {
			
			ISegmentRenderer renderer = segmentRendererMap.get(rendererName);
			
			
			IViewTransformer rendererViewTransformer = mStrategy.createViewTransformerForRenderer(DefaultModularStateRender.this, masterViewport, camera, rendererName);
			viewTransformerList.add(rendererViewTransformer);
			
			renderer.contextChanged(rendererViewTransformer, contextProjectionMatrix);
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
		hasSurfaceValues = false;
		mIsReady = false;
		
		for(ISegmentRenderer renderer: segmentRendererMap.values()) {
			renderer.contextDestroyed();
		}
	}
	
}
