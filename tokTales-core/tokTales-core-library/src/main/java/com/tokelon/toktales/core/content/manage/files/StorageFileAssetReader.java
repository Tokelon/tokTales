package com.tokelon.toktales.core.content.manage.files;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import javax.inject.Inject;

import com.tokelon.toktales.core.content.manage.keys.IReadDelegateAssetKey;
import com.tokelon.toktales.core.engine.content.ContentException;
import com.tokelon.toktales.core.engine.storage.IStorageService;
import com.tokelon.toktales.core.util.options.IOptions;

public class StorageFileAssetReader implements IStorageFileAssetReader {

	
	private final IStorageService storageService;

	@Inject
	public StorageFileAssetReader(IStorageService storageService) {
		this.storageService = storageService;
	}

	
	@Override
	public boolean canRead(Object key, Object options) {
		return IReadDelegateAssetKey.getReadableKey(key) instanceof IStorageFileKey;
	}
	
	@Override
	public InputStream read(Object key, Object options) throws ContentException {
		Object readableKey = IReadDelegateAssetKey.getReadableKey(key);
		
		if(!(readableKey instanceof IStorageFileKey)) {
			throw new ContentException("Unsupported key: must be instance of " + IStorageFileKey.class.getName());
		}
		IStorageFileKey fileKey = (IStorageFileKey) readableKey;
		IOptions iOptions = options instanceof IOptions ? (IOptions) options : null;

		return readAsset(fileKey.getStoragePath(), iOptions);
	}
	
	
	@Override
	public InputStream readAsset(Path storagePath, IOptions options) throws ContentException {
		Path fullPath = storageService.getRootPath().resolve(storagePath);
		try {
			return openStream(fullPath);
		} catch (IOException e) {
			throw new ContentException(e);
		}
	}
	
	protected InputStream openStream(Path path) throws IOException, ContentException {
		if(Files.isReadable(path)) { // TODO: File.isDirectory?
			return Files.newInputStream(path, StandardOpenOption.READ);
		}
		else {
			throw new ContentException("File is not readable: " + path);
		}
	}
	
}
