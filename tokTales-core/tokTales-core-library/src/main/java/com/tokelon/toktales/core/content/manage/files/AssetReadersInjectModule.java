package com.tokelon.toktales.core.content.manage.files;

import java.io.File;
import java.lang.reflect.Type;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.MapBinder;
import com.google.inject.multibindings.Multibinder;
import com.tokelon.toktales.core.content.manage.IManagedAssetReader;
import com.tokelon.toktales.core.content.manage.resources.IResourceAssetReader;
import com.tokelon.toktales.core.content.manage.resources.IResourceKey;
import com.tokelon.toktales.core.content.manage.resources.IResourceScannerAssetReader;
import com.tokelon.toktales.core.content.manage.resources.IResourceScannerKey;
import com.tokelon.toktales.core.content.manage.resources.ResourceAssetReader;
import com.tokelon.toktales.core.content.manage.resources.ResourceScannerAssetReader;
import com.tokelon.toktales.core.engine.inject.annotation.AssetReaders;
import com.tokelon.toktales.core.engine.inject.annotation.ParentIdentifiers;
import com.tokelon.toktales.core.engine.inject.annotation.ParentResolvers;
import com.tokelon.toktales.core.engine.inject.annotation.UserDir;

public class AssetReadersInjectModule extends AbstractModule {

	
	@Override
	protected void configure() {
		configureParentIdentifiersBinder();
		configureParentResolversBinder();
		configureAssetReaders();
		configureAssetReadersBinder();
	}
	
	
	protected void configureParentIdentifiersBinder() {
		MapBinder<Object, File> fileParentIdentifierBinder = MapBinder.newMapBinder(binder(), Object.class, File.class, ParentIdentifiers.class);
		fileParentIdentifierBinder.addBinding(UserDir.class).toInstance(new File("."));
	}
	
	protected void configureParentResolversBinder() {
		Multibinder<IParentResolver<File>> fileParentResolverBinder = Multibinder.newSetBinder(binder(), new TypeLiteral<IParentResolver<File>>() {}, ParentResolvers.class);
		fileParentResolverBinder.addBinding().to(FileParentResolver.class);
	}
	
	protected void configureAssetReaders() {
		bind(IFileAssetReader.class).to(FileAssetReader.class);
		bind(IRelativeFileAssetReader.class).to(RelativeFileAssetReader.class);
		bind(IResourceAssetReader.class).to(ResourceAssetReader.class);
		bind(IResourceScannerAssetReader.class).to(ResourceScannerAssetReader.class);
	}
	
	protected void configureAssetReadersBinder() {
		MapBinder<Type, IManagedAssetReader> assetReaderBinder = MapBinder.newMapBinder(binder(), Type.class, IManagedAssetReader.class, AssetReaders.class);
		assetReaderBinder.addBinding(IFileKey.class).to(IFileAssetReader.class);
		assetReaderBinder.addBinding(IRelativeFileKey.class).to(IRelativeFileAssetReader.class);
		assetReaderBinder.addBinding(IResourceKey.class).to(IResourceAssetReader.class);
		assetReaderBinder.addBinding(IResourceScannerKey.class).to(IResourceScannerAssetReader.class);
	}
	
}