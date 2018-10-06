package com.tokelon.toktales.core.storage.utils;

/** Provides additional functionality for {@link IConformedPath}. 
 *
 */
public interface IExtendedPath extends IConformedPath {

	/* This all could also be static functionality */
	
	
	public String getPathAppendedBy(String value);
	
	public String getLocationAppendedBy(String value);

	public IExtendedPath newPathByAppend(IConformedPath path);
	
	public IExtendedPath newPathByAppend(String value);
	
	public IExtendedPath newPathByCutLastPlace();
	
}
