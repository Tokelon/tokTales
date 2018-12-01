package com.tokelon.toktales.core.util;

import java9.util.function.Supplier;

public interface IObjectPool<T> {

	
	public T getObject();
	
	public void returnObject(T object);
	
	public int getCurrentSize();

	public int getPoolCapacity();

	
	
	public interface IObjectPoolFactory {
		
		public <T> IObjectPool<T> create(Supplier<T> supplier, int capacity, int initialSize);
	}
	
}
