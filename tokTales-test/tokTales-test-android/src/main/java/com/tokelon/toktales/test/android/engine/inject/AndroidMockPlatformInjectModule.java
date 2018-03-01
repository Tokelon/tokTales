package com.tokelon.toktales.test.android.engine.inject;

import static org.mockito.Mockito.mock;

import java.io.File;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.tokelon.toktales.android.storage.AndroidStorageService;
import com.tokelon.toktales.core.engine.AbstractEngineService;
import com.tokelon.toktales.core.engine.inject.AbstractInjectModule;
import com.tokelon.toktales.core.engine.log.ILogService;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.engine.storage.IStorageService;
import com.tokelon.toktales.core.game.IGameAdapter;
import com.tokelon.toktales.test.core.game.DummyGameAdapter;

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
        

        // Bind to avoid RuntimeException when trying to log
        bindInEngineScope(ILogService.class, SysoutLogService.class);
        
        // Bind to avoid RuntimeException when trying to access android Environment
		bindToProviderInEngineScope(IStorageService.class, ProviderIStorageService.class); // Maybe mock as well
	}
	
	
	private static class ProviderIStorageService implements Provider<IStorageService> {
		private final IStorageService storageService;
		@Inject
		public ProviderIStorageService(ILogger logger) {
			storageService = new AndroidStorageService(logger, new File(""));
		}

		@Override
		public IStorageService get() {
			return storageService;
		}
	}
	
	private static class SysoutLogService extends AbstractEngineService implements ILogService {

		@Override
		public void d(String tag, String message) {
			System.out.printf("d | %s : %s", tag, message);
			System.out.println();
		}

		@Override
		public void e(String tag, String message) {
			System.err.printf("e | %s : %s", tag, message);
			System.err.println();
		}

		@Override
		public void w(String tag, String message) {
			System.out.printf("w | %s : %s", tag, message);
			System.out.println();
		}

		@Override
		public void i(String tag, String message) {
			System.out.printf("i | %s : %s", tag, message);
			System.out.println();
		}
	}

}
