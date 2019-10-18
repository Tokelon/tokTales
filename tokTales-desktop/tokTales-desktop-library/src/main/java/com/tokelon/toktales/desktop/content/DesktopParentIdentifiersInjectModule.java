package com.tokelon.toktales.desktop.content;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.multibindings.MapBinder;
import com.tokelon.toktales.core.engine.inject.AbstractInjectModule;
import com.tokelon.toktales.core.engine.inject.annotation.StorageRoot;
import com.tokelon.toktales.desktop.engine.inject.annotation.AssetRoot;
import com.tokelon.toktales.tools.assets.annotation.ParentIdentifiers;

public class DesktopParentIdentifiersInjectModule extends AbstractInjectModule {
	// Add StorageRoot bindings in core?
	
	
	@Override
	protected void configure() {
		configureFileParentIdentifiers();
		configurePathParentIdentifiers();
	}

	
	protected void configureFileParentIdentifiers() {
		MapBinder<Object, File> fileParentIdentifierBinder = MapBinder.newMapBinder(binder(), Object.class, File.class, ParentIdentifiers.class);
		
		fileParentIdentifierBinder.addBinding(StorageRoot.class).toProvider(new Provider<File>() {
			@Inject @StorageRoot String storageRoot;
			
			@Override
			public File get() {
				return new File(storageRoot);
			}
		});
		
		fileParentIdentifierBinder.addBinding(AssetRoot.class).toProvider(new Provider<File>() {
			@Inject @AssetRoot String assetRoot;
			
			@Override
			public File get() {
				return new File(assetRoot);
			}
		});
	}
	
	protected void configurePathParentIdentifiers() {
		MapBinder<Object, Path> pathParentIdentifier = MapBinder.newMapBinder(binder(), Object.class, Path.class, ParentIdentifiers.class);
		
		pathParentIdentifier.addBinding(StorageRoot.class).toProvider(new Provider<Path>() {
			@Inject @StorageRoot String storageRoot;
			
			@Override
			public Path get() {
				return Paths.get(storageRoot);
			}
		});
		
		pathParentIdentifier.addBinding(AssetRoot.class).toProvider(new Provider<Path>() {
			@Inject @AssetRoot String assetRoot;
			
			@Override
			public Path get() {
				return Paths.get(assetRoot);
			}
		});
	}

}
