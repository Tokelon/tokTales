package com.tokelon.toktales.core.content;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tokelon.toktales.core.engine.TokTales;
import com.tokelon.toktales.core.engine.content.ContentException;
import com.tokelon.toktales.core.engine.content.IContentService;
import com.tokelon.toktales.core.engine.storage.IStorageService;
import com.tokelon.toktales.core.engine.storage.StorageException;
import com.tokelon.toktales.core.prog.annotation.ProgRequiresLog;
import com.tokelon.toktales.core.resources.IListing;
import com.tokelon.toktales.core.resources.IListingBatch;
import com.tokelon.toktales.core.resources.IResource;
import com.tokelon.toktales.core.resources.IResourceLookup;
import com.tokelon.toktales.core.resources.IResourceSet;
import com.tokelon.toktales.core.resources.ListingBatch;
import com.tokelon.toktales.core.resources.ResourceFilterView;
import com.tokelon.toktales.core.resources.ResourceSet;
import com.tokelon.toktales.core.resources.IListing.FileDescriptor;
import com.tokelon.toktales.core.storage.utils.ApplicationLocationWrapper;

@ProgRequiresLog
public class ResourceManager implements IResourceManager {

	private static final String TAG = ResourceManager.class.getSimpleName();

	public static final String LOOSE_RESOURCES_IDENTIFIER = "resource_manager_loose_resources_identifier"; 
	public static final String TEMP_RESOURCES_IDENTIFIER = "resource_manager_temp_resources_identifier";
	
	
	
	private final IContentService contentService;
	private final IStorageService storageService;
	
	private final Map<String, IResourceSet> resourceSetsMap;
	private final Map<IResourceSet, IListingBatch> listingBatchesMap;
	
	
	/* Filters */
	private ResourceFilterView filterSpriteManager;
	
	
	public ResourceManager(IContentService contentService, IStorageService storageService) {
		if(contentService == null || storageService == null) {
			throw new NullPointerException();
		}
		
		this.contentService = contentService;
		this.storageService = storageService;
		
		
		
		resourceSetsMap = Collections.synchronizedMap(new HashMap<String, IResourceSet>());
		listingBatchesMap = Collections.synchronizedMap(new HashMap<IResourceSet, IListingBatch>());
		
		// Add loose resources
		ResourceSet looseResources = new ResourceSet();
		resourceSetsMap.put(LOOSE_RESOURCES_IDENTIFIER, looseResources);
		listingBatchesMap.put(looseResources, new ListingBatch());
		
		/*
		// Add temp resources
		ResourceSet tempResources = new ResourceSet();
		resourceSetsMap.put(TEMP_RESOURCES_IDENTIFIER, tempResources);
		listingBatchesMap.put(tempResources, new ListingBatch());
		*/
	}
	
	/* TODO: Important - Check all maps sets etc. and related classes for thread safety and document it
	 * 
	 */
	

	@Override
	public void preloadResourceSet(String setIdentifier, IResourceSet resourceSet) {
		
		// Scan given resources locations
		IListingBatch listingBatch = scanResourceSet(resourceSet);
		
		resourceSetsMap.put(setIdentifier, resourceSet);		// Put into sets map
		listingBatchesMap.put(resourceSet, listingBatch);		// Put into listings map
	}

	
	@Override
	public IListingBatch scanResourceSet(IResourceSet resourceSet) {
		ListingBatch batch = new ListingBatch();
		
		for(IResource res: resourceSet) {

			try {
				IListing listing = scanResource(res);
				
				batch.addListing(res, listing);
			} catch (ContentException e) {
				
				TokTales.getLog().w(TAG, "Bad resource (internal). Ignoring: " +res.getName() +" (" +e.getMessage() +")");
			} catch (StorageException e) {

				TokTales.getLog().w(TAG, "Bad resource (external). Ignoring: " +res.getName() +" (" +e.getMessage() +")");
			}			
		}
		
		return batch;
	}

	@Override
	public IListingBatch scanResourceSetOrError(IResourceSet resourceSet) throws ContentException, StorageException {
		ListingBatch batch = new ListingBatch();
		
		for(IResource res: resourceSet) {

			IListing listing = scanResource(res);
			batch.addListing(res, listing);	
		}
		
		return batch;
	}

	
	
	@Override
	public FileDescriptor lookForFileMatching(IResourceLookup lookup) {
		
		for(IResourceSet rset: listingBatchesMap.keySet()) {			// For all resource sets that have a listing	
			IListingBatch listingBatch = listingBatchesMap.get(rset);
			
			for(IResource resource: listingBatch) {						// For all resources in the listing
				if(lookup.filterTypeApplies(resource.getType())) {		// That match the type filter
					
					IListing listing = listingBatch.getListing(resource);
					
					FileDescriptor fdres;
					switch (lookup.getNameMatchingType()) {
					case IResourceLookup.STRING_FILTER_TYPE_EQUALS:
						
						fdres = listing.lookupName(lookup.getFilterName());
						break;
					case IResourceLookup.STRING_FILTER_TYPE_STARTS_WITH:
						
						fdres = listing.lookupNameStart(lookup.getFilterName());
						break;
					case IResourceLookup.STRING_FILTER_TYPE_REGEX:
						
						fdres = listing.lookupNameExpression(lookup.getFilterName());
						break;
					default:
						fdres = null;
						break;
					}
					
					if(fdres != null) {
						return fdres;
					}
				}
			}
		}
		
		return null;
	}
	
	@Override
	public List<FileDescriptor> searchForFilesMatching(IResourceLookup lookup) {
		ArrayList<FileDescriptor> resultList = new ArrayList<FileDescriptor>();
		
		for(IResourceSet rset: listingBatchesMap.keySet()) {			// For all resource sets that have a listing	
			IListingBatch listingBatch = listingBatchesMap.get(rset);
			
			for(IResource resource: listingBatch) {						// For all resources in the listing
				if(lookup.filterTypeApplies(resource.getType())) {		// That match the type filter
					
					IListing listing = listingBatch.getListing(resource);
					
					FileDescriptor fdres;
					switch (lookup.getNameMatchingType()) {
					case IResourceLookup.STRING_FILTER_TYPE_EQUALS:
						
						fdres = listing.lookupName(lookup.getFilterName());
						break;
					case IResourceLookup.STRING_FILTER_TYPE_STARTS_WITH:
						
						fdres = listing.lookupNameStart(lookup.getFilterName());
						break;
					case IResourceLookup.STRING_FILTER_TYPE_REGEX:
						
						fdres = listing.lookupNameExpression(lookup.getFilterName());
						break;
					default:
						fdres = null;
						break;
					}
					
					if(fdres != null) {
						resultList.add(fdres);
					}
				}
			}
		}
		
		return resultList;
	}
	

	@Override
	public IListing scanResource(IResource resource) throws ContentException, StorageException {
		IListing listing = null;
		ApplicationLocationWrapper relLocation = ApplicationLocationWrapper.getObjectPool().newObject();	// Get the object

		
		switch(resource.getLocation().getPrefix()) {
		case INTERNAL:
		case ASSET:
			relLocation.setActualLocation(resource.getLocation());		// Set the object data
			
			listing = contentService.createAssetListing(relLocation);	// Use the object			
			break;
		case EXTERNAL:
			relLocation.setActualLocation(resource.getLocation());		// Set the object data
			
			listing = storageService.createFileListing(relLocation);
			break;
		default:
			
			throw new ContentException("Unsupported location for resource: " +resource.getName());
		}
		
		ApplicationLocationWrapper.getObjectPool().free(relLocation);	// Free the object
		return listing;
	}

	
	@Override
	public void preloadResource(IResource resource, String setIdentifier) throws ContentException, StorageException {
		preloadResource(resource, resourceSetsMap.get(setIdentifier));
	}


	@Override
	public void preloadResource(IResource resource, IResourceSet resourceSet) throws ContentException, StorageException {
		if(resource == null || resourceSet == null) {
			throw new NullPointerException();
		}
		
		// Do scan
		IListing listing = scanResource(resource);
		
		// Add into resources
		resourceSet.addResource(resource);
		
		// Add to listings
		IListingBatch listingBatch;
		if(listingBatchesMap.containsKey(resourceSet)) {
			
			listingBatch = listingBatchesMap.get(resourceSet);
		}
		else {
			listingBatch = new ListingBatch();
			listingBatchesMap.put(resourceSet, listingBatch);
		}
		
		listingBatch.addListing(resource, listing);
	}
	
	
	
	 
	
	
	@Override
	public boolean addListingBatch(IResourceSet resourceSet, IListingBatch listingBatch) {
		return listingBatchesMap.put(resourceSet, listingBatch) != null;
	}

	@Override
	public boolean hasListingBatch(IResourceSet resourceSet) {
		return listingBatchesMap.containsKey(resourceSet);
	}

	@Override
	public IListingBatch getListingBatch(IResourceSet resourceSet) {
		return listingBatchesMap.get(resourceSet);
	}

	@Override
	public boolean removeListingBatch(IResourceSet resourceSet) {
		return listingBatchesMap.remove(resourceSet) != null;
	}



	@Override
	public boolean addResourceSet(String setIdentifier, IResourceSet resourceSet) {
		return resourceSetsMap.put(setIdentifier, resourceSet) != null;
	}

	@Override
	public boolean hasResourceSet(String setIdentifier) {
		return resourceSetsMap.containsKey(setIdentifier);
	}

	@Override
	public IResourceSet getResourceSet(String setIdentifier) {
		return resourceSetsMap.get(setIdentifier);
	}

	@Override
	public boolean removeResourceSet(String setIdentifier) {
		return resourceSetsMap.remove(setIdentifier) != null;
	}


	@Override
	public void resetResources() {
		resourceSetsMap.clear();
		
		// Readd loose resources
		ResourceSet looseResources = new ResourceSet();
		resourceSetsMap.put(LOOSE_RESOURCES_IDENTIFIER, looseResources);
	}

	@Override
	public void resetListings() {
		listingBatchesMap.clear();
		
		listingBatchesMap.put(resourceSetsMap.get(LOOSE_RESOURCES_IDENTIFIER), new ListingBatch());
	}



	@Override
	public IResourceSet getLooseResources() {
		return resourceSetsMap.get(LOOSE_RESOURCES_IDENTIFIER);
	}

	@Override
	public IListingBatch getLooseListingBatch() {
		return listingBatchesMap.get(resourceSetsMap.get(LOOSE_RESOURCES_IDENTIFIER));
	}

	
	
	/*
	@Override
	public void scanAndAddListing(IResource resource) {
		IListing listing = scanResource(resource);
		
		
		//TODO: This will have to be synchronized with any iterators
		listingsMap.put(resource, listing);
	}
	*/
	

	
	/*

	// TODO: Maybe put in different interface, say "dynamic functions" or something
	@Override
	public void addResource(IResource resource, boolean save) {
		//TODO: Implement save as in save to map and not only add to loader

		//Prog.gHub().gGame().getMapManager().getCurrentMap().getResources().addResource(resource);
		//currentResources.

		if(save) {
			// Save to actual resources

			if(!currentResources.containsResource(resource)) {
				currentResources.addResource(resource);			// TODO: Synchronize this part to avoid conflict with iterator?

				managerSprite.runClearMissing();
			}
		}
		else if(filterSpriteManager.getFilter().applies(resource.getType())) {
			// Save to filter

			if(!filterSpriteManager.containsResource(resource)) {
				filterSpriteManager.getAdditional().addResource(resource);

				managerSprite.runClearMissing();
			}
		}
		else {
			Prog.gHub().gFrameworkLog().e(TAG, "Resource type does not match any filters");
		}
	}

	*/



}
