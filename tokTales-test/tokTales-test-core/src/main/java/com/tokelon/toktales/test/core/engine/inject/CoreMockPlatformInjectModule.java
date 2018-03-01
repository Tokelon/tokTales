package com.tokelon.toktales.test.core.engine.inject;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.tokelon.toktales.core.engine.IEnvironment;
import com.tokelon.toktales.core.engine.content.IContentService;
import com.tokelon.toktales.core.engine.inject.AbstractInjectModule;
import com.tokelon.toktales.core.engine.inject.For;
import com.tokelon.toktales.core.engine.input.IInputService;
import com.tokelon.toktales.core.engine.log.ILogService;
import com.tokelon.toktales.core.engine.render.IRenderService;
import com.tokelon.toktales.core.engine.render.ISurfaceHandler;
import com.tokelon.toktales.core.engine.storage.IStorageService;
import com.tokelon.toktales.core.engine.ui.IUIService;
import com.tokelon.toktales.core.game.IGameAdapter;
import com.tokelon.toktales.core.game.states.IGameStateInput;
import com.tokelon.toktales.core.game.states.IGameStateInputHandler;
import com.tokelon.toktales.core.game.states.InitialGamestate;

/** Core inject module used for testing.
 * <p>
 * Mocks dependencies that are normally provided by platform implementations.
 */
public class CoreMockPlatformInjectModule extends AbstractInjectModule {

	// TODO: Move these into configure? Do not have statically
	private static final IEnvironment environmentMock = mock(IEnvironment.class);
	private static final ILogService logServiceMock = mock(ILogService.class);
	private static final IUIService uiServiceMock = mock(IUIService.class);
	private static final IContentService contentServiceMock = mock(IContentService.class);
	private static final IStorageService storageServiceMock = mock(IStorageService.class);
	private static final IRenderService renderServiceMock = mock(IRenderService.class);
	private static final IInputService inputServiceMock = mock(IInputService.class);
	private static final IGameAdapter gameAdapterMock = mock(IGameAdapter.class);
	private static final IGameStateInput gameStateInputMock = mock(IGameStateInput.class);
	
	private static final IGameStateInputHandler gamestateInputHandlerMock = mock(IGameStateInputHandler.class);

	
	private static final ISurfaceHandler surfaceHandlerMock = mock(ISurfaceHandler.class);
	
	static {
		
		when(renderServiceMock.getSurfaceHandler()).thenReturn(surfaceHandlerMock);
	}
	
	
	@Override
	protected void configure() {
		bind(IEnvironment.class).toInstance(environmentMock);
		bind(ILogService.class).toInstance(logServiceMock);
		bind(IUIService.class).toInstance(uiServiceMock);
		bind(IContentService.class).toInstance(contentServiceMock);
		bind(IStorageService.class).toInstance(storageServiceMock);
		bind(IRenderService.class).toInstance(renderServiceMock);
		bind(IInputService.class).toInstance(inputServiceMock);
		bind(IGameAdapter.class).toInstance(gameAdapterMock);
		bind(IGameStateInput.class).toInstance(gameStateInputMock);
		
		bind(IGameStateInputHandler.class).annotatedWith(For.forClass(InitialGamestate.class)).toInstance(gamestateInputHandlerMock);
	}
	

}
