package com.tokelon.toktales.core.util.options;

import java.util.HashMap;
import java.util.Map;

public class NamedOptionsImpl implements INamedOptions {

	
	private Map<String, Object> options = new HashMap<String, Object>();

	
	@Override
	public void set(String name, Object option) {
		options.put(name, option);
	}

	
	@Override
	public Object get(String name) {
		return options.get(name);
	}
	
	@Override
	public Object getOrError(String name) {
		Object res = options.get(name);
		if(res == null) {
			throw new IllegalArgumentException("No option with name: " +name);
		}
		
		return res;
	}

	@Override
	public <T> T getOrDefault(String name, T defaultValue) {
		Object res = options.get(name);
		return res == null ? defaultValue : (T) res;
	}
	
	@Override
	public <T> T getAs(String name, Class<T> as) {
		Object res = options.get(name);
		return res == null ? null : as.cast(res);
	}
	

	@Override
	public boolean has(String name) {
		return options.containsKey(name);
	}
	
	@Override
	public void remove(String name) {
		options.remove(name);
	}

	
}
