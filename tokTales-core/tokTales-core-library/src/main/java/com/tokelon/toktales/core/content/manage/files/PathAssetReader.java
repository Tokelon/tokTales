package com.tokelon.toktales.core.content.manage.files;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import com.tokelon.toktales.core.content.manage.keys.IReadDelegateAssetKey;
import com.tokelon.toktales.core.engine.content.ContentException;
import com.tokelon.toktales.core.util.options.IOptions;

public class PathAssetReader implements IPathAssetReader {

	
	@Override
	public boolean canRead(Object key, Object options) {
		return IReadDelegateAssetKey.getReadableKey(key) instanceof IPathKey;
	}
	
	@Override
	public InputStream read(Object key, Object options) throws ContentException {
		Object readableKey = IReadDelegateAssetKey.getReadableKey(key);
		
		if(!(readableKey instanceof IPathKey)) {
			throw new ContentException("Unsupported key: must be instance of " + IPathKey.class.getName());
		}
		IPathKey pathKey = (IPathKey) readableKey;
		IOptions iOptions = options instanceof IOptions ? (IOptions) options : null;

		return readAsset(pathKey.getPath(), iOptions);
	}
	
	
	@Override
	public InputStream readAsset(Path path, IOptions options) throws ContentException {
		try {
			return openStream(path);
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
