package com.tokelon.toktales.android.render;

import javax.inject.Inject;

import com.tokelon.toktales.android.render.tools.IUIControl;
import com.tokelon.toktales.android.render.tools.IUIControl.IUIControlFactory;
import com.tokelon.toktales.core.engine.IEngineDriver;
import com.tokelon.toktales.core.game.screen.view.AccurateViewport;
import com.tokelon.toktales.core.render.ISurfaceManager;
import com.tokelon.toktales.core.render.ISurfaceManager.ISurfaceManagerFactory;

import android.view.MotionEvent;

public class DefaultRenderViewAdapter implements IRenderViewAdapter {


	private final IEngineDriver engineDriver;
	private final IViewRenderer viewRenderer;
	private final IUIControl uiControl;
	private final ISurfaceManagerFactory surfaceManagerFactory;

	@Inject
	public DefaultRenderViewAdapter(IEngineDriver engineDriver, IViewRenderer viewRenderer, IUIControlFactory uiControlFactory, ISurfaceManagerFactory surfaceManagerFactory) {
		this.engineDriver = engineDriver;
		this.viewRenderer = viewRenderer;
		this.uiControl = uiControlFactory.create();
		this.surfaceManagerFactory = surfaceManagerFactory;
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
	public void onSurfaceCreated(ISurfaceManager surfaceManager) {
		viewRenderer.onSurfaceCreated(surfaceManager);
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
		
		engineDriver.render();
		
		engineDriver.processInput(uiControl);
	}

	@Override
	public boolean onTouch(MotionEvent motionEvent) {
		// This should normally be done here and not when the surface changes (for synchronization reasons)
		// if(new viewport) mUIControl.set(newViewport)
		
		
		return uiControl.onTouch(motionEvent);
	}

	
	@Override
	public ISurfaceManagerFactory getSurfaceManagerFactory() {
		return surfaceManagerFactory;
	}
	
}
