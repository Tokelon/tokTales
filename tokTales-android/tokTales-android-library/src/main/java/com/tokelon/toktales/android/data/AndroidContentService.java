package com.tokelon.toktales.android.data;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import com.tokelon.toktales.core.engine.AbstractEngineService;
import com.tokelon.toktales.core.engine.content.ContentException;
import com.tokelon.toktales.core.engine.content.IContentService;
import com.tokelon.toktales.core.engine.inject.annotation.services.ContentServiceExtensions;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.resources.IListing;
import com.tokelon.toktales.core.resources.Listing;
import com.tokelon.toktales.core.storage.IApplicationLocation;
import com.tokelon.toktales.core.storage.LocationPrefix;
import com.tokelon.toktales.core.storage.utils.StructuredLocation;

import android.content.Context;

public class AndroidContentService extends AbstractEngineService implements IContentService {
	
	public static final String TAG = "AndroidContentService";

	
	private final Context globalContext;
	private final ILogger logger;
	
	public AndroidContentService(ILogger logger, Context globalContext) {
		this.logger = logger;
		this.globalContext = globalContext;
	}
	
	@Inject
	public AndroidContentService(ILogger logger, Context globalContext, @ContentServiceExtensions Map<String, IServiceExtension> extensions) {
		super(extensions);
		
		this.logger = logger;
		this.globalContext = globalContext;
	}
	
	
	public Context getGlobalContext() {
		return globalContext;
	}

	
	@Override
	public String[] listAssetDir(IApplicationLocation location) throws ContentException {
		String[] list;
		try {
			list = globalContext.getAssets().list(location.getLocationPath().getLocation());
		} catch (IOException e) {
			throw new ContentException(e);
		}
		
		return list;
	}
	
	
	@Override
	public IListing createAssetListing(IApplicationLocation location) throws ContentException {
		// Currently only lists files at location and does not follow directories
		
		String[] fileList = listAssetDir(location);
		
		String locationPath = location.getLocationPath().getPath();
		Listing listing = new Listing(locationPath);
		List<IListing.FileDescriptor> listingFileList = listing.getFileList();
		
		
		/* Note that the location will be the same object for all file descriptors.
		 * This is fine as long as they all are in the same location and because StructuredLocation is immutable. 
		 */
		StructuredLocation singleLocation = new StructuredLocation(LocationPrefix.ASSET, location.getLocationPath().getLocation());
		for(String fileName: fileList) {
			IListing.FileDescriptor fd = new Listing.FileDescriptorImpl(fileName, singleLocation);
			listingFileList.add(fd);
		}
		
		return listing;
	}
	
	
	@Override
	public InputStream readAppFileOnAssets(IApplicationLocation location, String fileName) throws ContentException {
		try {
			return globalContext.getAssets().open(location.getLocationPath().getPath() + fileName);
		} catch (IOException e) {
			throw new ContentException(e);
		}
	}
	
	@Override
	public InputStream tryReadAppFileOnAssets(IApplicationLocation location, String fileName) {
		try {
			return readAppFileOnAssets(location, fileName);
		} catch (ContentException e) {
			logger.i(TAG, "Failed to read app file on assets: " + e);
			return null;
		}
	}
	
}
