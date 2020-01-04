package com.tokelon.toktales.android.render.opengl;

import com.tokelon.toktales.android.render.IRenderViewAdapter;

public interface IGLRenderView { // TODO: Rename to IRenderView


	public void onResume();
	public void onPause();
	

	public void setRenderViewAdapter(IRenderViewAdapter adapter);
	
	
	public void queueEvent(Runnable event);
	
	//public boolean fireTouchEvent(MotionEvent event); // Add this?

	//public void setViewName(String name);
	
}