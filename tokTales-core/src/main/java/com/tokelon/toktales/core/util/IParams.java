package com.tokelon.toktales.core.util;

public interface IParams {

	
	public void setParam(String key, Object param);
	
	
	public Object getParam(String key);
	
	/**
	 * 
	 * @param key
	 * @return
	 * @throws IllegalArgumentException If a param for key does not exist.
	 */
	public Object getParamOrError(String key);
	
	/**
	 * 
	 * @param key
	 * @param type
	 * @return
	 * @throws IllegalArgumentException If a param for key does not exist or it does not match the given class.
	 */
	public <T> T getParamAs(String key, Class<T> type);
	
	
	
	public boolean hasParam(String key);
	
	public Object removeParam(String key);
	
}
