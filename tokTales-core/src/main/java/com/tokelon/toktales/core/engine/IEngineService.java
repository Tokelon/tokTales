package com.tokelon.toktales.core.engine;

public interface IEngineService {

	/** Adds the given extension with the given name.
	 * 
	 * @param name
	 * @param extension
	 * @throws ServiceException If there already exists an extension with the given name.
	 */
	public void addExtension(String name, IServiceExtension extension);

	/** Removes the extension for the given name.
	 * 
	 * @param name
	 * @return True if an extension was removed, false if not.
	 */
	public boolean removeExtension(String name);
	
	/**
	 * @param name
	 * @return True if there is an extension for the given name, false if not.
	 */
	public boolean hasExtension(String name);
	//public boolean hasExtensionForType(String name);
	
	
	/**
	 * @param name
	 * @return The extension for the given name, or null if there is none.
	 */
	public IServiceExtension getExtension(String name);
	
	/**
	 * @param name
	 * @param type
	 * @return The extension for the given name, in the given type.
	 * @throws ServiceException If the extension is not compatible with the given type or is null.
	 */
	public <T> T getExtensionAs(String name, Class<T> type);
	
	/**
	 * @param name
	 * @param type
	 * @return The extension for the given name or null if there is none, or if not compatible with the given type.
	 */
	public <T> T getExtensionAsIf(String name, Class<T> type);
	
	
	
	
	// Put in own file?
	public interface IServiceExtension {
		
		//public void serviceChanged(IEngineService service);
		public void attachService(IEngineService service);
		
	}
	
	
}
