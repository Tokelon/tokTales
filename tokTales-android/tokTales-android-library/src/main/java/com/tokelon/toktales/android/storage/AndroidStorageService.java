package com.tokelon.toktales.android.storage;

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
import com.tokelon.toktales.core.engine.storage.StorageUnavailableException;
import com.tokelon.toktales.core.location.ApplicationLocation;
import com.tokelon.toktales.core.location.IApplicationLocation;
import com.tokelon.toktales.core.location.LocationScheme;
import com.tokelon.toktales.core.location.UniformLocation;
import com.tokelon.toktales.core.resources.IListing;
import com.tokelon.toktales.core.resources.Listing;

import android.content.Context;
import android.os.Environment;

public class AndroidStorageService extends AbstractEngineService implements IStorageService {
	// TODO: Check if all the streams are being closed correctly!
	
	private static final IApplicationLocation ROOT_LOCATION = new ApplicationLocation(""); // Is empty

	
	private final Context globalContext;
	private final String storageRoot;
	
	public AndroidStorageService(Context globalContext, @StorageRoot String storageRoot) {
		this.globalContext = globalContext;
		this.storageRoot = storageRoot;
	}
	
	@Inject
	public AndroidStorageService(Context globalContext, @StorageRoot String storageRoot, @StorageServiceExtensions Map<String, IServiceExtension> extensions) {
		super(extensions);
		
		this.globalContext = globalContext;
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
		UniformLocation singleLocation = new UniformLocation(LocationScheme.STORAGE, location.getLocationPath().getLocation());
		for(String fileName: fileList) {
			IListing.FileDescriptor fd = new Listing.FileDescriptorImpl(fileName, singleLocation);
			listingFileList.add(fd);
		}
		
		return listing;
		
	}
	
	
	@Override
	public String[] listAppDirOnExternal(IApplicationLocation location) throws StorageException {
		if(!isExternalStorageReadable()) {
			throw new StorageUnavailableException();
		}
		

		File extDir = getExtAppDir();	// External app dir is parent directory
		File dir = new File(extDir, location.getLocationPath().getPath());
		
		if(!dir.exists()) {
			/* DO NOT CREATE IT
			if(!dir.mkdirs()) {
				throw new StorageException("Unable to create directory: " +location.getRelativePath());
			}*/
			
			throw new StorageException("Directory does not exist: " +location.getLocationPath().getPath());
		}

		if(!dir.isDirectory()) {
			throw new StorageException("Not a directory: " +location.getLocationPath().getPath());	
		}

		String[] list = dir.list();
		if(list == null) {
			throw new StorageException("IO Error while listing files for directory: " +location.getLocationPath().getPath());
		}

		return list;
	}
	

	@Override
	public File[] listFilesAppDirOnExternal(IApplicationLocation location) throws StorageException {
		if(!isExternalStorageReadable()) {
			throw new StorageUnavailableException();
		}
		

		File extDir = getExtAppDir();	// External app dir is parent directory
		File dir = new File(extDir, location.getLocationPath().getPath());
		
		if(!dir.exists()) {
			/* DO NOT CREATE IT
			if(!dir.mkdirs()) {
				throw new StorageException("Unable to create directory: " +location.getRelativePath());
			}*/
			
			throw new StorageException("Directory does not exist: " +location.getLocationPath().getPath());
		}

		if(!dir.isDirectory()) {
			throw new StorageException("Not a directory: " +location.getLocationPath().getPath());	
		}
		
		return dir.listFiles();
	}

	
	
	@Override
	public InputStream readAppFileOnExternal(IApplicationLocation storageLocation, String fileName) throws StorageException {
		if(!isExternalStorageReadable()) {
			throw new StorageUnavailableException();
		}
		
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
		if(!isExternalStorageReadable()) {
			return null;
		}
		
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
		if(!isExternalStorageWritable()) {
			throw new StorageUnavailableException("Unavailable for writing");
		}
		
		File locationDir = new File(getExtAppDir(), storageLocation.getLocationPath().getPath());
		File externalFile = new File(locationDir, fileName);
		
		OutputStream out;
		try {
			out = new FileOutputStream(externalFile);
		} catch (FileNotFoundException e) {
			throw new StorageException(String.format("Unable to write to file: %s (%s)", fileName, e.getMessage()));
		}
		
		return out;
	};
	
	
	
	@Override
	public File getAppLocationFileOnExternal(IApplicationLocation storageLocation) throws StorageException {
		if(!isExternalStorageReadable()) {
			throw new StorageUnavailableException();
		}
		
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
		if(!isExternalStorageReadable()) {
			throw new StorageUnavailableException();
		}
		
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
		if(!isExternalStorageReadable()) {
			throw new StorageUnavailableException();
		}
		
		File locationDir = new File(getExtAppDir(), storageLocation.getLocationPath().getPath());
		File externalFile = new File(locationDir, filename);
		
		// TODO: Add file.canRead() to all methods!!
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
			return File.createTempFile(prefix, suffix, globalContext.getCacheDir());
		} catch (IOException e) {
			throw new StorageException(e);
		}
	}
	
	@Override
	public File createTempFileOnExternal(String prefix, String suffix) throws StorageException {
		try {
			return File.createTempFile(prefix, suffix, globalContext.getExternalCacheDir());
		} catch (IOException e) {
			throw new StorageException(e);
		}
	}
	

	private boolean isExternalStorageWritable() {
		String state = Environment.getExternalStorageState();
		return Environment.MEDIA_MOUNTED.equals(state);
	}
	
	private boolean isExternalStorageReadable() {
		String state = Environment.getExternalStorageState();
		return Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
	}
	
	
	private File getExtAppDir() throws StorageException {
		File appDir = new File(storageRoot);
		
		if(!appDir.exists() || !appDir.isDirectory()) {
			if(!appDir.mkdir()) {
				throw new StorageException("Unable to create external application root directory");
			}
		}
		
		return appDir;
	}
	
	
	public static class AndroidStorageServiceFactory implements IStorageServiceFactory {
		private final Context globalContext;

		@Inject
		public AndroidStorageServiceFactory(Context globalContext) {
			this.globalContext = globalContext;
		}
		
		@Override
		public IStorageService create(String storageRoot) {
			return new AndroidStorageService(globalContext, storageRoot);
		}

		@Override
		public IStorageService create(String storageRoot, Map<String, IServiceExtension> extensions) {
			return new AndroidStorageService(globalContext, storageRoot, extensions);
		}
	}
	
}
