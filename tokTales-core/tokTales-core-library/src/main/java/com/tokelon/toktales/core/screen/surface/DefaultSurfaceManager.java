package com.tokelon.toktales.core.screen.surface;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class DefaultSurfaceManager implements ISurfaceManager {


	private final Map<ISurface, SurfaceData> surfaceMap;
	
	private final Set<ISurfaceCallback> callbackSet;
	
	public DefaultSurfaceManager() {
		surfaceMap = Collections.synchronizedMap(new HashMap<ISurface, SurfaceData>());
		callbackSet = Collections.synchronizedSet(new HashSet<ISurfaceCallback>());
	}
	
	
	
	@Override
	public void publishSurface(ISurface surface, ISurfaceController surfaceController) {
		surfaceMap.put(surface, new SurfaceData(surfaceController));
		
		synchronized (callbackSet) {
			for(ISurfaceCallback callback: callbackSet) {
				callback.surfaceCreated(surface);
			}
		}
	}

	@Override
	public void updateSurface(ISurface surface) {
		SurfaceData surfaceData = surfaceMap.get(surface);
		if(surfaceData == null) {
			throw new IllegalArgumentException(String.format("Surface named [%s] has not been published yet", surface.getName()));
		}
		
		surfaceData.surfaceChanged = true;
		
		synchronized (callbackSet) {
			for(ISurfaceCallback callback: callbackSet) {
				callback.surfaceChanged(surface);
			}
		}
	}

	@Override
	public void recallSurface(ISurface surface) {
		surfaceMap.remove(surface);
		
		synchronized (callbackSet) {
			for(ISurfaceCallback callback: callbackSet) {
				callback.surfaceDestroyed(surface);
			}
		}
	}
	
	
	@Override
	public ISurfaceController getController(ISurface surface) {
		return surfaceMap.get(surface).surfaceController;
	}
	
	
	@Override
	public void addCallback(ISurfaceCallback callback) {
		callbackSet.add(callback);
		
		synchronized (surfaceMap) {
			for(Entry<ISurface, SurfaceData> entry: surfaceMap.entrySet()) {
				ISurface surface = entry.getKey();
				SurfaceData surfaceData = entry.getValue();
				
				boolean surfaceChanged = surfaceData.surfaceChanged;
				surfaceData.surfaceController.queueEvent(() -> {
					callback.surfaceCreated(surface);
					
					if(surfaceChanged) {
						callback.surfaceChanged(surface);
					}
				});
			}
		}
	}

	@Override
	public void removeCallback(ISurfaceCallback callback) {
		callbackSet.remove(callback);
	}

	@Override
	public boolean hasCallback(ISurfaceCallback callback) {
		return callbackSet.contains(callback);
	}

	
	private static class SurfaceData {
		private boolean surfaceChanged = false;
		
		private final ISurfaceController surfaceController;
		
		public SurfaceData(ISurfaceController surfaceController) {
			this.surfaceController = surfaceController;
		}
	}
	
}
