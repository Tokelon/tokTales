package com.tokelon.toktales.core.content.manage.files;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Set;

import javax.inject.Inject;

import com.tokelon.toktales.core.engine.inject.annotation.ParentResolvers;
import com.tokelon.toktales.tools.assets.exception.AssetException;
import com.tokelon.toktales.tools.assets.key.IReadDelegateAssetKey;
import com.tokelon.toktales.tools.core.objects.options.IOptions;

public class RelativePathAssetReader implements IRelativePathAssetReader {

	
	private final Set<IParentResolver<Path>> parentResolvers;

	@Inject
	public RelativePathAssetReader(@ParentResolvers Set<IParentResolver<Path>> parentResolvers) {
		this.parentResolvers = parentResolvers;
	}

	
	@Override
	public boolean canRead(Object key, Object options) {
		return IReadDelegateAssetKey.getReadableKey(key) instanceof IRelativePathKey;
		// TODO: parentResolvers any where can resolve?
	}
	
	@Override
	public InputStream read(Object key, Object options) throws AssetException {
		Object readableKey = IReadDelegateAssetKey.getReadableKey(key);
		
		if(!(readableKey instanceof IRelativePathKey)) {
			throw new AssetException("Unsupported key: must be instance of " + IRelativePathKey.class.getName());
		}
		IRelativePathKey pathKey = (IRelativePathKey) readableKey;
		IOptions iOptions = options instanceof IOptions ? (IOptions) options : null;

		return readAsset(pathKey.getPath(), pathKey.getParentIdentifier(), iOptions);
	}
	
	
	@Override
	public InputStream readAsset(Path path, Object parentIdentifier, IOptions options) throws AssetException {
		if(parentIdentifier == null) {
			// TODO: Use path as absolut?
		}
		
		Path readablePath = null;
		for (IParentResolver<Path> parentResolver : parentResolvers) {
			readablePath = parentResolver.resolve(path, parentIdentifier);
			if(readablePath != null) {
				break;
			}
		}
		
		if(readablePath == null) {
			throw new AssetException(String.format("Unsupported parent [%s] for path [%s]", parentIdentifier, path));
		}
		
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
