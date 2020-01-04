package com.tokelon.toktales.android.render;

import android.view.MotionEvent;

public class DelegateRenderViewAdapter implements IRenderViewAdapter {


	private IRenderViewAdapter adapter;

	private boolean surfaceCreated = false;
	private boolean surfaceChanged = false;
	
	private boolean isResumed = false;
	
	private int currentWidth = 0;
	private int currentHeight = 0;
	
	private String currentSurfaceName;
	
	
	/** Sets the adapter.
	 * <p>
	 * Caution: This method must be called inside the rendering thread or before rendering has started, otherwise thread safety cannot be guaranteed.
	 * 
	 * @param adapter The adapter to delegate calls to.
	 */
	public void setAdapter(IRenderViewAdapter adapter) {
		this.adapter = adapter;
		
		if(adapter != null) {
			if(currentSurfaceName != null) {
				adapter.setSurfaceName(currentSurfaceName);
			}
			
			if(surfaceCreated) {
				adapter.onSurfaceCreated();
				
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
	public void setSurfaceName(String name) {
		this.currentSurfaceName = name;
		
		if(adapter != null) {
			adapter.setSurfaceName(name);
		}
	}

	
	@Override
	public void onSurfaceCreated() {
		surfaceCreated = true;
		surfaceChanged = false;
		
		if(adapter != null) {
			adapter.onSurfaceCreated();
		}
	}

	@Override
	public void onSurfaceChanged(int width, int height) {
		currentWidth = width;
		currentHeight = height;
		
		surfaceChanged = true;
		
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
	public void onSurfaceDestroyed() {
		surfaceCreated = false;
		surfaceChanged = false;
		
		if(adapter != null) {
			adapter.onSurfaceDestroyed();
		}
	}
	
}
