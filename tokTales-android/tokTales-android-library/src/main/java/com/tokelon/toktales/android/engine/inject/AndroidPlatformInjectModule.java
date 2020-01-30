package com.tokelon.toktales.android.engine.inject;

import com.tokelon.toktales.android.content.AndroidBitmapDecoder;
import com.tokelon.toktales.android.content.AndroidCodepointDecoder;
import com.tokelon.toktales.android.content.AndroidFontDecoder;
import com.tokelon.toktales.android.content.AndroidSoundDecoder;
import com.tokelon.toktales.android.render.opengl.AndroidRenderToolkit;
import com.tokelon.toktales.android.render.opengl.AndroidRenderToolkit.AndroidRenderToolkitFactory;
import com.tokelon.toktales.core.content.manage.bitmap.IBitmapAssetDecoder;
import com.tokelon.toktales.core.content.manage.codepoint.ICodepointAssetDecoder;
import com.tokelon.toktales.core.content.manage.font.IFontAssetDecoder;
import com.tokelon.toktales.core.content.manage.sound.ISoundAssetDecoder;
import com.tokelon.toktales.core.engine.inject.AbstractInjectModule;
import com.tokelon.toktales.core.render.IRenderToolkit;
import com.tokelon.toktales.core.render.IRenderToolkit.IRenderToolkitFactory;

public class AndroidPlatformInjectModule extends AbstractInjectModule {


	@Override
	protected void configure() {
		bind(IRenderToolkitFactory.class).to(AndroidRenderToolkitFactory.class);
		bind(IRenderToolkit.class).to(AndroidRenderToolkit.class);
		
		bind(ISoundAssetDecoder.class).to(AndroidSoundDecoder.class);
		bind(IBitmapAssetDecoder.class).to(AndroidBitmapDecoder.class);
		bind(IFontAssetDecoder.class).to(AndroidFontDecoder.class);
		bind(ICodepointAssetDecoder.class).to(AndroidCodepointDecoder.class);
	}
	
}
