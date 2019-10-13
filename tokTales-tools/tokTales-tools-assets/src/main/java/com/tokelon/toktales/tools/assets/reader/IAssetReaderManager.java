package com.tokelon.toktales.tools.assets.reader;

import java.lang.reflect.Type;

import com.tokelon.toktales.tools.core.objects.managers.ISealedObjectOrganizer.IObjectOrganizer;

public interface IAssetReaderManager extends IObjectOrganizer<Type, IManagedAssetReader> {
	// TODO: Maybe use String instead of Type as it might convey that a reader only supports one single type
	
	
	/** Returns a reader that can read the asset for the given key and options.
	 * 
	 * @param key
	 * @param options
	 * @return A reader, or null if none could be found.
	 */
	public IAssetReader findReader(Object key, Object options);

}
