package com.tokelon.toktales.tools.assets.files;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.tokelon.toktales.tools.assets.exception.AssetException;
import com.tokelon.toktales.tools.assets.key.IReadDelegateAssetKey;
import com.tokelon.toktales.tools.core.objects.options.IOptions;

public class FileAssetReader implements IFileAssetReader {

	
	@Override
	public boolean canRead(Object key, Object options) {
		return IReadDelegateAssetKey.getReadableKey(key) instanceof IFileKey;
	}
	
	@Override
	public InputStream read(Object key, Object options) throws AssetException {
		Object readableKey = IReadDelegateAssetKey.getReadableKey(key);
		
		if(!(readableKey instanceof IFileKey)) {
			throw new AssetException("Unsupported key: must be instance of " + IFileKey.class.getName());
		}
		IFileKey fileKey = (IFileKey) readableKey;
		IOptions iOptions = options instanceof IOptions ? (IOptions) options : null;

		return readAsset(fileKey.getFile(), iOptions);
	}
	
	
	@Override
	public InputStream readAsset(File file, IOptions options) throws AssetException {
		try {
			return openStream(file);
		} catch (IOException e) {
			throw new AssetException(e);
		}
	}
	
	protected InputStream openStream(File file) throws IOException, AssetException {
		if(file.canRead()) { // TODO: File.isDirectory?
			return new FileInputStream(file);
			//return Files.asByteSource(file).openStream();
		}
		else {
			throw new AssetException("File is not readable: " + file);
		}
	}

}
