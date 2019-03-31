package com.tokelon.toktales.core.content.manage.files;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import com.tokelon.toktales.core.content.manage.IManagedAssetReader;
import com.tokelon.toktales.core.engine.content.ContentException;

public class FileAssetReader implements IManagedAssetReader {

	
	@Override
	public boolean canRead(Object key, Object options) {
		return key instanceof IFileKey;
	}
	
	@Override
	public InputStream read(Object key, Object options) throws ContentException {
		if(!(key instanceof IFileKey)) {
			throw new ContentException("Unsupported key: must be instance of " + IFileKey.class.getName());
		}
		IFileKey fileKey = (IFileKey) key;
		
		try {
			return openStream(fileKey);
		} catch (IOException e) {
			throw new ContentException(e);
		}
	}
	
	
	protected InputStream openStream(IFileKey fileKey) throws IOException, ContentException {
		Path path = fileKey.getPath();
		if(Files.isReadable(path)) { // TODO: File.isDirectory?
			return Files.newInputStream(path, StandardOpenOption.READ);
		}
		else {
			throw new ContentException("File is not readable: " + path);
		}
	}

}
