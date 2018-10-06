package com.tokelon.toktales.core.storage.utils;

/** A mutable version of {@link IExtendedPath}.
 * 
 */
public interface IMutablePath extends IExtendedPath {

	/**
	 * 
	 * @param path
	 * @return This object.
	 */
	public IMutablePath setPath(IConformedPath path);
	
	/**
	 * 
	 * @param value
	 * @return This object.
	 */
	public IMutablePath setPath(String value);
	
	/**
	 * 
	 * @param path
	 * @return This object.
	 */
	public IMutablePath append(IConformedPath path);
	
	/**
	 * 
	 * @param value
	 * @return This object.
	 */
	public IMutablePath append(String value);
	
	/**
	 * 
	 * @return This object.
	 */
	public IMutablePath cutLastPlace();
	
}
