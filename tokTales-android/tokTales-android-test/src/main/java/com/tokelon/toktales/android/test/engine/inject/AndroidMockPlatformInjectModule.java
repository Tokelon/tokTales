package com.tokelon.toktales.android.test.engine.inject;

import static org.mockito.Mockito.mock;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.google.inject.TypeLiteral;
import com.tokelon.toktales.core.engine.inject.AbstractInjectModule;
import com.tokelon.toktales.core.engine.inject.annotation.ParentIdentifiers;
import com.tokelon.toktales.core.engine.inject.annotation.StorageRoot;
import com.tokelon.toktales.core.game.IGameAdapter;
import com.tokelon.toktales.core.test.game.DummyGameAdapter;

import android.content.Context;

/** Desktop inject module used for testing.
 * <p>
 * Mocks dependencies that are normally provided by the user.
 * Also mocks dependencies that are normally provided by the platform,
 * and some quality of life improvements for better testing.
 */
public class AndroidMockPlatformInjectModule extends AbstractInjectModule {

	private static final Context mockedContext = mock(Context.class);

	// TODO: Fix SpriteLoader Exception and refactor better
	
	
	@Override
	protected void configure() {
		bind(IGameAdapter.class).to(DummyGameAdapter.class);
        bind(Context.class).toInstance(mockedContext);
        
        
        // Bind to avoid RuntimeException when trying to access android Environment
		bind(String.class).annotatedWith(StorageRoot.class).toInstance("build/tmp/tests");
		// Maybe mock IStorageService to avoid file changes
		//bind(IStorageService.class).toInstance(mock(IStorageService.class));
		//bind(IStorageService.IStorageServiceFactory.class).toInstance(mock(IStorageService.IStorageServiceFactory.class));
		
		// Bind to override StorageRoot provider that calls Android Environment
		bind(new TypeLiteral<Map<Object, File>>() {}).annotatedWith(ParentIdentifiers.class).toInstance(new HashMap<>());
	}
	
}
