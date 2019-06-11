package com.tokelon.toktales.desktop.lwjgl;

import com.google.inject.AbstractModule;
import com.tokelon.toktales.core.content.manage.bitmap.IBitmapAssetDecoder;
import com.tokelon.toktales.core.content.manage.font.ITextureFontAssetDecoder;
import com.tokelon.toktales.core.content.manage.sound.ISoundAssetDecoder;
import com.tokelon.toktales.core.render.IRenderToolkit;
import com.tokelon.toktales.core.render.IRenderToolkit.IRenderToolkitFactory;
import com.tokelon.toktales.desktop.lwjgl.data.STBBitmapDecoder;
import com.tokelon.toktales.desktop.lwjgl.data.STBTextureFontDecoder;
import com.tokelon.toktales.desktop.lwjgl.data.STBVorbisSoundDecoder;
import com.tokelon.toktales.desktop.lwjgl.render.DesktopRenderToolkit;
import com.tokelon.toktales.desktop.lwjgl.render.DesktopRenderToolkit.DesktopRenderToolkitFactory;

public class LWJGLInjectModule extends AbstractModule {

	
	@Override
	protected void configure() {
		bind(IRenderToolkitFactory.class).to(DesktopRenderToolkitFactory.class);
		bind(IRenderToolkit.class).to(DesktopRenderToolkit.class);
		
		bind(ISoundAssetDecoder.class).to(STBVorbisSoundDecoder.class);
		bind(IBitmapAssetDecoder.class).to(STBBitmapDecoder.class);
		bind(ITextureFontAssetDecoder.class).to(STBTextureFontDecoder.class);
	}
	
}
