package com.tokelon.toktales.tools.core.sub.inject.guice;

import com.google.inject.Key;
import com.tokelon.toktales.tools.core.inject.IKey;

public interface IGuiceKey<T> extends IKey<T> {


	public Key<T> unwrap();
	
	
	public static class GuiceKey<U> implements IGuiceKey<U> {
		private final Key<U> guiceKey;

		public GuiceKey(Key<U> guiceKey) {
			this.guiceKey = guiceKey;
		}
		
		@Override
		public Key<U> unwrap() {
			return guiceKey;
		}

		
		public static <V> GuiceKey<V> wrap(Key<V> guiceKey) {
			return new GuiceKey<>(guiceKey);
		}
	}
	
}
