package com.tokelon.toktales.desktop.lwjgl;

import com.tokelon.toktales.core.content.manage.bitmap.IBitmapAssetDecoder;
import com.tokelon.toktales.core.content.manage.codepoint.ICodepointAssetDecoder;
import com.tokelon.toktales.core.content.manage.font.IFontAssetDecoder;
import com.tokelon.toktales.core.content.manage.sound.ISoundAssetDecoder;
import com.tokelon.toktales.core.engine.inject.AbstractInjectModule;
import com.tokelon.toktales.core.render.IRenderToolkit;
import com.tokelon.toktales.core.render.IRenderToolkit.IRenderToolkitFactory;
import com.tokelon.toktales.desktop.input.IDesktopInputService;
import com.tokelon.toktales.desktop.input.dispatch.IDesktopInputDispatch;
import com.tokelon.toktales.desktop.lwjgl.content.STBBitmapDecoder;
import com.tokelon.toktales.desktop.lwjgl.content.STBCodepointDecoder;
import com.tokelon.toktales.desktop.lwjgl.content.STBFontDecoder;
import com.tokelon.toktales.desktop.lwjgl.content.STBVorbisSoundDecoder;
import com.tokelon.toktales.desktop.lwjgl.input.GLFWInputConsumer;
import com.tokelon.toktales.desktop.lwjgl.input.IGLFWInputConsumer;
import com.tokelon.toktales.desktop.lwjgl.render.opengl.DesktopRenderToolkit;
import com.tokelon.toktales.desktop.lwjgl.render.opengl.DesktopRenderToolkit.DesktopRenderToolkitFactory;

public class LWJGLInjectModule extends AbstractInjectModule {


	@Override
	protected void configure() {
		bind(IRenderToolkitFactory.class).to(DesktopRenderToolkitFactory.class);
		bind(IRenderToolkit.class).to(DesktopRenderToolkit.class);

		bind(ISoundAssetDecoder.class).to(STBVorbisSoundDecoder.class);
		bind(IBitmapAssetDecoder.class).to(STBBitmapDecoder.class);
		bind(IFontAssetDecoder.class).to(STBFontDecoder.class);
		bind(ICodepointAssetDecoder.class).to(STBCodepointDecoder.class);

		
		bind(IDesktopInputService.class).to(ILWJGLInputService.class);
		bind(ILWJGLInputService.class).to(LWJGLInputService.class);
		 bindInEngineScope(LWJGLInputService.class);
		
		bind(IDesktopInputDispatch.class).to(ILWJGLInputDispatch.class);
		bind(ILWJGLInputDispatch.class).to(LWJGLInputDispatch.class);
		 bind(IGLFWInputConsumer.IGLFWInputConsumerFactory.class).to(GLFWInputConsumer.GLFWInputConsumerFactory.class);
	}

}
