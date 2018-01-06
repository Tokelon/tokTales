package com.tokelon.toktales.core.engine.inject;

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
	
	public <T> Provider<T> scope(Key<T> key, Provider<T> unscoped) {
		return new GameScopeProvider<>(key, unscoped);
	}

	
	private class GameScopeProvider<T> implements Provider<T> {
		private final Key<T> key;
		private final Provider<T> unscoped;
		
		public GameScopeProvider(Key<T> key, Provider<T> unscoped) {
			this.key = key;
			this.unscoped = unscoped;
		}

		
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
			return "GameScopeProvider backed by: " + unscoped.toString();
		}
	}
	
}
