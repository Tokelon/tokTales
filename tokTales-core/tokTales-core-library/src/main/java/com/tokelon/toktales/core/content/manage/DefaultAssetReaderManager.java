package com.tokelon.toktales.core.content.manage;

import java.lang.reflect.Type;

import javax.inject.Inject;

import com.tokelon.toktales.core.content.manage.keys.IReadDelegateAssetKey;
import com.tokelon.toktales.core.util.manage.ObjectOrganizer;

public class DefaultAssetReaderManager extends ObjectOrganizer<Type, IManagedAssetReader> implements IAssetReaderManager {

	
	@Inject
	public DefaultAssetReaderManager() { }
	
	
	@Override
	public IAssetReader findReader(Object key, Object options) {
		Object readableKey = IReadDelegateAssetKey.getReadableKey(key);
		
		IManagedAssetReader foundReader = null;
		for(IManagedAssetReader reader: getObjectMap().values()) {
			if(reader.canRead(readableKey, options)) {
				foundReader = reader;
				break;
			}
		}

		return foundReader;
	}

}
