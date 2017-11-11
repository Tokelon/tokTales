package com.tokelon.toktales.core.engine.render;


public interface ISurfaceHandler {

	
	public void addCallback(ISurfaceCallback callback);
	
	public void removeCallback(ISurfaceCallback callback);
	
	public boolean hasCallback(ISurfaceCallback callback);
	
	
	public void publishSurface(ISurface surface);
	
	public void updateSurface(ISurface surface);
	
	public void recallSurface(ISurface surface);
	
	
	
	public interface ISurfaceCallback {
		
		public void surfaceCreated(ISurface surface);
		
		public void surfaceChanged(ISurface surface);
		
		public void surfaceDestroyed(ISurface surface);
		
	}
	
	
}
