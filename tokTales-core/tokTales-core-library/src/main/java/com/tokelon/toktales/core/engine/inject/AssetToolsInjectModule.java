package com.tokelon.toktales.core.engine.inject;

import java.util.concurrent.ExecutorService;

import com.tokelon.toktales.core.content.manage.RecyclableExecutorService;
import com.tokelon.toktales.core.engine.inject.annotation.GlobalExecutorServiceImplementation;
import com.tokelon.toktales.core.engine.inject.scope.GameScoped;
import com.tokelon.toktales.tools.assets.annotation.AssetLoader;
import com.tokelon.toktales.tools.assets.loader.DefaultAssetLoader;
import com.tokelon.toktales.tools.assets.loader.DefaultExecutorServiceProvider;
import com.tokelon.toktales.tools.assets.loader.IAssetLoader;
import com.tokelon.toktales.tools.assets.reader.DefaultAssetReaderManager;
import com.tokelon.toktales.tools.assets.reader.IAssetReaderManager;

public class AssetToolsInjectModule extends AbstractInjectModule {


	@Override
	protected void configure() {
		/*
		//bind(IAssetManager.class) // Cannot bind this in a generic way
		install(new FactoryModuleBuilder() // If we do this we have to bind store, loader etc.
				.implement(IAssetManager.class, DefaultAssetManager.class)
				.build(IAssetManagerFactory.class));
		*/
		
		bind(IAssetLoader.IAssetLoaderFactory.class).to(DefaultAssetLoader.DefaultAssetLoaderFactory.class);
		bind(IAssetReaderManager.class).to(DefaultAssetReaderManager.class);
		
		
		bind(ExecutorService.class).toProvider(DefaultExecutorServiceProvider.class);
		bind(ExecutorService.class).annotatedWith(GlobalExecutorServiceImplementation.class).toProvider(DefaultExecutorServiceProvider.class);
		bind(ExecutorService.class).annotatedWith(AssetLoader.class).to(RecyclableExecutorService.class).in(GameScoped.class);
	}
	
}
