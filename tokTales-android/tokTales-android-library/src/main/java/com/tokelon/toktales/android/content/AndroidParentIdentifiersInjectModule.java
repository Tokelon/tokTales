package com.tokelon.toktales.android.content;

import java.io.File;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.multibindings.MapBinder;
import com.tokelon.toktales.core.engine.inject.AbstractInjectModule;
import com.tokelon.toktales.core.engine.inject.annotation.ContentRoot;
import com.tokelon.toktales.core.engine.inject.annotation.StorageRoot;
import com.tokelon.toktales.core.location.ApplicationLocation;
import com.tokelon.toktales.core.location.IApplicationLocation;
import com.tokelon.toktales.tools.assets.annotation.ParentIdentifiers;

public class AndroidParentIdentifiersInjectModule extends AbstractInjectModule {


	@Override
	protected void configure() {
		configureFileParentIdentifiers();

		configureApplicationLocationParentIdentifiers();
	}


	protected void configureFileParentIdentifiers() {
		MapBinder<Object, File> fileParentIdentifierBinder = MapBinder.newMapBinder(binder(), Object.class, File.class, ParentIdentifiers.class);

		fileParentIdentifierBinder.addBinding(StorageRoot.class).toProvider(new Provider<File>() {
			// Inject provider to avoid crashing in tests
			@Inject @StorageRoot Provider<String> storageRootProvider;


			@Override
			public File get() {
				return new File(storageRootProvider.get());
			}
		});

		fileParentIdentifierBinder.addBinding(ContentRoot.class).toProvider(new Provider<File>() {
			// Inject provider to avoid crashing in tests
			@Inject @ContentRoot Provider<String> contentRootProvider;


			@Override
			public File get() {
				return new File(contentRootProvider.get());
			}
		});
	}

	protected void configureApplicationLocationParentIdentifiers() {
		MapBinder<Object, IApplicationLocation> applicationLocationParentIdentifierBinder = MapBinder.newMapBinder(binder(), Object.class, IApplicationLocation.class, ParentIdentifiers.class);

		applicationLocationParentIdentifierBinder.addBinding(StorageRoot.class).toProvider(new Provider<IApplicationLocation>() {
			// Inject provider to avoid crashing in tests
			@Inject @StorageRoot Provider<String> storageRootProvider;

			@Override
			public IApplicationLocation get() {
				return new ApplicationLocation(storageRootProvider.get());
			}
		});

		applicationLocationParentIdentifierBinder.addBinding(ContentRoot.class).toProvider(new Provider<IApplicationLocation>() {
			// Inject provider to avoid crashing in tests
			@Inject @ContentRoot Provider<String> contentRootProvider;

			@Override
			public IApplicationLocation get() {
				return new ApplicationLocation(contentRootProvider.get());
			}
		});
	}

}
