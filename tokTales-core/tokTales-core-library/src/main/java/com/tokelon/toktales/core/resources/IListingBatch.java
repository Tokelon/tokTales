package com.tokelon.toktales.core.resources;


public interface IListingBatch extends Iterable<IResource> {

	public boolean addListing(IResource resource, IListing listing);
	
	public IListing getListing(IResource resource);
	
	public boolean hasListing(IResource resource);
	
	public boolean removeListing(IResource resource);
	
}
