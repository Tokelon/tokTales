package com.tokelon.toktales.core.content.manage.resources;

import java.io.InputStream;

import javax.inject.Inject;

import com.tokelon.toktales.core.content.IResourceManager;
import com.tokelon.toktales.core.resources.IListing.FileDescriptor;
import com.tokelon.toktales.tools.assets.exception.AssetException;
import com.tokelon.toktales.tools.assets.exception.AssetNotFoundException;
import com.tokelon.toktales.tools.assets.key.IReadDelegateAssetKey;
import com.tokelon.toktales.tools.core.objects.options.IOptions;
import com.tokelon.toktales.core.resources.IResourceLookup;
import com.tokelon.toktales.core.resources.ResourceLookup;
import com.tokelon.toktales.core.resources.ResourceTypeFilter;

public class ResourceScannerAssetReader implements IResourceScannerAssetReader {
	
	
	private final IResourceManager resourceManager;
	private final IResourceAssetReader resourceAssetReader;

	@Inject
	public ResourceScannerAssetReader(IResourceManager resourceManager, IResourceAssetReader resourceAssetReader) {
		this.resourceManager = resourceManager;
		this.resourceAssetReader = resourceAssetReader;
	}

	
	@Override
	public boolean canRead(Object key, Object options) {
		return IReadDelegateAssetKey.getReadableKey(key) instanceof IResourceScannerKey;
	}

	@Override
	public InputStream read(Object key, Object options) throws AssetException {
		Object readableKey = IReadDelegateAssetKey.getReadableKey(key);

		if(!(readableKey instanceof IResourceScannerKey)) {
			throw new AssetException("Unsupported key: must be instance of " + IResourceScannerKey.class.getName());
		}
		IResourceScannerKey resourceScannerKey = (IResourceScannerKey) readableKey;
		IOptions iOptions = options instanceof IOptions ? (IOptions) options : null;
		
		return readAsset(resourceScannerKey.getResourceName(), iOptions);
	}
	
	
	@Override
	public InputStream readAsset(String resourceName, IOptions options) throws AssetException {
		ResourceTypeFilter filterType = options == null ? null : options.getAsOrDefault(OPTION_RESOURCE_LOOKUP_FILTER_TYPE, null, ResourceTypeFilter.class);
		int matchingType = options == null ? IResourceLookup.STRING_FILTER_TYPE_EQUALS : options.getAsOrDefault(OPTION_RESOURCE_LOOKUP_NAME_MATCHING_TYPE, IResourceLookup.STRING_FILTER_TYPE_EQUALS, Integer.class);
		
		FileDescriptor resourceFileDescriptor = null;
		
		ResourceLookup resourceLookup = ResourceLookup.getObjectPool().newObject();
		resourceLookup.objectReset();
		try {
			resourceLookup.setFilterType(filterType);
			resourceLookup.setNameMatchingType(matchingType);
			resourceLookup.setFilterName(resourceName);
			
			resourceFileDescriptor = resourceManager.lookForFileMatching(resourceLookup);
		} finally {
			ResourceLookup.getObjectPool().free(resourceLookup);
		}
		
		if(resourceFileDescriptor == null) {
			throw new AssetNotFoundException("No resource found in listings for name: " + resourceName);
		}
		
		return resourceAssetReader.readAsset(resourceFileDescriptor.getLocation(), resourceFileDescriptor.getName(), options);
	}

}
