package com.tokelon.toktales.core.util.manage;

public interface IListenerHolder<T> {

	
	/** Adds the given listener, if not present.
	 * 
	 * @param listener
	 * @throws NullPointerException If listener is null.
	 */
	public void addListener(T listener);
	
	/** Removes the given listener, if present.
	 * 
	 * @param listener
	 * @throws NullPointerException If listener is null.
	 */
	public void removeListener(T listener);
	
	/** Returns whether the given listener is present or not.
	 * 
	 * @param listener
	 * @return True if the listener is present, false if not.
	 * @throws NullPointerException If listener is null.
	 */
	public boolean hasListener(T listener);
	
}
