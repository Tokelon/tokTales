package com.tokelon.toktales.core.content.manage;

import java.io.InputStream;

import com.tokelon.toktales.core.engine.content.ContentException;
import com.tokelon.toktales.core.prog.annotation.CustomFunctionalInterface;

@CustomFunctionalInterface
public interface IAssetReader {

	
	/** Opens an input stream to an asset for the given key.
	 * <p>
	 * The default implementation simply calls {@link #read(Object, Object)} by passing null for the options.
	 * 
	 * @param key
	 * @return An input stream of the asset for the given key.
	 * @throws ContentException If an error opening the stream occurs.
	 */
	public default InputStream read(Object key) throws ContentException {
		return read(key, null);
	}
	
	/** Opens an input stream to an asset for the given key with the given options.
	 * 
	 * @param key
	 * @param options
	 * @return An input stream of the asset for the given key.
	 * @throws ContentException If an error opening the stream occurs.
	 */
	public InputStream read(Object key, Object options) throws ContentException;
	
}
