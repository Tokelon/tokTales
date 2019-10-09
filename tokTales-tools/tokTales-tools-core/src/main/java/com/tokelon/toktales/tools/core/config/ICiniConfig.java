package com.tokelon.toktales.tools.core.config;

import java.util.List;

/** Configuration initialization config.<br><br>
 * 
 * All methods throw a NullpointerException for any passed null parameters.
 */
public interface ICiniConfig {
	
	/* TODO: Add support for multiple entries of the same kind (structures)
	 * For example by defining a section multiple times
	 * 
	 */
	

	/**
	 * 
	 * @return An unmodifiable List containing all sections, in the order they were added to this config.
	 */
	public List<String> getSections();
	
	/**
	 * 
	 * @param section
	 * @return An unmodifiable List containing all properties for the given section, in order they were added to this config. Returns null if the section does not exist.
	 */
	public List<String> getProperties(String section);
	
	/**
	 * 
	 * @param section
	 * @param propName
	 * @return The property value or null if the property does not exist.
	 */
	public String getPropertyValue(String section, String propName);
	
	
	
	public boolean hasSection(String section);
	
	/**
	 * 
	 * @param section
	 * @param propName
	 * @return Whether the section contains the property. Return false if the section does not exist. 
	 */
	public boolean hasProperty(String section, String propName);

	
	
	public int getSectionCount();
	
	/**
	 * 
	 * @param section
	 * @return The property count of the section. Returns 0 if the section does not exist.
	 */
	public int getPropertyCount(String section);
	
	
	
	public interface IMutableCiniConfig extends ICiniConfig {

		/** Clears section if existing.
		 * 
		 * @param section The section name.
		 * @return This object.
		 */
		public IMutableCiniConfig addSection(String section);
		
		/** Overrides value if existing.
		 * 
		 * @param section
		 * @param propName
		 * @param propValue
		 * @throws IllegalArgumentException If the section does not exist.
		 * @return This object.
		 */
		public IMutableCiniConfig addProperty(String section, String propName, String propValue);
		
		//public void addProperty(String section, String propName);	// empty value
		
		//public void setPropertyValue(String section, String propName, String propValue);
		
		
		/**
		 * 
		 * @param section
		 * @throws IllegalArgumentException If the section does not exist.
		 * @return This object.
		 */
		public IMutableCiniConfig removeSection(String section);
		
		/**
		 * 
		 * @param section
		 * @param propName
		 * @throws IllegalArgumentException If the section or property does not exist.
		 * @return This object.
		 */
		public IMutableCiniConfig removeProperty(String section, String propName);
	}
	
	
	
	/* Complex version

	//Confused Region with Section!


	public Set<ICiniRegion> getConfigRegions();
	
	
	public ICiniRegion getRegion(String regionName);

	public ICiniProperty getProperty(String regionName, String propName);
	
	public String getValue(String regionName, String propName);
	
	
	
	
	public interface ICiniRegion {
	
		public String getName();
		public ICiniProperty getProperty(String propName);
		public Set<ICiniProperty> getRegionProperties();
	}
	
	public interface ICiniProperty {
		public String getName();
		public String getValue();
	}
	
	*/
}
