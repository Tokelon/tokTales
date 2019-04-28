package com.tokelon.toktales.core.content.manage.resources;

import java.io.InputStream;

import com.tokelon.toktales.core.content.manage.IManagedAssetReader;
import com.tokelon.toktales.core.engine.content.ContentException;
import com.tokelon.toktales.core.util.options.IOptions;

public interface IResourceScannerAssetReader extends IManagedAssetReader {
	
	public static final String OPTION_RESOURCE_LOOKUP_FILTER_TYPE = "OPTION_RESOURCE_LOOKUP_FILTER_TYPE";
	public static final String OPTION_RESOURCE_LOOKUP_NAME_MATCHING_TYPE = "OPTION_RESOURCE_LOOKUP_NAME_MATCHING_TYPE";
	
	
	public InputStream readAsset(String resourceName, IOptions options) throws ContentException;
	
}
