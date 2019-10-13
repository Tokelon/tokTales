package com.tokelon.toktales.tools.assets.reader;

public interface IManagedAssetReader extends IAssetReader {

	
	/** Returns whether this reader can read the asset for the given key.
	 * 
	 * @param key
	 * @param options
	 * @return True if the asset can be read, false if not.
	 */
	public boolean canRead(Object key, Object options);
	
}
