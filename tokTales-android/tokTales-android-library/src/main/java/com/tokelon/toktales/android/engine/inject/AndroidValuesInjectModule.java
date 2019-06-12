package com.tokelon.toktales.android.engine.inject;

import java.io.File;

import com.tokelon.toktales.core.engine.inject.AbstractInjectModule;
import com.tokelon.toktales.core.engine.inject.annotation.StorageRoot;

import android.os.Environment;

public class AndroidValuesInjectModule extends AbstractInjectModule {


	public static final String DEFAULT_STORAGE_DIR_NAME = "Tokelon";
	
	
	@Override
	protected void configure() {
		bind(String.class).annotatedWith(StorageRoot.class).toProvider(() -> new File(Environment.getExternalStorageDirectory(), DEFAULT_STORAGE_DIR_NAME).getPath());
	}
	
}
