package com.tokelon.toktales.android.render;

public interface IRenderView {


	public void onResume();
	public void onPause();
	

	public void setRenderViewAdapter(IRenderViewAdapter adapter);
	
	
	public void queueEvent(Runnable event);
	
	//public boolean fireTouchEvent(MotionEvent event); // Add this?

	//public void setViewName(String name);
	
}