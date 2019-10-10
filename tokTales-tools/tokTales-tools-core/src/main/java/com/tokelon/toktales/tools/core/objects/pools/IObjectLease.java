package com.tokelon.toktales.tools.core.objects.pools;

public interface IObjectLease<T> extends AutoCloseable {
	
	
	public T getProperty();
	
	

	public static class ObjectLeaseImpl<T> implements IObjectLease<T> {

		private final IObjectPool<T> pool;
		private final T object;

		public ObjectLeaseImpl(IObjectPool<T> pool, T object) {
			this.pool = pool;
			this.object = object;
		}
		
		@Override
		public void close() throws Exception {
			pool.returnObject(object);
		}

		@Override
		public T getProperty() {
			return object;
		}
	}
	
}