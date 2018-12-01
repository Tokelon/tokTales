package com.tokelon.toktales.core.util;

import java.util.ArrayList;
import java.util.List;

import java9.util.function.Supplier;

public class DefaultLeaseObjectPool<T> implements ILeaseObjectPool<T> {

	
	private final List<IObjectLease<T>> leasePool;
	
	private final Supplier<T> supplier;
	private final int capacity;

	public DefaultLeaseObjectPool(Supplier<T> supplier, int capacity, int initialSize) {
		if(supplier == null) {
			throw new NullPointerException();
		}
		if(capacity < 0 || initialSize < 0 || initialSize > capacity) {
			throw new IllegalArgumentException();
		}
		
		this.supplier = supplier;
		this.capacity = capacity;
		
		this.leasePool = new ArrayList<IObjectLease<T>>(initialSize);
		init(initialSize);
	}
	

	private ObjectLease createLease() {
		return new ObjectLease(supplier.get());
	}
	
	private void init(int initialSize) {
		for(int i = 0; i < initialSize; i++) {
			leasePool.add(createLease());
		}
	}
	


	@Override
	public IObjectLease<T> getObject() {
		if(leasePool.isEmpty()) {
			return createLease();
		}
		else {
			return leasePool.get(0);
		}
	}

	@Override
	public void returnObject(IObjectLease<T> object) {
		if(leasePool.size() < capacity) {
			leasePool.add(object);
		}
	}

	@Override
	public int getCurrentSize() {
		return leasePool.size();
	}

	@Override
	public int getPoolCapacity() {
		return capacity;
	}
	
	

	private class ObjectLease implements IObjectLease<T> {
		private final T object;

		public ObjectLease(T object) {
			this.object = object;
		}
		
		@Override
		public void close() throws Exception {
			returnObject(this);
		}

		@Override
		public T getProperty() {
			return object;
		}
	}
	
	
	public static class DefaultLeaseObjectPoolFactory implements ILeaseObjectPoolFactory {

		@Override
		public <T> ILeaseObjectPool<T> create(Supplier<T> supplier, int capacity, int initialSize) {
			return new DefaultLeaseObjectPool<T>(supplier, capacity, initialSize);
		}
	}

}
