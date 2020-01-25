package com.tokelon.toktales.extensions.core.engine.inject;

import com.google.inject.multibindings.MapBinder;
import com.tokelon.toktales.core.engine.inject.AbstractInjectModule;
import com.tokelon.toktales.core.engine.inject.annotation.GlobalAssetKeyAliases;
import com.tokelon.toktales.core.engine.inject.annotation.GlobalAssetKeyEntries;
import com.tokelon.toktales.core.values.AssetValues;
import com.tokelon.toktales.extensions.core.game.screen.DebugRenderer;
import com.tokelon.toktales.extensions.core.game.states.console.ConsoleGamestate;
import com.tokelon.toktales.extensions.core.game.states.localmap.LocalMapGamestate;

public class CoreExtensionsGlobalRegistriesInjectModule extends AbstractInjectModule {


	@Override
	protected void configure() {
		super.configure();
		
		
	    MapBinder<Object, Object> globalAssetKeyEntriesBinder = MapBinder.newMapBinder(binder(), Object.class, Object.class, GlobalAssetKeyEntries.class);

	    MapBinder<Object, Object> globalAssetKeyAliasesBinder = MapBinder.newMapBinder(binder(), Object.class, Object.class, GlobalAssetKeyAliases.class);

	    
	    globalAssetKeyAliasesBinder.addBinding(DebugRenderer.ASSET_KEY_ID_FONT_MAIN).toInstance(AssetValues.ASSET_KEY_ID_FONT_DEFAULT);
	    globalAssetKeyAliasesBinder.addBinding(LocalMapGamestate.ASSET_KEY_ID_FONT_MAIN).toInstance(AssetValues.ASSET_KEY_ID_FONT_DEFAULT);
	    globalAssetKeyAliasesBinder.addBinding(ConsoleGamestate.ASSET_KEY_ID_FONT_MAIN).toInstance(AssetValues.ASSET_KEY_ID_FONT_DEFAULT);
	}
	
}
