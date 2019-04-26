package com.tokelon.toktales.core.test.engine.inject;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.google.inject.TypeLiteral;
import com.tokelon.toktales.core.content.manage.IAssetDecoder;
import com.tokelon.toktales.core.content.manage.bitmap.IBitmapAsset;
import com.tokelon.toktales.core.content.manage.bitmap.IBitmapAssetKey;
import com.tokelon.toktales.core.content.manage.font.ITextureFontAsset;
import com.tokelon.toktales.core.content.manage.font.ITextureFontAssetKey;
import com.tokelon.toktales.core.content.manage.sound.ISoundAsset;
import com.tokelon.toktales.core.content.manage.sound.ISoundAssetKey;
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
import com.tokelon.toktales.core.render.opengl.gl20.IGL11;
import com.tokelon.toktales.core.render.opengl.gl20.IGL13;
import com.tokelon.toktales.core.render.opengl.gl20.IGL14;
import com.tokelon.toktales.core.render.opengl.gl20.IGL15;
import com.tokelon.toktales.core.render.opengl.gl20.IGL20;
import com.tokelon.toktales.core.util.options.INamedOptions;
import com.tokelon.toktales.core.util.options.IOptions;

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
	private static final IGL11 gl11Mock = mock(IGL11.class);
	private static final IGL13 gl13Mock = mock(IGL13.class);
	private static final IGL14 gl14Mock = mock(IGL14.class);
	private static final IGL15 gl15Mock = mock(IGL15.class);
	private static final IGL20 gl20Mock = mock(IGL20.class);
	
	private static final IGameStateInputHandler gamestateInputHandlerMock = mock(IGameStateInputHandler.class);

	@SuppressWarnings("unchecked")
	private static final IAssetDecoder<ISoundAsset, ISoundAssetKey, INamedOptions> soundAssetDecoder = mock(IAssetDecoder.class);
	@SuppressWarnings("unchecked")
	private static final IAssetDecoder<IBitmapAsset, IBitmapAssetKey, IOptions> bitmapAssetDecoder = mock(IAssetDecoder.class);
	@SuppressWarnings("unchecked")
	private static final IAssetDecoder<ITextureFontAsset, ITextureFontAssetKey, IOptions> textureFontAssetDecoder = mock(IAssetDecoder.class);

	
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
		bind(IGL11.class).toInstance(gl11Mock);
		bind(IGL13.class).toInstance(gl13Mock);
		bind(IGL14.class).toInstance(gl14Mock);
		bind(IGL15.class).toInstance(gl15Mock);
		bind(IGL20.class).toInstance(gl20Mock);
		
		bind(IGameStateInputHandler.class).annotatedWith(For.forClass(InitialGamestate.class)).toInstance(gamestateInputHandlerMock);
		
		bind(new TypeLiteral<IAssetDecoder<ISoundAsset, ISoundAssetKey, INamedOptions>>() {}).toInstance(soundAssetDecoder);
		bind(new TypeLiteral<IAssetDecoder<IBitmapAsset, IBitmapAssetKey, IOptions>>() {}).toInstance(bitmapAssetDecoder);
		bind(new TypeLiteral<IAssetDecoder<ITextureFontAsset, ITextureFontAssetKey, IOptions>>() {}).toInstance(textureFontAssetDecoder);
	}

}
