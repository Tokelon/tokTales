package com.tokelon.toktales.core.resources;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ResourceFilterView implements IResourceView {

	
	private final ResourceSet additionalResources = new ResourceSet();
	
	private final IResourceSet resources;
	private final ResourceTypeFilter filter;
	
	public ResourceFilterView(IResourceSet resources, ResourceTypeFilter resourceFilter) {
		this.resources = resources;
		filter = resourceFilter;
	}

	
	@Override
	public Iterator<IResource> iterator() {
		return new FilterIteratorWrapper(new FilterIterator(resources.iterator()), additionalResources.iterator());
	}

	@Override
	public boolean containsResource(IResource resource) {
		return (filter.applies(resource.getType()) && resources.containsResource(resource)) || additionalResources.containsResource(resource);
	}
	
	
	public ResourceTypeFilter getFilter() {
		return filter;
	}
	
	public IResourceSet getAdditional() {
		return additionalResources;
	}
	
	
	
	
	
	
	private class FilterIterator implements Iterator<IResource> {


		private final Iterator<IResource> iterator;
		private IResource nextResource;
		
		public FilterIterator(Iterator<IResource> iterator) {
			this.iterator = iterator;
			
			nextResource = findNext();
		}
		
		private IResource findNext() {
			while(iterator.hasNext()) {
				IResource r = iterator.next();
				if(filter.applies(r.getType())) {
					return r;
				}
			}
			
			return null;
		}
		
		
		@Override
		public boolean hasNext() {
			return nextResource != null;
		}

		@Override
		public IResource next() {
			if(nextResource == null) {
				throw new NoSuchElementException("Iteration has no more elements");
			}
			
			IResource actualNext = nextResource;
			nextResource = findNext();
			
			return actualNext;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException("This implementation does not support remove()");
		}
		
	}
	
	
	private class FilterIteratorWrapper implements Iterator<IResource> {

		private final FilterIterator filterIterator;
		private final Iterator<IResource> additionalIterator;
		
		public FilterIteratorWrapper(FilterIterator filterIterator, Iterator<IResource> additionalIterator) {
			this.filterIterator = filterIterator;
			this.additionalIterator = additionalIterator;
		}
		
		
		@Override
		public boolean hasNext() {
			return filterIterator.hasNext() || additionalIterator.hasNext();
		}

		@Override
		public IResource next() {
			if(filterIterator.hasNext()) {
				return filterIterator.next();
			}
			else if(additionalIterator.hasNext()) {
				return additionalIterator.next();
			}
			else {
				throw new NoSuchElementException();
			}
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException("This implementation does not support remove()");
		}
		
	}
	
}
