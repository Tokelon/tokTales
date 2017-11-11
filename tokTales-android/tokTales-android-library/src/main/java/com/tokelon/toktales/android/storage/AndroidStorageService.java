package com.tokelon.toktales.android.storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import com.tokelon.toktales.core.engine.AbstractEngineService;
import com.tokelon.toktales.core.engine.storage.IStorageService;
import com.tokelon.toktales.core.engine.storage.StorageException;
import com.tokelon.toktales.core.engine.storage.StorageUnavailableException;
import com.tokelon.toktales.core.resources.IListing;
import com.tokelon.toktales.core.resources.Listing;
import com.tokelon.toktales.core.storage.IApplicationLocation;
import com.tokelon.toktales.core.storage.ILocation;
import com.tokelon.toktales.core.storage.LocationPrefix;
import com.tokelon.toktales.core.storage.utils.LocationImpl;
import com.tokelon.toktales.core.storage.utils.StructuredLocation;
import com.tokelon.toktales.core.values.LocationsAndPlaces;

import android.content.Context;
import android.os.Environment;

public class AndroidStorageService extends AbstractEngineService implements IStorageService {

	
	private Context globalContext;

	private static final ILocation EXTERNAL_APP_DIR = LocationsAndPlaces.LOCATION_EXTERNAL;
	
	private static final IApplicationLocation ROOT_LOCATION = new LocationImpl("");			// Is empty
	
	
	private final File extStorageRoot;
	
	public AndroidStorageService() {
		this(Environment.getExternalStorageDirectory());
	}
	
	public AndroidStorageService(File externalStorageRoot) {
		this.extStorageRoot = externalStorageRoot;
	}
	
	
	public void setGlobalContext(Context applicationContext) {
		globalContext = applicationContext;
	}
	
	
	
	//TODO: !! CHeck if all the streams are being closed correctly!
	
	
	
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
		
		return dir.list();
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

	
	
	

	private boolean isExternalStorageWritable() {
		String state = Environment.getExternalStorageState();
		return Environment.MEDIA_MOUNTED.equals(state);
	}
	
	private boolean isExternalStorageReadable() {
		String state = Environment.getExternalStorageState();
		return Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
	}
	
	
	private File getExtAppDir() throws StorageException {
		File appDir = new File(extStorageRoot, EXTERNAL_APP_DIR.getLocationPath().getPath());
		
		if(!appDir.exists()) {
			if(!appDir.mkdir()) {
				throw new StorageException("Unable to create external application root directory");
			}
		}
		
		return appDir;
	}
	
	
	
}
