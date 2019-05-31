package com.tokelon.toktales.core.content.manage.files;

import java.lang.reflect.Type;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.MapBinder;
import com.google.inject.multibindings.Multibinder;
import com.tokelon.toktales.core.content.manage.IManagedAssetReader;
import com.tokelon.toktales.core.engine.inject.annotation.AssetReaders;
import com.tokelon.toktales.core.engine.inject.annotation.ParentIdentifiers;
import com.tokelon.toktales.core.engine.inject.annotation.ParentResolvers;
import com.tokelon.toktales.core.engine.inject.annotation.UserDir;

public class PathAssetReadersInjectModule extends AbstractModule {

	
	@Override
	protected void configure() {
		configureParentIdentifiersBinder();
		configureParentResolversBinder();
		configureAssetReaders();
		configureAssetReadersBinder();
	}
	
	
	protected void configureParentIdentifiersBinder() {
		MapBinder<Object, Path> pathParentIdentifierBinder = MapBinder.newMapBinder(binder(), Object.class, Path.class, ParentIdentifiers.class);
		pathParentIdentifierBinder.addBinding(UserDir.class).toInstance(Paths.get("."));
	}
	
	protected void configureParentResolversBinder() {
		Multibinder<IParentResolver<Path>> pathParentResolver = Multibinder.newSetBinder(binder(), new TypeLiteral<IParentResolver<Path>>() {}, ParentResolvers.class);
		pathParentResolver.addBinding().to(PathParentResolver.class);
	}
	
	protected void configureAssetReaders() {
		bind(IPathAssetReader.class).to(PathAssetReader.class);
		bind(IRelativePathAssetReader.class).to(RelativePathAssetReader.class);
	}
	
	protected void configureAssetReadersBinder() {
		MapBinder<Type, IManagedAssetReader> assetReaderBinder = MapBinder.newMapBinder(binder(), Type.class, IManagedAssetReader.class, AssetReaders.class);
		assetReaderBinder.addBinding(IPathKey.class).to(IPathAssetReader.class);
		assetReaderBinder.addBinding(IRelativePathKey.class).to(IRelativePathAssetReader.class);
	}
	
}
