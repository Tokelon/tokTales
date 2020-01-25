package com.tokelon.toktales.core.storage.utils;

/** A mutable version of {@link IExtendedPath}.
 */
public interface IMutablePath extends ILocationPath {


	/**
	 * @param path
	 * @return This object.
	 */
	public IMutablePath setPath(ILocationPath path);
	
	/**
	 * @param value
	 * @return This object.
	 */
	public IMutablePath setPath(String value);
	
	/**
	 * @param path
	 * @return This object.
	 */
	public IMutablePath append(ILocationPath path);
	
	/**
	 * @param value
	 * @return This object.
	 */
	public IMutablePath append(String value);
	
	/**
	 * @return This object.
	 */
	public IMutablePath setToParent();
	
}
