package com.tokelon.toktales.desktop.engine.inject;

import java.io.File;

import com.tokelon.toktales.core.engine.inject.AbstractInjectModule;
import com.tokelon.toktales.core.engine.inject.annotation.ContentRoot;
import com.tokelon.toktales.core.engine.inject.annotation.StorageRoot;

public class DesktopValuesInjectModule extends AbstractInjectModule {


	private static final String DATA_LOCATION_NAME = "Data";

	private static final String STORAGE_LOCATION_NAME = "Storage";
	private static final String CONTENT_LOCATION_NAME = "Assets";
	
	
	@Override
	protected void configure() {
		bind(String.class).annotatedWith(StorageRoot.class).toInstance(new File(DATA_LOCATION_NAME, STORAGE_LOCATION_NAME).getPath());
		bind(String.class).annotatedWith(ContentRoot.class).toInstance(new File(DATA_LOCATION_NAME, CONTENT_LOCATION_NAME).getPath());
	}
	
}
