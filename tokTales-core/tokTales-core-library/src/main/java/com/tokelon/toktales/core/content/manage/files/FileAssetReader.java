package com.tokelon.toktales.core.content.manage.files;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.tokelon.toktales.core.content.manage.keys.IReadDelegateAssetKey;
import com.tokelon.toktales.core.engine.content.ContentException;
import com.tokelon.toktales.core.util.options.IOptions;

public class FileAssetReader implements IFileAssetReader {

	
	@Override
	public boolean canRead(Object key, Object options) {
		return IReadDelegateAssetKey.getReadableKey(key) instanceof IFileKey;
	}
	
	@Override
	public InputStream read(Object key, Object options) throws ContentException {
		Object readableKey = IReadDelegateAssetKey.getReadableKey(key);
		
		if(!(readableKey instanceof IFileKey)) {
			throw new ContentException("Unsupported key: must be instance of " + IFileKey.class.getName());
		}
		IFileKey fileKey = (IFileKey) readableKey;
		IOptions iOptions = options instanceof IOptions ? (IOptions) options : null;

		return readAsset(fileKey.getFile(), iOptions);
	}
	
	
	@Override
	public InputStream readAsset(File file, IOptions options) throws ContentException {
		try {
			return openStream(file);
		} catch (IOException e) {
			throw new ContentException(e);
		}
	}
	
	protected InputStream openStream(File file) throws IOException, ContentException {
		if(file.canRead()) { // TODO: File.isDirectory?
			return new FileInputStream(file);
			//return Files.asByteSource(file).openStream();
		}
		else {
			throw new ContentException("File is not readable: " + file);
		}
	}

}
