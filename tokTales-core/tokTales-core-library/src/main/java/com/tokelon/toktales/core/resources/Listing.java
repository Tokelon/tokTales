package com.tokelon.toktales.core.resources;

import java.util.ArrayList;
import java.util.List;

import com.tokelon.toktales.core.storage.IStructuredLocation;

public class Listing implements IListing {

	
	private final String root;
	
	private final List<FileDescriptor> directoryList;	// Should these be sets?
	private final List<FileDescriptor> fileList;
	
	public Listing(String root) {
		this.root = root;
		
		directoryList = new ArrayList<FileDescriptor>();
		fileList = new ArrayList<FileDescriptor>();
	}
	
	
	
	public List<FileDescriptor> getFileList() {
		return fileList;
	}
	
	public List<FileDescriptor> getDirectoryList() {
		return directoryList;
	}
	
	public String getRoot() {
		return root;
	}
	
	
	
	/* Lookup functions with one result */
	
	@Override
	public FileDescriptor lookupName(String value) {
		if(value == null) {
			throw new NullPointerException();
		}
		
		for(FileDescriptor fd: fileList) {
			
			if(fd.getName().equals(value)) {
				return fd;
			}
		}
		
		return null;
	}


	@Override
	public FileDescriptor lookupNameStart(String startingValue) {
		if(startingValue == null) {
			throw new NullPointerException();
		}
		
		for(FileDescriptor fd: fileList) {
			
			if(fd.getName().startsWith(startingValue)) {
				return fd;
			}
		}
		
		return null;
	}


	@Override
	public FileDescriptor lookupNameExpression(String regex) {
		if(regex == null) {
			throw new NullPointerException();
		}
		
		for(FileDescriptor fd: fileList) {
			
			if(fd.getName().matches(regex)) {
				return fd;
			}
		}
		
		return null;
	}
	
	
	/* Search functions with multiple results */
	
	@Override
	public void searchForName(String value, List<FileDescriptor> results) {
		if(value == null || results == null) {
			throw new NullPointerException();
		}
		
		for(FileDescriptor fd: fileList) {
			
			if(fd.getName().equals(value)) {
				results.add(fd);
			}
		}
	}
	
	@Override
	public List<FileDescriptor> searchForName(String value) {
		ArrayList<FileDescriptor> resl = new ArrayList<FileDescriptor>();
		
		searchForName(value, resl);
		return resl;
	}

	
	@Override
	public void searchForNameStart(String startingValue, List<FileDescriptor> results) {
		if(startingValue == null || results == null) {
			throw new NullPointerException();
		}
		
		for(FileDescriptor fd: fileList) {
			
			if(fd.getName().startsWith(startingValue)) {
				results.add(fd);
			}
		}
	}
	
	@Override
	public List<FileDescriptor> searchForNameStart(String startingValue) {
		ArrayList<FileDescriptor> resl = new ArrayList<FileDescriptor>();
	
		searchForNameStart(startingValue, resl);
		return resl;
	}
	
	
	
	@Override
	public void searchForNameExpression(String regex, List<FileDescriptor> results) {
		if(regex == null || results == null) {
			throw new NullPointerException();
		}
		
		for(FileDescriptor fd: fileList) {
			
			if(fd.getName().matches(regex)) {
				results.add(fd);
			}
		}
	}

	@Override
	public List<FileDescriptor> searchForNameExpression(String regex) {
		ArrayList<FileDescriptor> resl = new ArrayList<FileDescriptor>();
		
		searchForNameExpression(regex, resl);
		return resl;
	}

	
	
	
	@Override
	public boolean containsFile(FileDescriptor fd) {
		return fileList.contains(fd);
	}
	
	
	
	public static class FileDescriptorImpl implements FileDescriptor {

		private final String name;
		private final IStructuredLocation location;
		
		
		public FileDescriptorImpl(String name, IStructuredLocation location) {
			this.name = name;
			this.location = location;
		}
		
		
		@Override
		public String getName() {
			return name;
		}
		
		@Override
		public IStructuredLocation getLocation() {
			return location;
		}
		
	}


}
