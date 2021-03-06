package com.tokelon.toktales.tools.core.sub.inject.scope;

import java.util.HashMap;
import java.util.Map;

import com.google.inject.Key;
import com.google.inject.Provider;
import com.google.inject.Scope;
import com.google.inject.Scopes;

public class ManualScope implements Scope {

	private final Map<Key<?>, Object> values = new HashMap<>();
	
	
	public void resetScope() {
		synchronized (values) {
			values.clear();
		}
	}
	
	@Override
	public <T> Provider<T> scope(Key<T> key, Provider<T> unscoped) {
		return new ManualScopeProvider<>(key, unscoped);
	}

	
	private class ManualScopeProvider<T> implements Provider<T> {
		private final Key<T> key;
		private final Provider<T> unscoped;
		
		public ManualScopeProvider(Key<T> key, Provider<T> unscoped) {
			this.key = key;
			this.unscoped = unscoped;
		}

		
		@Override
		@SuppressWarnings("unchecked")
		public T get() {
			synchronized (values) {
				
				T current = (T) values.get(key);
				
				if (current == null && !values.containsKey(key)) {
					current = unscoped.get();

					// don't remember proxies; these exist only to serve circular dependencies
					if (Scopes.isCircularProxy(current)) {
						return current;
					}

					values.put(key, current);
				}
				
				return current;
			}
		}
		
		@Override
		public String toString() {
			return "ManualScopeProvider backed by: " + unscoped.toString();
		}
	}
	
}
