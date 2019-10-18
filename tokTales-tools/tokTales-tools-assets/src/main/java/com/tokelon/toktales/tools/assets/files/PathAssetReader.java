package com.tokelon.toktales.tools.assets.files;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import com.tokelon.toktales.tools.assets.exception.AssetException;
import com.tokelon.toktales.tools.assets.key.IReadDelegateAssetKey;
import com.tokelon.toktales.tools.core.objects.options.IOptions;

public class PathAssetReader implements IPathAssetReader {

	
	@Override
	public boolean canRead(Object key, Object options) {
		return IReadDelegateAssetKey.getReadableKey(key) instanceof IPathKey;
	}
	
	@Override
	public InputStream read(Object key, Object options) throws AssetException {
		Object readableKey = IReadDelegateAssetKey.getReadableKey(key);
		
		if(!(readableKey instanceof IPathKey)) {
			throw new AssetException("Unsupported key: must be instance of " + IPathKey.class.getName());
		}
		IPathKey pathKey = (IPathKey) readableKey;
		IOptions iOptions = options instanceof IOptions ? (IOptions) options : null;

		return readAsset(pathKey.getPath(), iOptions);
	}
	
	
	@Override
	public InputStream readAsset(Path path, IOptions options) throws AssetException {
		try {
			return openStream(path);
		} catch (IOException e) {
			throw new AssetException(e);
		}
	}
	
	protected InputStream openStream(Path path) throws IOException, AssetException {
		if(Files.isReadable(path)) { // TODO: File.isDirectory?
			return Files.newInputStream(path, StandardOpenOption.READ);
		}
		else {
			throw new AssetException("File is not readable: " + path);
		}
	}

}
