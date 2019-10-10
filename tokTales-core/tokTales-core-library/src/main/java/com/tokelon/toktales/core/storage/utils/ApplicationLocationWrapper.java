package com.tokelon.toktales.core.storage.utils;

import com.tokelon.toktales.core.storage.IApplicationLocation;
import com.tokelon.toktales.core.storage.IStructuredLocation;
import com.tokelon.toktales.tools.core.objects.pools.SynchronizedPool;
import com.tokelon.toktales.tools.core.objects.pools.SynchronizedPool.PoolObjectFactory;

public class ApplicationLocationWrapper implements IApplicationLocation {

	private static final SynchronizedPool<ApplicationLocationWrapper> pool = new SynchronizedPool<ApplicationLocationWrapper>(new ApplicationLocationWrapperFactory(), 8);	// optimized for 8 concurrent threads 
	
	private IStructuredLocation actualLocation;
	
	
	public ApplicationLocationWrapper() {
		// Nothing
	}
	
	public ApplicationLocationWrapper(IStructuredLocation initialLocation) {
		actualLocation = initialLocation;
	}
	
	public void setActualLocation(IStructuredLocation location) {
		actualLocation = location;
	}
	
	@Override
	public IConformedPath getLocationPath() {
		return actualLocation.getLocationPath();
	}
	
	
	
	public void objectReset() {
		actualLocation = null;
	}
	
	public static SynchronizedPool<ApplicationLocationWrapper> getObjectPool() {
		return pool;
	}
	
	private static class ApplicationLocationWrapperFactory implements PoolObjectFactory<ApplicationLocationWrapper> {

		@Override
		public ApplicationLocationWrapper createObject() {
			return new ApplicationLocationWrapper();
		}
	}

}
