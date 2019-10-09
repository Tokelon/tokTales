package com.tokelon.toktales.tools.core.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tokelon.toktales.tools.core.config.ICiniConfig.IMutableCiniConfig;

public class MutableCiniConfig implements IMutableCiniConfig {
	
	
	private final Map<String, Map<String, String>> sectionToPropertiesToValues;
	
	private final List<String> sectionList;
	private final List<String> immuSectionList;
	
	private final Map<String, List<String>> sectionToPropertiesList;
	private final Map<String, List<String>> immuSectionToPropertiesList;
	

	// Cannot modify while iterating over sections or properties list


	
	public MutableCiniConfig() {
		
		sectionList = new ArrayList<String>();
		immuSectionList = Collections.unmodifiableList(sectionList);
		
		sectionToPropertiesToValues = new HashMap<String, Map<String, String>>();
		sectionToPropertiesList = new HashMap<String, List<String>>();
		immuSectionToPropertiesList = new HashMap<String, List<String>>();
		
	}
	
	
	@Override
	public List<String> getSections() {
		return immuSectionList;
	}

	@Override
	public List<String> getProperties(String section) {		// Return null or throw an exception if the section does not exist ?
		if(section == null) {
			throw new NullPointerException();
		}
		
		return immuSectionToPropertiesList.get(section);
	}

	
	@Override
	public String getPropertyValue(String section, String propName) {
		if(section == null || propName == null) {
			throw new NullPointerException();
		}
		
		if(!sectionToPropertiesToValues.containsKey(section)) {
			// If the section does not exist, return null
			
			return null;
			//throw new IllegalArgumentException(String.format("Cannot get property \"%s\". No such section: %s", propName, section));
		}
		
		/*
		if(!sectionToPropertiesToValues.get(section).containsKey(propName)) {
			throw new IllegalArgumentException(String.format("No such property: %s", propName));
		}
		*/
		
		return sectionToPropertiesToValues.get(section).get(propName); 
	}

	@Override
	public boolean hasSection(String section) {
		if(section == null) {
			throw new NullPointerException();
		}
		
		return sectionToPropertiesToValues.containsKey(section);
	}

	@Override
	public boolean hasProperty(String section, String propName) {
		if(section == null || propName == null) {
			throw new NullPointerException();
		}
		
		if(!sectionToPropertiesToValues.containsKey(section)) {
			// Return false if the section does not exists
			
			return false;
			//throw new IllegalArgumentException(String.format("Cannot test for property. No such section: %s", section));
		}
		
		return sectionToPropertiesToValues.get(section).containsKey(propName);
	}

	@Override
	public int getSectionCount() {
		return sectionToPropertiesToValues.size();
	}

	@Override
	public int getPropertyCount(String section) {
		if(section == null) {
			throw new NullPointerException();
		}
		
		if(!sectionToPropertiesToValues.containsKey(section)) {
			// Return 0 if the section does not exist
			
			return 0;
			//throw new IllegalArgumentException(String.format("Cannot get property count. No such section: %s", section));
		}
		
		return sectionToPropertiesToValues.get(section).size();
	}

	
	
	@Override
	public IMutableCiniConfig addSection(String section) {
		if(section == null) {
			throw new NullPointerException();
		}
		
		
		Map<String, String> sectionContent = new HashMap<String, String>();
		sectionToPropertiesToValues.put(section, sectionContent);
		
		sectionList.remove(section);	// Remove the section from the list in case it's already on there
		sectionList.add(section);		// Add the section to the end of the list
		
		
		List<String> propertyList = new ArrayList<String>();
		List<String> immuPropertyList = Collections.unmodifiableList(propertyList);
		
		sectionToPropertiesList.put(section, propertyList);
		immuSectionToPropertiesList.put(section, immuPropertyList);
		
		return this;
	}

	@Override
	public IMutableCiniConfig addProperty(String section, String propName, String propValue) {
		if(section == null || propName == null || propValue == null) {
			throw new NullPointerException();
		}
		
		if(!sectionToPropertiesToValues.containsKey(section)) {
			throw new IllegalArgumentException(String.format("Cannot add property \"%s\". No such section: %s", propName, section));
		}
		
		sectionToPropertiesToValues.get(section).put(propName, propValue);
		
		sectionToPropertiesList.remove(propName);	// Remove the property in case it's already on there
		sectionToPropertiesList.get(section).add(propName);		// Add property name to the end of the list
		
		return this;
	}
	
	
	@Override
	public IMutableCiniConfig removeSection(String section) {
		if(section == null) {
			throw new NullPointerException();
		}

		if(!sectionToPropertiesToValues.containsKey(section)) {
			throw new IllegalArgumentException(String.format("Cannot remove section. No such section: %s", section));
		}
		
		
		sectionToPropertiesList.remove(section);
		sectionList.remove(section);
		sectionToPropertiesToValues.remove(section);
		
		return this;
	}

	@Override
	public IMutableCiniConfig removeProperty(String section, String propName) {
		if(section == null || propName == null) {
			throw new NullPointerException();
		}
		
		if(!sectionToPropertiesToValues.containsKey(section)) {
			throw new IllegalArgumentException(String.format("Cannot remove property \"%s\". No such section: %s", propName, section));
		}
		if(!sectionToPropertiesToValues.get(section).containsKey(propName)) {
			throw new IllegalArgumentException(String.format("Cannot remove property \"%s\". No such property", propName));
		}
		
		sectionToPropertiesList.get(section).remove(propName);
		sectionToPropertiesToValues.get(section).remove(propName);
		
		return this;
	}
	
}
