package com.tokelon.toktales.android.engine.inject;

import com.tokelon.toktales.android.data.AndroidContentUtils;
import com.tokelon.toktales.core.content.IContentUtils;
import com.tokelon.toktales.core.engine.inject.AbstractInjectModule;

public class AndroidPlatformOverrideInjectModule extends AbstractInjectModule {


	@Override
	protected void configure() {
		super.configure();
		
		bind(IContentUtils.class).to(AndroidContentUtils.class);
	}
	
}
