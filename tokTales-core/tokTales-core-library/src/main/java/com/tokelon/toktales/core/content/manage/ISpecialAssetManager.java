package com.tokelon.toktales.core.content.manage;

public interface ISpecialAssetManager<T> {
	
	
	public T getSpecialAssetNull();
	public T getSpecialAssetLoadError();
	

	/** Returns whether the given asset can be used as a normal asset.
	 * 
	 * @param asset
	 * @return True if the asset is valid, false if not.
	 */
	public boolean isAssetValid(T asset);
	
	/** Returns whether the given asset is special.
	 * 
	 * @param asset
	 * @return True if the asset if special, false if not.
	 */
	public boolean isAssetSpecial(T asset);
	
	
	// TODO: Implement asset flags ? AssetFlags is flag enum
	//public AssetFlags getAssetFlags(T asset);
	//public boolean hasAssetFlag(T asset, AssetFlags assetFlags);
	
}
