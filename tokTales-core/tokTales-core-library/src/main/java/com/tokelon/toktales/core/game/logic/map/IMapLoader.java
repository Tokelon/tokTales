package com.tokelon.toktales.core.game.logic.map;

import java.io.IOException;

import com.tokelon.toktales.core.game.model.map.IBlockMap;
import com.tokelon.toktales.tools.core.tiled.MapFormatException;

/** Note that for validation it is required to provide a separate InputStream / map file along with the InputStream / map file that will be read.  
 * 
 *
 */
public interface IMapLoader {

	// TODO: Correctly document this. 
	
	/**
	 * 
	 * @param validationFileIn The map file stream used for validation.
	 * @throws StorageException
	 */
	//public void enableValidation(InputStream validationFileIn) throws StorageException;
	
	/**
	 * 
	 * @param validationFileName The map file name used for validation.
	 * @throws StorageException
	 */
	//public void enableValidation(String validationFileName) throws StorageException;
	
	/**
	 * 
	 * @param validationFileIn The map file stream used for validation.
	 * @param dtdIn The DTD file stream used for validation.
	 */
	//public void enableValidation(InputStream validationFileIn, InputStream dtdIn);
	
	//public void disableValidation();
	
	
	/**
	 * 
	 * @param mapFileName The map file name used for loading.
	 * @throws StorageException
	 */
	//public void setTarget(String mapFileName) throws StorageException;
	
	/**
	 * 
	 * @param mapFileIn The map file stream used for loading.
	 */
	//public void setTarget(InputStream mapFileIn, String mapFileName);
	

	
	public void runComplete() throws MapLoaderException, MapFormatException, IOException;
	
	
	public void initialize() throws MapFormatException, IOException;
	
	public void load() throws MapLoaderException, MapFormatException, IOException;
	
	public void process() throws MapLoaderException;
	

	
	public IBlockMap getLoadedMap();
	
}
