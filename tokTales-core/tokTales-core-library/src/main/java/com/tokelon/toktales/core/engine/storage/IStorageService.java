package com.tokelon.toktales.core.engine.storage;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import com.tokelon.toktales.core.engine.IEngineService;
import com.tokelon.toktales.core.location.IApplicationLocation;
import com.tokelon.toktales.core.resources.IListing;

public interface IStorageService extends IEngineService {
	// TODO: Make same listing methods as in content framework
	// Also write javadoc

	
	public String getRoot();
	
	public IApplicationLocation getRootLocation();
	
	
	
	public IListing createFileListing(IApplicationLocation location) throws StorageException;
	
	/**
	 * 
	 * @param location
	 * @return
	 * @throws StorageException
	 * @throws StorageUnavailableException
	 */
	public String[] listAppDirOnExternal(IApplicationLocation location) throws StorageException;

	/**
	 * 
	 * @param location
	 * @return
	 * @throws StorageException
	 * @throws StorageUnavailableException
	 */
	public File[] listFilesAppDirOnExternal(IApplicationLocation location) throws StorageException;
	
	
	/**
	 * 
	 * @param storageLocation
	 * @param fileName
	 * @return
	 * @throws StorageException
	 * @throws StorageUnavailableException
	 */
	public InputStream readAppFileOnExternal(IApplicationLocation storageLocation, String fileName) throws StorageException;
	
	
	/** Does not throw any exceptions.
	 * 
	 * @param storageLocation
	 * @param fileName
	 * @return A valid InputStream for reading, or null if any error occurs.
	 */
	public InputStream tryReadAppFileOnExternal(IApplicationLocation storageLocation, String fileName);
	
	
	public OutputStream writeAppFileOnExternal(IApplicationLocation storageLocation, String fileName) throws StorageException;
	
	
	public File getAppLocationFileOnExternal(IApplicationLocation storageLocation) throws StorageException;
	
	public String getAppLocationPathOnExternal(IApplicationLocation storageLocation) throws StorageException;

	
	public File getAppFileOnExternal(IApplicationLocation storageLocation, String filename) throws StorageException;
	
	
	public File createTempFile(String prefix, String suffix) throws StorageException;
	public File createTempFileOnExternal(String prefix, String suffix) throws StorageException;
	
	
	public interface IStorageServiceFactory {
		
		public IStorageService create(String storageRoot);
		public IStorageService create(String storageRoot, Map<String, IServiceExtension> extensions);
	}
	
}
