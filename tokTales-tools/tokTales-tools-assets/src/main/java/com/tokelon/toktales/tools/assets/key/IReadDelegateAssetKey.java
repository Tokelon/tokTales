package com.tokelon.toktales.tools.assets.key;

public interface IReadDelegateAssetKey {
	
	
	public Object getReadableKey(); // Would return IResourceKey, IFileKey etc.
	
	
	/** Checks if the given key is an {@link IReadDelegateAssetKey} and if true returns {@link #getReadableKey()},
	 * otherwise returns the given key.
	 * 
	 * @param key
	 * @return A readable or the original key.
	 */
	public static Object getReadableKey(Object key) {
		Object readableKey;
		if(key instanceof IReadDelegateAssetKey) {
			readableKey = ((IReadDelegateAssetKey) key).getReadableKey();
		}
		else {
			readableKey = key;
		}
		
		return readableKey;
	}
	
}
