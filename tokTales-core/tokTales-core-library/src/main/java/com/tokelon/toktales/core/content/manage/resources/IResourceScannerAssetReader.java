package com.tokelon.toktales.core.content.manage.resources;

import java.io.InputStream;

import com.tokelon.toktales.tools.assets.exception.AssetException;
import com.tokelon.toktales.tools.assets.reader.IManagedAssetReader;
import com.tokelon.toktales.tools.core.objects.options.IOptions;

public interface IResourceScannerAssetReader extends IManagedAssetReader {
	
	public static final String OPTION_RESOURCE_LOOKUP_FILTER_TYPE = "OPTION_RESOURCE_LOOKUP_FILTER_TYPE";
	public static final String OPTION_RESOURCE_LOOKUP_NAME_MATCHING_TYPE = "OPTION_RESOURCE_LOOKUP_NAME_MATCHING_TYPE";
	
	
	public InputStream readAsset(String resourceName, IOptions options) throws AssetException;
	
}
