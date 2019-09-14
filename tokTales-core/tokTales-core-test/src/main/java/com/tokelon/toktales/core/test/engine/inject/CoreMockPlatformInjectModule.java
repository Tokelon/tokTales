package com.tokelon.toktales.core.test.engine.inject;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import com.google.inject.TypeLiteral;
import com.tokelon.toktales.core.content.manage.bitmap.IBitmapAssetDecoder;
import com.tokelon.toktales.core.content.manage.codepoint.ICodepointAssetDecoder;
import com.tokelon.toktales.core.content.manage.font.ITextureFontAssetDecoder;
import com.tokelon.toktales.core.content.manage.sound.ISoundAssetDecoder;
import com.tokelon.toktales.core.engine.IEnvironment;
import com.tokelon.toktales.core.engine.content.IContentService;
import com.tokelon.toktales.core.engine.inject.AbstractInjectModule;
import com.tokelon.toktales.core.engine.inject.For;
import com.tokelon.toktales.core.engine.inject.annotation.RenderDrivers;
import com.tokelon.toktales.core.engine.input.IInputService;
import com.tokelon.toktales.core.engine.render.IRenderService;
import com.tokelon.toktales.core.engine.render.ISurfaceHandler;
import com.tokelon.toktales.core.engine.storage.IStorageService;
import com.tokelon.toktales.core.engine.ui.IUIService;
import com.tokelon.toktales.core.game.IGameAdapter;
import com.tokelon.toktales.core.game.states.IGameStateInput;
import com.tokelon.toktales.core.game.states.IGameStateInputHandler;
import com.tokelon.toktales.core.game.states.InitialGamestate;
import com.tokelon.toktales.core.render.IRenderDriverFactory;
import com.tokelon.toktales.core.render.IRenderToolkit.IRenderToolkitFactory;
import com.tokelon.toktales.core.render.opengl.gl20.IGL11;
import com.tokelon.toktales.core.render.opengl.gl20.IGL13;
import com.tokelon.toktales.core.render.opengl.gl20.IGL14;
import com.tokelon.toktales.core.render.opengl.gl20.IGL15;
import com.tokelon.toktales.core.render.opengl.gl20.IGL20;

/** Core inject module used for testing.
 * <p>
 * Mocks dependencies that are normally provided by platform implementations.
 */
public class CoreMockPlatformInjectModule extends AbstractInjectModule {

	// TODO: Move these into configure? Do not have statically
	private static final IEnvironment environmentMock = mock(IEnvironment.class);
	private static final IUIService uiServiceMock = mock(IUIService.class);
	private static final IContentService contentServiceMock = mock(IContentService.class);
	private static final IStorageService storageServiceMock = mock(IStorageService.class);
	private static final IRenderService renderServiceMock = mock(IRenderService.class);
	private static final IInputService inputServiceMock = mock(IInputService.class);
	private static final IGameAdapter gameAdapterMock = mock(IGameAdapter.class);
	private static final IGameStateInput gameStateInputMock = mock(IGameStateInput.class);
	private static final IGL11 gl11Mock = mock(IGL11.class);
	private static final IGL13 gl13Mock = mock(IGL13.class);
	private static final IGL14 gl14Mock = mock(IGL14.class);
	private static final IGL15 gl15Mock = mock(IGL15.class);
	private static final IGL20 gl20Mock = mock(IGL20.class);
	private static final IRenderToolkitFactory renderToolkitFactoryMock = mock(IRenderToolkitFactory.class);

	private static final IGameStateInputHandler gamestateInputHandlerMock = mock(IGameStateInputHandler.class);

	private static final ISoundAssetDecoder soundAssetDecoderMock = mock(ISoundAssetDecoder.class);
	private static final IBitmapAssetDecoder bitmapAssetDecoderMock = mock(IBitmapAssetDecoder.class);
	private static final ITextureFontAssetDecoder textureFontAssetDecoderMock = mock(ITextureFontAssetDecoder.class);
	private static final ICodepointAssetDecoder codepointAssetDecoderMock = mock(ICodepointAssetDecoder.class);
	
	private static final ISurfaceHandler surfaceHandlerMock = mock(ISurfaceHandler.class);
	
	static {
		
		when(renderServiceMock.getSurfaceHandler()).thenReturn(surfaceHandlerMock);
	}
	
	
	@Override
	protected void configure() {
		bind(IEnvironment.class).toInstance(environmentMock);
		bind(IUIService.class).toInstance(uiServiceMock);
		bind(IContentService.class).toInstance(contentServiceMock);
		bind(IStorageService.class).toInstance(storageServiceMock);
		bind(IRenderService.class).toInstance(renderServiceMock);
		bind(IInputService.class).toInstance(inputServiceMock);
		bind(IGameAdapter.class).toInstance(gameAdapterMock);
		bind(IGameStateInput.class).toInstance(gameStateInputMock);
		bind(IGL11.class).toInstance(gl11Mock);
		bind(IGL13.class).toInstance(gl13Mock);
		bind(IGL14.class).toInstance(gl14Mock);
		bind(IGL15.class).toInstance(gl15Mock);
		bind(IGL20.class).toInstance(gl20Mock);
		bind(IRenderToolkitFactory.class).toInstance(renderToolkitFactoryMock);
		
		bind(IGameStateInputHandler.class).annotatedWith(For.forClass(InitialGamestate.class)).toInstance(gamestateInputHandlerMock);
		
		bind(ISoundAssetDecoder.class).toInstance(soundAssetDecoderMock);
		bind(IBitmapAssetDecoder.class).toInstance(bitmapAssetDecoderMock);
		bind(ITextureFontAssetDecoder.class).toInstance(textureFontAssetDecoderMock);
		bind(ICodepointAssetDecoder.class).toInstance(codepointAssetDecoderMock);
		
		bind(new TypeLiteral<Set<IRenderDriverFactory>>() {}).annotatedWith(RenderDrivers.class).toInstance(new HashSet<>());
	}

}
