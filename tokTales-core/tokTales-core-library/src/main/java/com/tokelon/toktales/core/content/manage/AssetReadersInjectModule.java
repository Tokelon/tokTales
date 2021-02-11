package com.tokelon.toktales.core.content.manage;

import java.io.File;
import java.lang.reflect.Type;

import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.MapBinder;
import com.google.inject.multibindings.Multibinder;
import com.tokelon.toktales.core.content.manage.contents.ContentAssetReader;
import com.tokelon.toktales.core.content.manage.contents.IContentAssetReader;
import com.tokelon.toktales.core.content.manage.contents.IContentKey;
import com.tokelon.toktales.core.content.manage.embedded.EmbeddedAssetReader;
import com.tokelon.toktales.core.content.manage.embedded.IEmbeddedAssetKey;
import com.tokelon.toktales.core.content.manage.embedded.IEmbeddedAssetReader;
import com.tokelon.toktales.core.content.manage.resources.IResourceAssetReader;
import com.tokelon.toktales.core.content.manage.resources.IResourceKey;
import com.tokelon.toktales.core.content.manage.resources.IResourceScannerAssetReader;
import com.tokelon.toktales.core.content.manage.resources.IResourceScannerKey;
import com.tokelon.toktales.core.content.manage.resources.ResourceAssetReader;
import com.tokelon.toktales.core.content.manage.resources.ResourceScannerAssetReader;
import com.tokelon.toktales.core.engine.inject.AbstractInjectModule;
import com.tokelon.toktales.core.engine.inject.annotation.UserDir;
import com.tokelon.toktales.core.location.ApplicationLocation;
import com.tokelon.toktales.core.location.IApplicationLocation;
import com.tokelon.toktales.tools.assets.annotation.AssetReaders;
import com.tokelon.toktales.tools.assets.annotation.ParentIdentifiers;
import com.tokelon.toktales.tools.assets.annotation.ParentResolvers;
import com.tokelon.toktales.tools.assets.files.FileAssetReader;
import com.tokelon.toktales.tools.assets.files.FileParentResolver;
import com.tokelon.toktales.tools.assets.files.IFileAssetReader;
import com.tokelon.toktales.tools.assets.files.IFileKey;
import com.tokelon.toktales.tools.assets.files.IParentResolver;
import com.tokelon.toktales.tools.assets.files.IRelativeFileAssetReader;
import com.tokelon.toktales.tools.assets.files.IRelativeFileKey;
import com.tokelon.toktales.tools.assets.files.RelativeFileAssetReader;
import com.tokelon.toktales.tools.assets.reader.IManagedAssetReader;

public class AssetReadersInjectModule extends AbstractInjectModule {


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

		MapBinder<Object, IApplicationLocation> applicationLocationParentIdentifierBinder = MapBinder.newMapBinder(binder(), Object.class, IApplicationLocation.class, ParentIdentifiers.class);
		applicationLocationParentIdentifierBinder.addBinding(UserDir.class).toInstance(new ApplicationLocation("."));
	}

	protected void configureParentResolversBinder() {
		Multibinder<IParentResolver<File>> fileParentResolverBinder = Multibinder.newSetBinder(binder(), new TypeLiteral<IParentResolver<File>>() {}, ParentResolvers.class);
		fileParentResolverBinder.addBinding().to(FileParentResolver.class);

		Multibinder<IParentResolver<IApplicationLocation>> applicationLocationParentResolverBinder = Multibinder.newSetBinder(binder(), new TypeLiteral<IParentResolver<IApplicationLocation>>() {}, ParentResolvers.class);
		applicationLocationParentResolverBinder.addBinding().to(ApplicationLocationParentResolver.class);
	}

	protected void configureAssetReaders() {
		bind(IFileAssetReader.class).to(FileAssetReader.class);
		bind(IRelativeFileAssetReader.class).to(RelativeFileAssetReader.class);
		bind(IResourceAssetReader.class).to(ResourceAssetReader.class);
		bind(IResourceScannerAssetReader.class).to(ResourceScannerAssetReader.class);
		bind(IContentAssetReader.class).to(ContentAssetReader.class);

		bind(IEmbeddedAssetReader.class).to(EmbeddedAssetReader.class);
	}

	protected void configureAssetReadersBinder() {
		MapBinder<Type, IManagedAssetReader> assetReaderBinder = MapBinder.newMapBinder(binder(), Type.class, IManagedAssetReader.class, AssetReaders.class);
		assetReaderBinder.addBinding(IFileKey.class).to(IFileAssetReader.class);
		assetReaderBinder.addBinding(IRelativeFileKey.class).to(IRelativeFileAssetReader.class);
		assetReaderBinder.addBinding(IResourceKey.class).to(IResourceAssetReader.class);
		assetReaderBinder.addBinding(IResourceScannerKey.class).to(IResourceScannerAssetReader.class);
		assetReaderBinder.addBinding(IContentKey.class).to(IContentAssetReader.class);

		assetReaderBinder.addBinding(IEmbeddedAssetKey.class).to(IEmbeddedAssetReader.class);
	}

}
