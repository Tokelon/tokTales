package com.tokelon.toktales.core.util;

import java.util.ArrayList;
import java.util.List;

import java9.util.function.Supplier;

public class DefaultObjectPool<T> implements IObjectPool<T> {

	
	private final List<T> pool;
	
	private final Supplier<T> supplier;
	private final int capacity;

	public DefaultObjectPool(Supplier<T> supplier, int capacity, int initialSize) {
		if(supplier == null) {
			throw new NullPointerException();
		}
		if(capacity < 0 || initialSize < 0 || initialSize > capacity) {
			throw new IllegalArgumentException();
		}
		
		this.supplier = supplier;
		this.capacity = capacity;
		
		this.pool = new ArrayList<T>(initialSize);
		init(initialSize);
	}
	
	private void init(int initialSize) {
		for(int i = 0; i < initialSize; i++) {
			pool.add(supplier.get());
		}
	}
	
	
	@Override
	public T getObject() {
		if(pool.isEmpty()) {
			return supplier.get();
		}
		else {
			return pool.get(0);
		}
	}

	@Override
	public void returnObject(T object) {
		if(pool.size() < capacity) {
			pool.add(object);
		}
	}

	@Override
	public int getCurrentSize() {
		return pool.size();
	}

	@Override
	public int getPoolCapacity() {
		return capacity;
	}
	

	
	public static class DefaultObjectPoolFactory implements IObjectPoolFactory {

		@Override
		public <T> IObjectPool<T> create(Supplier<T> supplier, int capacity, int initialSize) {
			return new DefaultObjectPool<>(supplier, capacity, initialSize);
		}
	}
	
}
