package com.tokelon.toktales.core.location;

/** A mutable version of {@link ILocationPath}.
 */
public interface IMutableLocationPath extends ILocationPath {


	/**
	 * @param path
	 * @return This object.
	 */
	public IMutableLocationPath set(ILocationPath path);
	
	/**
	 * @param value
	 * @return This object.
	 */
	public IMutableLocationPath set(String value);
	
	/**
	 * @param path
	 * @return This object.
	 */
	public IMutableLocationPath setToChild(ILocationPath path);
	
	/**
	 * @param value
	 * @return This object.
	 */
	public IMutableLocationPath setToChild(String value);
	
	/**
	 * @return This object.
	 */
	public IMutableLocationPath setToParent();
	
}
