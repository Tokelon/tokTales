package com.tokelon.toktales.core.content.manage.contents;

import java.io.InputStream;

import javax.inject.Inject;

import com.tokelon.toktales.core.engine.content.ContentException;
import com.tokelon.toktales.core.engine.content.IContentService;
import com.tokelon.toktales.core.location.IApplicationLocation;
import com.tokelon.toktales.tools.assets.exception.AssetException;
import com.tokelon.toktales.tools.assets.key.IReadDelegateAssetKey;
import com.tokelon.toktales.tools.core.objects.options.IOptions;

public class ContentAssetReader implements IContentAssetReader {


	private final IContentService contentService;

	@Inject
	public ContentAssetReader(IContentService contentService) {
		this.contentService = contentService;
	}
	
	
	@Override
	public boolean canRead(Object key, Object options) {
		return IReadDelegateAssetKey.getReadableKey(key) instanceof IContentKey;
	}

	@Override
	public InputStream read(Object key, Object options) throws AssetException {
		Object readableKey = IReadDelegateAssetKey.getReadableKey(key);
		
		if(!(readableKey instanceof IContentKey)) {
			throw new AssetException("Unsupported key: must be instance of " + IContentKey.class.getName());
		}
		IContentKey contentKey = (IContentKey) readableKey;
		IOptions iOptions = options instanceof IOptions ? (IOptions) options : null;

		return readAsset(contentKey.getName(), contentKey.getLocation(), iOptions);
	}

	
	@Override
	public InputStream readAsset(String name, IApplicationLocation location, IOptions options) throws AssetException {
		try {
			return contentService.readAppFileOnAssets(location, name);
		} catch (ContentException e) {
			throw new AssetException(e);
		}
	}

}
