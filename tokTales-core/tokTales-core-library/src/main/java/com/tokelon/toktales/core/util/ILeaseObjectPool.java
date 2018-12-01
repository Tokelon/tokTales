package com.tokelon.toktales.core.util;

import java9.util.function.Supplier;

public interface ILeaseObjectPool<T> extends IObjectPool<IObjectLease<T>> {
	
	
	
	public static interface ILeaseObjectPoolFactory {
		
		public <T> ILeaseObjectPool<T> create(Supplier<T> supplier, int capacity, int initialSize);
	}
	
}
