package com.tokelon.toktales.core.content.manage.resources;

import java.io.InputStream;

import javax.inject.Inject;

import com.tokelon.toktales.core.content.manage.keys.IReadDelegateAssetKey;
import com.tokelon.toktales.core.engine.content.ContentException;
import com.tokelon.toktales.core.engine.content.ContentLoadException;
import com.tokelon.toktales.core.engine.content.IContentService;
import com.tokelon.toktales.core.engine.storage.IStorageService;
import com.tokelon.toktales.core.engine.storage.StorageException;
import com.tokelon.toktales.core.resources.IResource;
import com.tokelon.toktales.core.storage.IStructuredLocation;
import com.tokelon.toktales.core.storage.utils.ApplicationLocationWrapper;
import com.tokelon.toktales.tools.core.objects.options.IOptions;

public class ResourceAssetReader implements IResourceAssetReader {

	
	private final IStorageService storageService;
	private final IContentService contentService;

	@Inject
	public ResourceAssetReader(IStorageService storageService, IContentService contentService) {
		this.storageService = storageService;
		this.contentService = contentService;
	}
	

	@Override
	public boolean canRead(Object key, Object options) {
		return IReadDelegateAssetKey.getReadableKey(key) instanceof IResourceKey;
	}
	
	@Override
	public InputStream read(Object key, Object options) throws ContentException {
		Object readableKey = IReadDelegateAssetKey.getReadableKey(key);

		if(!(readableKey instanceof IResourceKey)) {
			throw new ContentException("Unsupported key: must be instance of " + IResourceKey.class.getName());
		}
		IResourceKey resourceKey = (IResourceKey) readableKey;
		IOptions iOptions = options instanceof IOptions ? (IOptions) options : null;

		return readAsset(resourceKey.getResource(), iOptions);
	}
	
	
	@Override
	public InputStream readAsset(IResource resource, IOptions options) throws ContentException {
		return readAsset(resource.getLocation(), resource.getName(), options);
	}
	
	@Override
	public InputStream readAsset(IStructuredLocation location, String fileName, IOptions options) throws ContentException {
		ApplicationLocationWrapper locationWrapper = ApplicationLocationWrapper.getObjectPool().newObject();
		locationWrapper.objectReset();
		try {
			locationWrapper.setActualLocation(location);
			
			switch(location.getPrefix()) {
			case INTERNAL:	// Have internal the same as asset?
			case ASSET:
				return contentService.readAppFileOnAssets(locationWrapper, fileName);
			case EXTERNAL:
				return storageService.readAppFileOnExternal(locationWrapper, fileName);
			default:
				throw new ContentLoadException(String.format("Unable to load file \"%s\" from location \"%s\" (Unsupported location)", fileName, location.getOriginalValue()));
			}
		} catch (StorageException e) {
			throw new ContentLoadException(e);
		} finally {
			ApplicationLocationWrapper.getObjectPool().free(locationWrapper);
		}
	}

}
