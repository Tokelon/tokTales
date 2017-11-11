package com.tokelon.toktales.core.resources;

public interface IResourceLookup {

	public static final int STRING_FILTER_TYPE_NONE = 0;
	public static final int STRING_FILTER_TYPE_EQUALS = 1;
	public static final int STRING_FILTER_TYPE_STARTS_WITH = 2;
	public static final int STRING_FILTER_TYPE_REGEX = 3;
	
	
	public boolean filterNameApplies(String value);
	
	public boolean filterTypeApplies(IResourceType type);
	
	
	public String getFilterName();
	
	public ResourceTypeFilter getFilterType();
	
	public int getNameMatchingType();
	
}
