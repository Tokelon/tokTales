package com.tokelon.toktales.core.resources;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ListingBatch implements IListingBatch {

	private final Map<IResource, IListing> listingsMap = new HashMap<IResource, IListing>();

	@Override
	public boolean addListing(IResource resource, IListing listing) {
		return listingsMap.put(resource, listing) != null;
	}

	@Override
	public IListing getListing(IResource resource) {
		return listingsMap.get(resource);
	}

	@Override
	public boolean hasListing(IResource resource) {
		return listingsMap.containsKey(resource);
	}

	@Override
	public boolean removeListing(IResource resource) {
		return listingsMap.remove(resource) != null;
	}

	@Override
	public Iterator<IResource> iterator() {
		return listingsMap.keySet().iterator();
	}
	
}
