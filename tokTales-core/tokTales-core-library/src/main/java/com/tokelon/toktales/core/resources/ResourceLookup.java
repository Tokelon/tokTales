package com.tokelon.toktales.core.resources;

import com.tokelon.toktales.tools.core.objects.pools.SynchronizedPool;
import com.tokelon.toktales.tools.core.objects.pools.SynchronizedPool.PoolObjectFactory;

public class ResourceLookup implements IResourceLookup {
	
	private static final SynchronizedPool<ResourceLookup> pool = new SynchronizedPool<ResourceLookup>(new ResourceLookupFactory(), 4);	// This is optimized for 4 concurrent threads
	
	private ResourceTypeFilter currentTypeFilter;
	private String currentNameFilter;
	
	private int currentNameMatchingType = STRING_FILTER_TYPE_NONE;



	@Override
	public boolean filterNameApplies(String value) {
		if(currentNameFilter == null) {
			return true;
		}
		if(value == null) {
			throw new NullPointerException();
		}
		
		boolean result;
		
		switch (currentNameMatchingType) {
		case STRING_FILTER_TYPE_EQUALS:
			result = value.equals(currentNameFilter);
			break;
		case STRING_FILTER_TYPE_STARTS_WITH:
			result = value.startsWith(currentNameFilter);
			break;
		case STRING_FILTER_TYPE_REGEX:
			result = value.matches(currentNameFilter);
			break;
		case STRING_FILTER_TYPE_NONE:
			result = true;
			break;
		default:
			result = false;
			break;
		}
		
		return result;
	}

	@Override
	public boolean filterTypeApplies(IResourceType type) {
		if(currentTypeFilter == null) {
			return true;
		}
		if(type == null) {
			throw new NullPointerException();
		}
		
		return currentTypeFilter.applies(type);
	}
	
	
	@Override
	public ResourceTypeFilter getFilterType() {
		return currentTypeFilter;
	}
	public void setFilterType(ResourceTypeFilter typeFilter) {
		currentTypeFilter = typeFilter;
	}

	@Override
	public String getFilterName() {
		return currentNameFilter;
	}
	public void setFilterName(String nameFilter) {
		currentNameFilter = nameFilter;
	}

	@Override
	public int getNameMatchingType() {
		return currentNameMatchingType;
	}
	public void setNameMatchingType(int stringFilterType) {
		currentNameMatchingType = stringFilterType;
	}
	
	
	
	public void objectReset() {
		currentTypeFilter = null;
		currentNameFilter = null;
		currentNameMatchingType = STRING_FILTER_TYPE_NONE;
	}
	
	public static SynchronizedPool<ResourceLookup> getObjectPool() {
		return pool;
	}
	
	private static class ResourceLookupFactory implements PoolObjectFactory<ResourceLookup> {

		@Override
		public ResourceLookup createObject() {
			return new ResourceLookup();
		}
		
	}
	
}