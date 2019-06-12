package com.tokelon.toktales.desktop.engine.inject;

import java.io.File;

import com.tokelon.toktales.core.engine.inject.AbstractInjectModule;
import com.tokelon.toktales.core.engine.inject.annotation.StorageRoot;
import com.tokelon.toktales.desktop.engine.inject.annotation.AssetRoot;

public class DesktopValuesInjectModule extends AbstractInjectModule {


	private static final String DATA_LOCATION_NAME = "Data";            // This is the DATA location

	private static final String STORAGE_LOCATION_NAME = "StorageData";  // This is the STORAGE location
	private static final String CONTENT_LOCATION_NAME = "ContentData";  // This is the CONTENT location
	
	
	@Override
	protected void configure() {
		bind(String.class).annotatedWith(StorageRoot.class).toInstance(new File(DATA_LOCATION_NAME, STORAGE_LOCATION_NAME).getPath());
		bind(String.class).annotatedWith(AssetRoot.class).toInstance(new File(DATA_LOCATION_NAME, CONTENT_LOCATION_NAME).getPath());
	}
	
}
