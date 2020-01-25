package com.tokelon.toktales.core.resources;

/** A view that can be used to access a collection of resources.
 */
public interface IResourceView extends Iterable<IResource> {


	public boolean containsResource(IResource resource);
	
}
