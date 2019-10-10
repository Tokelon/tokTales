package com.tokelon.toktales.tools.core.objects.pools;

import java.util.ArrayList;
import java.util.List;

/** Any class that is used in this pool should have an objectReset() method that resets the object's data.
 *
 * @param <T>
 */
public class SynchronizedPool<T> {
	// TODO: Refactor this to implement IObjectPool
	
	
	private final List<T> freeObjects;
	private final PoolObjectFactory<T> factory;
	private final int maxSize;
	
	
	public SynchronizedPool(PoolObjectFactory<T> factory, int maxSize) {
		this.factory = factory;
		this.maxSize = maxSize;
		this.freeObjects = new ArrayList<T>(maxSize); 
	}
	
	
	/** <i>Remember to reset the object data and free it after you used it.</i>
	 * 
	 * @return
	 */
	public synchronized T newObject() {
		T object = null;
		
		if(freeObjects.isEmpty()) {
			object = factory.createObject();
		}
		else {
			object = freeObjects.remove(freeObjects.size() - 1);
		}
		
		return object;
	}
	
	
	/** <i>Consider clearing the object data so that it can be garbage collected.<i>
	 * 
	 * @param object
	 */
	public synchronized void free(T object) {
		if(freeObjects.size() < maxSize) {
			freeObjects.add(object);
		}
	}
	
	
	
	public interface PoolObjectFactory<T> {
		
		public T createObject();
	}
	
}
