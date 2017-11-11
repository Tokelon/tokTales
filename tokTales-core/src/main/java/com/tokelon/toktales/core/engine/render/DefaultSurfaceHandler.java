package com.tokelon.toktales.core.engine.render;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class DefaultSurfaceHandler implements ISurfaceHandler {

	
	private final Map<ISurface, Boolean> surfaceMap;
	
	private final Set<ISurfaceCallback> callbackSet;
	
	public DefaultSurfaceHandler() {
		
		surfaceMap = Collections.synchronizedMap(new HashMap<ISurface, Boolean>());
		callbackSet = Collections.synchronizedSet(new HashSet<ISurfaceCallback>());
	}
	
	
	@Override
	public void addCallback(ISurfaceCallback callback) {
		//if callback == null ?
		
		callbackSet.add(callback);
		
		synchronized (surfaceMap) {
			for(Entry<ISurface, Boolean> entry: surfaceMap.entrySet()) {
				callback.surfaceCreated(entry.getKey());
				
				if(entry.getValue()) {
					callback.surfaceChanged(entry.getKey());
				}
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

	
	@Override
	public void publishSurface(ISurface surface) {
		surfaceMap.put(surface, false);
		
		synchronized (callbackSet) {
			for(ISurfaceCallback callback: callbackSet) {
				callback.surfaceCreated(surface);
			}
		}
	}

	@Override
	public void updateSurface(ISurface surface) {
		surfaceMap.put(surface, true);
		
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

	
}
