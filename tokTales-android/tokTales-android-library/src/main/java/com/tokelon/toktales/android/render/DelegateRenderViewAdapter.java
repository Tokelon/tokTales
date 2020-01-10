package com.tokelon.toktales.android.render;

import com.tokelon.toktales.core.render.ISurfaceManager;
import com.tokelon.toktales.core.render.ISurfaceManager.ISurfaceManagerFactory;

import android.view.MotionEvent;

public class DelegateRenderViewAdapter implements IRenderViewAdapter {


	private IRenderViewAdapter adapter;

	private boolean surfaceCreated = false;
	private boolean surfaceChanged = false;
	
	private boolean isResumed = false;
	
	private int currentWidth = 0;
	private int currentHeight = 0;
	
	private ISurfaceManager currentSurfaceManager;
	
	
	/** Sets the adapter.
	 * <p>
	 * Caution: This method must be called inside the rendering thread or before rendering has started, otherwise thread safety cannot be guaranteed.
	 * 
	 * @param adapter The adapter to delegate calls to.
	 */
	public void setAdapter(IRenderViewAdapter adapter) {
		this.adapter = adapter;
		
		if(adapter != null) {
			if(surfaceCreated) {
				adapter.onSurfaceCreated(currentSurfaceManager);
				
				if(surfaceChanged) {
					adapter.onSurfaceChanged(currentWidth, currentHeight);
				}
			}
			
			if(isResumed) {
				adapter.onResume();
			}
		}
	}

	
	@Override
	public void onSurfaceCreated(ISurfaceManager surfaceManager) {
		this.surfaceCreated = true;
		this.surfaceChanged = false;
		this.currentSurfaceManager = surfaceManager;
		
		if(adapter != null) {
			adapter.onSurfaceCreated(surfaceManager);
		}
	}

	@Override
	public void onSurfaceChanged(int width, int height) {
		this.currentWidth = width;
		this.currentHeight = height;
		
		this.surfaceChanged = true;
		
		if(adapter != null) {
			adapter.onSurfaceChanged(width, height);
		}
	}

	
	@Override
	public void onResume() {
		this.isResumed = true;
		
		if(adapter != null) {
			adapter.onResume();	
		}
	}

	@Override
	public void onPause() {
		this.isResumed = false;
		
		if(adapter != null) {
			adapter.onPause();
		}
	}
	

	@Override
	public void onDrawFrame() {
		if(adapter != null) {
			adapter.onDrawFrame();
		}
	}

	@Override
	public boolean onTouch(MotionEvent motionEvent) {
		if(adapter != null) {
			return adapter.onTouch(motionEvent);
		}
		
		return false;
	}
	
	
	@Override
	public ISurfaceManagerFactory getSurfaceManagerFactory() {
		if(adapter != null) {
			return adapter.getSurfaceManagerFactory();
		}
		
		return null;
	}
	
	
	@Override
	public void onSurfaceDestroyed() {
		this.surfaceCreated = false;
		this.surfaceChanged = false;
		this.currentSurfaceManager = null;
		
		if(adapter != null) {
			adapter.onSurfaceDestroyed();
		}
	}
	
}
