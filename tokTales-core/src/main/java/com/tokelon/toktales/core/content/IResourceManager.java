package com.tokelon.toktales.core.content;

import java.util.List;

import com.tokelon.toktales.core.engine.content.ContentException;
import com.tokelon.toktales.core.engine.storage.StorageException;
import com.tokelon.toktales.core.resources.IListing;
import com.tokelon.toktales.core.resources.IListingBatch;
import com.tokelon.toktales.core.resources.IResource;
import com.tokelon.toktales.core.resources.IResourceLookup;
import com.tokelon.toktales.core.resources.IResourceSet;
import com.tokelon.toktales.core.resources.IListing.FileDescriptor;

/** <i>Implementations must be thread-safe.</i>
 * 
 *
 */
public interface IResourceManager {
	

	/** Adds the given resource set. Runs a scan for it and stores the resulting listing batch.
	 * 
	 * @param setIdentifier
	 * @param resourceSet
	 */
	public void preloadResourceSet(String setIdentifier, IResourceSet resourceSet);
	
	/** Runs a scan for every resource in the resource set. Returns the resulting listings.
	 * On error, ignores the failed resource and continues with the rest.
	 * 
	 * @param resourceSet
	 * @return
	 */
	public IListingBatch scanResourceSet(IResourceSet resourceSet);
	
	public IListingBatch scanResourceSetOrError(IResourceSet resourceSet) throws ContentException, StorageException;
	
	
	public FileDescriptor lookForFileMatching(IResourceLookup lookup);
	
	public List<FileDescriptor> searchForFilesMatching(IResourceLookup lookup);
	
	
	public IListing scanResource(IResource resource) throws ContentException, StorageException;
	
	public void preloadResource(IResource resource, String setIdentifier) throws ContentException, StorageException;
	public void preloadResource(IResource resource, IResourceSet resourceSet) throws ContentException, StorageException;
	
	
	/** Adds a listing batch for the given resource set.
	 * 
	 * @param resourceSet
	 * @param listingBatch
	 */
	public boolean addListingBatch(IResourceSet resourceSet, IListingBatch listingBatch);

	public boolean hasListingBatch(IResourceSet resourceSet);
	
	public IListingBatch getListingBatch(IResourceSet resourceSet);
	
	public boolean removeListingBatch(IResourceSet resourceSet);
	
	
	public boolean addResourceSet(String setIdentifier, IResourceSet resourceSet);
	
	public boolean hasResourceSet(String setIdentifier);
	
	public IResourceSet getResourceSet(String setIdentifier);
	
	public boolean removeResourceSet(String setIdentifier);
	
	
	public void resetResources();
	
	public void resetListings();
	
	
	public IResourceSet getLooseResources();
	
	public IListingBatch getLooseListingBatch();

	
}
