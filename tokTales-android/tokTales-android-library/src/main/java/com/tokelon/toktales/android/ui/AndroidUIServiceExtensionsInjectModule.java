package com.tokelon.toktales.android.ui;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.MapBinder;
import com.tokelon.toktales.android.engine.ui.AndroidConsoleUIExtension;
import com.tokelon.toktales.android.engine.ui.AndroidDebugUIExtension;
import com.tokelon.toktales.core.engine.IEngineService.IServiceExtension;
import com.tokelon.toktales.core.engine.inject.annotation.services.UIServiceExtensions;
import com.tokelon.toktales.core.engine.ui.IConsoleUIExtension;
import com.tokelon.toktales.core.engine.ui.IDebugUIExtension;

public class AndroidUIServiceExtensionsInjectModule extends AbstractModule {


	public static final String UI_SERVICE_EXTENSION_CONSOLE = "UI_SERVICE_EXTENSION_CONSOLE";
	public static final String UI_SERVICE_EXTENSION_DEBUG = "UI_SERVICE_EXTENSION_DEBUG";
	
	
	@Override
	protected void configure() {
		MapBinder<String, IServiceExtension> serviceExtensionsBinder = MapBinder.newMapBinder(binder(), String.class, IServiceExtension.class, UIServiceExtensions.class);
		
		serviceExtensionsBinder.addBinding(UI_SERVICE_EXTENSION_CONSOLE).to(IConsoleUIExtension.class);
		serviceExtensionsBinder.addBinding(UI_SERVICE_EXTENSION_DEBUG).to(IDebugUIExtension.class);
		
		bind(IConsoleUIExtension.class).to(AndroidConsoleUIExtension.class);
		bind(IDebugUIExtension.class).to(AndroidDebugUIExtension.class);
	}
	
}
