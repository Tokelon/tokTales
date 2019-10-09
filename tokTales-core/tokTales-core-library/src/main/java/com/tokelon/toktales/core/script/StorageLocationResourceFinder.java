package com.tokelon.toktales.core.script;

import java.io.InputStream;

import com.tokelon.toktales.core.engine.storage.IStorageService;
import com.tokelon.toktales.core.storage.IApplicationLocation;
import com.tokelon.toktales.tools.core.script.IResourceFinder;

public class StorageLocationResourceFinder implements IResourceFinder {
	
	
	private final IStorageService mStorageService;
	private final IApplicationLocation mLocation;
	
	public StorageLocationResourceFinder(IStorageService storageService, IApplicationLocation location) {
		mStorageService = storageService;
		mLocation = location;
	}

	
	
	@Override
	public InputStream findResource(String filename) {
		return mStorageService.tryReadAppFileOnExternal(mLocation, filename);
	}

}
