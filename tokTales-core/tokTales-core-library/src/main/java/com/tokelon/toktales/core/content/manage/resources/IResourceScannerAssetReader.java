package com.tokelon.toktales.core.content.manage.resources;

import java.io.InputStream;

import com.tokelon.toktales.core.content.manage.IManagedAssetReader;
import com.tokelon.toktales.core.engine.content.AssetException;
import com.tokelon.toktales.tools.core.objects.options.IOptions;

public interface IResourceScannerAssetReader extends IManagedAssetReader {
	
	public static final String OPTION_RESOURCE_LOOKUP_FILTER_TYPE = "OPTION_RESOURCE_LOOKUP_FILTER_TYPE";
	public static final String OPTION_RESOURCE_LOOKUP_NAME_MATCHING_TYPE = "OPTION_RESOURCE_LOOKUP_NAME_MATCHING_TYPE";
	
	
	public InputStream readAsset(String resourceName, IOptions options) throws AssetException;
	
}
