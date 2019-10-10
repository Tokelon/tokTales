package com.tokelon.toktales.tools.core.objects.params;

import java.util.HashMap;
import java.util.Map;

public class ParamsImpl implements IParams {

	private final Map<String, Object> parameterMap = new HashMap<String, Object>();
	
	
	
	@Override
	public Object getParam(String key) {
		return parameterMap.get(key);
	}
	
	@Override
	public Object getParamOrError(String key) {
		Object res = parameterMap.get(key);
		
		if(res == null) {
			throw new IllegalArgumentException("No param for key: " + key);
		}
		
		return res;
	}

	@Override
	public <T> T getParamAs(String key, Class<T> type) {
		Object p = parameterMap.get(key);
		
		if(p == null) {
			throw new IllegalArgumentException("No param for key: " + key);
		}
		
		if(!type.isInstance(p)) {
			throw new IllegalArgumentException("The requested parameter is not an instance of " +type.getName());
		}
		
		return type.cast(p);
	}
	
	
	@Override
	public void setParam(String key, Object param) {
		parameterMap.put(key, param);
	}

	@Override
	public boolean hasParam(String key) {
		return parameterMap.containsKey(key);
	}

	@Override
	public Object removeParam(String key) {
		return parameterMap.remove(key);
	}

}
