package com.tokelon.toktales.android.content;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import com.tokelon.toktales.core.engine.AbstractEngineService;
import com.tokelon.toktales.core.engine.content.ContentException;
import com.tokelon.toktales.core.engine.content.IContentService;
import com.tokelon.toktales.core.engine.inject.annotation.ContentRoot;
import com.tokelon.toktales.core.engine.inject.annotation.services.ContentServiceExtensions;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.engine.log.ILogging;
import com.tokelon.toktales.core.location.IApplicationLocation;
import com.tokelon.toktales.core.location.ILocationPath;
import com.tokelon.toktales.core.location.LocationScheme;
import com.tokelon.toktales.core.location.MutableLocationPath;
import com.tokelon.toktales.core.location.UniformLocation;
import com.tokelon.toktales.core.resources.IListing;
import com.tokelon.toktales.core.resources.Listing;

import android.content.Context;

public class AndroidContentService extends AbstractEngineService implements IContentService {


	private final ILocationPath contentPath;
	private final String contentRoot;
	private final Context globalContext;
	private final ILogger logger;
	
	public AndroidContentService(
			ILogging logging,
			Context globalContext,
			@ContentRoot String contentRoot
	) {
		this.logger = logging.getLogger(getClass());
		this.globalContext = globalContext;
		this.contentRoot = contentRoot;
		this.contentPath = new MutableLocationPath(contentRoot);
	}
	
	@Inject
	public AndroidContentService(
			ILogging logging,
			Context globalContext,
			@ContentRoot String contentRoot,
			@ContentServiceExtensions Map<String, IServiceExtension> extensions
	) {
		super(extensions);
		
		this.logger = logging.getLogger(getClass());
		this.globalContext = globalContext;
		this.contentRoot = contentRoot;
		this.contentPath = new MutableLocationPath(contentRoot);
	}
	
	
	public Context getGlobalContext() {
		return globalContext;
	}

	
	@Override
	public String getRoot() {
		return contentRoot;
	}
	
	@Override
	public String[] listAssetDir(IApplicationLocation location) throws ContentException {
		String[] list;
		try {
			list = globalContext.getAssets().list(contentPath.getChildLocation(location.getLocationPath().getLocation()));
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
		UniformLocation singleLocation = new UniformLocation(LocationScheme.CONTENT, location.getLocationPath().getLocation());
		for(String fileName: fileList) {
			IListing.FileDescriptor fd = new Listing.FileDescriptorImpl(fileName, singleLocation);
			listingFileList.add(fd);
		}
		
		return listing;
	}
	
	
	@Override
	public InputStream readAppFileOnAssets(IApplicationLocation location, String fileName) throws ContentException {
		try {
			return globalContext.getAssets().open(contentPath.getChildLocation(location.getLocationPath().getPath() + fileName));
		} catch (IOException e) {
			throw new ContentException(e);
		}
	}
	
	@Override
	public InputStream tryReadAppFileOnAssets(IApplicationLocation location, String fileName) {
		try {
			return readAppFileOnAssets(location, fileName);
		} catch (ContentException e) {
			logger.info("Failed to read app file on assets:", e);
			return null;
		}
	}
	
}
