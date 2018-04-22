package com.tokelon.toktales.core.content;

/** Specifies that an object should be disposed to free up resources.
 */
public interface IDisposable {

	
	/** Releases resources used by this object and marks it as disposed.
	 * <p>
	 * The object should not be used after this.
	 */
	public void dispose();
	
}
