package com.tokelon.toktales.desktop.content;

import java.io.InputStream;
import java.util.Map;

import javax.inject.Inject;

import com.tokelon.toktales.core.engine.AbstractEngineService;
import com.tokelon.toktales.core.engine.content.ContentException;
import com.tokelon.toktales.core.engine.content.IContentService;
import com.tokelon.toktales.core.engine.inject.annotation.ContentRoot;
import com.tokelon.toktales.core.engine.inject.annotation.services.ContentServiceExtensions;
import com.tokelon.toktales.core.engine.inject.annotation.services.StorageServiceExtensions;
import com.tokelon.toktales.core.engine.log.ILogging;
import com.tokelon.toktales.core.engine.storage.IStorageService;
import com.tokelon.toktales.core.engine.storage.IStorageService.IStorageServiceFactory;
import com.tokelon.toktales.core.engine.storage.StorageException;
import com.tokelon.toktales.core.location.IApplicationLocation;
import com.tokelon.toktales.core.resources.IListing;

public class DesktopContentService extends AbstractEngineService implements IContentService {


	private final IStorageService assetStorageService;

	public DesktopContentService(
			ILogging logging,
			IStorageServiceFactory storageServiceFactory,
			@ContentRoot String contentRoot
	) {
		this.assetStorageService = storageServiceFactory.create(contentRoot);
	}
	
	@Inject
	public DesktopContentService(
			ILogging logging,
			IStorageServiceFactory storageServiceFactory,
			@ContentRoot String contentRoot,
			@ContentServiceExtensions Map<String, IServiceExtension> contentExtensions,
			@StorageServiceExtensions Map<String, IServiceExtension> storageExtensions
	) {
		super(contentExtensions);
		
		this.assetStorageService = storageServiceFactory.create(contentRoot, storageExtensions);
	}
	
	
	@Override
	public String getRoot() {
		return assetStorageService.getRoot();
	}
	
	@Override
	public String[] listAssetDir(IApplicationLocation location) throws ContentException {
		try {
			return assetStorageService.listAppDirOnExternal(location);
		} catch (StorageException e) {
			throw new ContentException(e);
		}
	}

	@Override
	public IListing createAssetListing(IApplicationLocation location) throws ContentException {
		try {
			return assetStorageService.createFileListing(location);
		} catch (StorageException e) {
			throw new ContentException(e);
		}
	}
	
	
	@Override
	public InputStream readAppFileOnAssets(IApplicationLocation location, String fileName) throws ContentException {
		try {
			return assetStorageService.readAppFileOnExternal(location, fileName);
		} catch (StorageException e) {
			throw new ContentException(e);
		}
	}
	
	@Override
	public InputStream tryReadAppFileOnAssets(IApplicationLocation location, String fileName) {
		return assetStorageService.tryReadAppFileOnExternal(location, fileName);
	}
	
}
