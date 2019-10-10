package com.tokelon.toktales.tools.core.objects.pools;

import com.tokelon.toktales.tools.core.function.Supplier;

public interface ILeaseObjectPool<T> extends IObjectPool<IObjectLease<T>> {
	
	
	
	public static interface ILeaseObjectPoolFactory {
		
		public <T> ILeaseObjectPool<T> create(Supplier<T> supplier, int capacity, int initialSize);
	}
	
}
