package com.tokelon.toktales.core.util.options;

import java.util.HashMap;

public class KeyedOptionsImpl<K> implements IKeyedOptions<K> {

	
	private final HashMap<K, Object> optionsMap;
	
	public KeyedOptionsImpl() {
		optionsMap = new HashMap<>();
	}
	
	
	@Override
	public Object get(K key) {
		return optionsMap.get(key);
	}

	@Override
	public <T> T getAs(K key, Class<T> as) {
		Object option = optionsMap.get(key);
		return as.isInstance(option) ? as.cast(option) : null;
	}

	
	@Override
	public Object getOrDefault(K key, Object defaultValue) {
		Object option = optionsMap.get(key);
		return option == null ? defaultValue : option;
	}

	@Override
	public <T> T getAsOrDefault(K key, T defaultValue, Class<T> as) {
		Object option = optionsMap.get(key);
		return as.isInstance(option) ? as.cast(option) : defaultValue;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getAsExactOrDefault(K key, T defaultValue) {
		Object option = optionsMap.get(key);
		
		if(defaultValue == null) {
			return (T) option;
		}
		
		return defaultValue.getClass().isInstance(option) ? (T) option : defaultValue;
	}

	
	@Override
	public Object getOrError(K key) {
		Object option = optionsMap.get(key);
		if(option == null) {
			throw new IllegalArgumentException("No option for key: " + key);
		}
		
		return option;
	}

	@Override
	public <T> T getAsOrError(K key, Class<T> as) {
		Object option = optionsMap.get(key);
		if(!(as.isInstance(option))) {
			throw new ClassCastException("Incompatible type [" + as.getName() + "] for key: " + key);
		}
		
		return as.cast(option);
	}

	
	@Override
	public boolean has(K key) {
		return optionsMap.containsKey(key);
	}

	@Override
	public boolean hasValue(Object value) {
		return optionsMap.containsValue(value);
	}
	
	@Override
	public int count() {
		return optionsMap.size();
	}

	
	@Override
	public Object set(K key, Object option) {
		return optionsMap.put(key, option);
	}

	@Override
	public Object remove(K key) {
		return optionsMap.remove(key);
	}

	@Override
	public void reset() {
		optionsMap.clear();
	}

}
