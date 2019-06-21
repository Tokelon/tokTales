package com.tokelon.toktales.extens.def.core.engine.inject;

import com.google.inject.multibindings.MapBinder;
import com.tokelon.toktales.core.content.manage.embedded.EmbeddedStringAssetKey;
import com.tokelon.toktales.core.content.manage.font.ReadDelegateTextureFontAssetKey;
import com.tokelon.toktales.core.engine.inject.AbstractInjectModule;
import com.tokelon.toktales.core.engine.inject.annotation.GlobalAssetKeyAliases;
import com.tokelon.toktales.core.engine.inject.annotation.GlobalAssetKeyEntries;
import com.tokelon.toktales.core.values.AssetValues;
import com.tokelon.toktales.core.values.EmbeddedFontM5X7;

public class CoreDefExtensGlobalRegistriesInjectModule extends AbstractInjectModule {


	public static final String ASSET_KEY_NAME_FONT_M5X7 = "ASSET_KEY_NAME_FONT_M5X7"; //asset_key-font_m5x7
	
	
	@Override
	protected void configure() {
		super.configure();

	    //String path = "assets/fonts/m5x7.ttf"; // apparently this works on every platform
		//bind(ITextureFontAssetKey.class).annotatedWith(For.forClass(IDebugRenderer.class)).toInstance(new ReadDelegateTextureFontAssetKey(new ResourceScannerKey("assets/fonts/m5x7")));
	    //bind(ITextureFontAssetKey.class).annotatedWith(For.forClass(IDebugRenderer.class)).toInstance(new ReadDelegateTextureFontAssetKey(new RelativePathKey(Paths.get("assets", "fonts", "m5x7.ttf"), StorageRoot.class)));
	    //bind(ITextureFontAssetKey.class).annotatedWith(For.forClass(IDebugRenderer.class)).toInstance(new ReadDelegateTextureFontAssetKey(new RelativeFileKey(new File("assets/fonts/m5x7.ttf"), StorageRoot.class)));
	    //bind(ITextureFontAssetKey.class).annotatedWith(For.forClass(IDebugRenderer.class)).toInstance(new ReadDelegateTextureFontAssetKey(new EmbeddedStringAssetKey(EmbeddedFontM5X7.FONT_M5X7.replaceAll("\\s+", ""))));
	    
		
		// TODO: Move the asset defaults into core?
	    MapBinder<Object, Object> globalAssetKeyEntriesBinder = MapBinder.newMapBinder(binder(), Object.class, Object.class, GlobalAssetKeyEntries.class);
	    globalAssetKeyEntriesBinder.addBinding(ASSET_KEY_NAME_FONT_M5X7).toInstance(new ReadDelegateTextureFontAssetKey(new EmbeddedStringAssetKey(EmbeddedFontM5X7.FONT_M5X7.replaceAll("\\s+", ""))));

	    MapBinder<Object, Object> globalAssetKeyAliasesBinder = MapBinder.newMapBinder(binder(), Object.class, Object.class, GlobalAssetKeyAliases.class);
	    globalAssetKeyAliasesBinder.addBinding(AssetValues.ASSET_KEY_ID_FONT_DEFAULT).toInstance(ASSET_KEY_NAME_FONT_M5X7);
	}
	
}
