package com.tokelon.toktales.android.engine.inject;

import com.tokelon.toktales.android.content.AndroidContentUtils;
import com.tokelon.toktales.android.content.AndroidRelativeFileAssetReader;
import com.tokelon.toktales.android.content.IAndroidRelativeFileAssetReader;
import com.tokelon.toktales.core.content.IContentUtils;
import com.tokelon.toktales.core.engine.inject.AbstractInjectModule;
import com.tokelon.toktales.tools.assets.files.IRelativeFileAssetReader;

public class AndroidPlatformOverrideInjectModule extends AbstractInjectModule {


	@Override
	protected void configure() {
		super.configure();

		bind(IContentUtils.class).to(AndroidContentUtils.class);

		bind(IRelativeFileAssetReader.class).to(IAndroidRelativeFileAssetReader.class);
		bind(IAndroidRelativeFileAssetReader.class).to(AndroidRelativeFileAssetReader.class);
	}

}
