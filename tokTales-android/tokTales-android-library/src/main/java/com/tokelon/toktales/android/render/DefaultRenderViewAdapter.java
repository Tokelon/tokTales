package com.tokelon.toktales.android.render;

import javax.inject.Inject;

import com.tokelon.toktales.android.render.tools.IUIControl;
import com.tokelon.toktales.android.render.tools.IUIControl.IUIControlFactory;
import com.tokelon.toktales.core.engine.IEngineDriver;
import com.tokelon.toktales.core.game.screen.view.AccurateViewport;
import com.tokelon.toktales.core.render.ISurfaceHandler;
import com.tokelon.toktales.core.render.ISurfaceHandler.ISurfaceHandlerFactory;

import android.view.MotionEvent;

public class DefaultRenderViewAdapter implements IRenderViewAdapter {


	private final IEngineDriver engineDriver;
	private final IViewRenderer viewRenderer;
	private final IUIControl uiControl;
	private final ISurfaceHandlerFactory surfaceHandlerFactory;

	@Inject
	public DefaultRenderViewAdapter(IEngineDriver engineDriver, IViewRenderer viewRenderer, IUIControlFactory uiControlFactory, ISurfaceHandlerFactory surfaceHandlerFactory) {
		this.engineDriver = engineDriver;
		this.viewRenderer = viewRenderer;
		this.uiControl = uiControlFactory.create();
		this.surfaceHandlerFactory = surfaceHandlerFactory;
	}


	@Override
	public void onResume() {
		uiControl.startControl();
	}

	@Override
	public void onPause() {
		uiControl.stopControl();
	}

	
	@Override
	public void onSurfaceCreated(ISurfaceHandler surfaceHandler) {
		viewRenderer.onSurfaceCreated(surfaceHandler);
	}

	@Override
	public void onSurfaceChanged(int width, int height) {
		viewRenderer.onSurfaceChanged(width, height);
		
		
		AccurateViewport uiControlViewport = new AccurateViewport();
		uiControlViewport.setSize(width, height);
		
		uiControl.setViewport(uiControlViewport);
	}
	
	@Override
	public void onSurfaceDestroyed() {
		viewRenderer.onSurfaceDestroyed();
	}

	
	@Override
	public void onDrawFrame() {
		engineDriver.update();
		
		//engineDriver.render(); // Move this out of the renderer and into here?
		viewRenderer.onDrawFrame();
		
		engineDriver.processInput(uiControl);
	}

	@Override
	public boolean onTouch(MotionEvent motionEvent) {
		// This should normally be done here and not when the surface changes (for synchronization reasons)
		// if(new viewport) mUIControl.set(newViewport)
		
		
		return uiControl.onTouch(motionEvent);
	}

	
	@Override
	public ISurfaceHandlerFactory getSurfaceHandlerFactory() {
		return surfaceHandlerFactory;
	}
	
}
