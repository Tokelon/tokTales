package com.tokelon.toktales.android.render;

import com.tokelon.toktales.core.engine.render.ISurfaceController;

import android.view.MotionEvent;

public interface IRenderViewAdapter extends IViewRenderer {
	// Use view renderer property instead of extending?


	public void onResume();

	public void onPause();


	public boolean onTouch(MotionEvent motionEvent);
	
	
	
	public interface IRenderViewAdapterFactory {
		
		public IRenderViewAdapter create(ISurfaceController surfaceController);
	}
	
}
