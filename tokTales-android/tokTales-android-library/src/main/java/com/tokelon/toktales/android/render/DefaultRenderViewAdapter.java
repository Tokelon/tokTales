package com.tokelon.toktales.android.render;

import javax.inject.Inject;

import com.tokelon.toktales.android.render.tools.IUIControl;
import com.tokelon.toktales.android.render.tools.IUIControl.IUIControlFactory;
import com.tokelon.toktales.android.render.tools.IUIOverlay;
import com.tokelon.toktales.android.render.tools.IUIOverlayProvider;
import com.tokelon.toktales.core.engine.IEngineDriver;
import com.tokelon.toktales.core.engine.render.ISurfaceController;
import com.tokelon.toktales.core.game.screen.view.AccurateViewport;

import android.view.MotionEvent;

public class DefaultRenderViewAdapter implements IRenderViewAdapter {


	private final IEngineDriver engineDriver;
	private final IViewRenderer viewRenderer;
	private final IUIControl uiControl;

	@Inject
	public DefaultRenderViewAdapter(IEngineDriver engineDriver, IViewRenderer viewRenderer, IUIControl uiControl) {
		this.engineDriver = engineDriver;
		this.viewRenderer = viewRenderer;
		this.uiControl = uiControl;
	}

	
	@Override
	public void setSurfaceName(String name) {
		viewRenderer.setSurfaceName(name);
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
	public void onSurfaceCreated() {
		viewRenderer.onSurfaceCreated();
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
	
	
	
	public static class DefaultRenderViewAdapterFactory implements IRenderViewAdapterFactory {
		private final IEngineDriver engineDriver;
		private final IViewRendererFactory viewRendererFactory;
		private final IUIControlFactory uiControlFactory;

		@Inject
		public DefaultRenderViewAdapterFactory(IEngineDriver engineDriver, IViewRendererFactory viewRendererFactory, IUIControlFactory uiControlFactory) {
			this.engineDriver = engineDriver;
			this.viewRendererFactory = viewRendererFactory;
			this.uiControlFactory = uiControlFactory;
		}
		
		@Override
		public IRenderViewAdapter create(ISurfaceController surfaceController) {
			return new DefaultRenderViewAdapter(engineDriver, viewRendererFactory.create(surfaceController), uiControlFactory.create(new DummyUIOverlayProvider()));
		}
		
	}
	

	private static class DummyUIOverlayProvider implements IUIOverlayProvider {
		@Override
		public boolean hasUIOverlay() {
			return false;
		}

		@Override
		public IUIOverlay getUIOverlay() {
			return null;
		}
	}

}
