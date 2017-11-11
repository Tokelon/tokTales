package com.tokelon.toktales.core.resources;

/** A collection of resources that can be modified as a set.
 * 
 */
public interface IResourceSet extends IResourceView {

	
	//TODO: Throw exception if resource is already added?
	public boolean addResource(IResource resource);
	
	public boolean removeResource(IResource resource);

}
