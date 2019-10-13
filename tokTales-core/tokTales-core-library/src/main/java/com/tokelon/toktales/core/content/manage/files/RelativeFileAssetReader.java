package com.tokelon.toktales.core.content.manage.files;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

import javax.inject.Inject;

import com.tokelon.toktales.core.content.manage.keys.IReadDelegateAssetKey;
import com.tokelon.toktales.core.engine.content.AssetException;
import com.tokelon.toktales.core.engine.inject.annotation.ParentResolvers;
import com.tokelon.toktales.tools.core.objects.options.IOptions;

public class RelativeFileAssetReader implements IRelativeFileAssetReader {
	

	private final Set<IParentResolver<File>> parentResolvers;

	@Inject
	public RelativeFileAssetReader(@ParentResolvers Set<IParentResolver<File>> parentResolvers) {
		this.parentResolvers = parentResolvers;
	}

	
	@Override
	public boolean canRead(Object key, Object options) {
		return IReadDelegateAssetKey.getReadableKey(key) instanceof IRelativeFileKey;
		// TODO: parentResolvers any where can resolve?
	}
	
	@Override
	public InputStream read(Object key, Object options) throws AssetException {
		Object readableKey = IReadDelegateAssetKey.getReadableKey(key);
		
		if(!(readableKey instanceof IRelativeFileKey)) {
			throw new AssetException("Unsupported key: must be instance of " + IRelativeFileKey.class.getName());
		}
		IRelativeFileKey fileKey = (IRelativeFileKey) readableKey;
		IOptions iOptions = options instanceof IOptions ? (IOptions) options : null;

		return readAsset(fileKey.getFile(), fileKey.getParentIdentifier(), iOptions);
	}
	
	
	@Override
	public InputStream readAsset(File file, Object parentIdentifier, IOptions options) throws AssetException {
		if(parentIdentifier == null) {
			// TODO: Use file as absolut?
		}
		
		File readableFile = null;
		for (IParentResolver<File> parentResolver : parentResolvers) {
			readableFile = parentResolver.resolve(file, parentIdentifier);
			if(readableFile != null) {
				break;
			}
		}
		
		if(readableFile == null) {
			throw new AssetException(String.format("Unsupported parent [%s] for file [%s]", parentIdentifier, file));
		}
		
		try {
			return openStream(readableFile);
		} catch (IOException e) {
			throw new AssetException(e);
		}
	}
	
	protected InputStream openStream(File file) throws IOException, AssetException {
		if(file.canRead()) { // TODO: File.isDirectory?
			return new FileInputStream(file);
			//return Files.asByteSource(file).openStream(); // Guava way
		}
		else {
			throw new AssetException("File is not readable: " + file);
		}
	}

}
