package com.tokelon.toktales.core.content.manage;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;

import com.tokelon.toktales.core.content.manage.keys.IReadDelegateAssetKey;
import com.tokelon.toktales.core.engine.inject.annotation.AssetReaders;
import com.tokelon.toktales.tools.core.objects.managers.ObjectOrganizer;

public class DefaultAssetReaderManager extends ObjectOrganizer<Type, IManagedAssetReader> implements IAssetReaderManager {

	
	@Inject
	public DefaultAssetReaderManager(@AssetReaders Map<Type, IManagedAssetReader> assetReaders) {
		for (Entry<Type, IManagedAssetReader> assetReaderEntry : assetReaders.entrySet()) {
			add(assetReaderEntry.getKey(), assetReaderEntry.getValue());
		}
	}
	
	
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
