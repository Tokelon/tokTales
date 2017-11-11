package com.tokelon.toktales.core.resources;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/** Reference implementation for {@link IResourceSet}. 
 * 
 */
public class ResourceSet implements IResourceSet {

	private final Set<IResource> resourceSet = new HashSet<IResource>();

	
	@Override
	public Iterator<IResource> iterator() {
		return resourceSet.iterator();
	}
	
	@Override
	public boolean addResource(IResource resource) {
		return resourceSet.add(resource);
	}

	@Override
	public boolean removeResource(IResource resource) {
		return resourceSet.remove(resource);
	}

	@Override
	public boolean containsResource(IResource resource) {
		return resourceSet.contains(resource);
	}
	
}
