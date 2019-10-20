package com.tokelon.toktales.tools.core.dispose;

/** Specifies that an object should be disposed to free up resources.
 */
public interface IDisposable extends AutoCloseable {

	
	/** Releases resources used by this object and marks it as disposed.
	 * <p>
	 * The object should not be used after this.
	 */
	public void dispose();
	
	
	/** {@inheritDoc}
	 * <p>
	 * The default implementation simply calls {@link #dispose()}.
	 */
	@Override
	public default void close() {
		dispose();
	}
	
}
