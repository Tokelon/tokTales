package com.tokelon.toktales.extens.def.android.engine;

import static org.mockito.Mockito.mock;

import org.junit.Test;

import com.google.inject.Injector;
import com.tokelon.toktales.android.engine.inject.AndroidSetupInjectModule;
import com.tokelon.toktales.core.game.IGameAdapter;

import android.content.Context;

public class TestAndroidDefaultExtensionsInjection {
	
	private static final Context mockedContext = mock(Context.class);

	
	@Test
	public void injectorCreationWithSetupModule_ShouldSucceed() {
		AndroidDefaultExtensionsInjectConfig injectConfig = new AndroidDefaultExtensionsInjectConfig();
		
		injectConfig.override(new AndroidSetupInjectModule(mockedContext, new IGameAdapter.EmptyGameAdapter()));
		
		Injector injector = injectConfig.createInjector();
	}
	
}
