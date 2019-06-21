package com.tokelon.toktales.desktop.storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import com.tokelon.toktales.core.engine.AbstractEngineService;
import com.tokelon.toktales.core.engine.inject.annotation.StorageRoot;
import com.tokelon.toktales.core.engine.inject.annotation.services.StorageServiceExtensions;
import com.tokelon.toktales.core.engine.storage.IStorageService;
import com.tokelon.toktales.core.engine.storage.StorageException;
import com.tokelon.toktales.core.resources.IListing;
import com.tokelon.toktales.core.resources.Listing;
import com.tokelon.toktales.core.storage.IApplicationLocation;
import com.tokelon.toktales.core.storage.LocationPrefix;
import com.tokelon.toktales.core.storage.utils.LocationImpl;
import com.tokelon.toktales.core.storage.utils.StructuredLocation;

public class DesktopStorageService extends AbstractEngineService implements IStorageService {
	/* Most implementation is copied from AndroidStorageService */

	private static final IApplicationLocation ROOT_LOCATION = new LocationImpl(""); // Is empty | TODO: Should this mirror extStorageRoot?

	
	private final String storageRoot;
	
	public DesktopStorageService(@StorageRoot String storageRoot) {
		this.storageRoot = storageRoot;
	}
	
	@Inject
	public DesktopStorageService(@StorageRoot String storageRoot, @StorageServiceExtensions Map<String, IServiceExtension> extensions) {
		super(extensions);
		
		this.storageRoot = storageRoot;
	}
	
	
	@Override
	public String getRoot() {
		return storageRoot;
	}
	
	@Override
	public IApplicationLocation getRootLocation() {
		return ROOT_LOCATION;
	}

	
	@Override
	public IListing createFileListing(IApplicationLocation location) throws StorageException {
		// Currently only lists files at location and does not follow directories
		
		String[] fileList = listAppDirOnExternal(location);
		
		String locationPath = location.getLocationPath().getPath();
		Listing listing = new Listing(locationPath);
		List<IListing.FileDescriptor> listingFileList = listing.getFileList();
		
		
		/* Note that the location will be the same object for all file descriptors.
		 * This is fine as long as they all are in the same location and because StructuredLocation is immutable. 
		 */
		StructuredLocation singleLocation = new StructuredLocation(LocationPrefix.EXTERNAL, location.getLocationPath().getLocation());
		for(String fileName: fileList) {
			IListing.FileDescriptor fd = new Listing.FileDescriptorImpl(fileName, singleLocation);
			listingFileList.add(fd);
		}
		
		return listing;
	}

	
	@Override
	public String[] listAppDirOnExternal(IApplicationLocation location) throws StorageException {
		
		File dir = new File(getExtAppDir(), location.getLocationPath().getPath());

		if(!dir.exists()) {
			throw new StorageException("Directory does not exist: " +location.getLocationPath().getPath());
		}

		if(!dir.isDirectory()) {
			throw new StorageException("Not a directory: " +location.getLocationPath().getPath());	
		}
		
		return dir.list();
	}

	@Override
	public File[] listFilesAppDirOnExternal(IApplicationLocation location) throws StorageException {
		File dir = new File(getExtAppDir(), location.getLocationPath().getPath());

		if(!dir.exists()) {
			throw new StorageException("Directory does not exist: " +location.getLocationPath().getPath());
		}

		if(!dir.isDirectory()) {
			throw new StorageException("Not a directory: " +location.getLocationPath().getPath());	
		}
		
		return dir.listFiles();
	}

	
	@Override
	public InputStream readAppFileOnExternal(IApplicationLocation storageLocation, String fileName) throws StorageException {
		File locationDir = new File(getExtAppDir(), storageLocation.getLocationPath().getPath());
		File externalFile = new File(locationDir, fileName);
		
		InputStream is;
		try {
			is = new FileInputStream(externalFile);
		} catch (FileNotFoundException e) {
			throw new StorageException(e);
		}
		
		return is;
	}

	@Override
	public InputStream tryReadAppFileOnExternal(IApplicationLocation storageLocation, String fileName) {
		File locationDir;
		try {
			locationDir = new File(getExtAppDir(), storageLocation.getLocationPath().getPath());
		} catch (StorageException e1) {		// Catch possible exception from getExtAppDir()
			return null;
		}
		
		File externalFile = new File(locationDir, fileName);
		
		
		InputStream is = null;
		if(externalFile.exists()) {
			try {
				is = new FileInputStream(externalFile);
			} catch (FileNotFoundException e) {
				// Must not happen
			}
		}
		
		return is;
	}

	
	@Override
	public OutputStream writeAppFileOnExternal(IApplicationLocation storageLocation, String fileName) throws StorageException {
		File locationDir = new File(getExtAppDir(), storageLocation.getLocationPath().getPath());
		File externalFile = new File(locationDir, fileName);
		
		OutputStream out;
		try {
			out = new FileOutputStream(externalFile);
		} catch (FileNotFoundException e) {
			throw new StorageException(String.format("Unable to write to file: %s (%s)", fileName, e.getMessage()));
		}
		
		return out;
	}
	
	
	@Override
	public File getAppLocationFileOnExternal(IApplicationLocation storageLocation) throws StorageException {
		File locationDir = new File(getExtAppDir(), storageLocation.getLocationPath().getPath());
		
		if(!locationDir.exists()) {
			throw new StorageException("Location does not exist: " +storageLocation.getLocationPath().getPath());
		}
		if(!locationDir.isDirectory()) {
			throw new StorageException("Location is not a directory: " +storageLocation.getLocationPath().getPath());	
		}

		return locationDir;
	}

	@Override
	public String getAppLocationPathOnExternal(IApplicationLocation storageLocation) throws StorageException {
		File locationDir = new File(getExtAppDir(), storageLocation.getLocationPath().getPath());
		
		if(!locationDir.exists()) {
			throw new StorageException("Location does not exist: " +storageLocation.getLocationPath().getPath());
		}
		if(!locationDir.isDirectory()) {
			throw new StorageException("Location is not a directory: " +storageLocation.getLocationPath().getPath());	
		}

		return locationDir.getAbsolutePath();
	}
	
	
	@Override
	public File getAppFileOnExternal(IApplicationLocation storageLocation, String filename) throws StorageException {
		File locationDir = new File(getExtAppDir(), storageLocation.getLocationPath().getPath());
		File externalFile = new File(locationDir, filename);
	
		if(!externalFile.exists()) {
			throw new StorageException("File does not exist: " +storageLocation.getLocationPath().getPath() + filename);
		}
		if(!externalFile.isFile()) {
			throw new StorageException("File is not a file: " +storageLocation.getLocationPath().getPath() + filename);
		}
		
		return externalFile;
	}
	
	
	@Override
	public File createTempFile(String prefix, String suffix) throws StorageException {
		try {
			return File.createTempFile(prefix, suffix);
		} catch (IOException e) {
			throw new StorageException(e);
		}
	}
	
	@Override
	public File createTempFileOnExternal(String prefix, String suffix) throws StorageException {
		try {
			return File.createTempFile(prefix, suffix, getExtCacheDir());
		} catch (IOException e) {
			throw new StorageException(e);
		}
	}
	

	private File getExtAppDir() throws StorageException {
		File appDir = new File(storageRoot); // Make field?
		
		if(!appDir.exists() || !appDir.isDirectory()) {
			if(!appDir.mkdir()) {
				throw new StorageException("Unable to create external application root directory");
			}
		}
		
		return appDir;
	}
	
	private File getExtCacheDir() throws StorageException {
		File cacheDir = new File(storageRoot, "cache");
		
		if(!cacheDir.exists() || !cacheDir.isDirectory()) {
			if(!cacheDir.mkdir()) {
				throw new StorageException("Unable to create external application cache directory");
			}
		}
		
		return cacheDir;
	}
	
	
	
	public static class DesktopStorageServiceFactory implements IStorageServiceFactory {
		
		@Override
		public IStorageService create(String storageRoot) {
			return new DesktopStorageService(storageRoot);
		}

		@Override
		public IStorageService create(String storageRoot, Map<String, IServiceExtension> extensions) {
			return new DesktopStorageService(storageRoot, extensions);
		}
	}

}
