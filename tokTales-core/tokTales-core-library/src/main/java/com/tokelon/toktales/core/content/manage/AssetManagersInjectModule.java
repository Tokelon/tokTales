package com.tokelon.toktales.core.content.manage;

import com.google.inject.TypeLiteral;
import com.tokelon.toktales.core.content.manage.bitmap.BitmapAssetImpl;
import com.tokelon.toktales.core.content.manage.bitmap.BitmapAssetManager;
import com.tokelon.toktales.core.content.manage.bitmap.IBitmapAsset;
import com.tokelon.toktales.core.content.manage.bitmap.IBitmapAssetDecoder;
import com.tokelon.toktales.core.content.manage.bitmap.IBitmapAssetKey;
import com.tokelon.toktales.core.content.manage.bitmap.IBitmapAssetManager;
import com.tokelon.toktales.core.content.manage.codepoint.CodepointAsset;
import com.tokelon.toktales.core.content.manage.codepoint.CodepointAssetLoader;
import com.tokelon.toktales.core.content.manage.codepoint.CodepointAssetManager;
import com.tokelon.toktales.core.content.manage.codepoint.ICodepointAsset;
import com.tokelon.toktales.core.content.manage.codepoint.ICodepointAssetDecoder;
import com.tokelon.toktales.core.content.manage.codepoint.ICodepointAssetKey;
import com.tokelon.toktales.core.content.manage.codepoint.ICodepointAssetManager;
import com.tokelon.toktales.core.content.manage.font.IFontAsset;
import com.tokelon.toktales.core.content.manage.font.IFontAssetDecoder;
import com.tokelon.toktales.core.content.manage.font.IFontAssetKey;
import com.tokelon.toktales.core.content.manage.font.IFontAssetManager;
import com.tokelon.toktales.core.content.manage.font.FontAssetImpl;
import com.tokelon.toktales.core.content.manage.font.FontAssetManager;
import com.tokelon.toktales.core.content.manage.sound.ISoundAsset;
import com.tokelon.toktales.core.content.manage.sound.ISoundAssetDecoder;
import com.tokelon.toktales.core.content.manage.sound.ISoundAssetKey;
import com.tokelon.toktales.core.content.manage.sound.ISoundAssetManager;
import com.tokelon.toktales.core.content.manage.sound.SoundAsset;
import com.tokelon.toktales.core.content.manage.sound.SoundAssetManager;
import com.tokelon.toktales.core.content.manage.sprite.ISpriteAssetDecoder;
import com.tokelon.toktales.core.content.manage.sprite.ISpriteAssetKey;
import com.tokelon.toktales.core.content.manage.sprite.ISpriteAssetLoader;
import com.tokelon.toktales.core.content.manage.sprite.ISpriteAssetManager;
import com.tokelon.toktales.core.content.manage.sprite.SpriteAssetDecoder;
import com.tokelon.toktales.core.content.manage.sprite.SpriteAssetLoader;
import com.tokelon.toktales.core.content.manage.sprite.SpriteAssetManager;
import com.tokelon.toktales.core.content.sprite.ISpriteAsset;
import com.tokelon.toktales.core.content.sprite.SpriteAsset;
import com.tokelon.toktales.core.engine.inject.AbstractInjectModule;
import com.tokelon.toktales.core.engine.inject.scope.GameScoped;
import com.tokelon.toktales.tools.assets.loader.DefaultInjectableAssetLoader;
import com.tokelon.toktales.tools.assets.loader.IAssetDecoder;
import com.tokelon.toktales.tools.assets.loader.IAssetLoader;
import com.tokelon.toktales.tools.assets.manager.DefaultAssetStore;
import com.tokelon.toktales.tools.assets.manager.DefaultSpecialAssetManager;
import com.tokelon.toktales.tools.assets.manager.IAssetManager;
import com.tokelon.toktales.tools.assets.manager.IAssetStore;
import com.tokelon.toktales.tools.assets.manager.ISpecialAssetFactory;
import com.tokelon.toktales.tools.assets.manager.ISpecialAssetManager;
import com.tokelon.toktales.tools.core.objects.options.INamedOptions;
import com.tokelon.toktales.tools.core.objects.options.IOptions;

public class AssetManagersInjectModule extends AbstractInjectModule {
	// Rename to AssetManagementInjectModule ?
	// TODO: Use GameScoped or EngineScoped?
	// TODO: Use bindInGameScope methods instead of the annotation? Also bind in no scope?
	// TODO: Bind managers in game scope and no-scoped as it was for IContentManager properties?

	@Override
	protected void configure() {
		bindInGameScopeAndForNotScoped(ICodepointAssetManager.class, CodepointAssetManager.class);
		bind(new TypeLiteral<IAssetManager<ICodepointAsset, ICodepointAssetKey, INamedOptions>>() {}).to(ICodepointAssetManager.class);
		bind(new TypeLiteral<IAssetStore<ICodepointAsset, ICodepointAssetKey>>() {}).to(new TypeLiteral<DefaultAssetStore<ICodepointAsset, ICodepointAssetKey>>() {});
		bind(new TypeLiteral<IAssetLoader<ICodepointAsset, ICodepointAssetKey, INamedOptions>>() {}).to(CodepointAssetLoader.class);
		bind(new TypeLiteral<IAssetDecoder<ICodepointAsset, ICodepointAssetKey, INamedOptions>>() {}).to(ICodepointAssetDecoder.class);
		bind(new TypeLiteral<ISpecialAssetFactory<ICodepointAsset>>() {}).toInstance(() -> new CodepointAsset(null));
		bind(new TypeLiteral<ISpecialAssetManager<ICodepointAsset>>() {}).to(new TypeLiteral<DefaultSpecialAssetManager<ICodepointAsset>>() {}).in(GameScoped.class);

		bindInGameScopeAndForNotScoped(ISoundAssetManager.class, SoundAssetManager.class);
		bind(new TypeLiteral<IAssetManager<ISoundAsset, ISoundAssetKey, INamedOptions>>() {}).to(ISoundAssetManager.class);
		bind(new TypeLiteral<IAssetStore<ISoundAsset, ISoundAssetKey>>() {}).to(new TypeLiteral<DefaultAssetStore<ISoundAsset, ISoundAssetKey>>() {});
		bind(new TypeLiteral<IAssetLoader<ISoundAsset, ISoundAssetKey, INamedOptions>>() {}).to(new TypeLiteral<DefaultInjectableAssetLoader<ISoundAsset, ISoundAssetKey, INamedOptions>>() {});
		bind(new TypeLiteral<IAssetDecoder<ISoundAsset, ISoundAssetKey, INamedOptions>>() {}).to(ISoundAssetDecoder.class);
		bind(new TypeLiteral<ISpecialAssetFactory<ISoundAsset>>() {}).toInstance(() -> new SoundAsset(null));
		bind(new TypeLiteral<ISpecialAssetManager<ISoundAsset>>() {}).to(new TypeLiteral<DefaultSpecialAssetManager<ISoundAsset>>() {}).in(GameScoped.class);
		
		bindInGameScopeAndForNotScoped(IBitmapAssetManager.class, BitmapAssetManager.class);
		bind(new TypeLiteral<IAssetManager<IBitmapAsset, IBitmapAssetKey, IOptions>>() {}).to(IBitmapAssetManager.class);
		bind(new TypeLiteral<IAssetStore<IBitmapAsset, IBitmapAssetKey>>() {}).to(new TypeLiteral<DefaultAssetStore<IBitmapAsset, IBitmapAssetKey>>() {});
		bind(new TypeLiteral<IAssetLoader<IBitmapAsset, IBitmapAssetKey, IOptions>>() {}).to(new TypeLiteral<DefaultInjectableAssetLoader<IBitmapAsset, IBitmapAssetKey, IOptions>>() {});
		bind(new TypeLiteral<IAssetDecoder<IBitmapAsset, IBitmapAssetKey, IOptions>>() {}).to(IBitmapAssetDecoder.class);
		bind(new TypeLiteral<ISpecialAssetFactory<IBitmapAsset>>() {}).toInstance(() -> new BitmapAssetImpl(null));
		bind(new TypeLiteral<ISpecialAssetManager<IBitmapAsset>>() {}).to(new TypeLiteral<DefaultSpecialAssetManager<IBitmapAsset>>() {}).in(GameScoped.class);
		
		bindInGameScopeAndForNotScoped(IFontAssetManager.class, FontAssetManager.class);
		bind(new TypeLiteral<IAssetManager<IFontAsset, IFontAssetKey, IOptions>>() {}).to(IFontAssetManager.class);
		bind(new TypeLiteral<IAssetStore<IFontAsset, IFontAssetKey>>() {}).to(new TypeLiteral<DefaultAssetStore<IFontAsset, IFontAssetKey>>() {});
		bind(new TypeLiteral<IAssetLoader<IFontAsset, IFontAssetKey, IOptions>>() {}).to(new TypeLiteral<DefaultInjectableAssetLoader<IFontAsset, IFontAssetKey, IOptions>>() {});
		bind(new TypeLiteral<IAssetDecoder<IFontAsset, IFontAssetKey, IOptions>>() {}).to(IFontAssetDecoder.class);
		bind(new TypeLiteral<ISpecialAssetFactory<IFontAsset>>() {}).toInstance(() -> new FontAssetImpl(null));
		bind(new TypeLiteral<ISpecialAssetManager<IFontAsset>>() {}).to(new TypeLiteral<DefaultSpecialAssetManager<IFontAsset>>() {}).in(GameScoped.class);
		
		bindInGameScopeAndForNotScoped(ISpriteAssetManager.class, SpriteAssetManager.class);
		bind(new TypeLiteral<IAssetManager<ISpriteAsset, ISpriteAssetKey, IOptions>>() {}).to(ISpriteAssetManager.class);
		bind(new TypeLiteral<IAssetStore<ISpriteAsset, ISpriteAssetKey>>() {}).to(new TypeLiteral<DefaultAssetStore<ISpriteAsset, ISpriteAssetKey>>() {});
		bind(new TypeLiteral<IAssetLoader<ISpriteAsset, ISpriteAssetKey, IOptions>>() {}).to(ISpriteAssetLoader.class);
		bind(ISpriteAssetLoader.class).to(SpriteAssetLoader.class);
		bind(new TypeLiteral<IAssetDecoder<ISpriteAsset, ISpriteAssetKey, IOptions>>() {}).to(ISpriteAssetDecoder.class);
		bind(ISpriteAssetDecoder.class).to(SpriteAssetDecoder.class);
		bind(new TypeLiteral<ISpecialAssetFactory<ISpriteAsset>>() {}).toInstance(() -> new SpriteAsset(null));
		bind(new TypeLiteral<ISpecialAssetManager<ISpriteAsset>>() {}).to(new TypeLiteral<DefaultSpecialAssetManager<ISpriteAsset>>() {}).in(GameScoped.class);
	}
	
}
