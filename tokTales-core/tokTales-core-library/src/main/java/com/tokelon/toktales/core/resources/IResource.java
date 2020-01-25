package com.tokelon.toktales.core.resources;

import com.tokelon.toktales.core.location.IUniformLocation;

/* A location accompanied by a name and type, which contains a number of files that should be used in context of this resource definition.
 */
public interface IResource {
	// TODO: Have priorities for resources either here or implemented in IResourceView


	public String getName();
	
	public IUniformLocation getLocation(); // public IStorageLocation getLocation() ?
	
	public IResourceType getType();
}
