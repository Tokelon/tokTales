package com.tokelon.toktales.desktop.content;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.multibindings.MapBinder;
import com.tokelon.toktales.core.engine.inject.AbstractInjectModule;
import com.tokelon.toktales.core.engine.inject.annotation.ContentRoot;
import com.tokelon.toktales.core.engine.inject.annotation.StorageRoot;
import com.tokelon.toktales.core.location.ApplicationLocation;
import com.tokelon.toktales.core.location.IApplicationLocation;
import com.tokelon.toktales.tools.assets.annotation.ParentIdentifiers;

public class DesktopParentIdentifiersInjectModule extends AbstractInjectModule {
	// Add StorageRoot bindings in core?


	@Override
	protected void configure() {
		configureFileParentIdentifiers();
		configurePathParentIdentifiers();

		configureApplicationLocationParentIdentifiers();
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

		fileParentIdentifierBinder.addBinding(ContentRoot.class).toProvider(new Provider<File>() {
			@Inject @ContentRoot String contentRoot;


			@Override
			public File get() {
				return new File(contentRoot);
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

		pathParentIdentifier.addBinding(ContentRoot.class).toProvider(new Provider<Path>() {
			@Inject @ContentRoot String contentRoot;


			@Override
			public Path get() {
				return Paths.get(contentRoot);
			}
		});
	}

	protected void configureApplicationLocationParentIdentifiers() {
		MapBinder<Object, IApplicationLocation> fileParentIdentifierBinder = MapBinder.newMapBinder(binder(), Object.class, IApplicationLocation.class, ParentIdentifiers.class);

		fileParentIdentifierBinder.addBinding(StorageRoot.class).toProvider(new Provider<IApplicationLocation>() {
			@Inject @StorageRoot String storageRoot;

			@Override
			public IApplicationLocation get() {
				return new ApplicationLocation(storageRoot);
			}
		});

		fileParentIdentifierBinder.addBinding(ContentRoot.class).toProvider(new Provider<IApplicationLocation>() {
			@Inject @ContentRoot String contentRoot;

			@Override
			public IApplicationLocation get() {
				return new ApplicationLocation(contentRoot);
			}
		});
	}

}
