package com.tokelon.toktales.core.content.manage;

import java.io.InputStream;

import com.tokelon.toktales.core.engine.content.AssetException;
import com.tokelon.toktales.tools.core.annotations.CompatFunctionalInterface;

@CompatFunctionalInterface
public interface IAssetDecoder<T, K, O> {

	
	/** Decodes an asset from the given stream.
	 * <p>
	 * The default implementation for this simply calls {@link #decode(InputStream, Object, Object)} with null for options.
	 * 
	 * @param inputstream
	 * @param key
	 * @return The decoded asset, or null.
	 * @throws AssetException If an error occurs while decoding.
	 */
	public default T decode(InputStream inputstream, K key) throws AssetException {
		return decode(inputstream, key, null);
	}
	
	/** Decodes an asset from the given stream.
	 * 
	 * @param inputstream
	 * @param key
	 * @param options
	 * @return The decoded asset, or null.
	 * @throws AssetException If an error occurs while decoding.
	 */
	public T decode(InputStream inputstream, K key, O options) throws AssetException;
	
}
