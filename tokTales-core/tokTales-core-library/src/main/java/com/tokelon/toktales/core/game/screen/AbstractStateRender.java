package com.tokelon.toktales.core.game.screen;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.joml.Matrix4f;

import com.tokelon.toktales.core.engine.render.ISurface;
import com.tokelon.toktales.core.game.model.ICamera;
import com.tokelon.toktales.core.game.screen.view.AccurateViewport;
import com.tokelon.toktales.core.game.screen.view.DefaultViewTransformer;
import com.tokelon.toktales.core.game.screen.view.IScreenViewport;
import com.tokelon.toktales.core.game.screen.view.IViewTransformer;
import com.tokelon.toktales.core.render.IRenderer;
import com.tokelon.toktales.core.render.ITextureCoordinator;

public abstract class AbstractStateRender implements IStateRender {


	private final AccurateViewport contextViewport = new AccurateViewport();
	private final Matrix4f projectionMatrix = new Matrix4f();
	private final IViewTransformer viewTransformer;

	private final Map<String, IRenderer> managedRendererMap;

	private boolean hasSurface = false;
	private boolean hasView = false;
	
	
	private ISurface currentSurface;

	private final ITextureCoordinator textureCoordinator;
	
	public AbstractStateRender(ITextureCoordinator textureCoordinator) {
		this.textureCoordinator = textureCoordinator;
		
		this.managedRendererMap = Collections.synchronizedMap(new HashMap<String, IRenderer>());
		
		this.viewTransformer = new DefaultViewTransformer();
	}
	
	
	
	protected boolean hasView() {
		return hasView;
	}
	
	protected IScreenViewport getContextViewport() {
		return hasView() ? contextViewport : null;
	}
	
	protected Matrix4f getProjectionMatrix() {
		return hasView() ? projectionMatrix : null;
	}
	
	
	protected abstract void onSurfaceCreated();
	protected abstract void onSurfaceChanged();
	protected abstract void onSurfaceDestroyed();
	
	
	
	@Override
	public void addManagedRenderer(String name, IRenderer renderer) {
		managedRendererMap.put(name, renderer);
		
		if(hasSurface) {
			renderer.contextCreated();
			
			if(hasView) {
				renderer.contextChanged(viewTransformer, projectionMatrix);
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
	
	
	@Override
	public void updateCamera(ICamera camera) {
		getViewTransformer().updateCamera(camera);
	}
	
	@Override
	public ICamera getCurrentCamera() {
		return getViewTransformer().getCurrentCamera();
	}
	
	@Override
	public IViewTransformer getViewTransformer() {
		return viewTransformer;
	}
	
	@Override
	public ITextureCoordinator getTextureCoordinator() {
		return textureCoordinator;
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
	public void surfaceCreated(ISurface surface) {
		hasView = false; // Do this?
		currentSurface = surface;
		
		onSurfaceCreated();
		hasSurface = true;

		synchronized (managedRendererMap) {
			for(IRenderer renderer: managedRendererMap.values()) {
				renderer.contextCreated();
			}
		}
	}

	@Override
	public void surfaceChanged(ISurface surface) {
		IScreenViewport masterViewport = surface.getViewport();
		
		contextViewport.setSize(masterViewport.getWidth(), masterViewport.getHeight());
		contextViewport.setOffset(masterViewport.getHorizontalOffset(), masterViewport.getVerticalOffset());
		
		projectionMatrix.set(surface.getProjectionMatrix());
		
		viewTransformer.updateViewport(contextViewport);
		
		
		hasView = true;
		onSurfaceChanged();	// Call the state renderer first
		
		// Iterate over the managed renderers
		synchronized (managedRendererMap) {
			for(IRenderer renderer: managedRendererMap.values()) {
				renderer.contextChanged(getViewTransformer(), getProjectionMatrix());
			}
		}
		
	}

	@Override
	public void surfaceDestroyed(ISurface surface) {
		currentSurface = null;
		hasSurface = false;
		hasView = false;
		onSurfaceDestroyed();
		
		synchronized (managedRendererMap) {
			for(IRenderer renderer: managedRendererMap.values()) {
				renderer.contextDestroyed();
			}
		}
	}
	
}
